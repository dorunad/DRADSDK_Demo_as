package com.dr.dradsdk_demo_as;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.dr.dradsdk.open.DRADManager;
import com.dr.dradsdk_demo_as.base.BaseActivity;
import com.dr.dradsdk_demo_as.databinding.ActivityMainBinding;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements View.OnClickListener {
    private Bundle bundle = new Bundle();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mViewBinding.tvSplash.setOnClickListener(this);
        mViewBinding.tvBanner.setOnClickListener(this);
        mViewBinding.tvInfo.setOnClickListener(this);
        mViewBinding.tvBaidu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_splash:
                bundle.putInt(DrAdActivity.TYPE, DrAdActivity.SPLASH_AD);
                break;
            case R.id.tv_banner:
                bundle.putInt(DrAdActivity.TYPE, DrAdActivity.BANNER_AD);
                break;
            case R.id.tv_info:
                bundle.putInt(DrAdActivity.TYPE, DrAdActivity.INFO_AD);
                break;
            case R.id.tv_baidu:
                bundle.putInt(DrAdActivity.TYPE, DrAdActivity.BAIDU_AD);
                break;
        }
        //请求必须的权限
        checkAndRequestPermission();
    }

    /**
     * Android6.0以上的权限适配简单示例：
     * <p>
     * Demo代码里是一个基本的权限申请示例，请开发者根据自己的场景合理地编写这部分代码来实现权限申请。
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void checkAndRequestPermission() {
        List<String> lackedPermission = new ArrayList<String>();
        if (!(checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE))) {
            lackedPermission.add(Manifest.permission.READ_PHONE_STATE);
        }

//        if (!(checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
//            lackedPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        }
        if (!(checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION))) {
            lackedPermission.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (!(checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION))) {
            lackedPermission.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        if (lackedPermission.size() == 0) {
            // 权限都已经有了，那么直接调用SDK
            startActivity(DrAdActivity.class, bundle);
        } else {
            // 请求所缺少的权限，在onRequestPermissionsResult中再看是否获得权限，如果获得权限就可以调用SDK，否则不要调用SDK。
            String[] requestPermissions = new String[lackedPermission.size()];
            lackedPermission.toArray(requestPermissions);
            requestPermissions(requestPermissions, 1000);
        }
    }

    public static boolean checkSelfPermission(Context context, String permission) {
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                Method method = Context.class.getMethod("checkSelfPermission",
                        String.class);
                return (Integer) method.invoke(context, permission) == PackageManager.PERMISSION_GRANTED;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean hasAllPermissionsGranted(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000 && hasAllPermissionsGranted(grantResults)) {
            startActivity(DrAdActivity.class, bundle);
        } else {
            // 如果用户没有授权，那么应该说明意图，引导用户去设置里面授权。
            Toast.makeText(this, "应用缺少必要的权限！请点击\"权限\"，打开所需要的权限。", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DRADManager.getInstance().exit();
    }
}
