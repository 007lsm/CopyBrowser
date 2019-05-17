package com.example.admin.myapplication.module.me.calendar;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.LineBackgroundSpan;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.utils.DensityUtils;

public class EventSpan_Holiday implements LineBackgroundSpan {
    @SuppressLint("ResourceAsColor")
    @Override
    public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom,
                               CharSequence text, int start, int end, int lnum) {

        Paint paint = new Paint();
        paint.setColor(R.color.color_holiday_frame);
        RectF rectF = new RectF(0 , (-(right - bottom)/2), DensityUtils.dip2px(18),
                (-(right-bottom)/2)+ DensityUtils.dip2px(18));
        c.drawRoundRect(rectF, 0, 0, paint);

        paint.setTextSize(DensityUtils.dip2px(14));
        paint.setColor(Color.WHITE);
        c.drawColor(R.color.color_holiday_text);
        c.drawText("休", DensityUtils.dip2px(2), (-(right-bottom)/2)+ DensityUtils.dip2px(14), paint);
    }
}
