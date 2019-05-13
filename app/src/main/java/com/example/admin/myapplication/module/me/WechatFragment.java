package com.example.admin.myapplication.module.me;


import android.support.v4.app.Fragment;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class WechatFragment extends BaseFragment {

    public static WechatFragment getInstance(){
        WechatFragment fragment = new WechatFragment();
        return fragment;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_wechat;
    }

    @Override
    protected void managerArguments() {

    }
}
