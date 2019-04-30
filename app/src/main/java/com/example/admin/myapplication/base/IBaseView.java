package com.example.admin.myapplication.base;

import android.content.Context;

public interface IBaseView {

    /**
     * 显示可取消得进度条
     * @param flag 是否可取消
     * @param message
     */
    void showProgress(boolean flag, String message);

    /**
     * 显示进度条
     * @param message
     */
    void showProgress(String message);

    /**
     * 显示可取消的无文字进度条
     */
    void showProgress();

    /**
     * 隐藏进度条
     */
    void hideProgress();

    /**
     * 根据资源文件id弹出toast
     * @param resId
     */
    void showToast(int resId);

    /**
     * 根据字符串弹出toast
     * @param msg
     */
    void showToast(String msg);

    /**
     * 获取当前上下文对象
     */
    Context getContext();

    /**
     * 结束当前页面
     */
    void close();
}
