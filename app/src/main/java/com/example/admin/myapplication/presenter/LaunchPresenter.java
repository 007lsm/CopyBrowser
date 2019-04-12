package com.example.admin.myapplication.presenter;


import android.app.Activity;
import android.support.v4.app.ActivityCompat;

import com.example.admin.myapplication.LaunchActivity;
import com.example.admin.myapplication.model.LaunchModel;
import com.example.admin.myapplication.model.inter.OnEnterIntoFinishListener;
import com.example.admin.myapplication.utils.PermissionUtils;
import com.example.admin.myapplication.view.LaunchView;

import java.lang.ref.WeakReference;

import permissions.dispatcher.PermissionRequest;

public class LaunchPresenter {

    private LaunchModel launchModel;
    private LaunchView mLaunchView;
    private static final String[] PERMISSION_STARTWELCOMEGUIDEACTIVITY = new
            String[]{"android.permission.READ_CONTACTS", "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"};

    public LaunchPresenter(LaunchView launchView){
        mLaunchView = launchView;
        launchModel = new LaunchModel();
    }

    private void skipToPage(){
        launchModel.skipToInfoJudge(new OnEnterIntoFinishListener() {
            @Override
            public void isFirstOpen() {
                startPageCheckPermission();
            }

            @Override
            public void isNotFirstOpen() {
                mLaunchView.skipToLoginPage();
            }
        });
    }

    public void startPageCheckPermission() {
        LaunchActivity launchActivity = null;
        if(mLaunchView != null && mLaunchView instanceof LaunchActivity) {
            launchActivity = (LaunchActivity) mLaunchView;
        }
        if (PermissionUtils.checkMoreIsPermission(launchActivity, PERMISSION_STARTWELCOMEGUIDEACTIVITY)) {
//            launchActivity.skipToWelcomePage();
        } else {
            launchActivity.showRationale(new StartWelcomeGuideActivityPermissionRequest(launchActivity));
        }
    }

    private static final class StartWelcomeGuideActivityPermissionRequest implements PermissionRequest {
        private final WeakReference<LaunchActivity> weakTarget;

        private StartWelcomeGuideActivityPermissionRequest(LaunchActivity target) {
            this.weakTarget = new WeakReference<>(target);
        }

        @Override
        public void proceed() {
            Activity target = weakTarget.get();
            if (target == null) return;
            ActivityCompat.requestPermissions(target, PERMISSION_STARTWELCOMEGUIDEACTIVITY, 3);
        }

        @Override
        public void cancel() {
            LaunchActivity target = weakTarget.get();
            if (target == null) return;
            target.showDeniedForCamera();
        }
    }
}
