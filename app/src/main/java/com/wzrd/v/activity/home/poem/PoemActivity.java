package com.wzrd.v.activity.home.poem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wzrd.R;
import com.wzrd.m.been.Poem;
import com.wzrd.m.db.manger.PoemManager;
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
    private PoemManager mPoemManager;
    private List<Poem> mLists = new ArrayList<>();
    private PoemListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poem);
        ButterKnife.bind(this);
        Utils.backToolbar(this, mToolbarBack, mToolbarTitle, "诗歌", mToolbarMenu, 0, null, mToolbarMenuText, "");
        mPoemManager = new PoemManager(this);
        List<Poem> allPoem = mPoemManager.getAllPoem();
        mLists.addAll(allPoem);
        adapter = new PoemListAdapter(mLists, this);
        mList.setAdapter(adapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> view, View view1, int i, long l) {
                Intent intent = new Intent(PoemActivity.this, AddPoemActivity.class);
                intent.putExtra("id", mLists.get(i).getId());
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Poem> allPoem = mPoemManager.getAllPoem();
        if (mLists != null) {
            mLists.clear();
        }
        mLists.addAll(allPoem);
        adapter.notifyDataSetChanged();

    }

    /**
     *
     */
    private void getDataList() {
        List<Poem> poems = new ArrayList<>();
        poems.add(new Poem(Utils.getuuid(), "致豫树", "舒婷", "我如果爱你——<br>" +
                "绝不像攀援的凌霄花，<br>" +
                "借你的高枝炫耀自己：", "1", "", R.drawable.a19049ea3874d0bb4837f114095abd601));
        poems.add(new Poem(Utils.getuuid(), "静夜思", "李白", "窗前明月光，疑是地上霜。<br>举头望明月，低头思故乡。", "0", "", R.drawable.a0d8b0e7b6b175e1c16f243bf37226706));
        poems.add(new Poem(Utils.getuuid(), "上邪", "诗经", "我欲与君相知， 长命无绝衰。<br>" +
                "山无陵， 江水为竭， 冬雷震震， 夏雨雪 ，", "1", "", R.drawable.a0d8b0e7b6b175e1c16f243bf37226706));
        poems.add(new Poem(Utils.getuuid(), "蒹葭", "诗经", "蒹葭苍苍，白露为霜。所谓伊人，在水一方。<br>" +
                "溯洄从之，道阻且长。溯游从之，宛在水中央。<br>" +
                "蒹葭萋萋，白露未晞。所谓伊人，在水之湄。", "1", "", R.drawable.a3a1a3ea5a1ae9b0235198956b667e04b));
        mPoemManager.insertMultPoem(poems);
    }

    @OnClick(R.id.addPoem)
    public void onViewClicked() {
        Intent intent = new Intent(this, AddPoemActivity.class);
        startActivity(intent);
    }
}
