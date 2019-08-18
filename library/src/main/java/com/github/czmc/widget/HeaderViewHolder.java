package com.github.czmc.widget;

import android.content.Context;
import android.view.View;

/**
 * @author czmc
 * @version 1.0
 * @email: chenzhiming@dkhs.com
 * @description 刷新头部标准
 * @date 2018/11/21
 */
public interface HeaderViewHolder {
    View createHeaderView(Context context);

    /**
     * 下拉刷新控件可见时，处理上下拉进度
     *
     * @param percent       下拉过程0 到 1，回弹过程1 到 0，没有加上弹簧距离移动时的比例
     * @param moveYDistance 整个下拉刷新控件paddingTop变化的值，如果有弹簧距离，会大于整个下拉刷新控件的高度
     */
    void onPullDown(float percent, int moveYDistance);

    /**
     * 进入下拉状态
     */
    void onStartPullDown();

    /**
     * 未进入刷新
     */
    void onReleaseRefresh();

    /**
     * 进入正在刷新状态
     */
    void onRefreshing();

    /**
     * 结束下拉刷新
     */
    void onEndRefreshing();


}
