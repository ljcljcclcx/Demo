package com.clcx.demo.ui.hotfix;

import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.clcx.demo.R;
import com.clcx.demo.base.BaseActivity;
import com.clcx.demo.base.DownloadUtils;
import com.clcx.demo.base.LogCLCXUtils;
import com.clcx.demo.base.MyApplication;
import com.clcx.demo.base.ToastClcxUtil;

import java.util.Date;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by ljc123 on 2017/8/21.
 */

public class AtyHOTFIX extends BaseActivity {

    @Bind(R.id.hotfix_switch)
    Switch hotfix_switch;
    @Bind(R.id.hotfix_tv)
    TextView hotfix_tv;

    @OnClick(R.id.hotfix_btn)
    public void hotfix_btn(View v) {
        hotfix();
    }

    @OnClick(R.id.hotfix_btn2)
    public void hotfix_btn2(View v) {
        ToastClcxUtil.getInstance().showToast("热修复完成x2！");
    }

    @OnClick(R.id.hotfix_switch)
    public void hotfix_switch(View v) {
        hotfix_tv.setText(hotfix_switch.isChecked() ? "自动更新" : "手动更新");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_hotfix;
    }

    @Override
    public void initView() {

    }

    @Override
    public void onClick(View v) {

    }

    //热修复
    //fix.apatch文件名
    private static final String URL = "http://114.141.175.83:8086/_mproj/fix.apatch";

    private void hotfix() {
        /*这里要注意，服务器上的名字可以统一为fix.apatch
        但是每一次新的名字必须修改，否则就不会覆盖更新。
        这里有一个问题，那就是如果是自动更新，那么每次都会下载补丁进行更新，这样会导致
        APP越来越大，所以andfix的策略就是每一次更新就把apatch文件复制到app内，下一
        次更新时会先进行比较，如果已经打过这个补丁，就不会再次打同名补丁

        个人认为解决方法就是在服务器上设置一个补丁表，判断是否有更新，有补丁更新，就下载补丁
        然后修改本地补丁的名字
        */
        DownloadUtils.getInstance().download(this, URL, "fix_" + String.valueOf(System.currentTimeMillis() / 1000) +
                "" +
                ".apatch", (filepath) -> {
            LogCLCXUtils.e(filepath);
            try {
                MyApplication.mPatchManager.addPatch(filepath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }
}
