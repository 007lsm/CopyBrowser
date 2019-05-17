package com.example.admin.myapplication.module.me.calendar;

import android.text.TextUtils;

import com.example.admin.myapplication.module.me.HolidaysManager;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Map;

public class EventDecorator_Holiday implements DayViewDecorator {

    private Map<String, String> mDateStrMap;

    public EventDecorator_Holiday (Map<String, String> map) {
        mDateStrMap = map;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        String formatDate = HolidaysManager.formatDate(day.getDate());
        boolean isIncludeHoliday = mDateStrMap.containsKey(formatDate);
        if(isIncludeHoliday) {
            String s = mDateStrMap.get(formatDate);
            if(!TextUtils.isEmpty(s)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new EventSpan_Holiday());
    }
}
