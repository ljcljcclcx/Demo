package com.clcx.demo.ui.main;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.clcx.demo.R;
import com.clcx.demo.base.BaseRecyclerAdapter;
import com.clcx.demo.ui.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by ljc123 on 2016/7/4.
 */

/**
 * RecyclerView适配器Demo，需要自定义修改
 */
public class RecycAdaMain extends BaseRecyclerAdapter {
    private OnItemClickListener onItemClickListener;

    public RecycAdaMain(Activity mContext) {
        super(mContext);
    }

    @Override
    protected RecyclerView.ViewHolder createHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.item_main, parent, false);
        MyHolder holder = new MyHolder(v, onItemClickListener);

        return holder;
    }

    @Override
    protected void bindHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder mHolder = (MyHolder) holder;
        MainActivity.MainBean item = (MainActivity.MainBean) getItem(position);
        mHolder.item_main_tv.setText(item.getTitle());
    }

    public void setItems(List<MainActivity.MainBean> beans) {
        this.clear();
        this.addItem(beans);
        notifyDataSetChanged();
    }

    private class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView item_main_tv;
        private OnItemClickListener onItemClickListener;

        public MyHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            item_main_tv = (TextView) itemView.findViewById(R.id.item_main_tv);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }


    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
