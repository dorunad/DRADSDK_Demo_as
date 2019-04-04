<h3>东润广告SDK Android</h3>

[![release-image]][release-url]
[![minSdkVersion-image]][minSdkVersion-url]


## 简介
**版本历史**

| 版本         | 日期          | 备注  |
| ------------ |:------------:| ------ |
| v1.0.1        | 2019/4/3     |  初版  |

东润移动广告SDk(Android)是官方推出的移动广告SDK在Android平台上的版本（以下简称SDK）。

SDK的发行版本包括 [AAR 包][release-url]、[Demo 示例][demo-url]、接入文档（您当前阅读的文档）、[javadoc][javadoc-url]、[错误码对照表][errorCode-url]。

:zap:SDK接入前，请先联系合作方获取需要接入的广告位id(impid)。:zap:

## 运行环境

可运行于`Android 4.0(API level 14)`及以上版本。

## 代码混淆
如果您需要使用 proguard 混淆代码，需确保不要混淆 SDK 的代码。
可参考 DRADSDK_Demo_as/app/proguard-rules.pro，混淆文件尾部加入：
```pro
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class *  extends android.content.BroadcastReceiver
-keep class com.dr.dradsdk.open.** {*;}
```
**注意：SDK代码被混淆会导致广告无法获取或其他异常。**

## SDK包导入及权限配置
### 导入aar包
以AndroidStudio为例。将广告的aar包复制到您工程的 libs 目录下，在对应的 build.gradle 文件里面添加如下配置：
```gradle
android {
    repositories {
        flatDir {
            dirs 'libs' //this way we can find the .aar file in libs folder
        }
    }
}

dependencies {
    implementation(name:'dradsdk_v1.0.1',ext:'aar')
}
```
点击 `Sync Now` 等待同步结束。

### 添加权限
在获取广告前，请先判断权限是否获取，Android 6.0以上请先动态请求权限。
```xml
<manifest>
    <uses-permission android:name="android.permission.INTERNET" />
    <!-- 获取网络状态 -->
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
    <!-- 定位 -->
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <!-- 获取手机标识 -->
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <!-- 安装apk包 -->
    <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />
    <uses-permission android:name="android.permission.REPLACE_EXISTING_PACKAGE" />
</manifest>

```
### 配置FileProvider
```xml
<provider
    android:name="android.support.v4.content.FileProvider"
    android:authorities="${packageName}.fileprovider"
    android:exported="false"
    android:grantUriPermissions="true">
    <meta-data
        android:name="android.support.FILE_PROVIDER_PATHS"
        android:resource="@xml/dr_file_paths" />
</provider>
```
+ 注意：`${packageName}`为您apk运行时的包名。
如果 targetSdkVersion 设置值>=24，则强烈建议添加 provider，否则可能会抛异常，甚至会影响 app 变现效率。

## 广告接入
### 初始化
在调用SDK代码前，调用初始化方法。
```java
public class App extends Application {
     @Override
    public void onCreate() {
         //DRADSDK 初始化
         DRADManager.getInstance().init(this);
    }
}
```
推荐在Application初始化时调用。

### 广告请求配置

广告请求通过`com.dr.dradsdk.open.ImpBean`配置请求参数，具体配置说明请查看**DRADSDK_Doc**。

**1.开屏广告**

开屏广告在您的应用启动时使用，嵌入在您的应用启动页Activity中，此类广告推荐全屏展现开始5s后关闭。
```java
public class DrAdActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ImpBean mImpBean = new ImpBean();
        //广告位id 接入前请联系合作方获取
        mImpBean.setImpId("2c09d2e58d4aaa874e2a3d181c8f3f08");
        //开屏广告类型
        mImpBean.setAdType(DRFastenConstant.AD.SPLASH);
        //广告展示方式
        mImpBean.setPos(DRFastenConstant.POS.FULLSCREEN);
        //广告尺寸 接入前请联系合作方获取
        mImpBean.setWidth(640);
        mImpBean.setHeight(960);
    }
}
```
**2.Banner广告**

横幅广告。建议嵌入Banner广告的时候，Banner view的宽高比例需要保持与设置广告位的比例一致。如果宽和高都不指明具体大小，宽的大小为屏幕的宽度，高度根据图片比例自适应。
```java
public class DrAdActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ImpBean mImpBean = new ImpBean();
        //广告位id 接入前请联系合作方获取
        mImpBean.setImpId("be53a65b58fc25cdc4616d74f2da30dd");
        //开屏广告类型
        mImpBean.setAdType(DRFastenConstant.AD.BANNER);
        //广告展示方式
        mImpBean.setPos(DRFastenConstant.POS.TOP);
        //广告尺寸 接入前请联系合作方获取
        mImpBean.setWidth(690);
        mImpBean.setHeight(388);
    }
}
```
**3.信息流广告**

信息流广告推荐采用图文展示。
```java
public class DrAdActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ImpBean mImpBean = new ImpBean();
        //广告位id 接入前请联系合作方获取
        mImpBean.setImpId("2849273d544d581e8d2153b95307d300");
        //开屏广告类型
        mImpBean.setAdType(DRFastenConstant.AD.INFO_STREAM);
        //广告展示方式
        mImpBean.setPos(DRFastenConstant.POS.CENTER);
        //广告尺寸 接入前请联系合作方获取
        mImpBean.setWidth(690);
        mImpBean.setHeight(388);
    }
}
```
### 广告请求
广告请求前，请确保所需权限已授予，可参考DRADSDK_Demo_as下MainActivity的权限请求。
```java
public class DrAdActivity extends Activity{
    /**
    * 广告请求
    * @param mImpBean 
    */
    private void getAd(ImpBean mImpBean){
        DRADManager.getInstance().getAd(mImpBean, new AdCallback() {
            @Override
            public void onStart() {
                //开始请求广告
            }
    
            @Override
            public void onReceivedAd(AdSourceData adSourceData) {
                //广告获取成功 根据creativeType值区分展示。
                //可参考DRADSDK_Demo_as下DrAdActivity的showAd()方法
            }
    
            @Override
            public void onReceivedError(AdException e) {
                //获取广告失败
                Log.e("onReceivedError",e.getErrorCode() + "\n" + e.getMsg());
            }

            @Override
            public void onFinish() {
                //广告请求结束
            }
        });
    }
}
```
`creativeType`相关值：
+ DRFastenConstant.TYPE.NO：无创意类型。
+ DRFastenConstant.TYPE.TEXT：纯文本广告。广告由title、description组成。
+ DRFastenConstant.TYPE.IMAGE：纯图片。广告由imageList组成。
+ DRFastenConstant.TYPE.MIX_1：图文混合1。广告由iconSrc和title、description构成。
+ DRFastenConstant.TYPE.MIX_2：图文混合2。广告由imageList和title、description构成。
+ DRFastenConstant.TYPE.HTML：html广告。此类广告暂时未接入。
+ DRFastenConstant.TYPE.VIDEO：视频广告。此类广告暂时未接入。
+ DRFastenConstant.TYPE.INCENTIVE_VIDEO：激励视频广告。此类广告暂时未接入。

根据`creativeType`对应类型，取`AdSourceData`相关字段展现广告。

`AdSourceData`具体参数说明请查看**DRADSDK_Doc**。

### 展现/曝光上报
广告曝光给用户时，开发者需要实时将本次曝光事件上报给系统，如果没有进行曝光事件上报或曝光上报延迟很多，系统可能会将该曝光产生的点击判为作弊流量。
```java
public class DrAdActivity extends Activity{
    /**
    * 展现/曝光上报
    * 
    * @param adSourceData 广告实体
    */
    private void showUpload(AdSourceData adSourceData){
        DRADManager.getInstance().showUpload(mAdSourceData, new UploadCallback() {
            @Override
            public void onReceivedSuccess() {
                //上报成功
            }

            @Override
            public void onReceivedError(AdException e) {
                //上报失败
                Log.e("onReceivedError",e.getErrorCode() + "\n" + e.getMsg());
            }
        });
    }
}
```

### 点击处理
根据`AdSourceData#interactionType`属性对广告的点击进行处理。`interactionType`的处理值含义请查看**DRADSDK_Doc**。
点击事件处理可参考DRADSDK_Demo_as下DrAdActivity#adClick方法。
```java
public class DrAdActivity extends Activity{
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
                //下载只需调用SDK封装的方法，即可完成下载、安装及相关上报
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
    }
}
```

### 点击上报
广告被用户点击时，开发者需要实时将本次点击事件上报给系统，如果没有进行点击事件上报或点击上报延迟很多，系统可能会将该点击判为作弊流量，这会影响开发者收入。
```java
public class DrAdActivity extends Activity{
    /**
    * 点击上报
    * 
    * @param adSourceData 广告实体
    */
    private void showUpload(AdSourceData adSourceData){
        DRADManager.getInstance().clickUpload(mAdSourceData, new UploadCallback() {
            @Override
            public void onReceivedSuccess() {
                //上报成功
            }

            @Override
            public void onReceivedError(AdException e) {
                //上报失败
                Log.e("onReceivedError",e.getErrorCode() + "\n" + e.getMsg());
            }
        });
    }
}
```
*本文档最终解释权归东润所有。*







[release-image]:https://img.shields.io/badge/release-v1.0.1-brightgreen.svg
[release-url]:https://github.com/dorunad/DRADSDK_Demo_as/tree/master/app/libs

[minSdkVersion-image]:https://img.shields.io/badge/minSdkVersion-14-yellowgreen.svg
[minSdkVersion-url]:https://android-arsenal.com/api?level=14

[demo-url]:https://github.com/dorunad/DRADSDK_Demo_as

[javadoc-url]:https://github.com/dorunad/DRADSDK_Demo_as/tree/master/DRADSDK_Doc

[readme-url]:https://github.com/dorunad/DRADSDK_Demo_as/README.md

[errorCode-url]:https://github.com/dorunad/DRADSDK_Demo_as/blob/master/DRADSDKv1.0.1%E9%94%99%E8%AF%AF%E7%A0%81%E5%AF%B9%E7%85%A7%E8%A1%A8.pdf


