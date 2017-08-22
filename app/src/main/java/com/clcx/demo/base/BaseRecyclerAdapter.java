package com.clcx.demo.base;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by ljc123 on 2016/7/4.
 */
public abstract class BaseRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /**
     * 数据存储集合
     **/
    protected List<Object> mDataList = new ArrayList<Object>();
    /**
     * Context上下文
     **/
    private Activity mContext;
    /**
     * 日志输出标志
     **/
    protected final String TAG = this.getClass().getSimpleName();

    public BaseRecyclerAdapter(Activity mContext) {
        this.mContext = mContext;
    }

    protected abstract RecyclerView.ViewHolder createHolder(ViewGroup parent, int viewType);

    protected abstract void bindHolder(RecyclerView.ViewHolder holder, int position);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return createHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public Object getItem(int position) {
        if (position < mDataList.size())
            return mDataList.get(position);
        else
            return null;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    /**
     * 添加数据
     *
     * @param object 数据项
     */
    public boolean addItem(Object object) {
        return mDataList.add(object);
    }

    /**
     * 在指定索引位置添加数据
     *
     * @param location 索引
     * @param object   数据
     */
    public void addItem(int location, Object object) {
        mDataList.add(location, object);
    }

    /**
     * 集合方式添加数据
     *
     * @param collection 集合
     */
    public boolean addItem(Collection<? extends Object> collection) {
        return mDataList.addAll(collection);
    }

    /**
     * 在指定索引位置添加数据集合
     *
     * @param location   索引
     * @param collection 数据集合
     */
    public boolean addItem(int location, Collection<? extends Object> collection) {
        return mDataList.addAll(location, collection);
    }

    /**
     * 移除指定对象数据
     *
     * @param object 移除对象
     * @return 是否移除成功
     */
    public boolean removeItem(Object object) {
        return mDataList.remove(object);
    }

    /**
     * 移除指定索引位置对象
     *
     * @param location 删除对象索引位置
     * @return 被删除的对象
     */
    public Object removeItem(int location) {
        return mDataList.remove(location);
    }

    /**
     * 移除指定集合对象
     *
     * @param collection 待移除的集合
     * @return 是否移除成功
     */
    public boolean removeAll(Collection<? extends Object> collection) {
        return mDataList.removeAll(collection);
    }

    /**
     * 清空数据
     */
    public void clear() {
        mDataList.clear();
    }

    public Activity getActivity() {
        return mContext;
    }
}
