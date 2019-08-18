package com.github.czmc.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;


/**
 * @author czmc
 * @version 1.0
 * @email: chenzhiming@dkhs.com
 * @description 默认刷新头部
 * @date 2018/11/21
 */
public class DefaultHeaderViewHolder extends RelativeLayout implements HeaderViewHolder {
    private static final String TAG = DefaultHeaderViewHolder.class.getSimpleName();
    private ImageView mProgressView;
    private TextView mTitleText;
    private TextView mDateText;
    private LinearLayout mTextContaniner;
    private State currentState = State.Noraml;
    private ProgressDrawable mProgressDrawable;

    enum State {
        Noraml,
        Start,
        Refreshing,
        Release,
        PullDown,
    }

    public DefaultHeaderViewHolder(Context context) {
        super(context);
        init();
    }


    public DefaultHeaderViewHolder(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DefaultHeaderViewHolder(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public View createHeaderView(Context context) {
        return this;
    }

    private void init() {
        mProgressView = new ImageView(getContext());
        mTitleText = new TextView(getContext());
        mDateText = new TextView(getContext());

        mTextContaniner = new LinearLayout(getContext());
        mTextContaniner.addView(mTitleText);
        mTextContaniner.addView(mDateText);
        mTextContaniner.setId(R.id.ll_text_container);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_IN_PARENT);
        addView(mTextContaniner, params);
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(dip2px(24) ,dip2px(24));
        params2.addRule(LEFT_OF, mTextContaniner.getId());
        params2.addRule(CENTER_VERTICAL);
        params2.setMargins(0, 0, dip2px(20), 0);
        addView(mProgressView, params2);

        mTextContaniner.setOrientation(LinearLayout.VERTICAL);
        mTextContaniner.setGravity(Gravity.CENTER);
        mTextContaniner.setMinimumHeight(dip2px(80));

        mTitleText.setGravity(Gravity.CENTER);
        mTitleText.setTextSize(16);
        mTitleText.setTextColor(0xff666666);

        mDateText.setGravity(Gravity.CENTER);
        mDateText.setTextSize(12);
        mTitleText.setTextColor(0xff666666);

        mProgressDrawable =  new ProgressDrawable();

    }


    public int dip2px(float dipValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    private static SimpleDateFormat formater = new SimpleDateFormat("上次更新于M月dd日 HH:mm");

    public String getCurrentTime() {
        return formater.format(System.currentTimeMillis());
    }

    @Override
    public void onPullDown(float percent, int moveYDistance) {
        Log.d(TAG, "percent=" + percent + ",moveYDistance=" + moveYDistance + ",meatureHeight=" + mTextContaniner.getMeasuredHeight());
        if (moveYDistance > 0) {
            if (currentState == State.Start) {
                mTitleText.setText("释放立即刷新");
                mProgressView.animate().rotation(-180);
            }
            currentState = State.PullDown;
        } else {
            if (currentState == State.PullDown) {
                mTitleText.setText("下拉可以刷新");
                mProgressView.animate().rotation(0);
            }
            currentState = State.Start;
        }
    }

    @Override
    public void onStartPullDown() {
        Log.d(TAG, "onStartPullDown");
        mTitleText.setText("下拉可以刷新");
        mDateText.setText(getCurrentTime());
        currentState = State.Start;
        mProgressView.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_downward_black_24dp));
    }

    @Override
    public void onReleaseRefresh() {
        Log.d(TAG, "onReleaseRefresh");
        currentState = State.Release;
    }

    @Override
    public void onRefreshing() {
        mProgressView.animate().cancel();
        mProgressView.setImageDrawable(mProgressDrawable);
        mProgressDrawable.start();
        Log.d(TAG, "onRefreshing");
        mTitleText.setText("正在刷新");
        currentState = State.Refreshing;
    }

    @Override
    public void onEndRefreshing() {
        mProgressView.animate().rotation(0);
        Log.d(TAG, "onEndRefreshing");
        currentState = State.Noraml;
    }

}
