package com.android.smartbutler.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.smartbutler.R;
import com.android.smartbutler.entity.WechatData;
import com.android.smartbutler.util.LogUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 项目名：SmartButler
 * 包名：  com.android.smartbutler.adapter
 * 文件名：WechatAdaptor
 * 创建者：HY
 * 创建时间：2018/9/16 14:13
 * 描述：  微信精选
 */

public class WechatAdaptor extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<WechatData> dataList;
    private WechatData data;
    private int width,height;
    private WindowManager windowManager;

    public WechatAdaptor(Context context, List<WechatData> dataList) {
        this.context = context;
        this.dataList = dataList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);
        width = point.x;
        height = point.y;
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.wechat_item, null);
            viewHolder.iv_img = convertView.findViewById(R.id.iv_img);
            viewHolder.tv_title = convertView.findViewById(R.id.tv_title);
            viewHolder.tv_source = convertView.findViewById(R.id.tv_source);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        data = dataList.get(position);
        viewHolder.tv_title.setText(data.getTitle());
        viewHolder.tv_source.setText(data.getSource());
        //加载图片
        Picasso.get().load(data.getImgUrl())
                .resize(width/3,200)
                .centerCrop()
                .into(viewHolder.iv_img);
        return convertView;
    }

    class ViewHolder {
        private ImageView iv_img;
        private TextView tv_title;
        private TextView tv_source;

    }
}
