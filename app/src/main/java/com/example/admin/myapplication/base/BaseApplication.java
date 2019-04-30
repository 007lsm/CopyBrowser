package com.example.admin.myapplication.base;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.BuildConfig;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;


public class BaseApplication extends Application {

    private static BaseApplication mBaseApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mBaseApplication = this;
        initLogger();
    }

    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(0)
                .tag("TAG")
                .build();
        //打印到文件 对应目录
        Logger.addLogAdapter(new DiskLogAdapter(formatStrategy){
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
        //logcat打印
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy){
            @Override
            public boolean isLoggable(int priority, String tag) {
                if(priority == Logger.VERBOSE) { //日志级别过滤
                    return true;
                }
                return true;
            }
        });
    }

    public static Context getApplicationContexts() {
        return mBaseApplication;
    }

    /**
     * 获得版本名称
     * @return
     */
    public static String getVersionName(){
        try {
            PackageManager manager = mBaseApplication.getPackageManager();
            PackageInfo info = manager.getPackageInfo(mBaseApplication.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

}
