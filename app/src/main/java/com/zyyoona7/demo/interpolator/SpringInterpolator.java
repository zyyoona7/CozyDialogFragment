package com.zyyoona7.demo.interpolator;

import android.view.animation.Interpolator;

/**
 * from http://inloop.github.io/interpolator/
 * @author zyyoona7
 * @version v1.0.0
 * @since 2018/10/23.
 */
public class SpringInterpolator implements Interpolator {

    //越大动作幅度越小，抖动越不明显
    private float mFactor = 0.4f;

    public SpringInterpolator(float factor) {
        mFactor = factor;
    }

    public SpringInterpolator() {
    }

    @Override
    public float getInterpolation(float input) {
        return (float) (Math.pow(2, -10 * input) * Math.sin((input - mFactor / 4) *
                (2 * Math.PI) / mFactor) + 1);
    }
}