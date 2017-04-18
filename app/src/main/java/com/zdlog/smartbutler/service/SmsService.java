package com.zdlog.smartbutler.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsMessage;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.zdlog.smartbutler.R;
import com.zdlog.smartbutler.utils.L;
import com.zdlog.smartbutler.utils.ShareUtils;
import com.zdlog.smartbutler.utils.StaticClass;
import com.zdlog.smartbutler.view.DispatchLinearLayout;

/**
 * 项目名：SmartButle
 * 包名 ： com.zdlog.smartbutler.service
 * 文件名：SmsService
 * 创建者：jiushaba
 * 创建时间：2017/4/16 0016下午 4:58
 * 描述： 短信监听服务
 */
public class SmsService extends Service implements View.OnClickListener{

    private SmsReceiver smsReceiver;
    //TTS
    private SpeechSynthesizer mTts;
    //发件人号码
    private String smsPhone;
    //短信内容
    private String smsContent;
    //窗口管理
    private WindowManager wm;
    //布局参数
    private WindowManager.LayoutParams layoutParams;
    //VIEW
    private DispatchLinearLayout mView;
    private TextView tv_phone;
    private TextView tv_content;
    private Button btn_send_sms;

    private HomeWatchReceiver mHomeWatchReceiver;
    public static final String SYSTEM_DIALOGS_RESON_KEY = "reason";
    public static final String SYSTEM_DIALOGS_HOME_KEY = "homekey";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }
    //初始化
    private void init() {

        //1.创建SpeechSynthesizer对象, 第二个参数：本地合成时传InitListener
        mTts = SpeechSynthesizer.createSynthesizer(getApplicationContext(), null);
        //2.合成参数设置，详见《科大讯飞MSC API手册(Android)》SpeechSynthesizer 类
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
        //设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”
        //保存在SD卡需要在AndroidManifest.xml添加写SD卡权限
        //如果不需要保存合成音频，注释该行代码
        //mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");

        L.i("init service");
        //动态注册
        smsReceiver = new SmsReceiver();
        IntentFilter intent = new IntentFilter();
        intent.addAction(StaticClass.SMS_ACTION);
        //设置权限
        intent.setPriority(Integer.MAX_VALUE);
        //注册
        registerReceiver(smsReceiver, intent);

        mHomeWatchReceiver=new HomeWatchReceiver();
        IntentFilter intentFiler = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        registerReceiver(mHomeWatchReceiver, intentFiler);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.i("stop service");
        //注销
        unregisterReceiver(smsReceiver);

        unregisterReceiver(mHomeWatchReceiver);

    }



    //短信广播
    public class SmsReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (StaticClass.SMS_ACTION.equals(action)) {
                L.i("来短信了");
                //获取短信内容返回的是一个object数组
                Object[] objs = (Object[]) intent.getExtras().get("pdus");
                //遍历数组得到相关数据
                for (Object obj : objs) {
                    //巴蜀组元素转化成短信对象
                    SmsMessage sms = SmsMessage.createFromPdu((byte[]) obj);
                    //发件人
                    smsPhone = sms.getOriginatingAddress();
                    //内容
                    smsContent = sms.getMessageBody();
                    L.i("短信内容"+smsPhone+":"+smsContent);
                    showWindow();
                }
                boolean isSpeak = ShareUtils.getBoolen(getApplication(), "isSpeak", false);
                if (isSpeak){
                    startSpeak("来自"+smsPhone+"短信，短信内容为："+smsContent);
                }
            }
        }
    }
    //窗口提示
    private void showWindow() {
        //获取系统服务
        wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        //获取布局参数
        layoutParams = new WindowManager.LayoutParams();
        //定义宽高
        layoutParams.width=WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        //定义标记
        layoutParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        //定义格式
        layoutParams.format= PixelFormat.TRANSLUCENT;
        //定义类型
        layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        //加载布局
        mView = (DispatchLinearLayout) View.inflate(getApplicationContext(), R.layout.sms_item, null);
        tv_phone = (TextView) mView.findViewById(R.id.tv_phone);
        tv_content = (TextView) mView.findViewById(R.id.tv_content);
        btn_send_sms = (Button) mView.findViewById(R.id.btn_send_sms);
        btn_send_sms.setOnClickListener(this);
        tv_phone.setText("发件人："+smsPhone);
        tv_content.setText(smsContent);
        //添加view到窗口
        wm.addView(mView, layoutParams);

        mView.setDispatchKeyEventListener(mDispatchKeyEventListener);
    }

    private DispatchLinearLayout.DispatchKeyEventListener mDispatchKeyEventListener = new DispatchLinearLayout.DispatchKeyEventListener() {
        @Override
        public boolean dispatchKeyEvent(KeyEvent event) {
            //判断是否是按返回键
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                L.i("我按了BACK键");
                if (mView.getParent() != null) {
                    wm.removeView(mView);
                }else {
                    return true;
                }
            }
            return false;
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send_sms:
                sendSms();
                //消失窗口
                if (mView.getParent() != null) {
                    wm.removeView(mView);
                }
                break;
        }
    }
    //回复短信
    private void sendSms() {
        Uri uri = Uri.parse("smsto：" + smsPhone);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        //设置启动模式
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("sms_body", "");
        startActivity(intent);
    }


    //开始说话
    private void startSpeak(String text) {
        //3.开始合成
        mTts.startSpeaking(text, mSynListener);
    }

    //合成监听器
    private SynthesizerListener mSynListener = new SynthesizerListener() {
        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError error) {
        }

        //缓冲进度回调
        //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
        }

        //开始播放
        public void onSpeakBegin() {
        }

        //暂停播放
        public void onSpeakPaused() {
        }

        //播放进度回调
        //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
        }

        //恢复播放回调接口
        public void onSpeakResumed() {
        }

        //会话事件回调接口
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
        }
    };

    //监听home键广播
    class HomeWatchReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason =intent.getStringExtra(SYSTEM_DIALOGS_RESON_KEY);
                if (SYSTEM_DIALOGS_HOME_KEY.equals(reason)) {
                    L.i("点击了home键");
                    if (mView.getParent()!=null){
                        wm.removeView(mView);
                    }
                }
            }
        }
    }

}
