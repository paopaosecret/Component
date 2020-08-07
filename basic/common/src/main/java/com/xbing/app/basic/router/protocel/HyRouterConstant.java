package com.xbing.app.basic.router.protocel;

public class HyRouterConstant {

    /**
     * 协议例子
     * merchant://native/page/shangjiatong/setting?id=2
     */


    /**
     * Uri scheme： merchant -》 现只支持的scheme
     */
    public static final String HYROUTER_SCHEME = "merchant";

    /**
     * Uri host: Flutter-> 代表目标页flutter
     */
    public static final String HYROUTER_HOST_FLUTTER = "flutter";

    /**
     * Uri host:  web -> 代表目标页为web
     */
    public static final String  HYROUTER_HOST_WEB = "web";

    /**
     * Uri host： native -> 代表目标页为native
     */
    public static final String  HYROUTER_HOST_NATIVE  = "native";

    /**
     * Uri frist Path-> page:代表页面跳转
     */
    public static final String HYROUTER_NATIVE_TYPE_PAGE = "page";

    /**
     * Uri frist path -> function:代表方法调用
     */
    public static final String HYROUTER_NATIVE_TYPE_FUNCTION = "function";

    /**
     * Uri second path -> shangjiatong:代表业务方法
     */
    public static final String HYROUTER_NATIVE_BUSINESS_SHANGJIATONG = "shangjiatong";
}
