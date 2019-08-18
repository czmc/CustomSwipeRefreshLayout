package com.github.czmc.demo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class SwipeRefreshLayoutContainer extends FrameLayout {
    public SwipeRefreshLayoutContainer(@NonNull Context context) {
        super(context);
    }

    public SwipeRefreshLayoutContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SwipeRefreshLayoutContainer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
