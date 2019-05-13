package com.example.admin.myapplication.test;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.util.List;

public class TestService extends Service {

    private static final String TAG = "TestService";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent1 = new Intent(getBaseContext(), TestActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
//                getPositonPermission();
            }
        }, 20000);
        return super.onStartCommand(intent, flags, startId);
    }

    private void getPositonPermission() {
        boolean backgroundLocationPermissionApproved = ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED;
        if (backgroundLocationPermissionApproved) {
            //拥有权限时，可以直接获取到用户位置
        }else {
            ActivityCompat.requestPermissions((Activity) getBaseContext(), new String[] {
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION}, 200);
        }
        getCurrentPosition();
    }

    private void getCurrentPosition() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // 返回所有已知的位置提供者的名称列表，包括未获准访问或调用活动目前已停用的。
        List<String> lp = lm.getAllProviders();
        for (String item : lp) {
            Log.i(TAG, "可用位置服务：" + item);
        }
        Criteria criteria = new Criteria();
        criteria.setCostAllowed(false);
        //设置位置服务免费
        criteria.setAccuracy(Criteria.ACCURACY_COARSE); //设置水平位置精度
        String providerName = lm.getBestProvider(criteria, true);
        @SuppressLint("MissingPermission") Location location = lm.getLastKnownLocation(providerName);
        Log.i(TAG, "location ="+location);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
