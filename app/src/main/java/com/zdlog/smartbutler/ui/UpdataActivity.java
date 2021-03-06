package com.zdlog.smartbutler.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.ProgressListener;
import com.kymjs.rxvolley.http.VolleyError;
import com.kymjs.rxvolley.toolbox.FileUtils;
import com.zdlog.smartbutler.R;
import com.zdlog.smartbutler.utils.L;

import java.io.File;

/**
 * 项目名：SmartButle
 * 包名 ： com.zdlog.smartbutler.ui
 * 文件名：UpdataActivity
 * 创建者：jiushaba
 * 创建时间：2017/4/17 0017下午 4:57
 * 描述： 下载
 */
public class UpdataActivity extends BaseActivity {

    //正在下载
    public static final int HANDLER_LODING=10001;
    //下载完成
    public static final int HANDLER_OK=10002;
    //下载失败
    public static final int HANDLER_ON=10003;

    private TextView tv_size;
    private String url;
    private String path;

    //进度tiao
    private NumberProgressBar number_progress_bar;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_LODING :
                    //时时更新进度
                    Bundle bundle = msg.getData();
                    long transferredBytes=bundle.getLong("transferredBytes");
                    long totalSize=bundle.getLong("totalSize");
                    tv_size.setText(transferredBytes+"/"+totalSize);
                    //设置进度
                    number_progress_bar.setProgress((int) (((float)transferredBytes /(float)totalSize) * 100));
                    break;
                case HANDLER_OK :
                    tv_size.setText("下载成功");
                    //启动这个应用安装
                    startInstallApk();
                    break;
                case HANDLER_ON :
                    tv_size.setText("下载失败");
                    break;
            }
        }
    };

    //启动安装
    private void startInstallApk() {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_VIEW);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
        startActivity(i);
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata);
        initView();
    }

    private void initView() {

        tv_size = (TextView) findViewById(R.id.tv_size);
        number_progress_bar = (NumberProgressBar) findViewById(R.id.number_progress_bar);
        number_progress_bar.setMax(100);
        path= FileUtils.getSDCardPath()+"/"+System.currentTimeMillis()+".apk";

        //下载
        url = getIntent().getStringExtra("url");
        if (url != null) {
            //下载
            RxVolley.download(path, url, new ProgressListener() {
                @Override
                public void onProgress(long transferredBytes, long totalSize) {
                    L.i("transferredBytes:" + transferredBytes + "totalSize:" + totalSize);
                    Message msg=new Message();
                    msg.what=HANDLER_LODING;
                    Bundle bundle=new Bundle();
                    bundle.putLong("transferredBytes", transferredBytes);
                    bundle.putLong("totalSize", totalSize);
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
            }, new HttpCallback() {
                @Override
                public void onSuccess(String t) {
                    L.e("成功");
                    handler.sendEmptyMessage(HANDLER_OK);

                }

                @Override
                public void onFailure(VolleyError error) {
                    L.e("失败");
                    handler.sendEmptyMessage(HANDLER_ON);
                }
            });
        }
    }
}
