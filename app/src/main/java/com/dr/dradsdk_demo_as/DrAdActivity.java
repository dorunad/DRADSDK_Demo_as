package com.dr.dradsdk_demo_as;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import com.dr.dradsdk.open.AdCallback;
import com.dr.dradsdk.open.AdException;
import com.dr.dradsdk.open.AdSourceData;
import com.dr.dradsdk.open.DRADManager;
import com.dr.dradsdk.open.DRFastenConstant;
import com.dr.dradsdk.open.ImpBean;
import com.dr.dradsdk.open.UploadCallback;
import com.dr.dradsdk_demo_as.base.BaseActivity;
import com.dr.dradsdk_demo_as.databinding.ActivityDradBinding;
import com.dr.dradsdk_demo_as.utils.GlideImageLoader;
import com.dr.dradsdk_demo_as.utils.SimpleUtil;

public class DrAdActivity extends BaseActivity<ActivityDradBinding> implements View.OnClickListener {
    /**
     * 广告类型
     */
    public static final int SPLASH_AD = 0x001;
    public static final int BANNER_AD = 0x002;
    public static final int INFO_AD = 0x003;
    public static final int BAIDU_AD = 0x004;

    public static final String TYPE = "type";

    private int type = SPLASH_AD;

    private ImpBean mImpBean = new ImpBean();

    private AdSourceData mAdSourceData;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_drad;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        type = extras.getInt(TYPE);
    }

    @Override
    protected void initView() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        switch (type) {
            case SPLASH_AD:
                setTitle("开屏广告");
                mImpBean.setImpId("2c09d2e58d4aaa874e2a3d181c8f3f08");
                mImpBean.setAdType(DRFastenConstant.AD.SPLASH);
                mImpBean.setPos(DRFastenConstant.POS.FULLSCREEN);
                mImpBean.setWidth(640);
                mImpBean.setHeight(960);
                break;
            case BANNER_AD:
                setTitle("Banner广告");
                mImpBean.setImpId("be53a65b58fc25cdc4616d74f2da30dd");
                mImpBean.setAdType(DRFastenConstant.AD.BANNER);
                mImpBean.setPos(DRFastenConstant.POS.TOP);
                mImpBean.setWidth(690);
                mImpBean.setHeight(388);
                break;
            case INFO_AD:
                setTitle("信息流广告");
                mImpBean.setImpId("2849273d544d581e8d2153b95307d300");
                mImpBean.setAdType(DRFastenConstant.AD.INFO_STREAM);
                mImpBean.setPos(DRFastenConstant.POS.CENTER);
                mImpBean.setWidth(690);
                mImpBean.setHeight(388);
                break;
            case BAIDU_AD:
                setTitle("百度广告");
                mImpBean.setImpId("3469e7937b459717d0e003a2bd597da5");
                mImpBean.setAdType(DRFastenConstant.AD.BAIDU);
                mImpBean.setPos(DRFastenConstant.POS.CENTER);
                mImpBean.setWidth(690);
                mImpBean.setHeight(388);
                break;
        }
    }

    @Override
    protected void initData() {
        getAd();
    }

    /**
     * 请求广告
     */
    private void getAd() {
        DRADManager.getInstance().getAd(mImpBean, new AdCallback() {
            @Override
            public void onStart() {
                mViewBinding.pb.setVisibility(View.VISIBLE);
            }

            @Override
            public void onReceivedAd(AdSourceData adSourceData) {
                mAdSourceData = adSourceData;
                showAd();
            }

            @Override
            public void onReceivedError(AdException e) {
                mViewBinding.llNoAd.setVisibility(View.VISIBLE);
                mViewBinding.tvErrorMsg.setText(e.getErrorCode() + "\n" + e.getMsg());
            }

            @Override
            public void onFinish() {
                mViewBinding.pb.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 广告展示
     */
    private void showAd() {
        mViewBinding.llNoAd.setVisibility(View.GONE);
        switch (type) {
            case SPLASH_AD:
                mViewBinding.ivSplash.setVisibility(View.VISIBLE);
                if (mAdSourceData.getCreativeType().equals(DRFastenConstant.TYPE.IMAGE)||mAdSourceData.getCreativeType().equals(DRFastenConstant.TYPE.MIX_2)) {
                    if (mAdSourceData.getImageList() != null && mAdSourceData.getImageList().size() > 0) {
                        GlideImageLoader.displayImageByUrlWithoutPlace(mViewBinding.ivSplash, mAdSourceData.getImageList().get(0).getSrc());
                    }
                }else if (mAdSourceData.getCreativeType().equals(DRFastenConstant.TYPE.MIX_1)){
                    GlideImageLoader.displayImageByUrlWithoutPlace(mViewBinding.ivSplash, mAdSourceData.getIconSrc());
                }else {
                    mViewBinding.llNoAd.setVisibility(View.VISIBLE);
                    mViewBinding.tvErrorMsg.setText("No Ad");
                }
                mViewBinding.tvAdDesc.setVisibility(View.VISIBLE);
                mViewBinding.llAppDescBar.setVisibility(View.VISIBLE);
                break;
            case BANNER_AD:
                mViewBinding.ivBanner.setVisibility(View.VISIBLE);
                if (mAdSourceData.getCreativeType().equals(DRFastenConstant.TYPE.IMAGE)||mAdSourceData.getCreativeType().equals(DRFastenConstant.TYPE.MIX_2)) {
                    if (mAdSourceData.getImageList() != null && mAdSourceData.getImageList().size() > 0) {
                        GlideImageLoader.displayImageByUrlWithoutPlace(mViewBinding.ivBanner, mAdSourceData.getImageList().get(0).getSrc());
                    }
                }else if (mAdSourceData.getCreativeType().equals(DRFastenConstant.TYPE.MIX_1)){
                    GlideImageLoader.displayImageByUrlWithoutPlace(mViewBinding.ivBanner, mAdSourceData.getIconSrc());
                }else {
                    mViewBinding.llNoAd.setVisibility(View.VISIBLE);
                    mViewBinding.tvErrorMsg.setText("No Ad");
                }
                mViewBinding.tvAdDesc.setVisibility(View.VISIBLE);
                break;
            case INFO_AD:
            case BAIDU_AD:
                mViewBinding.llInfo.setVisibility(View.VISIBLE);
                if (mAdSourceData.getCreativeType().equals(DRFastenConstant.TYPE.IMAGE)||mAdSourceData.getCreativeType().equals(DRFastenConstant.TYPE.MIX_2)) {
                    if (mAdSourceData.getImageList() != null && mAdSourceData.getImageList().size() > 0) {
                        GlideImageLoader.displayImageByUrlWithoutPlace(mViewBinding.ivInfo, mAdSourceData.getImageList().get(0).getSrc());
                    }
                }else if (mAdSourceData.getCreativeType().equals(DRFastenConstant.TYPE.MIX_1)){
                    GlideImageLoader.displayImageByUrlWithoutPlace(mViewBinding.ivInfo, mAdSourceData.getIconSrc());
                }
                if (!TextUtils.isEmpty(mAdSourceData.getDescription())) {
                    mViewBinding.tvDesc.setVisibility(View.VISIBLE);
                    mViewBinding.tvDesc.setText(mAdSourceData.getDescription());
                }

                if (!TextUtils.isEmpty(mAdSourceData.getAppName())) {
                    mViewBinding.tvAppName.setText(mAdSourceData.getAppName());
                }

                if (!TextUtils.isEmpty(mAdSourceData.getButtonText())) {
                    mViewBinding.tvButtonText.setText(mAdSourceData.getButtonText());
                }

                if (!TextUtils.isEmpty(mAdSourceData.getAppName()) || !TextUtils.isEmpty(mAdSourceData.getButtonText())) {
                    mViewBinding.llInfoBottom.setVisibility(View.VISIBLE);
                }
                break;
        }
        DRADManager.getInstance().showUpload(mAdSourceData, new UploadCallback() {
            @Override
            public void onReceivedSuccess() {

            }

            @Override
            public void onReceivedError(AdException e) {

            }
        });
    }


    @Override
    protected void initListener() {
        mViewBinding.btnRetry.setOnClickListener(this);

        mViewBinding.ivSplash.setOnClickListener(this);
        mViewBinding.ivBanner.setOnClickListener(this);
        mViewBinding.llInfo.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_retry:
                getAd();
                break;
            case R.id.iv_splash:
            case R.id.iv_banner:
            case R.id.ll_info:
                adClick();
                break;
        }
    }

    /**
     * 广告点击
     */
    private void adClick() {
        Bundle bundle = new Bundle();
        switch (mAdSourceData.getInteractionType()) {
            case DRFastenConstant.ACTION.VIEW://打开网页
                Uri uri = Uri.parse(mAdSourceData.getTargetUrl()); // url为你要链接的地址
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case DRFastenConstant.ACTION.IN://应用内打开
                bundle.putString("title", mAdSourceData.getDescription());
                bundle.putString("url", mAdSourceData.getTargetUrl());
                startActivity(WebActivity.class, bundle);
                break;
            case DRFastenConstant.ACTION.OPEN_MARKET://Android应用市场下载
                SimpleUtil.launchAppDetail(this, mAdSourceData.getAppPackage());
                break;
            case DRFastenConstant.ACTION.DOWNLOAD://下载
                DRADManager.getInstance().requestDownload(this, mAdSourceData);
                break;
            case DRFastenConstant.ACTION.HTML://html
                bundle.putString("title", mAdSourceData.getDescription());
                bundle.putString("param", mAdSourceData.getTargetUrl());
                startActivity(WebActivity.class, bundle);
                break;
            case DRFastenConstant.ACTION.APP_AWAKE://app唤醒
                Uri uri1 = Uri.parse(mAdSourceData.getDeeplink_url()); // url为你要链接的地址
                Intent intent1 = new Intent(Intent.ACTION_VIEW, uri1);
                startActivity(intent1);
                break;
        }
        //点击上报
        DRADManager.getInstance().clickUpload(mAdSourceData, new UploadCallback() {
            @Override
            public void onReceivedSuccess() {

            }

            @Override
            public void onReceivedError(AdException e) {

            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        DRADManager.getInstance().onActivityResult(requestCode, resultCode, data);
    }
}
