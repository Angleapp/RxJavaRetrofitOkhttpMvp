package com.wzrd.v.activity.calendar;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.wzrd.R;
import com.wzrd.m.utils.DateUtils;
import com.wzrd.p.inteface.TimeInteface.TimeInfer;
import com.wzrd.v.view.calentar.CustomCalendar;
import com.wzrd.v.view.pick.PickerMinView;
import com.wzrd.v.view.pick.PickerView;
import com.wzrd.v.view.pick.TimeSelector;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarcletarActivity extends AppCompatActivity implements TimeInfer.getTime{
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
    private TimeSelector timeSelector;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carcletar);
        ButterKnife.bind(this);


        //TODO 模拟请求当月数据
        final List<DayFinish> list = new ArrayList<>();

        int day = 30;
        Calendar c = Calendar.getInstance();
        // 取得系统日期
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int fen = c.get(Calendar.MINUTE);


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
        tvTime.setText(DateUtils.getCurrentNomiaoDate());

        cal.setRenwu(month1, list);
        cal.setOnClickListener(new CustomCalendar.onClickListener() {
            @Override
            public void onLeftRowClick() {
                Toast.makeText(CarcletarActivity.this, "点击减箭头", Toast.LENGTH_SHORT).show();
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
//                Toast.makeText(CarcletarActivity.this, "点击了日期：" + dayStr, Toast.LENGTH_SHORT).show();
//                Log.w("", "点击了日期:" + dayStr);
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

        hourPv.setData(hostList, hour,this);
        List<String> MinList = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            if (i <= 9) {
                MinList.add("0" + i);
            } else {
                MinList.add("" + i);
            }
        }

        minutePv.setData(MinList,fen,this);

    }

    @Override
    public void getHours(int hours) {
Log.e("hours","hours--->"+hours);
    }

    @Override
    public void getMin(int Min) {
        Log.e("Min","Min--->"+Min);
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
