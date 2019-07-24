package com.zyyoona7.easydfrag.helper;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.NonNull;

/**
 * some ObjectAnimator utils
 *
 * @author zyyoona7
 * @since 2019/07/24
 */
public class AnimatorHelper {

    private AnimatorHelper() {
    }

    public static ObjectAnimator zoomIn(@NonNull View target) {
        PropertyValuesHolder scaleXHolder =
                PropertyValuesHolder.ofFloat("scaleX", 0.5f, 1f);
        PropertyValuesHolder scaleYHolder =
                PropertyValuesHolder.ofFloat("scaleY", 0.5f, 1f);
        PropertyValuesHolder alphaHolder =
                PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
        return ObjectAnimator
                .ofPropertyValuesHolder(target, scaleXHolder, scaleYHolder, alphaHolder);
    }

    public static ObjectAnimator zoomOut(@NonNull View target) {
        PropertyValuesHolder scaleXHolder =
                PropertyValuesHolder.ofFloat("scaleX", 1f, 0.5f);
        PropertyValuesHolder scaleYHolder =
                PropertyValuesHolder.ofFloat("scaleY", 1f, 0.5f);
        PropertyValuesHolder alphaHolder =
                PropertyValuesHolder.ofFloat("alpha", 1f, 0f);
        return ObjectAnimator
                .ofPropertyValuesHolder(target, scaleXHolder, scaleYHolder, alphaHolder);
    }

    public static ObjectAnimator bottomSlideIn(@NonNull View target) {
        return ObjectAnimator
                .ofFloat(target, "translationY", target.getHeight(), 0);
    }

    public static ObjectAnimator bottomSlideOut(@NonNull View target) {
        return ObjectAnimator
                .ofFloat(target, "translationY", 0, target.getHeight());
    }

    public static ObjectAnimator topSlideIn(@NonNull View target) {
        return ObjectAnimator
                .ofFloat(target, "translationY", -target.getHeight(), 0);
    }

    public static ObjectAnimator topSlideOut(@NonNull View target) {
        return ObjectAnimator
                .ofFloat(target, "translationY", 0, -target.getHeight());
    }

    public static Interpolator spring(){
        return new SpringInterpolator();
    }

    public static Interpolator linear(){
        return new LinearInterpolator();
    }

    public static Interpolator accelerate(){
        return new AccelerateInterpolator();
    }

    public static Interpolator decelerate(){
        return new DecelerateInterpolator();
    }

    public static Interpolator accelerateDecelerate(){
        return new AccelerateDecelerateInterpolator();
    }

    public static Interpolator overshot(){
        return new OvershootInterpolator();
    }

    public static Interpolator bounce(){
        return new BounceInterpolator();
    }

    public static class SpringInterpolator implements Interpolator {

        //越大动作幅度越小，抖动越不明显
        private float mFactor = 0.5f;

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
}
