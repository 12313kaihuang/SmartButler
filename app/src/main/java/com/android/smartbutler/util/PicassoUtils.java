package com.android.smartbutler.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * 项目名：SmartButler
 * 包名：  com.android.smartbutler.util
 * 文件名：PicassoUtils
 * 创建者：HY
 * 创建时间：2018/9/16 19:31
 * 描述：  Picasso
 */

public class PicassoUtils {

    //默认加载图片
    public static void loadImageView(Context context, String url, ImageView imageView) {
        Picasso.get().load(url).into(imageView);
    }

    //裁剪图片
    public static void loadImageViewCrop(String url, ImageView imageView) {
        Picasso.get().load(url).transform(new CropSquareTransformation()).into(imageView);
    }

    //按比例裁剪 矩形
    public static class CropSquareTransformation implements Transformation {
        @Override public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
            if (result != source) {
                //回收
                source.recycle();
            }
            return result;
        }

        @Override public String key() { return "hy"; }
    }
}
