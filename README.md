<h1 align="center" style="border-bottom:none;">东润广告SDK Android</h1>

[![](https://img.shields.io/badge/release-v1.0.1-brightgreen.svg)](https://github.com/dorunad/DRADSDK_Demo_as/tree/master/app/libs)

## 简介
**版本历史**

| 版本          | 日期          | 备注  |
| ------------  |:----------------:| ------------|
| v1.0.1        | 2019/4/3  |  初版  |

东润移动广告SDk(Android)是官方推出的移动广告SDK在Android平台上的版本（以下简称SDK）。

SDK的发行版本包括 AAR 包、Demo 示例、接入文档。

SDK接入前，请先联系合作方获取需要接入的

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
### 1.联系合作方获取


