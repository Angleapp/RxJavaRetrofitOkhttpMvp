package com.wzrd.v.fragment.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        MineFragmnetBinding binding = DataBindingUtil.inflate(inflater, R.layout.mine_fragmnet, container, false);
        MineAndLover mineAndLover=new MineAndLover();
        String path= SharedPreferencesUtil.getString(getActivity(),"icon",null);
        String nickname=SharedPreferencesUtil.getString(getActivity(),"nickname",null);
        Log.e("nickname","nickname--->"+nickname);
        mineAndLover.setIconpath(path);
        mineAndLover.setUserMessage(new UserMessage(nickname,path));
        binding.setItembeen(mineAndLover);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
