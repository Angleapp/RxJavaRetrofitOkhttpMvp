package com.wzrd.v.fragment.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wzrd.R;
import com.wzrd.databinding.MineFragmnetBinding;
import com.wzrd.m.been.MineAndLover;
import com.wzrd.m.been.UserMessage;
import com.wzrd.m.utils.SharedPreferencesUtil;

/**
 * Created by lk on 2018/1/3.
 */

public class MineFragment extends Fragment {
    private MineAndLover mineAndLover;
    private MineFragmnetBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mineAndLover = new MineAndLover();
        binding = DataBindingUtil.inflate(inflater, R.layout.mine_fragmnet, container, false);
        setdata();
        return binding.getRoot();
    }


    public void setdata() {

        String path = SharedPreferencesUtil.getString(getActivity(), "icon", null);
        String nickname = SharedPreferencesUtil.getString(getActivity(), "nickname", null);
        mineAndLover.setIconpath(path);
        mineAndLover.setUserMessage(new UserMessage(nickname, path, ""));
        binding.setItembeen(mineAndLover);
    }

    @Override
    public void onResume() {
        super.onResume();
        setdata();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
