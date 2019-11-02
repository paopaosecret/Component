package com.xbing.app.net.common.cache.memcache;

import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebResourceResponse;


import com.xbing.app.net.common.utils.MD5Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 网络缓存资源管理类
 */
public class WebResourceCacheManager {

    private String TAG = "WebResourceCacheManager";

    /**
     * 已缓存列表
     */
    private Map<String, String> cachedList = new ConcurrentHashMap<>();

    /**
     * 正在缓存列表
     */
    private Map<String, String> cachingList = new ConcurrentHashMap<>();

    /**
     * 缓存根路径
     */
    private String cacheParentDir = Environment.getExternalStorageDirectory() + "/component/";

    /**
     * 缓存资源路径
     */
    private String cacheResourceDir = cacheParentDir + "resource/";

    /**
     * 缓存文件名称
     */
    private String cachedText = ".cachedText.txt";

    private static final WebResourceCacheManager INSTANCE = new WebResourceCacheManager();

    public static WebResourceCacheManager getInstance() {
        return INSTANCE;
    }

    private WebResourceCacheManager() {
    }

    public void init() {
        FileReader fr = null;
        BufferedReader bf = null;
        try {
            File cacheDir = new File(cacheParentDir);
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }

            File cacheResource = new File(cacheResourceDir);
            if (!cacheResource.exists()) {
                cacheResource.mkdirs();
            }

            File cacheTest = new File(cacheParentDir, cachedText);
            if (!cacheTest.exists()) {
                cacheTest.createNewFile();
                // 清除缓存文件
                delFile(cacheResource);
            } else {
                File file = new File(cacheParentDir, cachedText);
                fr = new FileReader(file);
                bf = new BufferedReader(fr);
                String str;
                // 按行读取字符串
                while ((str = bf.readLine()) != null) {
                    if (!TextUtils.isEmpty(str)) {
                        String[] pairs = str.split(",");
                        if (pairs != null
                                && pairs.length > 1
                                && !TextUtils.isEmpty(pairs[0])
                                && !TextUtils.isEmpty(pairs[1])) {
                            cachedList.put(pairs[0].trim(), pairs[1].trim());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (Exception e) {
            }
            try {
                if (bf != null) {
                    bf.close();
                }
            } catch (Exception e) {
            }
        }
    }

    public boolean isCached(String url) {
        String cacheUri = getCacheUri(url);
        return cachedList.containsKey(cacheUri);
    }

    public String getResourcePath(String url) {
        String cacheUri = getCacheUri(url);
        return cachedList.get(cacheUri);
    }

    public boolean isNeedCache(String url) {
        String cacheUri = getCacheUri(url);
        return !cachedList.containsKey(cacheUri) && !cachingList.containsKey(cacheUri);
    }

    public String getCacheUri(String url){
        url = url.trim().toLowerCase();
        int i = url.indexOf("58cdn");
        if(i > 0 && (url.endsWith("png") || url.indexOf("png") > 0)||
                (url.endsWith("jpg") || url.indexOf("jpg") > 0)){
            return url.substring(url.indexOf("58cdn.com.cn") + 13);
        }
        return url;
    }

    public Boolean isMatchUrl(String url) {
        url = url.trim().toLowerCase();
        int i = url.indexOf("58cdn");
        return i > 0 && ((url.endsWith("css") && url.matches("\"\\\\_v(\\\\d+)\\\\.[a-zA-Z]+$\"")) ||
                (url.endsWith(".js") && url.matches("\"\\\\_v(\\\\d+)\\\\.[a-zA-Z]+$\"")) ||
                (url.endsWith("png") || url.indexOf("png") > 0) ||
                (url.endsWith("jpg") || url.indexOf("jpg") > 0));
    }

    public WebResourceResponse getWebResource(String path, String url) {
        try {
            File file = new File(path);
            if (file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(file);
                String mime = getMime(url);
                return new WebResourceResponse(mime, "utf-8", fileInputStream);
            } else {
                downloadResourceToLocal(url);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 把js,css,png,jpg 保存在本地
     */
    public void downloadResourceToLocal(final String uri) {

        ThreadPoolManager.newInstance().addExecuteTask(new Runnable() {
            @Override
            public void run() {
                String cacheUri = getCacheUri(uri);
                FileOutputStream output = null;
                InputStream uristream = null;
                try {
                    String fileName = MD5Utils.encode(cacheUri);
                    Log.i("WebviewTest",  cacheUri + " 开始下载:" + cacheResourceDir + fileName);
                    File newFile = new File(cacheResourceDir, fileName);
                    if(!newFile.exists()){
                        newFile.createNewFile();
                    }
                    cachingList.put(cacheUri, cacheResourceDir + fileName);

                    URL uri = new URL(cacheUri);
                    URLConnection connection = uri.openConnection();
                    uristream = connection.getInputStream();
                    output = new FileOutputStream(newFile);

                    int readLen;
                    byte[] buffer = new byte[1024];
                    while ((readLen = uristream.read(buffer)) > 0) {
                        output.write(buffer, 0, readLen);
                        output.flush();
                    }

                    synchronized (WebResourceCacheManager.class) {
                        WriteStringToFile(cacheUri, cacheResourceDir + fileName);
                        cachingList.remove(cacheUri);
                        cachedList.put(cacheUri, cacheResourceDir + fileName);
                    }

                    Log.i("WebviewTest", "url下载完成：" + cacheUri);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (output != null) {
                            output.close();
                        }
                        if (uristream != null) {
                            uristream.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }


    private String getMime(String url) {
        String mime = "text/html";
        Uri currentUri = Uri.parse(url);
        String path = currentUri.getPath();
        if (path.endsWith(".css")) {
            mime = "text/css";
        } else if (path.endsWith(".js")) {
            mime = "application/x-javascript";
        } else if (path.endsWith(".jpg") || path.endsWith(".gif") ||
                path.endsWith(".png") || path.endsWith(".jpeg") ||
                path.endsWith(".webp") || path.endsWith(".bmp")) {
            mime = "image/*";
        }
        return mime;
    }


    private void WriteStringToFile(String name, String path) {
        FileWriter fw = null;
        PrintWriter pw = null;
        try {
            File file = new File(cacheParentDir, cachedText);
            if(!file.exists()){
                file.createNewFile();
            }
            fw = new FileWriter(file, true);
            if (file.exists()) {
                pw = new PrintWriter(fw);
                pw.println(name + "," + path);
                pw.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (Exception e) {
                fw = null;
            }
            try {
                if (pw != null) {
                    pw.close();
                }
            } catch (Exception e) {
                pw = null;
            }
        }
    }


    static boolean delFile(File file) {
        if (!file.exists()) {
            return false;
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                delFile(f);
            }
        }
        return file.delete();
    }

    public void clearCache() {
        File cacheResource = new File(cacheParentDir);
        if (cacheResource.exists()) {
            delFile(cacheResource);
        }
    }


}