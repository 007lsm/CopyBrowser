package com.example.admin.myapplication.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Toast;

public abstract class BaseCustomActivity extends AppCompatActivity implements IBaseView{

    private ProgressDialog mProgressDialog;
    private FragmentManager mFragmentManager;

    /**
     * 初始化布局
     */
    public abstract void initContentView();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化控制中心
     */
    protected abstract void initPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initContentView();
        initPresenter();
        initView();
    }

    /**
     * 获取Fragment管理器
     * @return
     */
    public FragmentManager getBaseFragmentManager(){
        if(mFragmentManager == null) {
            mFragmentManager = getSupportFragmentManager();
        }
        return mFragmentManager;
    }

    /**
     * 获取Fragment事务管理器
     * @return
     */
    public FragmentTransaction getTransaction(){
        return getBaseFragmentManager().beginTransaction();
    }

    /**
     * 添加fragment
     * @param res
     * @param fragment
     * @param tag
     */
    public void addFragment(int res, Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getTransaction();
        fragmentTransaction.add(res, fragment);
        fragmentTransaction.commit();
    }

    /**
     * 移除fragment
     * @param res
     * @param fragment
     * @param tag
     */
    public void removeFragment(int res, Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
    }

    /**
     * 显示一个fragment
     * @param res
     * @param fragment
     * @param tag
     */
    public void showFragment(int res, Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getTransaction();
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void showProgress(boolean flag, String message) {
        if(mProgressDialog != null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setCancelable(flag);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setMessage(message);
        }
        mProgressDialog.show();
    }

    @Override
    public void showProgress(String message) {
        showProgress(true, message);
    }

    @Override
    public void showProgress() {
        showProgress("");
    }

    @Override
    public void hideProgress() {
        if(mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showToast(int resId) {
        showToast(getString(resId));
    }

    @Override
    public void showToast(String msg) {
        if(!isFinishing()) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void close() {
        finish();
    }
}
