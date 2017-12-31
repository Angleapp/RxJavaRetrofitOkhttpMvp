package com.wzrd.v.activity.welcome;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wzrd.R;

public class IconActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_icon);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_icon);
//        viewDataBinding.setVariable()
    }
}
