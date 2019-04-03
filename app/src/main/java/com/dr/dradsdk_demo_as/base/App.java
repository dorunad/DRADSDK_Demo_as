package com.dr.dradsdk_demo_as.base;

import android.app.Application;

import com.dr.dradsdk.open.DRADManager;

/**
 * author：Christina
 * time：2019/3/26
 * e-mail：luohongand@qq.com
 * desc：
 */
public class App extends Application {
    protected static App instance;

    static {
        instance = null;
    }


    public static App me() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        initDrAdSDK();
    }

    /**
     * 东润广告初始化
     */
    private void initDrAdSDK() {
        DRADManager.getInstance().init(this);
    }
}
