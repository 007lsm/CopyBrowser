package com.example.admin.myapplication.model;

import android.os.Handler;

import com.example.admin.myapplication.config.Const;
import com.example.admin.myapplication.model.inter.LaunchModelImpl;
import com.example.admin.myapplication.model.inter.OnEnterIntoFinishListener;
import com.example.admin.myapplication.utils.BaseApplicationUtils;
import com.example.admin.myapplication.utils.SPUtils;

public class LaunchModel implements LaunchModelImpl {

    @Override
    public void skipToInfoJudge(final OnEnterIntoFinishListener onEnterIntoFinishListener) {
        boolean isFirstOpen = (boolean) SPUtils.get(BaseApplicationUtils.getContext(), Const.FIRST_OPEN, false);
        if(!isFirstOpen) {
            onEnterIntoFinishListener.isFirstOpen();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    onEnterIntoFinishListener.isNotFirstOpen();
                }
            },2000);
        }
    }
}
