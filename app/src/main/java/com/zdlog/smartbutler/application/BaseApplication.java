package com.zdlog.smartbutler.application;

import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.tencent.bugly.crashreport.CrashReport;
import com.zdlog.smartbutler.utils.StaticClass;

import cn.bmob.v3.Bmob;

/**
 * Created by Administrator on 2017/4/10 0010.
 */

public class BaseApplication extends Application{
    //创建
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化bugly
        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APP_ID, true);
        //初始化Bmob
        Bmob.initialize(this,StaticClass.BMOB_APP_ID);
//        // 将“12345678”替换成您申请的APPID，申请地址：http://open.voicecloud.cn
//        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID + "="+StaticClass.VOICE_KEY);
        // 将“12345678”替换成您申请的APPID，申请地址：http://open.voicecloud.cn
        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID +"=58f1adca");
    }
}
