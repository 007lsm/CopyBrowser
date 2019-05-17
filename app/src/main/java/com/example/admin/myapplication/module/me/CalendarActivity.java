package com.example.admin.myapplication.module.me;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.base.BaseCustomActivity;
import com.example.admin.myapplication.module.me.calendar.EventDecorator_Holiday;
import com.example.admin.myapplication.module.me.calendar.HighlightWeekendsDecorator;
import com.example.admin.myapplication.module.me.calendar.LunarDecorator;
import com.example.admin.myapplication.module.me.calendar.TodayDecorator;
import com.example.admin.myapplication.utils.DateUtils;
import com.orhanobut.logger.Logger;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;

import java.util.Calendar;
import java.util.Date;


public class CalendarActivity extends BaseCustomActivity implements OnMonthChangedListener, OnDateSelectedListener, View.OnClickListener {

    MaterialCalendarView calendarView;
    LunarDecorator mLunarDecorator;
    private Toolbar toolbarCalendar;
    private MaterialCalendarView calendarViewCalendar;
    private TextView distanceTodaynumberCalendar;
    private TextView beforeorbackCalendar;
    private TextView nongliDateCalendar;
    private TextView holidaynameDateCalendar;
    private TextView cyclicalCalendar;
    private TextView animalsYearCalendar;
    private TextView backTodayCalendar;
    private TextView monthViewCalendar;
    private TextView weekViewCalendar;
    private CalendarDay today;
    private static String TAG = "CalendarActivity";

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_calendar);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        calendarView = findViewById(R.id.calendarView_calendar);
        toolbarCalendar = findViewById(R.id.toolbar_calendar);
        distanceTodaynumberCalendar = findViewById(R.id.distance_todaynumber_calendar);
        beforeorbackCalendar = findViewById(R.id.beforeorback_calendar);
        nongliDateCalendar = findViewById(R.id.nongli_date_calendar);
        holidaynameDateCalendar = findViewById(R.id.holidayname_date_calendar);
        cyclicalCalendar = findViewById(R.id.cyclical_calendar);
        animalsYearCalendar = findViewById(R.id.animals_year_calendar);
        backTodayCalendar = findViewById(R.id.back_today_calendar);
        monthViewCalendar = findViewById(R.id.month_view_calendar);
        weekViewCalendar = findViewById(R.id.week_view_calendar);
        initToolbar();
        //获取今天
        today = CalendarDay.today();
        initToDayInfo(today.getDate());
        calendarView.setShowOtherDates(MaterialCalendarView.SHOW_DEFAULTS);
        //月模式
        calendarView.state().edit().setCalendarDisplayMode(CalendarMode.MONTHS).commit();
        //设置选中今天
        calendarView.setSelectedDate(today);
        //获取年月转换成String
        String year = DateUtils.date2String(today.getDate(), "yyyy");
        String month = DateUtils.date2String(today.getDate(), "MM");
        //文字下新增农历
        mLunarDecorator = new LunarDecorator(year, month);
        //当点击头和尾处的其他月的日期时，跳转到其他月
        calendarView.setAllowClickDaysOutsideCurrentMonth(true);
        calendarView.setOnMonthChangedListener(this);
        calendarView.setOnDateChangedListener(this);
        HolidaysManager manager = new HolidaysManager();
        //添加修饰
        calendarView.addDecorators(
                new TodayDecorator(),//当前日期圆环
                new HighlightWeekendsDecorator(),//周末高亮
                mLunarDecorator, //新增农历
                new EventDecorator_Holiday(manager.mDateMap));

        //设置title文字
        calendarView.setTitleFormatter(new TitleFormatter() {
            @Override
            public CharSequence format(CalendarDay day) {
                StringBuffer buffer = new StringBuffer();
                return buffer.append(day.getYear()).append("年").append(day.getMonth()).append("月");
            }
        });

        backTodayCalendar.setOnClickListener(this);
        monthViewCalendar.setOnClickListener(this);
        weekViewCalendar.setOnClickListener(this);
    }

    private void initToDayInfo(Date date) {

    }

    private void initToolbar() {
        setSupportActionBar(toolbarCalendar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        //避免月份变化时，获取到的农历不对
        mLunarDecorator.setYear(DateUtils.date2String(date.getDate(), "yyyy"));
        mLunarDecorator.setMonth(DateUtils.date2String(date.getDate(), "MM"));
        widget.invalidateDecorators();
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Logger.t(TAG).i("itemId"+ item.getItemId());
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.year_view:
                break;
            case R.id.skip_to_day:
                skipToDay();
                break;
            case R.id.holidays_view:
                showHolidays();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void skipToDay() {
        Calendar calendar = today.getCalendar();
        final DatePickerDialog dialog = new DatePickerDialog(this, null, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "完成", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatePicker datePicker = dialog.getDatePicker();
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int day = datePicker.getDayOfMonth();
                CalendarDay calendarDay = CalendarDay.from(year, month, day);
                calendarView.setCurrentDate(calendarDay);
                calendarView.setSelectedDate(calendarDay);
            }
        });

        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        dialog.show();
    }

    private void showHolidays() {
        View view = LayoutInflater.from(this).inflate(R.layout.holiday_list, null);
        RecyclerView recyclerView = view.findViewById(R.id.holidays_recy);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        HolidayAdapter adapter = new HolidayAdapter();
        recyclerView.setAdapter(adapter);

        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);
        dialog.show();
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, String text) {
                calendarView.setCurrentDate(CalendarDay.from(2019,mHolidayDate[position][0]-1,mHolidayDate[position][1]));
                calendarView.setSelectedDate(CalendarDay.from(2019,mHolidayDate[position][0]-1,mHolidayDate[position][1]));
                dialog.dismiss();
            }
        });
    }

    static String [] mHolidayNameArray = {"元旦","春节","清明节","劳动节","端午节","中秋国庆"};
    static String [] mHolidayInfoArray = {"1月1日放假，1月2日（星期一）补休。",
            "1月27日至2月2日放假调休，共7天。1月22日（星期日）、2月4日（星期六）上班。",
            "4月2日至4日放假调休，共3天。4月1日（星期六）上班。",
            "5月1日放假，与周末连休。",
            "5月28日至30日放假调休，共3天。5月27日（星期六）上班。",
            "10月1日至8日放假调休，共8天。9月30日（星期六）上班。"};
    static int[][] mHolidayDate={{1,1},{1,27},{4,2},{5,1},{5,28},{10,1}};

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_today_calendar:
                calendarView.setSelectedDate(today);
                break;
            case R.id.month_view_calendar:
                calendarView.state().edit().setCalendarDisplayMode(CalendarMode.MONTHS).commit();
                break;
            case R.id.week_view_calendar:
                calendarView.state().edit().setCalendarDisplayMode(CalendarMode.WEEKS).commit();
                break;
        }
    }

    class HolidayAdapter extends RecyclerView.Adapter<HolidayAdapter.Holder>{

        private OnItemClickListener mItemClickListener;

        public void setOnItemClickListener(OnItemClickListener li) {
            mItemClickListener = li;
        }

        @NonNull
        @Override
        public HolidayAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holiday_list_item, parent, false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final HolidayAdapter.Holder holder, int position) {
            holder.holiday_name.setText(mHolidayNameArray[position]);
            holder.holiday_info.setText(mHolidayInfoArray[position]);
            if(mItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemClickListener.onItemClick(holder.getLayoutPosition(),
                                holder.holiday_name.getText().toString());
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return mHolidayNameArray.length;
        }

        public class Holder extends RecyclerView.ViewHolder{
            TextView holiday_name;
            TextView holiday_info;
            public Holder(View itemView) {
                super(itemView);
                holiday_name = itemView.findViewById(R.id.name_holiday_item);
                holiday_info = itemView.findViewById(R.id.info_holiday_item);
            }
        }
    }

    interface OnItemClickListener {
        void onItemClick(int position, String text);
    }

    @Override
    protected void initPresenter() {
    }
}
