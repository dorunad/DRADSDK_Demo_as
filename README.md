<h3>东润广告SDK Android</h3>

[![release-image]][release-url]
[![minSdkVersion-image]][minSdkVersion-url]


## 简介
**版本历史**

| 版本         | 日期          | 备注  |
| ------------ |:------------:| ------ |
| v1.0.1        | 2019/4/3     |  初版  |

东润移动广告SDk(Android)是官方推出的移动广告SDK在Android平台上的版本（以下简称SDK）。

SDK的发行版本包括 AAR 包、Demo 示例、接入文档。

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
以AndroidStudio为例。将广告的aar包复制到您⼯程的 libs ⽬录下，在对应的 build.gradle ⽂件⾥⾯添加如下配置：
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
点击 `Sync Now` 

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
    android:authorities="${applicationId}.fileprovider"
    android:exported="false"
    android:grantUriPermissions="true">
    <meta-data
        android:name="android.support.FILE_PROVIDER_PATHS"
        android:resource="@xml/dr_file_paths" />
</provider>
```
+ 注意：`${applicationId}`需要改为您apk运行时的包名。
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
推荐在Application初始化时。

### 开屏广告
开屏广告在您的应用启动时使用，嵌入在您的应用启动⻚Activity中，此类广告推荐展现开始5s后关闭。
```java
public class DrAdActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ImpBean mImpBean = new ImpBean();
        //广告位id 接入前请联系合作方获取
        mImpBean.setImpId("2c09d2e58d4aaa874e2a3d181c8f3f08");
        //开屏广告类型
        mImpBean.setAdType(DRFastenConstant.AD_SPLASH);
        //广告展示方式
        mImpBean.setPos(DRFastenConstant.POS_FULLSCREEN);
        //广告尺寸 接入前请联系合作方获取
        mImpBean.setWidth(640);
        mImpBean.setHeight(960);
        
        DRADManager.getInstance().getAd(mImpBean, new AdCallback() {
            @Override
            public void onStart() {
                //开始请求开屏广告
            }
    
            @Override
            public void onReceivedAd(AdSourceData adSourceData) {
                //开屏广告获取成功
            }
    
            @Override
            public void onReceivedError(AdException e) {
                //获取开屏广告失败
                Log.e("onReceivedError",e.getErrorCode() + "\n" + e.getMsg());
            }

            @Override
            public void onFinish() {
                //开屏广告请求结束
            }
        });
    }
}
```


[release-image]:https://img.shields.io/badge/release-v1.0.1-brightgreen.svg
[release-url]:https://github.com/dorunad/DRADSDK_Demo_as/tree/master/app/libs

[minSdkVersion-image]:https://img.shields.io/badge/minSdkVersion-14-yellowgreen.svg
[minSdkVersion-url]:https://android-arsenal.com/api?level=14


