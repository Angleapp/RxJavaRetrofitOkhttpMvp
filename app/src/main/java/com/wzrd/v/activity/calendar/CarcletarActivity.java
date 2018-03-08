package com.wzrd.v.activity.calendar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.wzrd.R;
import com.wzrd.m.been.TSYSCONTANTS;
import com.wzrd.m.utils.ActivityCollector;
import com.wzrd.m.utils.Constants;
import com.wzrd.m.utils.DateUtils;
import com.wzrd.m.utils.Utils;
import com.wzrd.p.impl.AbsToolBarMenuPresenter;
import com.wzrd.p.inteface.TimeInteface.TimeInfer;
import com.wzrd.v.view.calentar.CustomCalendar;
import com.wzrd.v.view.pick.PickerMinView;
import com.wzrd.v.view.pick.PickerView;
import com.wzrd.v.view.pick.TimeSelector;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarcletarActivity extends AppCompatActivity implements TimeInfer.getTime, AbsToolBarMenuPresenter{
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.cal)
    CustomCalendar cal;
    @BindView(R.id.hour_pv)
    PickerView hourPv;
    @BindView(R.id.hour_text)
    TextView hourText;
    @BindView(R.id.minute_pv)
    PickerMinView minutePv;
    @BindView(R.id.toolbar_back)
    ImageView mToolbarBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_menu)
    ImageView mToolbarMenu;
    @BindView(R.id.toolbar_menu_text)
    TextView mToolbarMenuText;
    private TimeSelector timeSelector;
    private String lastdate;
    private String lastfen;
    private String lasthours;
    private List<TSYSCONTANTS> list = new ArrayList<>();
    private boolean exituser;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carcletar);
        ButterKnife.bind(this);
        Utils.backToolbar(this, mToolbarBack, mToolbarTitle, "定时发送", mToolbarMenu, 0, this, mToolbarMenuText, "发送");
        ActivityCollector.addTimerActivity(this);
        ActivityCollector.addActivity(this);

        list.addAll((List<TSYSCONTANTS>) getIntent().getSerializableExtra("list"));
        exituser = (Boolean) getIntent().getExtras().get("id");


        //TODO 模拟请求当月数据
        final List<DayFinish> list = new ArrayList<>();

        int day = 30;
        Calendar c = Calendar.getInstance();
        // 取得系统日期
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day1 = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int fen = c.get(Calendar.MINUTE);
        String day0 = day1 + "";
        if (day1 < 10) {
            day0 = "0" + day1;
        } else {
            day0 = "" + day1;
        }
        String month0 = month + "";

        if (month < 10) {
            month0 = "0" + month;
        } else {
            month0 = "" + month;
        }
        lastdate = year + "年" + month0 + "月" + day0;

        Log.e("date", "year--->" + year + "---month---" + month);
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                day = 30;
            case 2:
                if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                    day = 29;

                } else {
                    day = 28;
                }
                break;
        }


        for (int i = 1; i < day; i++) {
            list.add(new DayFinish(i));
        }
        String month1 = year + "年" + month + "月";
        if (fen < 10) {
            lastfen = "0" + fen;
        } else {
            lastfen = "" + fen;
        }

        if (hour < 10) {
            lasthours = "0" + hour;
        } else {
            lasthours = hour + "";
        }


        tvTime.setText(DateUtils.getCurrentNomiaoDate());

        cal.setRenwu(month1, list);
        cal.setOnClickListener(new CustomCalendar.onClickListener() {
            @Override
            public void onLeftRowClick() {
//                Toast.makeText(CarcletarActivity.this, "点击减箭头", Toast.LENGTH_SHORT).show();
                cal.monthChange(-1);
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            CarcletarActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    cal.setRenwu(list);
                                }
                            });
                        } catch (Exception e) {
                        }
                    }
                }.start();
            }

            @Override
            public void onRightRowClick() {
//                Toast.makeText(CarcletarActivity.this, "点击加箭头", Toast.LENGTH_SHORT).show();
                cal.monthChange(1);
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            CarcletarActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    cal.setRenwu(list);
                                }
                            });
                        } catch (Exception e) {
                        }
                    }
                }.start();
            }

            @Override
            public void onTitleClick(String monthStr, Date month) {
//                Toast.makeText(CarcletarActivity.this, "点击了标题：" + monthStr, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onWeekClick(int weekIndex, String weekStr) {
//                Toast.makeText(CarcletarActivity.this, "点击了星期：" + weekStr, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDayClick(int day, String dayStr, DayFinish finish) {
                lastdate = dayStr;
//                Toast.makeText(CarcletarActivity.this, "点击了日期：" + dayStr, Toast.LENGTH_SHORT).show();
//                Log.e("dayStr", "点击了日期:" + dayStr);
                tvTime.setText(gettime());
            }
        });


        initfen(hour, fen);

    }

    private void initfen(int hour, int fen) {
        List<String> hostList = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            if (i <= 9) {
                hostList.add("0" + i);
            } else {
                hostList.add("" + i);
            }

        }

        hourPv.setData(hostList, hour, this);
        List<String> MinList = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            if (i <= 9) {
                MinList.add("0" + i);
            } else {
                MinList.add("" + i);
            }
        }

        minutePv.setData(MinList, fen, this);
        hourPv.setCanScroll(true);
        minutePv.setCanScroll(true);

    }

    @Override
    public void getHours(int hours) {
        Log.e("hours", "hours--->" + hours);
        if (hours < 10) {
            lasthours = "0" + hours;
        } else {
            lasthours = "" + hours;
        }

        tvTime.setText(gettime());
    }

    @Override
    public void getMin(int Min) {
        Log.e("Min", "Min--->" + Min);
        if (Min < 10) {
            lastfen = "0" + Min;
        } else {
            lastfen = "" + Min;
        }

        tvTime.setText(gettime());
    }


    public String gettime() {
        int a = lastdate.length();
        String s = lastdate.substring(a - 1, a);
        String lastdate1 = lastdate;
        if ("日".equals(s)) {
            lastdate1 = lastdate1 + "  ";
        } else {
            lastdate1 = lastdate + "日  ";
        }

        return lastdate1 + lasthours + ":" + lastfen;
    }

    @Override
    public void setToolBarMenu() {
        String lasttime = gettime();
        lasttime = lasttime.replace("年", "-");
        lasttime = lasttime.replace("月", "-");
        lasttime = lasttime.replace("日", "");
        lasttime = lasttime.replace("  ", " ");
        boolean time = Utils.compareTime(lasttime, DateUtils.getCurrentDate());
        if (time) {
            Intent intent = new Intent();
            intent.setAction(Constants.timeconstactsexit);
            Bundle bundle = new Bundle();
            bundle.putSerializable("list", (Serializable) list);
            bundle.putString("time", lasttime);
            intent.putExtra("id", exituser);
            intent.putExtras(bundle);
            sendBroadcast(intent);
            ActivityCollector.finishAllTimer();


        } else {
            Utils.ToastShort(this, "选择定时发送的时间必须大于当前时间,请重新选择");
        }
    }

    public class DayFinish {
        public int day;
//        public int all;
//        public int finish;

        public DayFinish(int day) {
            this.day = day;
//            this.all = all;
//            this.finish = finish;
        }
    }
}
