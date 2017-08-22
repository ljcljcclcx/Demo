package com.clcx.demo.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
/**
 * ii.                                         ;9ABH,
 * SA391,                                    .r9GG35&G
 * &#ii13Gh;                               i3X31i;:,rB1
 * iMs,:,i5895,                         .5G91:,:;:s1:8A
 * 33::::,,;5G5,                     ,58Si,,:::,sHX;iH1
 * Sr.,:;rs13BBX35hh11511h5Shhh5S3GAXS:.,,::,,1AG3i,GG
 * .G51S511sr;;iiiishS8G89Shsrrsh59S;.,,,,,..5A85Si,h8
 * :SB9s:,............................,,,.,,,SASh53h,1G.
 * .r18S;..,,,,,,,,,,,,,,,,,,,,,,,,,,,,,....,,.1H315199,rX,
 * ;S89s,..,,,,,,,,,,,,,,,,,,,,,,,....,,.......,,,;r1ShS8,;Xi
 * i55s:.........,,,,,,,,,,,,,,,,.,,,......,.....,,....r9&5.:X1
 * 59;.....,.     .,,,,,,,,,,,...        .............,..:1;.:&s
 * s8,..;53S5S3s.   .,,,,,,,.,..      i15S5h1:.........,,,..,,:99
 * 93.:39s:rSGB@A;  ..,,,,.....    .SG3hhh9G&BGi..,,,,,,,,,,,,.,83
 * G5.G8  9#@@@@@X. .,,,,,,.....  iA9,.S&B###@@Mr...,,,,,,,,..,.;Xh
 * Gs.X8 S@@@@@@@B:..,智慧的凝视. rA1 ,A@@@@@@@@@H:........,,,,,,.iX:
 * ;9. ,8A#@@@@@@#5,.,,,,,,,,,... 9A. 8@@@@@@@@@@M;    ....,,,,,,,,S8
 * X3    iS8XAHH8s.,,,,,,,,,,...,..58hH@@@@@@@@@Hs       ...,,,,,,,:Gs
 * r8,        ,,,...,,,,,,,,,,.....  ,h8XABMMHX3r.          .,,,,,,,.rX:
 * :9, .    .:,..,:;;;::,.,,,,,..          .,,.               ..,,,,,,.59
 * .Si      ,:.i8HBMMMMMB&5,....                    .            .,,,,,.sMr
 * SS       :: h@@@@@@@@@@#; .                     ...  .         ..,,,,iM5
 * 91  .    ;:.,1&@@@@@@MXs.                            .          .,,:,:&S
 * hS ....  .:;,,,i3MMS1;..,..... .  .     ...                     ..,:,.99
 * ,8; ..... .,:,..,8Ms:;,,,...                                     .,::.83
 * s&: ....  .sS553B@@HX3s;,.    .,;13h.                            .:::&1
 * SXr  .  ...;s3G99XA&X88Shss11155hi.                             ,;:h&,
 * iH8:  . ..   ,;iiii;,::,,,,,.                                 .;irHA
 * ,8X5;   .     .......                                       ,;iihS8Gi
 * 1831,                                                 .,;irrrrrs&@
 * ;5A8r.                                            .:;iiiiirrss1H
 * :X@H3s.......                                .,:;iii;iiiiirsrh
 * r#h:;,...,,.. .,,:;;;;;:::,...              .:;;;;;;iiiirrss1
 * ,M8 ..,....,.....,,::::::,,...         .     .,;;;iiiiiirss11h
 * 8B;.,,,,,,,.,.....          .           ..   .:;;;;iirrsss111h
 * i@5,:::,,,,,,,,.... .                   . .:::;;;;;irrrss111111
 * 9Bi,:,,,,......                        ..r91;;;;;iirrsss1ss1111
 */


/**
 * Created by ljc123 on 2017/1/9.
 */

public class DownloadUtils {
    private DownloadUtils() {
    }

    private static DownloadUtils instance = null;

    public static DownloadUtils getInstance() {
        if (instance == null) {
            synchronized (DownloadUtils.class) {
                if (instance == null) {
                    instance = new DownloadUtils();
                }

            }
        }
        return instance;
    }

    public void download(final Activity context, final String url, String filename, DownloadListener
            mDownloadListener) {
        File exifile = new File(getAppCachePath(context, filename));
        if (exifile.exists()) {
            exifile.delete();
            LogCLCXUtils.e("补丁存在，先删除");
        }
        Request request = new Request.Builder().url(url)
                .build();
        OkHttpClient mOkHttpClient = new OkHttpClient();
        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                context.runOnUiThread(() -> {
                    ToastClcxUtil.getInstance().showToast("打开失败！");
                    File file = new File(getAppCachePath(context, filename));
                    file.delete();
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = new File(getAppCachePath(context, filename));
                    final String filapath = file.getPath();
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        LogCLCXUtils.e("progress:" + progress);
                    }
                    fos.flush();
                    mDownloadListener.finish(filapath);
                } catch (Exception e) {
                    context.runOnUiThread(() -> {
                        ToastClcxUtil.getInstance().showToast("打开失败！");
                    });
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }


        });

    }

    public String getAppCachePath(Context context,
                                  String fileName) {
        String filePath = context.getFilesDir().getAbsolutePath() + "/pdf/";
        if (!new File(filePath).exists()) {
            new File(filePath).mkdirs();
        }
        String appCachePath = context.getFilesDir().getAbsolutePath() + "/pdf/" + fileName;

        return appCachePath;
    }

    public interface DownloadListener {
        void finish(String filepath);
    }
}
