package com.example.admin.myapplication;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.admin.myapplication.config.Const;
import com.example.admin.myapplication.presenter.LaunchPresenter;
import com.example.admin.myapplication.utils.BaseApplicationUtils;
import com.example.admin.myapplication.utils.PermissionUtils;
import com.example.admin.myapplication.utils.SPUtils;
import com.example.admin.myapplication.utils.StateBarTranslucentUtils;
import com.example.admin.myapplication.view.LaunchView;
import com.flaviofaria.kenburnsview.KenBurnsView;

import java.lang.ref.WeakReference;

import permissions.dispatcher.PermissionRequest;

public class LaunchActivity extends Activity implements LaunchView {
    private KenBurnsView ivKenBurnsView;
    private ImageView ivCat;
    private TextView tvAppName;
    Animation anim;
    ObjectAnimator alphaAnimation;
    LaunchPresenter mLaunchPresenter;

    private static final int REQUEST = 200;
    private static final String[] PERMISSION_STARTWELCOMEGUIDEACTIVITY = new
            String[]{"android.permission.READ_CONTACTS", "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_launch);
        initContentView();
        mLaunchPresenter = new LaunchPresenter(this);
        boolean isFirstOpen = (boolean) SPUtils.get(this, Const.FIRST_OPEN, true);
        mLaunchPresenter.skipToPage(isFirstOpen);
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
        startWelcomeGuideActivityWithCheck();
    }

    private void skipToPage(Class cls) {
        SPUtils.put(this, Const.FIRST_OPEN, false);
        startActivity(new Intent(this, cls));
        finish();
    }

    private void startWelcomeGuideActivityWithCheck(){
        if (PermissionUtils.hasSelfPermissions(this, PERMISSION_STARTWELCOMEGUIDEACTIVITY)) {//有权限
            skipToPage(WelcomeActivity.class);
        } else {//没有权限
            if (PermissionUtils.shouldShowRequestPermissionRationale(this, PERMISSION_STARTWELCOMEGUIDEACTIVITY)) {//禁止不再访问
                showRationaleForCamera(new StartWelcomeGuideActivityPermissionRequest(this));
            } else {
                ActivityCompat.requestPermissions(this, PERMISSION_STARTWELCOMEGUIDEACTIVITY, REQUEST);
            }
        }
    }

    void showRationaleForCamera(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("有部分权限需要你的授权")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .show();
    }

    private static final class StartWelcomeGuideActivityPermissionRequest implements PermissionRequest {
        private final WeakReference<LaunchActivity> weakTarget;

        private StartWelcomeGuideActivityPermissionRequest(LaunchActivity target) {
            this.weakTarget = new WeakReference<>(target);
        }

        @Override
        public void proceed() {
            LaunchActivity target = weakTarget.get();
            if (target == null) return;
            ActivityCompat.requestPermissions(target, PERMISSION_STARTWELCOMEGUIDEACTIVITY, REQUEST);
        }

        @Override
        public void cancel() {
            LaunchActivity target = weakTarget.get();
            if (target == null) return;
            target.showPermissionDenied();
        }
    }

    @Override
    public void skipToLoginPage() {
        SPUtils.put(this, Const.FIRST_OPEN, false);
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            switch (requestCode) {
            case REQUEST:
                if (PermissionUtils.getTargetSdkVersion(this) < 23 && !PermissionUtils.hasSelfPermissions(this, PERMISSION_STARTWELCOMEGUIDEACTIVITY)) {
                    showPermissionDenied();
                    return;
                }
                if (PermissionUtils.verifyPermissions(grantResults)) {//是否申请了
                    skipToPage(WelcomeActivity.class);
                } else {
                    if (!PermissionUtils.shouldShowRequestPermissionRationale(this, PERMISSION_STARTWELCOMEGUIDEACTIVITY)) {
                        Toast.makeText(this, "不再询问授权权限！", Toast.LENGTH_SHORT).show();
                        skipToPage(WelcomeActivity.class);
                    } else {
                        showPermissionDenied();
                    }
                }
            break;
            default:
        }
    }

    public void showPermissionDenied(){
        new AlertDialog.Builder(this)
                .setTitle("权限说明")
                .setCancelable(false)
                .setMessage("本应用需要部分必要的权限，如果不授予可能会影响正常使用！")
                .setNegativeButton("退出应用",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setPositiveButton("赋予权限", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startWelcomeGuideActivityWithCheck();
                    }
                }).create().show();
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
}
