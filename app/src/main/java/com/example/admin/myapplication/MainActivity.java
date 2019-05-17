package com.example.admin.myapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.admin.myapplication.base.BaseCustomActivity;
import com.example.admin.myapplication.config.Const;
import com.example.admin.myapplication.config.StatusBarCompat;
import com.example.admin.myapplication.databean.EventBusMessage;
import com.example.admin.myapplication.module.FindFragment;
import com.example.admin.myapplication.module.me.MeFragment;
import com.example.admin.myapplication.module.NewsFragment;
import com.example.admin.myapplication.module.WechatFragment;
import com.example.admin.myapplication.utils.SPUtils;
import com.example.admin.myapplication.utils.StateBarTranslucentUtils;
import com.example.admin.myapplication.view.TabBar_Mains;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BaseCustomActivity implements View.OnClickListener {

    private FragmentManager manager;
    private FrameLayout frameMain;

    private TabBar_Mains newsMains;
    private TabBar_Mains wechatMains;
    private TabBar_Mains findMains;
    private TabBar_Mains meMainsMains;

    private NewsFragment newsFragment;
    private WechatFragment wechatFragment;
    private FindFragment findFragment;
    private MeFragment meFragment;

    private static final String NEWS_FRAGMENT = "NEWS_FRAGMENT";
    private static final String WECHAT_FRAGMENT = "WECHAT_FRAGMENT";
    private static final String FIND_FRAGMENT = "FIND_FRAGMENT";
    public static final String ME_FRAGMENT = "ME_FRAGMENT";

    public static final String FROM_FLAG = "FROM_FLAG";
    private static final String TAG = "MainActivity";

    boolean isRestart = false;//是否重启应用
    private String mCurrentIndex;

    @Override
    public void initContentView() {
        StateBarTranslucentUtils.setStateBarTranslucent(this);
        setContentView(R.layout.activity_main);
        StatusBarCompat.compat(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        frameMain = findViewById(R.id.frame_main);

        newsMains = findViewById(R.id.tb_recommend_mains);
        wechatMains = findViewById(R.id.tb_cityfinder_mains);
        findMains = findViewById(R.id.tb_findtravel_mains);
        meMainsMains = findViewById(R.id.tb_me_mains_mains);

        manager = getBaseFragmentManager();
        newsMains.setOnClickListener(this);
        wechatMains.setOnClickListener(this);
        findMains.setOnClickListener(this);
        meMainsMains.setOnClickListener(this);
        String startPage = WECHAT_FRAGMENT;
        String s = (String) SPUtils.get(this, Const.OPENNEWS, "nomagic");
        if(s.equals("magicopen")) {
            newsMains.setVisibility(View.VISIBLE);
            startPage = NEWS_FRAGMENT;
        }
        if(savedInstanceState != null) {
            initByRestart(savedInstanceState);
        } else {
            switchToFragment(startPage);
            mCurrentIndex = startPage;
        }

        //订阅事件
        EventBus.getDefault().register(this);
    }

    private void initByRestart(Bundle savedInstanceState) {
        mCurrentIndex = savedInstanceState.getString("mCurrentIndex");
        isRestart = true;
        Logger.t(TAG).i("恢复状态：" + mCurrentIndex);

        meFragment = (MeFragment) manager.findFragmentByTag(ME_FRAGMENT);
        newsFragment = (NewsFragment) manager.findFragmentByTag(NEWS_FRAGMENT);
        wechatFragment = (WechatFragment) manager.findFragmentByTag(WECHAT_FRAGMENT);
        findFragment = (FindFragment) manager.findFragmentByTag(FIND_FRAGMENT);
        switchToFragment(mCurrentIndex);
    }

    private void switchToFragment(String index) {
        hideAllFragment();
        switch (index){
            case NEWS_FRAGMENT:
                showNewsFragment();
                break;
            case WECHAT_FRAGMENT:
                showWechatFragment();
                break;
            case FIND_FRAGMENT:
                showFindFragment();
                break;
            case ME_FRAGMENT:
                showMeFragment();
                break;
            default:
        }
        mCurrentIndex = index;
    }

    private void showNewsFragment() {
        if(newsMains.getVisibility() != View.VISIBLE) {
            return;
        }
        if(!newsMains.isSelected()) {
            newsMains.setSelected(true);
        }
        if(newsFragment == null) {
            newsFragment = NewsFragment.getInstance();
            addFragment(R.id.frame_main, newsFragment, NEWS_FRAGMENT);
        }else {
            showFragment(newsFragment);
        }
    }

    private void showWechatFragment() {
        if(wechatMains.getVisibility() != View.VISIBLE) {
            return;
        }
        if(!wechatMains.isSelected()) {
            wechatMains.setSelected(true);
        }
        if(wechatFragment == null) {
            wechatFragment = WechatFragment.getInstance();
            addFragment(R.id.frame_main, wechatFragment, NEWS_FRAGMENT);
        }else {
            showFragment(wechatFragment);
        }
    }

    private void showFindFragment() {
        if(findMains.getVisibility() != View.VISIBLE) {
            return;
        }
        if(!findMains.isSelected()) {
            findMains.setSelected(true);
        }
        if(findFragment == null) {
            findFragment = FindFragment.getInstance();
            addFragment(R.id.frame_main, findFragment, NEWS_FRAGMENT);
        }else {
            showFragment(findFragment);
        }
    }

    private void showMeFragment() {
        if(meMainsMains.getVisibility() != View.VISIBLE) {
            return;
        }
        if(!meMainsMains.isSelected()) {
            meMainsMains.setSelected(true);
        }
        if(meFragment == null) {
            meFragment = MeFragment.getInstance();
            addFragment(R.id.frame_main, meFragment, NEWS_FRAGMENT);
        }else {
            showFragment(meFragment);
        }
    }

    private void hideAllFragment() {
        if(meFragment != null) {
            hideFragment(meFragment);
        }
        if(newsFragment != null) {
            hideFragment(newsFragment);
        }
        if(wechatFragment != null) {
            hideFragment(wechatFragment);
        }
        if(findFragment != null) {
            hideFragment(findFragment);
        }
        meMainsMains.setSelected(false);
        newsMains.setSelected(false);
        wechatMains.setSelected(false);
        findMains.setSelected(false);
    }

    @Override
    protected void initPresenter() {
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBusMessage msg) {
        Logger.t(TAG).i("EventBusMessage =" + msg.getMessage());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tb_recommend_mains:
                if(!mCurrentIndex.equals(NEWS_FRAGMENT)) {
                    switchToFragment(NEWS_FRAGMENT);
                }
                break;
            case R.id.tb_cityfinder_mains:
                if(!mCurrentIndex.equals(WECHAT_FRAGMENT)) {
                    switchToFragment(WECHAT_FRAGMENT);
                }
                break;
            case R.id.tb_findtravel_mains:
                if(!mCurrentIndex.equals(FIND_FRAGMENT)) {
                    switchToFragment(FIND_FRAGMENT);
                }
                break;
            case R.id.tb_me_mains_mains:
                if(!mCurrentIndex.equals(ME_FRAGMENT)) {
                    switchToFragment(ME_FRAGMENT);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("mCurrentIndex", mCurrentIndex);
        Logger.t(TAG).i("保存状态" + mCurrentIndex);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (Fragment fragment: getBaseFragmentManager().getFragments()) {
            getTransaction().remove(fragment);
        }
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    long mExitTime;
    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}
