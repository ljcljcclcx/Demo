package com.clcx.demo.base;

import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CLCXUtils {

    public static int DPexchange(int i) {
        return (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        i, MyApplication.getContext().getResources()
                                .getDisplayMetrics());
    }

    /**
     * RecyclerView是否滑动到底部
     *
     * @param recyclerView
     * @return
     */
    public static boolean recyclerViewSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView
                .computeVerticalScrollRange())
            return true;
        return false;
    }

    /**
     * md5加密
     *
     * @param str
     * @return
     */
    public static String MD5encodeString(String str) {
        StringBuilder builder = new StringBuilder();
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            digest.update(str.getBytes());

            byte[] bytes = digest.digest();
            for (int i = 0; i < bytes.length; i++) {
                String hexString = Integer.toHexString(0xFF & bytes[i]);
                if (hexString.length() == 1) {
                    builder.append("0");
                }
                builder.append(hexString);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    /**
     * 从min到max只见的所有浮点型数据，三位小数
     *
     * @param min
     * @param max
     * @return
     */
    public static float RandomFloat(float min, float max) {
        Random random = new Random();
        int iMin = (int) (min * 1000);
        int iMax = (int) (max * 1000);

        int randInt = iMin + random.nextInt(iMax - iMin);

        random = null;
        return (float) randInt / 1000.0f;

    }

    /**
     * 随机数 0-i
     *
     * @return
     */
    public static int RandomInt(int i) {
        Random random = new Random();
        return random.nextInt(i);
    }

    /**
     * 金钱格式转换器
     */
    public static String MoneyFormat(int money) {
        if (money <= 0) {
            return Integer.toString(money);
        }
        StringBuffer stb = new StringBuffer();
        List<String> split = new ArrayList<String>();
        int rest = money;
        while (rest > 0) {
            String restStr = "";
            int mod = rest % 1000;
            // 补0
            if (mod < 100 && mod >= 10) {
                restStr = ",0" + mod;
            } else if (mod < 10) {
                restStr = ",00" + mod;
            } else {
                restStr = "," + Integer.toString(mod);
            }

            rest /= 1000;
            if (rest == 0) {
                split.add(Integer.toString(mod));
            } else {
                split.add(restStr);
            }
        }

        for (int i = split.size() - 1; i >= 0; i--) {
            stb.append(split.get(i));
        }
        return stb.toString();

    }
}
