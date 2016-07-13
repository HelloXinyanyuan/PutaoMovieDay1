package com.whunf.putaomovieday1.common.core;

import android.os.Environment;

import com.umeng.analytics.MobclickAgent;
import com.whunf.putaomovieday1.common.util.LogUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2016/7/13.
 * 线程（挂掉）异常实现类
 */
public class MyCrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "MyCrashHandler";
    private final Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;

    public MyCrashHandler() {
        defaultUncaughtExceptionHandler = Thread.currentThread().getDefaultUncaughtExceptionHandler();
        Thread.currentThread().setUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
//                StringWriter w = new StringWriter();
//        PrintWriter pw = new PrintWriter(w);
//        ex.printStackTrace(pw);
//        LogUtil.e(TAG, w.toString());
        FileWriter fw = null;
        try {
            File file=new File(Environment.getExternalStorageDirectory()+"/PMovie/crash",System.currentTimeMillis()+".txt");
            if (!file.getParentFile().exists()){
                LogUtil.e(TAG, "uncaughtException() called with: " + "thread = [" + thread + "], ex = [" + ex + "]"+"file:"+file);
                file.getParentFile().mkdirs();
            }
            fw = new FileWriter(file);
            PrintWriter pw = new PrintWriter(fw);
            ex.printStackTrace(pw);
            fw.close();//记住字符流一定要close
            LogUtil.e(TAG, "uncaughtException() write to sdcard success"+file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //上传Umeng友盟的错误日志
        MobclickAgent.reportError(PMApplication.getInstance(),ex);
        //交给默认的Handler
        defaultUncaughtExceptionHandler.uncaughtException(thread,ex);
    }
}
