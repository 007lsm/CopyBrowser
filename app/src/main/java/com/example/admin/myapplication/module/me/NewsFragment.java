package com.example.admin.myapplication.module.me;


import android.support.v4.app.Fragment;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends BaseFragment {

    public static NewsFragment getInstance(){
        NewsFragment fragment = new NewsFragment();
        return fragment;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_news;
    }

    @Override
    protected void managerArguments() {

    }
}
