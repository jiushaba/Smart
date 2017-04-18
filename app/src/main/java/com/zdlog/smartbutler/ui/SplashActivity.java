package com.zdlog.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.zdlog.smartbutler.R;
import com.zdlog.smartbutler.utils.ShareUtils;
import com.zdlog.smartbutler.utils.StaticClass;
import com.zdlog.smartbutler.utils.UtilTools;

/**
 * 项目名：SmartButler
 * 包名 ： com.zdlog.smartbutler.ui
 * 文件名：SplashActivity
 * 创建者：jiushaba
 * 创建时间：2017/4/10 0010下午 2:39
 * 描述： 闪屏页
 */
public class SplashActivity extends AppCompatActivity{
    /**
     * 1、延时2000ms
     * 2、判断程序是否第一次运行
     * 3、自定义字体
     * 4、Activity全屏主题
     * @param savedInstanceState
     */
    private TextView tv_splash;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case StaticClass.HANDLER_SPLASH:
                    //判断程序是否第一次运行

                    if (isFirst()){
                        startActivity(new Intent(SplashActivity.this,GuideTwoActivity.class));
                    }else {
                        startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                    }
                    finish();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
    }
    //初始化View
    private void initView() {
        //延时2000ms
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH,2000);
        tv_splash= (TextView) findViewById(R.id.tv_splash);
        //设置字体
        UtilTools.setFont(this,tv_splash);
    }
    //判断程序是否第一次运行
    private boolean isFirst() {
        boolean isFirst=ShareUtils.getBoolen(this,StaticClass.SHARE_IS_FIRST,true);
        if (isFirst){
            ShareUtils.putBoolen(this,StaticClass.SHARE_IS_FIRST,false);
            //是第一次运行
            return true;
        }else {
            return false;
        }
    }
    //禁止返回键
    @Override
    public void onBackPressed() {
       // super.onBackPressed();
    }
}
