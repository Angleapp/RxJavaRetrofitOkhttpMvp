package com.wzrd.v.activity.home.poem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.wzrd.R;
import com.wzrd.m.been.Poem;
import com.wzrd.m.utils.Utils;
import com.wzrd.v.adapter.PoemListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PoemActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_back)
    ImageView mToolbarBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_menu)
    ImageView mToolbarMenu;
    @BindView(R.id.toolbar_menu_text)
    TextView mToolbarMenuText;
    @BindView(R.id.list)
    ListView mList;
    @BindView(R.id.addPoem)
    LinearLayout mAddPoem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poem);
        ButterKnife.bind(this);
        Utils.backToolbar(this, mToolbarBack, mToolbarTitle, "诗歌", mToolbarMenu, 0, null, mToolbarMenuText, "");
        List<Poem> mLists = getDataList();

        PoemListAdapter adapter = new PoemListAdapter(mLists, this);
        mList.setAdapter(adapter);
    }

    /**
     *
     */
    private List<Poem> getDataList() {
        List<Poem> poems = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Poem poem = new Poem(i + "", "致豫树", "舒婷", "我如果爱你——\n" +
                    "绝不像攀援的凌霄花，\n" +
                    "借你的高枝炫耀自己：");
            poems.add(poem);
        }
        return poems;
    }

    @OnClick(R.id.addPoem)
    public void onViewClicked() {

    }
}
