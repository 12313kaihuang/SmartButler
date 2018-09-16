package com.android.smartbutler.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.smartbutler.R;
import com.android.smartbutler.entity.CourierData;

import java.util.List;

/**
 * 项目名：SmartButler
 * 包名：  com.android.smartbutler.adaptor
 * 文件名：CourierAdapter
 * 创建者：HY
 * 创建时间：2018/9/12 10:11
 * 描述：  快递查询
 */

public class CourierAdapter extends BaseAdapter {

    private Context context;
    private List<CourierData> list;

    //布局加载器
    private LayoutInflater inflater;
    private CourierData data;

    public CourierAdapter(Context context, List<CourierData> list) {
        this.context = context;
        this.list = list;
        //获取系统服务
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder ;
        //第一次加载
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.layout_courier_item, null);
            viewHolder.tv_remark = convertView.findViewById(R.id.tv_remark);
            viewHolder.tv_zone = convertView.findViewById(R.id.tv_zone);
            viewHolder.tv_datetime = convertView.findViewById(R.id.tv_datetime);
            //设置缓存
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //设置数据
        data = list.get(position);

        viewHolder.tv_remark.setText(data.getRemark());
        viewHolder.tv_zone.setText(data.getZone());
        viewHolder.tv_datetime.setText(data.getDatetime());

        return convertView;
    }

    class ViewHolder {
        private TextView tv_remark;
        private TextView tv_zone;
        private TextView tv_datetime;
    }
}
