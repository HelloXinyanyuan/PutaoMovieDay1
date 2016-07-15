# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\Tools\AndroidStudioSdk\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-ignorewarnings
#---------------百度地图开始------------------
-keep class com.baidu.** { *; }
-keep class vi.com.gdi.bgl.android.**{*;}
#---------------百度地图结束------------------
#---------------极光开始------------------
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
#==================gson==========================
-dontwarn com.google.**
-keep class com.google.gson.** {*;}
#==================protobuf======================
-dontwarn com.google.**
-keep class com.google.protobuf.** {*;}
#---------------极光结束------------------

#---------------友盟开始------------------
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keep public class com.whunf.putaomovieday1.R$*{
public static final int *;
}
#---------------友盟结束------------------

#---------------ShareSdk开始------------------
-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class **.R$* {*;}
-keep class **.R{*;}
-dontwarn cn.sharesdk.**
-dontwarn **.R$*
#---------------ShareSdk结束------------------
#---------------支付宝开始----------------------
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
#---------------支付宝结束----------------------
#--------使用fastjson时必须加上以下代码------------
-keepattributes Signature
-keep class com.android.volley.**{ *;}
-keep class com.alibaba.fastjson.**{ *;}
#---------------android支持包不混淆----------------------
-keep class android.support.**{ *;}
#---------------event bus混淆配置开始----------------------
-keep class de.greenrobot.event.** {*;}
-keepclassmembers class ** {
    public void onEvent*(**);
    void onEvent*(**);
}
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
#---------------event bus混淆配置结束----------------------
-keep public class com.whunf.putaomovieday1.module.movie.resp.** {
     void set*(***);
     *** get*();
}

#内容配置
-keep public class * extends BaseReqJson {
     void set*(***);
     *** get*();
}
