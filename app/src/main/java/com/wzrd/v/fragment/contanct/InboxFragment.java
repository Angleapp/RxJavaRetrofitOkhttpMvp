package com.wzrd.v.fragment.contanct;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wzrd.R;
import com.wzrd.databinding.InboxFragmentBinding;

/**
 * Created by lk on 2018/2/9.
 */

public class InboxFragment extends Fragment{
    private  InboxFragmentBinding dataBinding;
    private String TAG="InboxFragment";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         dataBinding= DataBindingUtil.inflate(inflater, R.layout.inbox_fragment, container, false);
        Log.e(TAG,"1234");
        return dataBinding.getRoot();
    }
}