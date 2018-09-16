package com.android.smartbutler.adapter;

import android.content.Context;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.smartbutler.R;
import com.android.smartbutler.entity.GirlData;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 项目名：SmartButler
 * 包名：  com.android.smartbutler.adapter
 * 文件名：GirlAdaptor
 * 创建者：HY
 * 创建时间：2018/9/16 20:37
 * 描述：
 */

public class GirlAdaptor extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<GirlData> girlDataList;
    //屏幕宽度
    private int width;

    public GirlAdaptor(Context context, List<GirlData> girlDataList) {
        this.context = context;
        this.girlDataList = girlDataList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        if (windowManager != null) {
            windowManager.getDefaultDisplay().getSize(point);
        }
        width = point.x;
    }

    @Override
    public int getCount() {
        return girlDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return girlDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.gril_item, null);
            viewHolder.imageView = convertView.findViewById(R.id.imageView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GirlData data = girlDataList.get(position);
        //解析图片
        String url = data.getUrl();
        Picasso.get().load(url)
                .resize(width / 2, 300)
                .into(viewHolder.imageView);

        return convertView;
    }

    class ViewHolder {
        private ImageView imageView;
    }
}
