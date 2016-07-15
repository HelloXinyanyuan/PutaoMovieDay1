package com.whunf.putaomovieday1.common.version;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;

import com.whunf.putaomovieday1.R;
import com.whunf.putaomovieday1.common.core.UrlConfig;
import com.whunf.putaomovieday1.common.util.LogUtil;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/7/15.
 */
public class VersionMgr implements Runnable {
    private static final String TAG = "VersionMgr";
    private static VersionMgr ourInstance = new VersionMgr();
    private Activity mActivity;

    public static VersionMgr getInstance() {
        return ourInstance;
    }

    private VersionMgr() {
    }

    public void autoCheckVersion(Activity context) {
        this.mActivity = context;
        new Thread(this).start();
    }


    @Override
    public void run() {
        String reqPath = UrlConfig.UserPath.LOGIN;
        String reqContent = new QueryVersionReq().getJsonString();

        try {
            URL url = new URL(reqPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");//想往body写数据需设置post方式请求
            //添加请求头
            connection.addRequestProperty("Cookie", "app_id=10;channel=wandoujia;version=2.6.0;dev_no=208bb3c7cc0da3223edbb99b40eb46a6;band=DOOV_D910T;city=%E6%B7%B1%E5%9C%B3;loc=30.2784662,120.1194347;pt_token=23bc5ad532694652b504eee425c4904f");
            connection.addRequestProperty("Content-Type", "application/json; charset=utf-8");

            //获得向服务器写数据的输出流
            OutputStream outputStream = connection.getOutputStream();
            //向请求的body里写json内容
            outputStream.write(reqContent.getBytes("UTF-8"));
            //拿到服务器的返回
            InputStream inputStream = connection.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int hasRead = -1;
            while ((hasRead = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, hasRead);
            }
            String jsonStr = baos.toString();
            LogUtil.i(TAG, jsonStr);

            final QueryVersionResp qvr = com.alibaba.fastjson.JSONObject.parseObject(jsonStr, QueryVersionResp.class);

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    new AlertDialog.Builder(mActivity).setTitle("升级提示").setMessage(qvr.remark).setNegativeButton("不升级", null).setPositiveButton("升级", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            goDownloadApk1(qvr.down_url);
//                            goDownloadApk2(qvr.down_url);
                            goDownloadApk3(qvr.down_url);
                        }
                    }).create().show();
                }
            });
            LogUtil.i(TAG, qvr.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 利用浏览器的方式下载apk
     *
     * @param down_url
     */
    private void goDownloadApk1(String down_url) {
        if (mActivity != null && !mActivity.isFinishing()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(down_url));
            mActivity.startActivity(intent);
        }
    }


    private BroadcastReceiver mBr;

    /**
     * 通过DownloadManager下载apk
     *
     * @param down_url
     */
    private void goDownloadApk2(String down_url) {

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
        intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        mBr = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {//下载完毕
                    long dId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                    LogUtil.i(TAG, "dId:" + dId);
                    if (mDownloadId == dId) {
                        Uri apkUri = dm.getUriForDownloadedFile(mDownloadId);
                        installApk(apkUri);
                    }
                } else if (DownloadManager.ACTION_NOTIFICATION_CLICKED.equals(intent.getAction())) {//点击某下载资源时触发

                }
            }
        };
        mActivity.registerReceiver(mBr, intentFilter);

        dm = (DownloadManager) mActivity.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request requst = new DownloadManager.Request(Uri.parse(down_url));
//        requst.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        requst.setTitle("这是标题");
        requst.setDescription("这是描述");
        requst.setVisibleInDownloadsUi(true);//在系统“下载”app是否可见
        requst.setMimeType("application/vnd.android.package-archive");
        requst.allowScanningByMediaScanner();
        requst.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);//在通知栏显示下载进度UI
        requst.setDestinationUri(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/PMovie", "MyMovie.apk")));
        mDownloadId = dm.enqueue(requst);//加入下载队列
        LogUtil.i(TAG, "mDownloadId:" + mDownloadId);
    }

    private void installApk(Uri apkUri) {
        Intent installIntent = new Intent(Intent.ACTION_VIEW);
        installIntent.setDataAndType(apkUri,
                "application/vnd.android.package-archive");
        installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//保证安装成功新起一个任务
        mActivity.startActivity(installIntent);//直接跳到安装页面，但是还要点击按钮确定安装，还是取消安装
    }

    ProgressBar pb;
    AsyncTask<String, Integer, Uri> mTask;

    private void goDownloadApk3(String down_url) {
        View v = View.inflate(mActivity, R.layout.layout_download_progress, null);
        pb = (ProgressBar) v.findViewById(R.id.progress_bar);
        new AlertDialog.Builder(mActivity).setPositiveButton("取消下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mTask.cancel(true);
            }
        }).setView(v).create().show();

        mTask = new AsyncTask<String, Integer, Uri>() {
            @Override
            protected Uri doInBackground(String... params) {//子线程
                File downFile = null;
                try {
                    URL url = new URL((String) params[0]);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");//想往body写数据需设置post方式请求
                    connection.setConnectTimeout(5 * 1000);
                    connection.connect();
                    //拿到服务器的返回
                    int totalLength = connection.getContentLength();//获得apk总长度
                    InputStream inputStream = connection.getInputStream();
                    int hasDownLen = 0;//存储已经下载的字节数量
                    downFile = new File(Environment.getExternalStorageDirectory() + "/PMovie", "MyMovie_3.apk");
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(downFile));
                    byte[] buffer = new byte[1024];
                    int hasRead = -1;
                    while (!isCancelled()&&(hasRead = inputStream.read(buffer)) != -1) {
                        bos.write(buffer, 0, hasRead);
                        hasDownLen += hasRead;//累加
                        int prog = hasDownLen * 100 / totalLength;//求得进度
                        publishProgress(prog);
                    }
                    bos.flush();//缓冲流要flush
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return downFile==null?null:Uri.fromFile(downFile);
            }

            @Override
            protected void onProgressUpdate(Integer... values) {//主线程
                pb.setProgress(values[0]);//主线程和子线程中调用都可以
                super.onProgressUpdate(values);
            }

            @Override
            protected void onPostExecute(Uri uri) {
                //去安装
                installApk(uri);
            }
        };
        mTask.execute(down_url);
    }


    private DownloadManager dm;
    private long mDownloadId;

    public void destroy() {
        mDownloadId = -1;
        if (mBr != null && mActivity != null) {//解除广播
            mActivity.unregisterReceiver(mBr);
        }
    }


}
