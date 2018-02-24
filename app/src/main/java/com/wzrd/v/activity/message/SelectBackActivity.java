package com.wzrd.v.activity.message;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wzrd.R;
import com.wzrd.databinding.ActivitySelectBackBinding;
import com.wzrd.m.been.SelectBean;
import com.wzrd.m.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SelectBackActivity extends AppCompatActivity {
    private ActivitySelectBackBinding binding;
    private List<SelectBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_back);
        list = new ArrayList<>();
        initdata();
        binding.setSelectdata(list);
        binding.setSelect(new SelectBean());



    }

    /**
     * 初始化数据
     */
    private void initdata() {
        list.add(new SelectBean(R.drawable.a19049ea3874d0bb4837f114095abd601, Utils.getuuid(), "", true));
        list.add(new SelectBean(R.drawable.a0d8b0e7b6b175e1c16f243bf37226706, Utils.getuuid(), "", false));
        list.add(new SelectBean(R.drawable.a3a1a3ea5a1ae9b0235198956b667e04b, Utils.getuuid(), "", false));
        list.add(new SelectBean(R.drawable.a3dc5f8670e187100493889a3453cdeb7, Utils.getuuid(), "", false));
        list.add(new SelectBean(R.drawable.a9bd441b37cad0173906cc75784a8a360, Utils.getuuid(), "", false));
        list.add(new SelectBean(R.drawable.a63edcb4c956631ec618f7445d24a653b, Utils.getuuid(), "", false));
        list.add(new SelectBean(R.drawable.a87fd6d66d5e90b06838b69e90a34e07f, Utils.getuuid(), "", false));
        list.add(new SelectBean(R.drawable.a92a93ff72ea1787961652b107c3d135e, Utils.getuuid(), "", false));
        list.add(new SelectBean(R.drawable.a102f9eadd79722fb050f28da20164241, Utils.getuuid(), "", false));
        list.add(new SelectBean(R.drawable.a33123a94f678b12c875b9d52984bdab2, Utils.getuuid(), "", false));
        list.add(new SelectBean(R.drawable.ab92dc4c0d66488cc1448a6996bfb26ee, Utils.getuuid(), "", false));
        list.add(new SelectBean(R.drawable.abfd77aa878c072290bb7e24f2ae6e9db, Utils.getuuid(), "", false));
        list.add(new SelectBean(R.drawable.ac0913c67e9bda9ac7af06c5a21c6734a, Utils.getuuid(), "", false));
    }
}
