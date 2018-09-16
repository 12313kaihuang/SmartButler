package com.android.smartbutler.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.smartbutler.R;
import com.android.smartbutler.entity.ChatListData;

import java.util.List;

/**
 * 项目名：SmartButler
 * 包名：  com.android.smartbutler.adaptor
 * 文件名：ChatListAdapter
 * 创建者：HY
 * 创建时间：2018/9/16 9:54
 * 描述：  对话adapter
 */

public class ChatListAdapter extends BaseAdapter {

    //区别消息类别
    public static final int VALUE_LEFT_TYPE = 1;
    public static final int VALUE_RIGHT_TYPE = 2;

    private Context context;
    private LayoutInflater inflater;
    private ChatListData data;
    private List<ChatListData> dataList;

    public ChatListAdapter(Context context, List<ChatListData> dataList) {
        this.context = context;
        this.dataList = dataList;
        //获取系统服务
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderLeftText viewHolderLeftText = null;
        ViewHolderRightText viewHolderRightText = null;

        //获取当前要显示的type，根据type来区分数据的加载
        int type = getItemViewType(position);
        if (convertView == null) {
            switch (type){
                case VALUE_LEFT_TYPE:
                    viewHolderLeftText = new ViewHolderLeftText();
                    convertView = inflater.inflate(R.layout.left_item,null);
                    viewHolderLeftText.tv_left_text = convertView.findViewById(R.id.tv_left_text);
                    convertView.setTag(viewHolderLeftText);
                    break;
                case VALUE_RIGHT_TYPE:
                    viewHolderRightText = new ViewHolderRightText();
                    convertView = inflater.inflate(R.layout.right_item,null);
                    viewHolderRightText.tv_right_text = convertView.findViewById(R.id.tv_right_text);
                    convertView.setTag(viewHolderRightText);
                    break;
            }
        }else {
            switch (type) {
                case VALUE_LEFT_TYPE:
                    viewHolderLeftText = (ViewHolderLeftText) convertView.getTag();
                    break;
                case VALUE_RIGHT_TYPE:
                    viewHolderRightText = (ViewHolderRightText) convertView.getTag();
                    break;
            }
        }

        //赋值
        ChatListData data = dataList.get(position);
        switch (type) {
            case VALUE_LEFT_TYPE:
                viewHolderLeftText.tv_left_text.setText(data.getText());
                break;
            case VALUE_RIGHT_TYPE:
                viewHolderRightText.tv_right_text.setText(data.getText());
                break;
        }
        return convertView;
    }


    //根据数据源的position返回要显示的item
    @Override
    public int getItemViewType(int position) {
        ChatListData data = dataList.get(position);
        return data.getType();
    }

    //返回所有的layout数量
    @Override
    public int getViewTypeCount() {
        return 3;  //dataList.size + 1
    }

    //左边的文本
    class ViewHolderLeftText {
        private TextView tv_left_text;
    }


    //右边的文本
    class ViewHolderRightText {
        private TextView tv_right_text;
    }
}
