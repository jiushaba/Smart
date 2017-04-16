package com.zdlog.smartbutler.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * 项目名：SmartButler
 * 包名 ： com.zdlog.smartbutler.ui
 * 文件名：BaseActivity
 * 创建者：jiushaba
 * 创建时间：2017/4/10 0010下午 12:41
 * 描述： Activity基类
 */

/**
 * 主要作用：
 *  1、统一的属性
 *  2、统一的接口
 *  3、统一的方法
 */
public class BaseActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //显示返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }

    //菜单栏操作


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
