package com.dr.dradsdk_demo_as;

import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dr.dradsdk_demo_as.base.BaseActivity;
import com.dr.dradsdk_demo_as.databinding.ActivityWebBinding;

public class WebActivity extends BaseActivity<ActivityWebBinding> {
    private String title = "";
    private String url = "";
    private String param = "";


    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        title = extras.getString("title");
        url = extras.getString("url");
        param = extras.getString("param");
    }

    @Override
    protected void initView() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initData() {

        setTitle(title);
        if (!TextUtils.isEmpty(url)) {
            if (url.startsWith("http://") || url.startsWith("https://")) {
                if (!TextUtils.isEmpty(param)) {
                    mViewBinding.webView.postUrl(url, param.getBytes());
                } else {
                    mViewBinding.webView.loadUrl(url);
                }
            }
        } else if (!TextUtils.isEmpty(param)) {
            //直接加载html
            mViewBinding.webView.loadData(param, "text/html", "utf-8");
        }

        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        mViewBinding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    if (url.endsWith(".apk")) {
                        Uri uri = Uri.parse(url); // url为你要链接的地址
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    } else {
                        if (!TextUtils.isEmpty(param)) {
                            view.loadUrl(url + "?" + param);
                        } else {
                            view.loadUrl(url);
                        }
                    }
                }
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                if (errorCode != SslError.SSL_INVALID) {
//                  // 校验过程遇到了bug
                }
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                super.onReceivedSslError(view, handler, error);
//                if(error.getPrimaryError() == android.net.http.SslError.SSL_INVALID ){// 校验过程遇到了bug
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mViewBinding.webView.getSettings().setMixedContentMode(
                            WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
                }//打开https
                handler.proceed();
//                }else{
//                    handler.cancel();
//                }
            }
        });

        /**
         * 设置h5回调
         */

        WebSettings settings = mViewBinding.webView.getSettings();
        //优先使用缓存
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //响应JS
        settings.setJavaScriptEnabled(true);
        //允许js弹窗
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        //无痕浏览
        settings.setDatabaseEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        //不随系统字体变化
        settings.setTextZoom(100);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        settings.setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        settings.setAppCachePath(appCachePath);
        settings.setAllowFileAccess(true);

        settings.setAllowContentAccess(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            settings.setAllowFileAccessFromFileURLs(false);
            settings.setAllowUniversalAccessFromFileURLs(false);
        }

        mViewBinding.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    // 网页加载完成
                    mViewBinding.progressBar.setVisibility(View.GONE);
                } else {
                    // 加载中
                    mViewBinding.progressBar.setVisibility(View.VISIBLE);
                    mViewBinding.progressBar.setProgress(newProgress);
                }
            }
        });

    }


    //改写物理按键——返回的逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mViewBinding.webView.canGoBack()) {
                mViewBinding.webView.goBack();//返回上一页面
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onDestroy() {
        //注册协议关闭
        mViewBinding.webView.setVisibility(View.GONE);
        mViewBinding.webView.getSettings().setBuiltInZoomControls(true);
        ((ViewGroup) mViewBinding.webView.getParent()).removeView(mViewBinding.webView);
        mViewBinding.webView.removeAllViews();
        mViewBinding.webView.destroy();

        ViewGroup view = (ViewGroup) getWindow().getDecorView();
        view.removeAllViews();
        mViewBinding.unbind();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
