package com.wzrd.v.activity.home.poem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wzrd.R;
import com.wzrd.m.been.Poem;
import com.wzrd.m.been.SelectBean;
import com.wzrd.m.db.manger.PoemManager;
import com.wzrd.m.utils.TextUtil;
import com.wzrd.m.utils.Utils;
import com.wzrd.p.impl.AbsToolBarMenuPresenter;
import com.wzrd.v.activity.message.SelectBackActivity;
import com.yyx.beautifylib.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddPoemActivity extends AppCompatActivity implements AbsToolBarMenuPresenter {

    @BindView(R.id.toolbar_back)
    ImageView mToolbarBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_menu)
    ImageView mToolbarMenu;
    @BindView(R.id.toolbar_menu_text)
    TextView mToolbarMenuText;
    @BindView(R.id.change_letter)
    TextView mChangeLetter;
    @BindView(R.id.letter_bg)
    LinearLayout mLetterBg;
    @BindView(R.id.start_speak)
    ImageView mStartSpeak;
    @BindView(R.id.down_arrow)
    ImageView mDownArrow;
    @BindView(R.id.time)
    Chronometer mTime;
    @BindView(R.id.stop)
    ImageView mStop;
    @BindView(R.id.play_speak)
    LinearLayout mPlaySpeak;
    @BindView(R.id.unSave)
    ImageView mUnSave;
    @BindView(R.id.save)
    ImageView mSave;
    @BindView(R.id.end_speak)
    LinearLayout mEndSpeak;
    @BindView(R.id.title)
    EditText mTitle;
    @BindView(R.id.author)
    EditText mAuthor;
    @BindView(R.id.content)
    EditText mContent;
    private String mId;
    private PoemManager mPoemManager;
    private Poem mPoem;
    private SelectBean mSelectBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_poem);
        ButterKnife.bind(this);
        mTime = (Chronometer) findViewById(R.id.time);
        Intent intent = getIntent();
        mId = intent.getStringExtra("id");
        mPoemManager = PoemManager.getInstance(this);
        if (TextUtil.isEmpty(mId)) {
            //新增
            Utils.backToolbar(this, mToolbarBack, mToolbarTitle, "文字", mToolbarMenu, 0, this, mToolbarMenuText, "完成");
            mLetterBg.setBackgroundResource(R.drawable.a19049ea3874d0bb4837f114095abd601);
            mContent.setText("");
            mTitle.setHint("请输入诗标题");
            mAuthor.setHint("请输入作者姓名");
            mContent.setHint("请输入诗内容");
        } else {
            //详情页面
            Utils.backToolbar(this, mToolbarBack, mToolbarTitle, "文字", mToolbarMenu, 0, null, mToolbarMenuText, "");
            mChangeLetter.setVisibility(View.INVISIBLE);
            mPoem = mPoemManager.findPoemById(mId);
            mTitle.setText(mPoem.getTitle());
            mAuthor.setText(mPoem.getAuthor());
            mContent.setText(Html.fromHtml(mPoem.getContent()));
            mTitle.setEnabled(false);
            mAuthor.setEnabled(false);
            mContent.setEnabled(false);
            mLetterBg.setBackgroundResource(mPoem.getBgPath());
        }


    }

    @OnClick({R.id.change_letter, R.id.start_speak, R.id.down_arrow, R.id.stop, R.id.unSave, R.id.save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.change_letter:
                //切换信纸
                Intent intent = new Intent(this, SelectBackActivity.class);
                startActivityForResult(intent, 100);
                break;
            case R.id.start_speak:
                if (TextUtil.isEmpty(mId)) {
                    mStartSpeak.setVisibility(View.GONE);
                    mPlaySpeak.setVisibility(View.VISIBLE);
                    mTime.start();
                } else {
                    //播放录音
                }
                break;
            case R.id.down_arrow:
                //放弃录音
                mStartSpeak.setVisibility(View.VISIBLE);
                mPlaySpeak.setVisibility(View.GONE);
                break;
            case R.id.stop:
                //录音停止
                mPlaySpeak.setVisibility(View.GONE);
                mEndSpeak.setVisibility(View.VISIBLE);
                mTime.stop();
                break;
            case R.id.unSave:
                mStartSpeak.setVisibility(View.VISIBLE);
                mEndSpeak.setVisibility(View.GONE);
                break;
            case R.id.save:
                mStartSpeak.setVisibility(View.VISIBLE);
                mEndSpeak.setVisibility(View.GONE);
                //保存录音
                break;
        }
    }

    //点击完成按钮进行保存
    @Override
    public void setToolBarMenu() {
        int path = R.drawable.a19049ea3874d0bb4837f114095abd601;
        if (mSelectBean != null) {
            path = mSelectBean.getPath();
        }

        Poem poem = new Poem(Utils.getuuid(), mTitle.getText().toString(), mAuthor.getText().toString(), mContent.getText().toString(), "0", "", path);
        mPoemManager.insertPoem(poem);
        ToastUtils.toast(this, "保存成功");
        mTime = null;
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            mSelectBean = (SelectBean) data.getExtras().get("bean");
            if (mSelectBean != null) {
                mLetterBg.setBackgroundResource(mSelectBean.getPath());
            }
        }
    }
}
