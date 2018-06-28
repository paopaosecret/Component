package com.xbing.app.component.ui.activity.layer2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.xbing.app.component.R;
import com.xbing.app.component.ui.activity.BaseActivity;


/**
 * Created by zhaobing04 on 2018/3/17.
 */

public class JavaJsInteractiveActivity extends BaseActivity {

    private static final String TAG = JavaJsInteractiveActivity.class.getSimpleName();

    private WebView wvContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_js_interactive);
        initWebView();
        findViewById(R.id.btn_java_use_js).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String iccid = "java hello word";
                String showICCID = "javascript:setICCID('"+iccid+"')";
                wvContainer.loadUrl(showICCID);
            }
        });

    }

    @SuppressLint("JavascriptInterface")
    private void initWebView() {
        wvContainer = (WebView) findViewById(R.id.wv_root);
        WebSettings webSettings = wvContainer.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //设置WebView加载页面文本内容的编码，默认“UTF-8”。
        webSettings.setDefaultTextEncodingName("UTF-8"); // 设置默认的显示编码
        //设置是否开启DOM存储API权限，默认false，未开启，设置为true，WebView能够使用DOM storage API
        webSettings.setDomStorageEnabled(true);
        //设置Application缓存API是否开启，默认false，设置有效的缓存路径参考setAppCachePath(String path)方法
        webSettings.setAppCacheEnabled(false);
        webSettings.setBlockNetworkImage(false);
        //设置WebView是否加载图片资源，默认true，自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
        //设置WebView商家通useragent
        webSettings.setUserAgentString(webSettings.getUserAgentString()+"58shangjiatong");
        //设置WebView是否使用viewport，当该属性被设置为false时，加载页面的宽度总是适应WebView控件宽度；
        // 当被设置为true，当前页面包含viewport属性标签，在标签中指定宽度值生效，
        // 如果页面不包含viewport标签，无法提供一个宽度值，这个时候该方法将被使用。
        webSettings.setUseWideViewPort(false);
//        webSettings.setAllowFileAccess(false);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.setAllowFileAccessFromFileURLs(false);
            webSettings.setAllowUniversalAccessFromFileURLs(false);
        }
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
//         设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        wvContainer.loadUrl("https://p.m.58.com/hy/hyindex/?cityid=1");   //file:///android_asset/test.html

        // 添加一个对象, 让JS可以访问该对象的方法, 该对象中可以调用JS中的方法
        wvContainer.addJavascriptInterface(this, "jswritecard");
        //相当于添加一个js回调接口，然后给这个起一个别名，
//我这里起的名字jswritecard（js写卡）。
        wvContainer.setWebViewClient(new BaseWebClient());
        wvContainer.setWebChromeClient(new WebChromeBaseClient());

    }

    private boolean isClearCache;

    private class BaseWebClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @SuppressLint("UseCheckPermission")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d(TAG,"shouldOverrideUrlLoading url:" + url);
            if (url.startsWith("tel:")) {
                checkPermission(Uri.parse(url));
            } else if (url.startsWith("wtai:")) {
                checkPermission(Uri.parse(url.replace("wtai://wp/mc;", "tel:")));
            }
            wvContainer.loadUrl(url);
            return false;
        }

        // 不在 onPageStart() 中去设置是因为设置完以后又loadUrl(url),之前设定的值就无效了
        // 当然,在 onPageFinished() 设置的话也得H5中在document.ready()之后才能去获取
        // 或者也可以考虑在 WebChromeClient 的 onProgressChanged() 方法中作设定
        @Override
        public void onPageFinished(WebView view, final String url) {
            Log.d(TAG,"onPageFinished url:" + url);
            sendParamToJs(view);
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed(); // Ignore SSL certificate errors
        }
    }

    private void sendParamToJs(WebView webview) {


            String params = "{}";
            webview.loadUrl("javascript:shangjiatongApp.init('" + params + "')");

    }

    protected boolean overrideUrlLoading(WebView view, String url) {
        return BaseProtocolOverrideUrlLoading(view, url);
    }

    protected boolean BaseProtocolOverrideUrlLoading(WebView view, String url) {

        return true;
    }



    //@android.webkit.JavascriptInterface为了解
    //决addJavascriptInterface漏洞的，在4.2以后才有的。
    @android.webkit.JavascriptInterface
    public void getICCID(){
        //js 调用Java 方法  无参
        Log.e(TAG, "js 调用 Java 的 getICCID！！");
        //js若想更改Activity 需要使其运行在主线程
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                getCardICCID();
            }
        });

    }

    private void getCardICCID() {
        Toast.makeText(this,"js 调用 java method getCarddCCID()", Toast.LENGTH_SHORT).show();
        Log.e(TAG,"js 调用 java method getCarddCCID()");
    }

    @android.webkit.JavascriptInterface
    public void get2F99(){
        Log.e(TAG, "js 调用 Java de get2F99！！");
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                getCard2F99();
            }
        });
    }

    private void getCard2F99() {
        Toast.makeText(this,"js 调用 java method getCard2F99()", Toast.LENGTH_SHORT).show();
        Log.e(TAG,"js 调用 java method getCard2F99()");
    }

    @android.webkit.JavascriptInterface
    public void writeCard(final String indata){
        //js调用Java 有参
        Log.e(TAG, "js 调用 Java de writeCard ,indata : "+indata);
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                writeJavaCrad(indata);
            }
        });
    }

    private void writeJavaCrad(String indata) {
        Toast.makeText(this,"js 调用 java method writeJavaCrad()", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "js 调用 Java method writeJavaCrad() ,param : "+indata);
    }

    private void checkPermission(Uri uri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean findMethod = true;
            try {
                ContextCompat.class.getMethod("checkSelfPermission", Context.class, String.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                findMethod = false;
            }
            if (findMethod && ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE_CALL_PHONE);
            } else {
                startActivity(new Intent(Intent.ACTION_DIAL, uri));
            }
        } else {
            startActivity(new Intent(Intent.ACTION_DIAL, uri));
        }
    }

    public final static String URL = "url";
    private static final int REQUEST_CODE_CALL_PHONE = 6;

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length == 0) {
            return;
        }
        String url = getIntent().getStringExtra(URL);
        if (requestCode == REQUEST_CODE_CALL_PHONE) {
            if (PackageManager.PERMISSION_GRANTED == grantResults[0]) {
                try {
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(url.replace("wtai://wp/mc;", "tel:"))));
                } catch (SecurityException e) {
                }
            } else {
            }
        }
    }

    private class WebChromeBaseClient extends WebChromeClient {

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);

        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {

            super.onProgressChanged(view, newProgress);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {

            AlertDialog.Builder builder = new AlertDialog.Builder(JavaJsInteractiveActivity.this);
            builder.setMessage(message);
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    result.confirm();
                }
            });
            builder.setCancelable(false);
            builder.create();
            builder.show();
            return true;
        }

        // For 3.0+ Devices (Start)
        // onActivityResult attached before constructor
        protected void openFileChooser(ValueCallback uploadMsg, String acceptType) {
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("*/*");
            startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
        }

        // For Lollipop 5.0+ Devices
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            if (uploadMessage != null) {
                uploadMessage.onReceiveValue(null);
                uploadMessage = null;
            }
            uploadMessage = filePathCallback;
            Intent intent = fileChooserParams.createIntent();
            try
            {
                startActivityForResult(intent, REQUEST_SELECT_FILE);
            } catch (ActivityNotFoundException e)
            {
                uploadMessage = null;
                Toast.makeText(getBaseContext(), "Cannot Open File Chooser", Toast.LENGTH_LONG).show();
                return false;
            }
            return true;
        }

        //For Android 4.1 only
        protected void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            mUploadMessage = uploadMsg;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            startActivityForResult(Intent.createChooser(intent, "File Browser"), FILECHOOSER_RESULTCODE);
        }

        protected void openFileChooser(ValueCallback<Uri> uploadMsg) {
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("*/*");
            startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
        }
    }
    /** 支持M页添加图片 */
    private android.webkit.ValueCallback<Uri> mUploadMessage;

    private final static int FILECHOOSER_RESULTCODE = 2;

    public ValueCallback<Uri[]> uploadMessage;
    public static final int REQUEST_SELECT_FILE = 100;

}

