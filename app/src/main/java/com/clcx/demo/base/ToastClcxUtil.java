package com.clcx.demo.base;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

public class ToastClcxUtil {
    private ToastClcxUtil() {
    }

    private static ToastClcxUtil instance = null;

    public static ToastClcxUtil getInstance() {
        if (instance == null) {
            synchronized (ToastClcxUtil.class) {
                if (instance == null) {
                    instance = new ToastClcxUtil();
                }

            }
        }
        return instance;
    }

    private Toast mToast;

    public void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(MyApplication.getContext(), msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }

    public void showAlert(Context context, String msg) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("提示！")
                .setMessage(msg)
                .setNegativeButton("确定", null)
                .create();

        dialog.show();
    }

}
