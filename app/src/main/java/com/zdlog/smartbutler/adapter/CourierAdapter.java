package com.zdlog.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zdlog.smartbutler.R;
import com.zdlog.smartbutler.entity.CourierData;

import java.util.List;

/**
 * 项目名：SmartButler
 * 包名 ： com.zdlog.smartbutler.adapter
 * 文件名：CourierAdapter
 * 创建者：jiushaba
 * 创建时间：2017/4/12 0012下午 12:19
 * 描述： 快递查询适配器
 */
public class CourierAdapter extends BaseAdapter {

    private Context mContent;
    private List<CourierData> mList;
    //布局加载器
    private LayoutInflater inflater;
    private CourierData data;
    public CourierAdapter(Context mContent, List<CourierData> mList) {
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
        //第一次加载
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=inflater.inflate(R.layout.layout_courier_item,null);
            viewHolder.tv_remark= (TextView) convertView.findViewById(R.id.tv_remark);
            viewHolder.tv_zone= (TextView) convertView.findViewById(R.id.tv_zone);
            viewHolder.tv_datatime= (TextView) convertView.findViewById(R.id.tv_datatime);
            //设置缓存
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        //设置数据
        data=mList.get(position);
        viewHolder.tv_remark.setText(data.getRemark());
        if (data.getZone()==null|data.getZone().equals("")){
            viewHolder.tv_zone.setVisibility(View.GONE);
        }else {
            viewHolder.tv_zone.setText(data.getZone());
        }
        viewHolder.tv_datatime.setText(data.getDatatime());
        return convertView;
    }
    class ViewHolder{
        private TextView tv_remark;
        private TextView tv_zone;
        private TextView tv_datatime;
    }
}
