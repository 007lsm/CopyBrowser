package com.example.admin.myapplication.module.me.calendar;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.utils.DensityUtils;

public class AnnulusSpan implements LineBackgroundSpan {
    @SuppressLint("ResourceAsColor")
    @Override
    public void drawBackground(Canvas canvas, Paint p, int left, int right, int top, int baseline, int i4, CharSequence charSequence, int i5, int i6, int i7) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);//消除锯齿
        paint.setStyle(Paint.Style.STROKE);//绘制空心圆
        int ringWidth = DensityUtils.dip2px(1);//圆环宽度
        paint.setColor(R.color.color_calendar_ring_show);
        paint.setStrokeWidth(ringWidth);
        canvas.drawCircle((right - left)/2, (i4 - top)/2, right/2-DensityUtils.dip2px(1), paint);
    }
}
