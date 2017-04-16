package com.zdlog.smartbutler.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zdlog.smartbutler.R;
import com.zdlog.smartbutler.entity.WeChatData;
import com.zdlog.smartbutler.utils.PicassoUtils;

import java.util.List;

/**
 * 项目名：SmartButler
 * 包名 ： com.zdlog.smartbutler.adapter
 * 文件名：WeChatAdapter
 * 创建者：jiushaba
 * 创建时间：2017/4/14 0014上午 10:36
 * 描述： 微信精选适配器
 */
public class WeChatAdapter extends BaseAdapter{
    private Context mContent;
    private LayoutInflater inflater;
    private List<WeChatData> mList;
    private WeChatData data;
    public WeChatAdapter(Context mContent,List<WeChatData> mList){
        this.mContent=mContent;
        this.mList=mList;
        //获取系统服务
        inflater = (LayoutInflater) mContent.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=inflater.inflate(R.layout.wechat_item, null);
            viewHolder.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.tv_source = (TextView) convertView.findViewById(R.id.tv_source);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        data = mList.get(position);
        viewHolder.tv_title.setText(data.getTitle());
        viewHolder.tv_source.setText(data.getSource());
        //加载图片
        if (!TextUtils.isEmpty(data.getImgUrl())){
            PicassoUtils.loadImageViewSize(mContent,data.getImgUrl(),300,100,viewHolder.iv_img);
        }
        return convertView;
    }

    class ViewHolder{
        private ImageView iv_img;
        private TextView tv_title;
        private TextView tv_source;
    }
}
