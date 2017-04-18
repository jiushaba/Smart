package com.zdlog.smartbutler.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zdlog.smartbutler.R;
import com.zdlog.smartbutler.utils.UtilTools;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名：SmartButle
 * 包名 ： com.zdlog.smartbutler.ui
 * 文件名：AboutActivity
 * 创建者：jiushaba
 * 创建时间：2017/4/18 0018上午 11:41
 * 描述： 关于软件
 */
public class AboutActivity extends BaseActivity{
    private ListView mListView;
    private List<String> mList = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        //去除阴影
        getSupportActionBar().setElevation(0);

        initView();

    }
    //初始化view
    private void initView() {
        mListView = (ListView) findViewById(R.id.mListView);
        mList.add("应用名：" + getString(R.string.app_name));
        mList.add("版本号：" + UtilTools.getVersion(this));
        mList.add("官网：www.zdlog.com");
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mList);
        //设置适配器
        mListView.setAdapter(mAdapter);
    }

}
