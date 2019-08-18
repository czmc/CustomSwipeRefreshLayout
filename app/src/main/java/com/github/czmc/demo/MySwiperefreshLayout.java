package com.github.czmc.demo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ListViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.github.czmc.widget.CustomSwipeRefreshLayout;
import com.github.czmc.widget.HeaderViewHolder;
/**
 * @author czmc
 * @version 1.0
 * @description 定义刷新头部的SwiperefreshLayout
 * @date 2019/2/25
 */
public class MySwiperefreshLayout extends CustomSwipeRefreshLayout {
    public MySwiperefreshLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public MySwiperefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init(){
        setOnChildScrollUpCallback((parent, child) -> {
            if (child == null) return true;
            if (child instanceof ListView) {
                return ListViewCompat.canScrollList((ListView) child, -1);
            }
            /**
             * 解决嵌套framelayout 导致SwipeRefreshLayout 滑动冲突
             * 使用 SwipeRefreshLayoutContainer 避免多余判断
             */
            if (child instanceof SwipeRefreshLayoutContainer) {
                ViewGroup moveTargetView = (ViewGroup) child;
                for (int i = 0; i < moveTargetView.getChildCount(); i++) {
                    View c = moveTargetView.getChildAt(i);
                    if (c instanceof ListView) {
                        return ListViewCompat.canScrollList((ListView) c, -1);
                    }
                }
            }
            return child.canScrollVertically(-1);
        });
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder() {
        // TODO: 2019/2/25 自定义刷新头部
        return super.onCreateHeaderViewHolder();
    }

}
