package com.wzrd.v.activity.contacts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wzrd.R;

public class ContanctsMessageActivity extends AppCompatActivity {
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contancts_message);
        username= getIntent().getExtras().getString("name");
//        Utils.ToastShort(this,username);

    }
}
