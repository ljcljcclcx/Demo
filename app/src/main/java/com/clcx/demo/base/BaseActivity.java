package com.clcx.demo.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.lang.ref.WeakReference;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/4/5.
 */
public abstract class BaseActivity<T extends BasePresenter, E extends BaseModel> extends FragmentActivity
        implements
        View.OnClickListener {
    public boolean isNight;
    public T mPresenter;
    public E mModel;
    public Context mContext;

    private LinearLayout statebar_view;
    protected TextView toolbar_title;

    private boolean hasToolbar;

    //是否已经初始化
    private boolean isInit = false;

    /***
     * 整个应用Applicaiton
     **/
    private MyApplication mApplication = null;
    /**
     * 当前Activity的弱引用，防止内存泄露
     **/
    private WeakReference<Activity> context = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        isNight = SpUtil.isNight();
//        setTheme(isNight ? R.style.AppThemeNight : R.style.AppThemeDay);
        this.setContentView(this.getLayoutId());
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        ButterKnife.bind(this);
        mContext = this;
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (this instanceof BaseView) mPresenter.setVM(this, mModel);
        // 获取应用Application
        mApplication = MyApplication.getInstance();

        // 将当前Activity压入栈
        context = new WeakReference<Activity>(this);
        mApplication.pushTask(context);

        try {
            hasToolbar = true;
        } catch (Exception e) {
            hasToolbar = false;
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (isNight != SpUtil.isNight()) reload();
    }

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }


    public abstract int getLayoutId();

    public abstract void initView();

    /**
     * 沉浸式重新设置高度
     *
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasToolbar && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            //沉浸式，把系统标题栏的margin隔出来
            Rect frame = new Rect();
            getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            // 状态栏高度
//            int statusBarHeight = frame.top == 0 ? getResources().getDimensionPixelOffset(R.dimen
//                    .content_list_padding_20) : frame.top;
            int statusBarHeight = frame.top;
            try {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams
                        .MATCH_PARENT,
                        frame.top);
                statebar_view.setLayoutParams(params);
            } catch (Exception e) {
            }
        }
        //----------------------------------
        if (!isInit) {
            isInit = true;
            this.initView();
        }

    }

    protected void setTitleToolbar(String title) {
        if (toolbar_title != null) {
            toolbar_title.setText(title);
        }
    }
}
