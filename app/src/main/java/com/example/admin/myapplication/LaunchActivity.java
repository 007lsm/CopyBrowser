package com.example.admin.myapplication;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.myapplication.config.Const;
import com.example.admin.myapplication.presenter.LaunchPresenter;
import com.example.admin.myapplication.utils.SPUtils;
import com.example.admin.myapplication.utils.StateBarTranslucentUtils;
import com.example.admin.myapplication.view.LaunchView;
import com.flaviofaria.kenburnsview.KenBurnsView;

import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;

public class LaunchActivity extends Activity implements LaunchView {
    private KenBurnsView ivKenBurnsView;
    private ImageView ivCat;
    private TextView tvAppName;
    Animation anim;
    ObjectAnimator alphaAnimation;
    LaunchPresenter mLaunchPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_launch);
        initContentView();
        mLaunchPresenter = new LaunchPresenter(this);
        mLaunchPresenter.startPageCheckPermission();
    }

    public void initContentView() {
        StateBarTranslucentUtils.setStateBarTranslucent(this);
        ivKenBurnsView = findViewById(R.id.iv_kenBurnsView);
        ivCat =  findViewById(R.id.iv_cat);
        tvAppName = findViewById(R.id.tv_appName);
        Glide.with(this)
                .load(R.drawable.welcometoqbox)
                .into(ivKenBurnsView);
        animationCat();
        animationAppName();
    }

    @Override
    public void skipToWelcomePage() {
        SPUtils.put(this, Const.FIRST_OPEN, true);
        startActivity(new Intent(this, WelcomeActivity.class));
    }

    @Override
    public void skipToLoginPage() {
        SPUtils.put(this, Const.FIRST_OPEN, true);
        startActivity(new Intent(this, LoginActivity.class));
    }

    private void animationCat() {
        ivCat.setAlpha(1.0F);
        anim = AnimationUtils.loadAnimation(this, R.anim.translate_top_to_center);
        ivCat.startAnimation(anim);
    }

    private void animationAppName() {
        alphaAnimation = ObjectAnimator.ofFloat(tvAppName, "alpha", 0.0F, 1.0F);
        alphaAnimation.setStartDelay(1700);
        alphaAnimation.setDuration(500);
        alphaAnimation.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ivKenBurnsView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(ivKenBurnsView != null) {
            ivKenBurnsView.pause();
        }
        if(anim != null) {
            anim = null;
        }
        if(alphaAnimation != null) {
            alphaAnimation = null;
        }
    }

    @OnShowRationale({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
        //给用户解释要请求什么权限，为什么需要此权限
    public void showRationale(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("使用此功能需要WRITE_EXTERNAL_STORAGE和RECORD_AUDIO权限，下一步将继续请求权限")
                .setPositiveButton("下一步", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();//继续执行请求
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                request.cancel();//取消执行请求
            }
        }).show();
    }

    /**
     * 如果用户不授予权限调用的方法
     */
    @OnPermissionDenied({Manifest.permission.READ_CONTACTS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA,
            /*Manifest.permission.WRITE_CONTACTS,*/
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void showDeniedForCamera() {
        new AlertDialog.Builder(this)
                .setMessage("使用此功能需要WRITE_EXTERNAL_STORAGE和RECORD_AUDIO权限，下一步将继续请求权限")
                .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton("重试", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mLaunchPresenter.startPageCheckPermission();
            }
        }).show();
    }
}
