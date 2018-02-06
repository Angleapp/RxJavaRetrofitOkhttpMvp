package com.wzrd.v.adapter;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wzrd.R;
import com.wzrd.v.view.GlideCircleTransform;

/**
 * Created by lk on 2017/10/31.
 */

public class ImageViewAttrAdapter {

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {

        if(!TextUtils.isEmpty(imageUrl)){

            Glide.with(view.getContext())
                    .load(imageUrl)
                    .placeholder(R.mipmap.icon_signin_default)
                    .bitmapTransform(new GlideCircleTransform(view.getContext()))
                    .error(R.mipmap.icon_signin_default)
                    .into(view);

        }else{
//            http://img.kaiyanapp.com/d89390927060bfc8b9c8a0befbf8dc5e.png?imageMogr2/quality/60/format/jpg
            int resource = R.mipmap.icon_signin_default;
            Glide.with(view.getContext())
                    .load(resource)
                    .bitmapTransform(new GlideCircleTransform(view.getContext()))
                    .error(R.mipmap.icon_signin_default)
                    .into(view);
        }

    }
}
