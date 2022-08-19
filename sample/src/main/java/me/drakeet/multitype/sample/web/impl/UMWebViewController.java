package me.drakeet.multitype.sample.web.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class UMWebViewController {
    protected static final String TAG = UMWebViewController.class.getSimpleName();

    protected Context mContext;
    protected WebView mWebView;
    protected WebSettings mSettings;
    protected UMWebViewClient mWebViewClient;
    protected UMWebChromeClient mWebChromeClient;

    public UMWebViewController(WebView webview) {
        mWebView = webview;
        mContext = mWebView.getContext();
        mSettings = mWebView.getSettings();
    }

    /**
     * 初始化WebView
     */
    @SuppressLint("SetJavaScriptEnabled")
    public void setupWebView() {
        mSettings.setSaveFormData(false);
        mSettings.setAllowFileAccess(true);
        mSettings.setDatabaseEnabled(true);
        mSettings.setJavaScriptEnabled(true);
        mSettings.setUseWideViewPort(true);
        mSettings.setAppCacheEnabled(true);
        mSettings.setDomStorageEnabled(true);
        //允许跨域
        mSettings.setAllowUniversalAccessFromFileURLs(true);
        mSettings.setDisplayZoomControls(false);
        mSettings.setLoadWithOverviewMode(true);
        mSettings.setPluginState(WebSettings.PluginState.ON);
        // WebView自适应屏幕大小
        mSettings.setDefaultTextEncodingName("UTF-8");
        mSettings.setLoadsImagesAutomatically(true);
        // 设置可以支持缩放
        mSettings.setSupportZoom(true);
        // 设置默认缩放方式尺寸是far
        mSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        // 设置出现缩放工具
        mSettings.setBuiltInZoomControls(true);
        // 设置WebView的缓存模式
        mSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 不然5.0以后http和https混合的页面会加载不出来
            mSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    /**
     * 设置ua，需要注意的是我已经把原来的ua追加了，调用这个方法，只需要添加你想要添加的字符串即可
     *
     * @param userAgent ua
     */
    public void setUserAgent(String userAgent) {
        String origin = mSettings.getUserAgentString();
        mSettings.setUserAgentString(origin + " " + userAgent);
        // KLog.i(TAG, "UA------------------>" + mSettings.getUserAgentString());
    }

    /**
     * 设置webViewClient
     *
     * @param webViewClient webViewClient必须继承{@link UMWebViewClient}
     */
    public void setWebViewClient(UMWebViewClient webViewClient) {
        if (webViewClient != null) {
            mWebViewClient = webViewClient;
            mWebView.setWebViewClient(webViewClient);
        }
    }

    /**
     * 设置webChromeClient
     *
     * @param webChromeClient webChromeClient必须继承{@link UMWebChromeClient}
     */
    public void setWebChromeClient(UMWebChromeClient webChromeClient) {
        if (webChromeClient != null) {
            mWebChromeClient = webChromeClient;
            mWebView.setWebChromeClient(webChromeClient);
        }
    }

    /**
     * 添加js调用java的接口
     *
     * @param object
     * @param name
     */
    @SuppressWarnings("All")
    public void addJavascriptInterface(Object object, String name) {
        mWebView.addJavascriptInterface(object, name);
    }

    /**
     * 移除java和js的调用
     */
    public void removeJavascriptInterface(String name) {
        mWebView.removeJavascriptInterface(name);
    }

}
