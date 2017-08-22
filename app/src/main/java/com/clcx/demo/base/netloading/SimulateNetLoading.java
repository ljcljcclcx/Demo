package com.clcx.demo.base.netloading;

/**
 * Created by ljc123 on 2017/7/14.
 */

import android.app.Activity;
import android.os.SystemClock;
import android.support.v4.util.SparseArrayCompat;

import com.clcx.demo.base.LogCLCXUtils;


/**
 * 网络加载模拟
 * 真是网络请求参考一下
 * loadingFlag用于存储每一个网络请求是否请求过，如果请求就不可再次请求（尤其是上啦刷新的问题）
 * 后期可以通过md5加密链接作为键
 * 每一个网络请求都在请求前设置一个独立的tag
 * 通过tag的key-value值来找到每一个网络请求
 */
public class SimulateNetLoading {

    private SimulateNetLoading() {
    }

    private static SimulateNetLoading instance = null;

    public static SimulateNetLoading getInstance() {
        if (instance == null) {
            synchronized (SimulateNetLoading.class) {
                if (instance == null) {
                    instance = new SimulateNetLoading();
                }

            }
        }
        return instance;
    }

    private SparseArrayCompat<Boolean> loadingFlag = new SparseArrayCompat<>();//网络加载，同一请求不可多次发送

    public void loadMore(final Activity context, final CallBack callBack) {
        final int tag = 10000;
        if (loadingFlag.get(tag) == null) loadingFlag.put(tag, false);
        if (!loadingFlag.get(tag)) {
            LogCLCXUtils.e("loadMore请求中...");
            loadingFlag.put(tag, true);
            new Thread(() -> {
                SystemClock.sleep(1500);
                context.runOnUiThread(() -> {
                    String load = "http://img.vipcn.com/img2016/1/6/2016010657540969.jpg";
                    callBack.success(load);
                    loadingFlag.put(tag, false);
                });

            }).start();
        }
    }

    public void refreshMore(final Activity context, final CallBack callBack) {
        final int tag = 10001;
        if (loadingFlag.get(tag) == null) loadingFlag.put(tag, false);
        if (!loadingFlag.get(tag)) {
            LogCLCXUtils.e("refreshMore请求中...");
            loadingFlag.put(tag, true);
            new Thread(() -> {
                SystemClock.sleep(1500);
                context.runOnUiThread(() -> {
                    callBack.success("http://b.hiphotos.baidu" +
                            ".com/zhidao/pic/item/9345d688d43f8794f53b0495d41b0ef41ad53a40.jpg");
                    loadingFlag.put(tag, false);
                });

            }).start();
        }
    }

    public void login(final String user, final String pwd, final CallBack callBack) {
        final int tag = 10002;
        if (loadingFlag.get(tag) == null) loadingFlag.put(tag, false);
        if (!loadingFlag.get(tag)) {
            loadingFlag.put(tag, true);
            new Thread(() -> {
                SystemClock.sleep(1500);
                if (user.equals("ad") && pwd.equals("123")) {
                    callBack.success("1");
                } else {
                    callBack.success("Username or Password error!");
                }
                loadingFlag.put(tag, false);
            }).start();
        }
    }

    public interface CallBack {
        void success(String str);

        void error();
    }
}
