package com.xbing.app.component.utils.webview;

public interface IWebviewProxy {

    /**
     * 在加载页面之前提前缓存页面数据
     * @param url
     */
    void load(String url);
}
