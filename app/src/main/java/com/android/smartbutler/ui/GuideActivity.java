package com.android.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.smartbutler.MainActivity;
import com.android.smartbutler.R;
import com.android.smartbutler.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名：SmartButler
 * 包名：  com.android.smartbutler.ui
 * 文件名：GuideActivity
 * 创建者：HY
 * 创建时间：2018/9/9 16:16
 * 描述：  TODO
 */

public class GuideActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    //容器
    private List<View> mList = new ArrayList<>();
    private View view1,view2,view3;
    //小圆点
    private ImageView point1,point2,point3;
    //跳过
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initView();
    }

    private void initView() {
        mViewPager = findViewById(R.id.mViewPager);

        view1 = View.inflate(this,R.layout.page_item_one,null);
        view2 = View.inflate(this,R.layout.page_item_two,null);
        view3 = View.inflate(this,R.layout.page_item_three,null);

        imageView = findViewById(R.id.iv_back);

        view3.findViewById(R.id.btn_start).setOnClickListener(this);
        imageView.setOnClickListener(this);

        point1 = findViewById(R.id.point1);
        point2 = findViewById(R.id.point2);
        point3 = findViewById(R.id.point3);

        mList.add(view1);
        mList.add(view2);
        mList.add(view3);

        //设置适配器
        mViewPager.setAdapter(new GuideAdapter());

        //监听ViewPager的滑动
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setPointImg(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
            case R.id.iv_back:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
    }

    class GuideAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(mList.get(position));
            return mList.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(mList.get(position));
//            super.destroyItem(container, position, object);
        }
    }


    //设置小圆点的选中效果
    private void setPointImg(int position) {
        LogUtil.d("position = "+position);
        switch (position) {
            case 0:
                imageView.setVisibility(View.VISIBLE);
                point1.setBackgroundResource(R.drawable.point_on);
                point2.setBackgroundResource(R.drawable.point_off);
                point3.setBackgroundResource(R.drawable.point_off);
                break;
            case 1:
                imageView.setVisibility(View.VISIBLE);
                point1.setBackgroundResource(R.drawable.point_off);
                point2.setBackgroundResource(R.drawable.point_on);
                point3.setBackgroundResource(R.drawable.point_off);
                break;
            case 2:
                imageView.setVisibility(View.GONE);
                point1.setBackgroundResource(R.drawable.point_off);
                point2.setBackgroundResource(R.drawable.point_off);
                point3.setBackgroundResource(R.drawable.point_on);
                break;
            default:
                break;
        }
    }
}
