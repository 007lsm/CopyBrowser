package com.example.admin.myapplication.module.me.calendar;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

/**
 * 日历添加农历标志
 */
public class LunarDecorator implements DayViewDecorator {

    private String mYear;
    private String mMonth;

    public LunarDecorator(String year, String month) {
        this.mYear = year;
        this.mMonth = month;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return true;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new LunarSpan(mYear, mMonth));
    }

   public void setYear(String year){
       mYear = year;
   }

   public void setMonth(String month){
       mMonth = month;
   }
}
