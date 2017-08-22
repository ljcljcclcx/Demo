package com.clcx.demo.base;

import android.support.v7.widget.RecyclerView;

/**
 * RecyclerView滑动事件处理方法：
 * listen方法必须写在对应的Activity之中，不可单独开类创建，否则会因为View被持有无法释放导致内存泄漏
 */
@Deprecated
public class RecyclerScrollListener {

    private RecyclerScrollListener() {
    }

    private int scrolly = 0;//滑动的y绝对值位置
    private int scrollx = 0;//滑动的X绝对值位置
    private int state = RecyclerView.SCROLL_STATE_IDLE;

    public void listen(RecyclerView rv) {
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                state = newState;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                /**
                 * 重要！
                 */
                scrolly += dy;
                scrollx += dx;
            }
        });
    }

}
