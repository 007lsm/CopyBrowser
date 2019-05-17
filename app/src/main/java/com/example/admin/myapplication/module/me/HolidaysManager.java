package com.example.admin.myapplication.module.me;

import android.icu.util.Calendar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HolidaysManager {
    Map<String, String> mDateMap;
    private Calendar calendar = Calendar.getInstance();

    public  HolidaysManager () {
        mDateMap = new HashMap<>();
        //元旦
        mDateMap.put(getDate( 01, 01), "元旦");
        mDateMap.put(getDate( 01, 02), "");
        //春节
        mDateMap.put(getDate( 01, 22), "");
        mDateMap.put(getDate( 01, 27), "春节");
        mDateMap.put(getDate( 01, 28), "春节");
        mDateMap.put(getDate( 01, 29), "春节");
        mDateMap.put(getDate( 01, 30), "春节");
        mDateMap.put(getDate( 01, 31), "春节");
        mDateMap.put(getDate( 02, 02), "春节");
        mDateMap.put(getDate( 02, 04), "");
        //清明节
        mDateMap.put(getDate( 04, 02), "清明节");
        mDateMap.put(getDate( 04, 03), "清明节");
        mDateMap.put(getDate( 04, 04), "清明节");
        mDateMap.put(getDate( 04, 01), "");
        //劳动节
        mDateMap.put(getDate( 05, 01), "劳动节");
        mDateMap.put(getDate( 04, 29), "劳动节");
        mDateMap.put(getDate( 04, 30), "劳动节");
        //端午节
        mDateMap.put(getDate( 05, 27), "");
        mDateMap.put(getDate( 05, 28), "端午节");
        mDateMap.put(getDate( 05, 29), "端午节");
        mDateMap.put(getDate( 05, 30), "端午节");
        //中秋节、国庆节
        mDateMap.put(getDate( 10, 01), "国庆节");
        mDateMap.put(getDate( 10, 02), "国庆节");
        mDateMap.put(getDate( 10, 03), "国庆节");
        mDateMap.put(getDate( 10, 04), "中秋节");
        mDateMap.put(getDate( 10, 05), "中秋节");
        mDateMap.put(getDate( 10, 06), "国庆节");
        mDateMap.put(getDate( 10, 07), "国庆节");
        mDateMap.put(getDate( 10, 8), "国庆节");
        mDateMap.put(getDate( 9, 30), "");
    }

    private String getDate(int month, int day) {
        calendar.set(0,month -1, day);
        Date time = calendar.getTime();
        return formatDate(time);
    }

    public static String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        String formatHoliday = format.format(date);
        return formatHoliday;
    }


}
