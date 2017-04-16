package com.zdlog.smartbutler.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 项目名：SmartButler
 * 包名 ： com.zdlog.smartbutler.utils
 * 文件名：UtilTools
 * 创建者：jiushaba
 * 创建时间：2017/4/10 0010下午 12:54
 * 描述： 工具的统一类
 */
public class UtilTools {
    //设置字体
    public static void setFont(Context mContent, TextView textView){
        Typeface fontType=Typeface.createFromAsset(mContent.getAssets(),"fonts/FONT.TTF");
        textView.setTypeface(fontType);
    }

    //获取网络图片
    public static Bitmap getPicture(String path){
        Bitmap bm=null;
        try{
            URL url=new URL(path);
            URLConnection connection=url.openConnection();
            connection.connect();
            InputStream inputStream=connection.getInputStream();
            bm= BitmapFactory.decodeStream(inputStream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  bm;
    }
}
