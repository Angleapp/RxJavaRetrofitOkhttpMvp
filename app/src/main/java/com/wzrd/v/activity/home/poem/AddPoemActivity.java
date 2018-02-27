package com.wzrd.v.activity.home.poem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wzrd.R;
import com.wzrd.m.utils.Utils;
import com.wzrd.p.impl.AbsToolBarMenuPresenter;
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
    TextView mTime;
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
    int[] resIds = {
            R.drawable.a19049ea3874d0bb4837f114095abd601,
            R.drawable.a0d8b0e7b6b175e1c16f243bf37226706,
            R.drawable.a102f9eadd79722fb050f28da20164241,
            R.drawable.a33123a94f678b12c875b9d52984bdab2,
            R.drawable.a3a1a3ea5a1ae9b0235198956b667e04b,
            R.drawable.a3dc5f8670e187100493889a3453cdeb7,
            R.drawable.a63edcb4c956631ec618f7445d24a653b,
            R.drawable.a87fd6d66d5e90b06838b69e90a34e07f,
            R.drawable.a92a93ff72ea1787961652b107c3d135e,
            R.drawable.a9bd441b37cad0173906cc75784a8a360,
            R.drawable.ab92dc4c0d66488cc1448a6996bfb26ee,
            R.drawable.abfd77aa878c072290bb7e24f2ae6e9db,
            R.drawable.ac0913c67e9bda9ac7af06c5a21c6734a
    };
    int index = 0;
    @BindView(R.id.title)
    EditText mTitle;
    @BindView(R.id.author)
    EditText mAuthor;
    @BindView(R.id.content)
    EditText mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_poem);
        ButterKnife.bind(this);
        Utils.backToolbar(this, mToolbarBack, mToolbarTitle, "文字", mToolbarMenu, 0, null, mToolbarMenuText, "完成");
        mLetterBg.setBackgroundResource(resIds[index]);
        mContent.setText(Html.fromHtml("关关雎鸠，在河之洲。<br>" +
                "窈窕淑女，君子好逑。<br>" +
                "<br>" +
                "参差荇菜，左右流之。<br>" +
                "窈窕淑女，寤寐求之。<br>" +
                "<br>" +
                "求之不得，寤寐思服。<br>" +
                "悠哉悠哉，辗转反侧。<br>" +
                "<br>" +
                "参差荇菜，左右采之。<br>" +
                "窈窕淑女，琴瑟友之。<br>" +
                "<br>" +
                "参差荇菜，左右芼之。<br>" +
                "窈窕淑女，钟鼓乐之。"));

    }

    @OnClick({R.id.change_letter, R.id.start_speak, R.id.down_arrow, R.id.stop, R.id.unSave, R.id.save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.change_letter:
                //切换信纸
                if (index == 0) {
                    index++;
                }
                if (index == resIds.length - 1) {
                    index = 0;
                }

                mLetterBg.setBackgroundResource(resIds[index]);
                index++;
                break;
            case R.id.start_speak:
                mStartSpeak.setVisibility(View.GONE);
                mPlaySpeak.setVisibility(View.VISIBLE);
                break;
            case R.id.down_arrow:
                mStartSpeak.setVisibility(View.VISIBLE);
                mPlaySpeak.setVisibility(View.GONE);
                break;
            case R.id.stop:
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
        ToastUtils.toast(this, "保存成功");
    }
}
