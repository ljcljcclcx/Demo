package com.clcx.demo.base;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ljc123 on 2017/7/14.
 */

public class FooterHeaderWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int BASEFOOTERVIEW = 100000;
    private static final int BASEHEADERVIEW = 300000;

    private BaseRecyclerAdapter mInadapter;
    private SparseArrayCompat<View> footerViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> headerViews = new SparseArrayCompat<>();

    public void addHeaderView(View v) {
        headerViews.put(BASEHEADERVIEW + headerViews.size(), v);
        notifyDataSetChanged();
    }

    public void addFooterView(View v) {
        footerViews.put(BASEFOOTERVIEW + footerViews.size(), v);
        notifyDataSetChanged();
    }

    public int getFooterCount() {
        return footerViews.size();
    }

    public int getHeaderCount() {
        return headerViews.size();
    }

    public FooterHeaderWrapper(BaseRecyclerAdapter mInadapter) {
        this.mInadapter = mInadapter;
    }

    private boolean isHeaderPos(int position) {
        return position < getHeaderCount();
    }

    private boolean isFooterPos(int position) {
        return position >= getInnerCount() + getHeaderCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderPos(position)) {
            return headerViews.keyAt(position);
        } else if (isFooterPos(position)) {
            return footerViews.keyAt(position - getHeaderCount() - getInnerCount());
        }

        return mInadapter.getItemViewType(position - getHeaderCount());
    }

    private int getInnerCount() {
        if (mInadapter != null) {
            return mInadapter.getItemCount();
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (headerViews.get(viewType) != null) {
            return new MyHolder(headerViews.get(viewType));
        } else if (footerViews.get(viewType) != null) {
            return new MyHolder(footerViews.get(viewType));
        }

        return mInadapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isFooterPos(position) || isHeaderPos(position)) {
            return;
        }
        mInadapter.onBindViewHolder(holder, position - getHeaderCount());
    }

    @Override
    public int getItemCount() {
        return getInnerCount() + getHeaderCount() + getFooterCount();
    }

    private class MyHolder extends RecyclerView.ViewHolder {
        public MyHolder(View itemView) {
            super(itemView);
        }
    }
}
