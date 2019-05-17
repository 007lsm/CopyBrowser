package com.example.admin.myapplication.utils;

import android.content.Context;

import com.example.admin.myapplication.base.BaseApplication;


public class DensityUtils {
    public static int dip2px(float dpValue) {
        return (int) (dpValue * BaseApplication.fDensity + 0.5f);
    }

    public static int px2dip(float pxValue) {
        return (int) (pxValue / BaseApplication.fDensity + 0.5f);
    }
}
