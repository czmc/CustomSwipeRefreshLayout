package com.github.czmc.widget;

import android.animation.ValueAnimator;
import android.graphics.*;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.animation.LinearInterpolator;

public class ProgressDrawable extends Drawable implements Animatable, ValueAnimator.AnimatorUpdateListener {

    protected int mWidth = 0;
    protected int mHeight = 0;
    protected int mProgressDegree = 0;
    protected ValueAnimator mValueAnimator;
    protected Path mPath = new Path();
    protected Paint mPaint;

    public ProgressDrawable() {
        getPaint().setStyle(Paint.Style.FILL);
        getPaint().setAntiAlias(true);
        getPaint().setColor(0xffaaaaaa);
        mValueAnimator = ValueAnimator.ofInt(30, 3600);
        mValueAnimator.setDuration(10000);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
    }

    public Paint getPaint() {
        if (mPaint == null)
            mPaint = new Paint();
        return mPaint;
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        int value = (int) animation.getAnimatedValue();
        mProgressDegree = 30 * (value / 30);
        final Drawable drawable = ProgressDrawable.this;
        drawable.invalidateSelf();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        final Drawable drawable = ProgressDrawable.this;
        final Rect bounds = drawable.getBounds();
        final int width = bounds.width();
        final int height = bounds.height();
        final int r = Math.max(1, width / 20);

        if (mWidth != width || mHeight != height) {
            mPath.reset();
            mPath.addCircle(width - r, height / 2, r, Path.Direction.CW);
            mPath.addRect(width - 5 * r, height / 2 - r, width - r, height / 2 + r, Path.Direction.CW);
            mPath.addCircle(width - 5 * r, height / 2, r, Path.Direction.CW);
            mWidth = width;
            mHeight = height;
        }

        canvas.save();
        canvas.rotate(mProgressDegree, (width) / 2, (height) / 2);
        for (int i = 0; i < 12; i++) {
            getPaint().setAlpha((i + 5) * 0x11);
            canvas.rotate(30, (width) / 2, (height) / 2);
            canvas.drawPath(mPath, getPaint());
        }
        canvas.restore();
    }

    @Override
    public void setAlpha(int alpha) {
        getPaint().setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        getPaint().setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void start() {
        if (!mValueAnimator.isRunning()) {
            mValueAnimator.addUpdateListener(this);
            mValueAnimator.start();
        }
    }

    @Override
    public void stop() {
        if (mValueAnimator.isRunning()) {
            mValueAnimator.removeAllListeners();
            mValueAnimator.removeAllUpdateListeners();
            mValueAnimator.cancel();
        }
    }

    @Override
    public boolean isRunning() {
        return mValueAnimator.isRunning();
    }

}