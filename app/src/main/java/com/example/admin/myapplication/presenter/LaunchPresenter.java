package com.example.admin.myapplication.presenter;


import com.example.admin.myapplication.model.LaunchModel;
import com.example.admin.myapplication.model.inter.OnEnterIntoFinishListener;
import com.example.admin.myapplication.view.LaunchView;
public class LaunchPresenter {

    private LaunchModel launchModel;
    private LaunchView mLaunchView;

    public LaunchPresenter(LaunchView launchView){
        mLaunchView = launchView;
        launchModel = new LaunchModel();
    }

    public void skipToPage(boolean isFirstOpen){
        launchModel.skipToInfoJudge(isFirstOpen, new OnEnterIntoFinishListener() {
            @Override
            public void isFirstOpen() {
                mLaunchView.skipToWelcomePage();
            }

            @Override
            public void isNotFirstOpen() {
                mLaunchView.skipToLoginPage();
            }
        });
    }
}
