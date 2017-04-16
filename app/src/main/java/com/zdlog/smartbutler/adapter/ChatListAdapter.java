package com.zdlog.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zdlog.smartbutler.R;
import com.zdlog.smartbutler.entity.ChatListData;

import java.util.List;

/**
 * 项目名：SmartButler
 * 包名 ： com.zdlog.smartbutler.adapter
 * 文件名：ChatListAdapter
 * 创建者：jiushaba
 * 创建时间：2017/4/12 0012下午 5:33
 * 描述： 对话adapter
 */
public class ChatListAdapter extends BaseAdapter{
    private Context mContent;
    private LayoutInflater inflater;
    private ChatListData data;
    private List<ChatListData> mList;
    //左边的type
    public static final int VALUE_LEFT = 1;
    //右边的type
    public static final int VALUE_RIGHT=2;
    public ChatListAdapter(Context mContent,List<ChatListData> mList){
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
        ViewHoldLeftText viewHoldLeftText=null;
        ViewHoldRightText viewHoldRightText=null;
        //获取当前要显示的type，根据type来区分数据的加载
        int type = getItemViewType(position);
        if (convertView==null){
            switch (type){
                case VALUE_LEFT:
                    viewHoldLeftText=new ViewHoldLeftText();
                    convertView=inflater.inflate(R.layout.left_item,null);
                    viewHoldLeftText.tv_left_text = (TextView) convertView.findViewById(R.id.tv_left_text);
                    convertView.setTag(viewHoldLeftText);
                    break;
                case VALUE_RIGHT:
                    viewHoldRightText=new ViewHoldRightText();
                    convertView=inflater.inflate(R.layout.right_item,null);
                    viewHoldRightText.tv_Right_text = (TextView) convertView.findViewById(R.id.tv_right_text);
                    convertView.setTag(viewHoldRightText);
                    break;
            }
        }else {
            switch (type){
                case VALUE_LEFT:
                    viewHoldLeftText= (ViewHoldLeftText) convertView.getTag();
                    break;
                case VALUE_RIGHT:
                    viewHoldRightText = (ViewHoldRightText) convertView.getTag();
                    break;
            }
        }
        //赋值
        ChatListData data=mList.get(position);
        switch (type){
            case VALUE_LEFT:
                viewHoldLeftText.tv_left_text.setText(data.getText());
                break;
            case VALUE_RIGHT:
                viewHoldRightText.tv_Right_text.setText(data.getText());
                break;
        }
        return convertView;
    }
    //根据数据源的position来返回要显示的item
    @Override
    public int getItemViewType(int position) {
        ChatListData data = mList.get(position);
        int type = data.getType();
        return type;
    }

    //返回所有的layout数据
    @Override
    public int getViewTypeCount() {
        return 3;  //mList.size+1
    }
    //左边的文本
    class ViewHoldLeftText{
        private TextView tv_left_text;
    }
    //右边的文本
    class ViewHoldRightText{
        private TextView tv_Right_text;
    }

}
