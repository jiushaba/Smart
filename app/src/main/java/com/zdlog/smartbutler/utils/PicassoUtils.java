package com.zdlog.smartbutler.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * 项目名：SmartButler
 * 包名 ： com.zdlog.smartbutler.utils
 * 文件名：PicassoUtils
 * 创建者：jiushaba
 * 创建时间：2017/4/14 0014下午 2:33
 * 描述： Picasso封装
 */
public class PicassoUtils {
    //默认加载图片
    public static void loadImageView(Context mContent, String url, ImageView imageView){
        Picasso.with(mContent).load(url).into(imageView);
    }
    //默认加载指定大小图片
    public static void loadImageViewSize(Context mContent, String url,int width,int height, ImageView imageView){
        Picasso.with(mContent).load(url).resize(width,height).centerCrop().into(imageView);
    }
    //加载图片有默认图片
    public static void loadImageViewHolder(Context mContent, String url,int loadimg,int errorimg, ImageView imageView){
        Picasso.with(mContent).load(url).placeholder(loadimg).error(errorimg).into(imageView);
    }

    //裁剪图片
    public static void loadImageViewCrop(Context mContent, String url, ImageView imageView){
        Picasso.with(mContent).load(url).transform(new CropSquareTransformation()).into(imageView);
    }

    //按比例裁剪矩形
    public static class CropSquareTransformation implements Transformation{

        @Override
        public Bitmap transform(Bitmap source) {
            int size=Math.min(source.getWidth(),source.getHeight());
            int x=(source.getWidth()-size)/2;
            int y=(source.getHeight()-size)/2;
            Bitmap result=Bitmap.createBitmap(source,x,y,size,size);
            if (result!=source){
                source.recycle();
            }
            return result;
        }

        @Override
        public String key() {
            return "fate";
        }
    }
}
