package com.example.admin.myapplication.module.me;


import android.support.v4.app.Fragment;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindFragment extends BaseFragment {

    public static FindFragment getInstance(){
        FindFragment fragment = new FindFragment();
        return fragment;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_find;
    }

    @Override
    protected void managerArguments() {

    }
}
