package com.dr.dradsdk_demo_as.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

/**
 * author：Christina
 * time：2019/3/14
 * e-mail：luohongand@qq.com
 * desc：
 */
public class SimpleUtil {
    /**
     * 打开应用市场
     *
     * @param context
     * @param appPkg    app包名
     * @param marketPkg 应用市场包名 制定应用市场
     */
    public static void launchAppDetail(Context context, String appPkg, String marketPkg) {

        try {
            if (TextUtils.isEmpty(appPkg)) return;
            Uri uri = Uri.parse("market://details?id=" + appPkg);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            //如果设置了market包名 打开指定app市场
            if (!TextUtils.isEmpty(marketPkg)) intent.setPackage(marketPkg);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开应用市场
     * 不指定让用户选择
     *
     * @param context
     * @param appPkg
     */
    public static void launchAppDetail(Context context, String appPkg) {
        launchAppDetail(context, appPkg, null);
    }
}
