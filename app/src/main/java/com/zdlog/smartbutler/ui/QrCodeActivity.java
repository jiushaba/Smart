package com.zdlog.smartbutler.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.xys.libzxing.zxing.encoding.EncodingUtils;
import com.zdlog.smartbutler.R;

/**
 * 项目名：SmartButle
 * 包名 ： com.zdlog.smartbutler.ui
 * 文件名：QrCodeActivity
 * 创建者：jiushaba
 * 创建时间：2017/4/17 0017下午 9:21
 * 描述： 生成二维码
 */
public class QrCodeActivity extends BaseActivity{
    //我的二维码
    private ImageView iv_qr_code;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        initView();
    }

    private void initView() {
        iv_qr_code = (ImageView) findViewById(R.id.iv_qr_code);
        int width=getResources().getDisplayMetrics().widthPixels;
        //根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（350*350）
        Bitmap qrCodeBitmap = EncodingUtils.createQRCode("我是只能管家", width/2, width/2,
                        BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        iv_qr_code.setImageBitmap(qrCodeBitmap);

    }
}
