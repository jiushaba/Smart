package com.zdlog.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.zdlog.smartbutler.R;
import com.zdlog.smartbutler.entity.GirlData;
import com.zdlog.smartbutler.utils.PicassoUtils;

import java.util.List;

/**
 * 项目名：SmartButler
 * 包名 ： com.zdlog.smartbutler.adapter
 * 文件名：GirlAdapter
 * 创建者：jiushaba
 * 创建时间：2017/4/14 0014下午 3:32
 * 描述： 妹子适配器
 */
public class GirlAdapter extends BaseAdapter{
    private Context mContent;
    private List<GirlData> mList;
    private LayoutInflater inflater;
    private GirlData data;
    private WindowManager wm;
    //屏幕宽
    private int width;
    public  GirlAdapter(Context mContent,List<GirlData> mList){
        this.mContent=mContent;
        this.mList=mList;
        //获取系统服务
        inflater = (LayoutInflater) mContent.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        wm = (WindowManager) mContent.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (viewHolder==null){
            viewHolder=new ViewHolder();
            convertView = inflater.inflate(R.layout.girl_item, null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageview);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        data = mList.get(position);
        //解析图片
        String url=data.getImgUrl();
        PicassoUtils.loadImageViewSize(mContent,url,width/2,250,viewHolder.imageView);
        return convertView;
    }


    class ViewHolder{
        private ImageView imageView;
    }
}
