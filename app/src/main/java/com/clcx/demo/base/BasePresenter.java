package com.clcx.demo.base;

import android.content.Context;

/**
 * Created by baixiaokang on 16/4/22.
 */
public abstract class BasePresenter<M, T> {
    public Context context;
    public M mModel;
    public T mView;

    public void setVM(T v, M m) {
        this.mView = v;
        this.mModel = m;
        this.onStart();
    }

    public abstract void onStart();

}
