package com.zdlog.smartbutler.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.zdlog.smartbutler.R;
import com.zdlog.smartbutler.adapter.GirlAdapter;
import com.zdlog.smartbutler.entity.GirlData;
import com.zdlog.smartbutler.utils.L;
import com.zdlog.smartbutler.utils.PicassoUtils;
import com.zdlog.smartbutler.view.CustomDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * A simple {@link Fragment} subclass.
 */
public class GirlFragment extends Fragment {
    //列表
    private GridView mGirlView;
    //数据
    private List<GirlData> mList = new ArrayList<>();
    //适配器
    private GirlAdapter mAdapter;

    //提示框
    private CustomDialog dialog;

    //预览图片
    private ImageView iv_img;

    //图片地址数据
    private List<String> mListUrl = new ArrayList<>();
    //photoView
    private PhotoViewAttacher mAttacher;




    /**
     * 1、监听点击事件
     * 2、提示框
     * 3、加载图片
     * 4、PhotoView
     *
     *
     */


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_girl,null);
        findView(view);
        return view;
    }

    private void findView(View view)  {
        mGirlView = (GridView) view.findViewById(R.id.mGirlView);
        //字符串转码
        String welfare=null;
        try {
            welfare= URLEncoder.encode(getString(R.string.text_welfare),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //初始化提示框
        dialog = new CustomDialog(getActivity(), LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, R.layout.dialog_girl, R.style.Theme_dialog, Gravity.CENTER,R.style.pop_anim_style);
        iv_img = (ImageView) dialog.findViewById(R.id.iv_img);

        //解析
        RxVolley.get("http://gank.io/api/search/query/listview/category/"+welfare+"/count/50/page/1", new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                L.i("json"+t);
                parsingJson(t);
            }
        });
        //监听点击事件
        mGirlView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //解析图片
                PicassoUtils.loadImageView(getActivity(), mListUrl.get(position), iv_img);
                //缩放
                mAttacher = new PhotoViewAttacher(iv_img);
                //刷新
                mAttacher.update();
                dialog.show();
            }
        });

    }
    //解析Json
    private void parsingJson(String t){

        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i=0;i<jsonArray.length();i++){
                JSONObject json = (JSONObject) jsonArray.get(i);
                String url = json.getString("url");
                mListUrl.add(url);
                GirlData data = new GirlData();
                data.setImgUrl(url);
                mList.add(data);
            }
            mAdapter = new GirlAdapter(getActivity(), mList);
            mGirlView.setAdapter(mAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
