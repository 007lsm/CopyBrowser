package com.example.admin.myapplication.module.me;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends BaseFragment {

    public static MeFragment getInstance(){
        MeFragment fragment = new MeFragment();
        return fragment;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_me;
    }

    @Override
    protected void managerArguments() {

    }
}
