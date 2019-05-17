package com.example.admin.myapplication.module.me.calendar;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.icu.util.Calendar;
import android.text.style.LineBackgroundSpan;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.utils.DensityUtils;
import com.example.admin.myapplication.utils.LunarUtils;


public class LunarSpan implements LineBackgroundSpan {
    String mYear;
    String mMonth;

    public LunarSpan(String year, String month) {
        mYear = year;
        mMonth = month;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void drawBackground(Canvas canvas, Paint paint, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lineNumber) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.valueOf(mYear), Integer.valueOf(mMonth) - 1, Integer.valueOf(text.toString()));
        LunarUtils lunarUtils = new LunarUtils(calendar.getTime());

        String chinaDayString = lunarUtils.getChinaDayString();
        Paint paintText = new Paint();
        paintText.setTextSize(DensityUtils.dip2px(10));
        paint.setColor(R.color.color_calendar_lunar_show);
        canvas.drawText(chinaDayString, (right - left)/2 - DensityUtils.dip2px(10), (bottom - top)/2 + DensityUtils.dip2px(17), paint);
    }
}
