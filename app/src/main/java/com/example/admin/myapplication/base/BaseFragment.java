package com.example.admin.myapplication.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {

    private View mLayoutView;
    private BaseCustomActivity mActivity;

    /**
     * 初始化布局文件Id
     */
    protected abstract void initView();

    /**
     *初始化布局
     * @return
     */
    protected abstract int getLayoutRes();

    /**
     * 如果Fragment创建需要数据，在这里接受传进来的数据
     * 如果没有这个抽象方法就空实现
     */
    protected abstract void managerArguments();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            managerArguments();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(mLayoutView != null) {
            ViewGroup parent = (ViewGroup) mLayoutView.getParent();
            if(parent != null) {
                parent.removeView(mLayoutView);
            }
        } else {
            mLayoutView = getCreateView(inflater, container);
            initView();
        }
        return mLayoutView;
    }

    /**
     * 获取Fragment布局文件的VIew
     * @param inflater
     * @param viewGroup
     * @return
     */
    private View getCreateView(LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(getLayoutRes(), viewGroup, false);
    }

    /**
     * 判断Fragment当前状态
     * True代表正常，false代表未加载或者正在删除
     * @return
     */
    protected boolean getStatus() {
        return (isAdded() && !isRemoving());
    }

    public BaseCustomActivity GetBaseActivity() {
        if(mActivity == null) {
            mActivity = (BaseCustomActivity) getActivity();
        }
        return mActivity;
    }
}
