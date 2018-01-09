package com.wzrd.v.view.popup;

import android.app.Activity;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.muzhi.camerasdk.library.filter.util.ImageFilterTools;
import com.muzhi.camerasdk.library.views.HorizontalListView;
import com.wzrd.R;
import com.yyx.beautifylib.adapter.Filter_Effect_Adapter;
import com.yyx.beautifylib.model.Filter_Effect_Info;
import com.yyx.beautifylib.utils.FilterUtils;
import com.yyx.beautifylib.view.BLBeautifyImageView;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by lk on 2017/5/9.
 */

public class PhotoPopupWindow extends PopupWindow {


    private View mMenuView;
    private List<Filter_Effect_Info> mFilterData = new ArrayList<>(); //特效
    private Filter_Effect_Adapter mFilterAdapter;
    private HorizontalListView effect_listview;
    private BLBeautifyImageView imageView;
    private TextView tv_complete;
    private ImageView iv_back;

    public PhotoPopupWindow(final Activity context, Uri uri) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dailog_photo_pop, null, false);
        tv_complete = (TextView) view.findViewById(R.id.tv_photocomplete);
        iv_back = (ImageView) view.findViewById(R.id.iv_photoback);
        imageView = (BLBeautifyImageView) view.findViewById(R.id.blbeautify_image);
        effect_listview = (HorizontalListView) view.findViewById(R.id.hlv_listview);
       ImageView iv=(ImageView) view.findViewById(R.id.iv_test);
        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;

        this.setWidth(widthPixels);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setContentView(view);
        this.setAnimationStyle(R.style.AnimBottom);
        Drawable drawable = context.getResources().getDrawable(R.drawable.bg_shape);
        setBackgroundDrawable(drawable);
        setadapter(context);
        setlistener();

        Bitmap bm = null;
        ContentResolver cr = context.getContentResolver();

        try {
            bm = BitmapFactory.decodeStream(cr.openInputStream(uri));
            if (bm != null) {
                effect_listview.setVisibility(View.VISIBLE);
                imageView.setImage(bm);
//                iv.setImageBitmap(bm);
            } else {
                effect_listview.setVisibility(View.GONE);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    private void setlistener() {

        tv_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String path = imageView.save();
                dismiss();

            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        effect_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                mFilterAdapter.setSelectItem(position);
                final int itemWidth = view.getWidth();
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        effect_listview.scrollTo(itemWidth * (position - 1) - itemWidth / 4);
                    }
                }, 100);

                ImageFilterTools.FilterType filterType = mFilterData.get(position).getFilterType();
                imageView.addFilter(filterType);

            }
        });
    }

    private void setadapter(Activity context) {
        mFilterData = FilterUtils.getEffectList();
        mFilterAdapter = new Filter_Effect_Adapter(context, mFilterData);
        effect_listview.setAdapter(mFilterAdapter);
    }

}

















