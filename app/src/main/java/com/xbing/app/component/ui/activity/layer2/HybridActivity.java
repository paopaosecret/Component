package com.xbing.app.component.ui.activity.layer2;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebBackForwardList;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.api.InjectHelper;
import com.example.lib_annotation.BindView;
import com.xbing.app.component.R;
import com.xbing.app.component.ui.activity.BaseActivity;
import com.xbing.app.net.common.cache.memcache.WebResourceCacheManager;

public class HybridActivity extends BaseActivity {

    @BindView(R.id.webview_root)
    public WebView webView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("WebviewTest","创建页面容器：" + System.currentTimeMillis());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hybrid);
        InjectHelper.inject(this);
        
        initWebView();

    }

    private void initWebView() {

        /**
         * 配置WebView的一些属性
         */
        initWebSetting();

        /**
         * 设置webView的委托客户端
         */
        webView.setWebViewClient(new MyWebViewClient());


        /**
         * 将商学院首页当做测试页面加载
         */
        webView.loadUrl("https://hyapp.58.com/app/school/open/articles/tohome");
    }

    private void initWebSetting() {

        /**
         * 设置WebView加载页面文本内容的编码，默认“UTF-8”
         */
        webView.getSettings().setDefaultTextEncodingName("UTF-8"); // 设置默认的显示编码

        /**
         * 启用HTML5 DOM storage API, 默认是false
         */
        webView.getSettings().setDomStorageEnabled(true);

        /**
         * 设置支持js
         */
        webView.getSettings().setJavaScriptEnabled(true);

        /**
         * 设置不使用缓存
         */
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        /**
         * 设置WebView 是否自动加载图片资源，默认是true:自动加载图片
         */
        webView.getSettings().setLoadsImagesAutomatically(true);

        /**
         * 是否可访问本地文件，默认是true
         */
        webView.getSettings().setAllowFileAccess(true);

        /**
         * 设置是否允许通过file urls加载的js读取本地文件，默认false;
         */
        webView.getSettings().setAllowFileAccessFromFileURLs(true);

        /**
         * 设置是否允许通过file urls加载的js读取全部资源，默认是false
         */
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);

        /**
         * Android5.0上 WebView中Http和Https混合问题
         * MIXED_CONTENT_ALWAYS_ALLOW：允许从任何来源加载内容，即使起源是不安全的；
         * MIXED_CONTENT_NEVER_ALLOW：不允许Https加载Http的内容，即不允许从安全的起源去加载一个不安全的资源；
         * MIXED_CONTENT_COMPATIBILITY_MODE：当涉及到混合式内容时，WebView 会尝试去兼容最新Web浏览器的风格。
         **/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        /**
         * 设置是否可用Javascript(window.open)打开窗口
         */
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
    }


    class MyWebViewClient extends WebViewClient {

        /**
         * 拦截页面加载，返回true表示宿主app拦截并处理了该url，否则返回false由当前WebView处理
         * 此方法在API24被废弃，不处理POST请求
         * @param view
         * @param url
         * @return
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.e("WebviewTest","开始访问网页：" + System.currentTimeMillis());
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.e("WebviewTest","访问网页结束：" + System.currentTimeMillis());
        }

        /**
         * 发生资源加载，拦截顺序
         *
         * 此方法添加于API21，调用于非UI线程，拦截资源请求并返回数据，返回null时WebView将继续加载资源
         * @param view
         * @param request
         * @return
         */
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Nullable
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            Log.d("WebviewTest","拦截WebResourceRequest url：" + request.getUrl().toString());
            return interceptRequest(view, request.getUrl().toString());
        }

        /**
         * https://pic1.58cdn.com.cn/nowater/sjtnw/n_v2d2dd3ffb95d84cc8ae2dad24e8bd4a5b.jpg
         * https://pic2.58cdn.com.cn/nowater/sjtnw/n_v2d2dd3ffb95d84cc8ae2dad24e8bd4a5b.jpg
         * https://pic3.58cdn.com.cn/nowater/sjtnw/n_v2d2dd3ffb95d84cc8ae2dad24e8bd4a5b.jpg
         * 此方法废弃于API21，调用于非UI线程拦截资源请求并返回响应数据，返回null时WebView将继续加载资源。
         * 注意：API21以下的AJAX请求会走onLoadResource，无法通过此方法拦截
         * @param view
         * @param url
         * @return
         */
        @Nullable
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            Log.d("WebviewTest","拦截 url：" + url);
            return interceptRequest(webView, url);
        }


        /**
         * 将要加载资源(url):
         * API21以下的AJAX请求会走onLoadResource，通过此方法拦截
         * @param view
         * @param url
         */
        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        private WebResourceResponse interceptRequest(WebView view, String url){
            if (!TextUtils.isEmpty(url) && WebResourceCacheManager.getInstance().isMatchUrl(url)) {
                if (WebResourceCacheManager.getInstance().isCached(url)) {
                    String resourcePath = WebResourceCacheManager.getInstance().getResourcePath(url);
                    Log.i("WebviewTest", System.currentTimeMillis() + " cache getUrl:" + url);
                    return WebResourceCacheManager.getInstance().getWebResource(resourcePath, url);
                } else {
                    if (WebResourceCacheManager.getInstance().isNeedCache(url)) {
                        Log.i("WebviewTest", "download url:" + url);
                        WebResourceCacheManager.getInstance().downloadResourceToLocal(url);
                    }
                }
            } else {
                Log.i("WebviewTest", "unmatch url:" + url);
            }
            return super.shouldInterceptRequest(view, url);
        }


    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
