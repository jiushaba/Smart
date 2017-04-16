package com.zdlog.smartbutler.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 项目名：SmartButler
 * 包名 ： com.zdlog.smartbutler.utils
 * 文件名：ShareUtils
 * 创建者：jiushaba
 * 创建时间：2017/4/10 0010下午 2:20
 * 描述： SharedPreferences封装
 */
public class ShareUtils {
    public static final String NAME="config";

    //键  值
    public static void putString(Context mContext,String key,String value){
        SharedPreferences sp=mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().putString(key,value).commit();
    }
    //键   默认值
    public static String getString(Context mContext,String key,String defValue){
        SharedPreferences sp=mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return sp.getString(key,defValue);
    }
    public static void putInt(Context mContext,String key,int value){
        SharedPreferences sp=mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().putInt(key,value).commit();
    }
    //键   默认值
    public static int getInt(Context mContext,String key,int defValue){
        SharedPreferences sp=mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return sp.getInt(key,defValue);
    }
    public static void putBoolen(Context mContext,String key,boolean value){
        SharedPreferences sp=mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }
    //键   默认值
    public static boolean getBoolen(Context mContext,String key,boolean defValue){
        SharedPreferences sp=mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return sp.getBoolean(key,defValue);
    }
    //删除单个
    public static void deleShare(Context mContent,String key){
        SharedPreferences sp=mContent.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }
    //删除全部
    public static void deleAll(Context mContent){
        SharedPreferences sp=mContent.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }
}
