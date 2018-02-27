package com.wzrd.v.activity.homepage;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.wzrd.R;
import com.wzrd.databinding.ActivityWisdomBinding;
import com.wzrd.m.been.WisdomBeen;
import com.wzrd.m.utils.DateUtils;
import com.wzrd.m.utils.SharedPreferencesUtil;
import com.wzrd.m.utils.Utils;
import com.wzrd.p.inteface.AdapterClickPosition;

import java.util.ArrayList;
import java.util.List;

public class WisdomActivity extends AppCompatActivity implements AdapterClickPosition.position {
    private ActivityWisdomBinding binding;
    private List<WisdomBeen> list;
    private int position = 0;
    private   List<String> messlist;
    private String widom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wisdom);
        widom = getIntent().getExtras().getString("type");
        list = new ArrayList<>();
        messlist=new ArrayList<>();
        initdata();
        binding.setWisdom(list.get(0));
        binding.setWisdomdata(list);


    }

    /**
     * 初始化数据
     */
    private void initdata() {

        messlist.add("珍惜上帝赐予的点点滴滴；善待自己，让自己的心中永远有一片阳光照耀的晴空；善待自己，把眼前的痛苦看淡，或许痛苦之后就是幸福的……");
        messlist.add("优秀的人身上会分散着诱人的光彩，他不仅吸引你，同时也吸引着和你同样有着鉴赏能力的人。就象美丽的风景，它的存在不是为了一座山，一片旷野，而是为了整个自然，是为了点缀这美丽的世界，是为了让更多的人去欣赏去品味去陶醉其间。");
        messlist.add("什么是幸福，幸福就是自己的一种愉快的心理状态和感受。时时、事事都能使自己快乐的人才是最幸福的人。最快乐的人，就是最幸福的人。笑口常开的人，是最幸福的。");
        messlist.add("生命中，好多的事是这样，生活中，好多的情是这样，没有理由，也无需理由，爱就是爱，喜欢就是喜欢，没有结果，也无须结果，心甘情愿，无怨无悔。");
        messlist.add("年有春夏秋冬，人有喜怒忧悲。快乐的最好办法就是忘记不快，幸福的最好办法就是忘记不幸。不能忘记失败的教训，但要忘记失败的痛苦。快乐只属于那些善于忘记不快的人，幸福只属于那些善于忘记不幸的人。牢记不快只能使自己痛苦，牢记不幸只能使自己悲伤。遗忘失去的，欣赏拥有的，才能使自己快乐幸福。");
        String id = SharedPreferencesUtil.getString(this, "userid", "");
        String date = DateUtils.getCurrentDate();
        list.add(new WisdomBeen(Utils.getuuid(), id, messlist.get(0), "1", true, date,0));
        list.add(new WisdomBeen(Utils.getuuid(), id,  messlist.get(1), "", false, date,1));
        list.add(new WisdomBeen(Utils.getuuid(), id,  messlist.get(2), "", false, date,2));
        list.add(new WisdomBeen(Utils.getuuid(), id,  messlist.get(3), "", false, date,3));
        list.add(new WisdomBeen(Utils.getuuid(), id,  messlist.get(4), "", false, date,4));
        if("popre".equals(widom)){
            int clickposition = SharedPreferencesUtil.getInt(this, "clickposition", 0);
            if(clickposition!=0){
                list.get(0).setIscheckd(false);
                list.get(clickposition).setIscheckd(true);
            }
        }


    }

    @Override
    public void adapterposition(int i) {
        Log.e("i","i----"+i);
        if(!"popre".equals(widom)){
            binding.setWisdom(list.get(i));
        }

    }
}
