package com.zyyoona7.cozydfrag.helper;

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

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * some ObjectAnimator utils
 *
 * @author zyyoona7
 * @since 2019/07/24
 */
public class AnimatorHelper {

    /**
     * Animator type
     */
    public static final int ANIM_ZOOM_IN = 0;
    public static final int ANIM_ZOOM_OUT = 1;
    public static final int ANIM_BOTTOM_SLIDE_IN = 2;
    public static final int ANIM_BOTTOM_SLIDE_OUT = 3;
    public static final int ANIM_TOP_SLIDE_IN = 4;
    public static final int ANIM_TOP_SLIDE_OUT = 5;

    /**
     * Interpolator type
     */
    public static final int INTERPOLATOR_SPRING = 0;
    public static final int INTERPOLATOR_LINEAR = 1;
    public static final int INTERPOLATOR_ACCELERATE = 2;
    public static final int INTERPOLATOR_DECELERATE = 3;
    public static final int INTERPOLATOR_ACCE_DECE = 4;
    public static final int INTERPOLATOR_OVERSHOT = 5;
    public static final int INTERPOLATOR_BOUNCE = 6;

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

    public static Interpolator spring() {
        return new SpringInterpolator();
    }

    public static Interpolator linear() {
        return new LinearInterpolator();
    }

    public static Interpolator accelerate() {
        return new AccelerateInterpolator();
    }

    public static Interpolator decelerate() {
        return new DecelerateInterpolator();
    }

    public static Interpolator accelerateDecelerate() {
        return new AccelerateDecelerateInterpolator();
    }

    public static Interpolator overshot() {
        return new OvershootInterpolator();
    }

    public static Interpolator bounce() {
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

    @IntDef({ANIM_ZOOM_IN, ANIM_ZOOM_OUT, ANIM_BOTTOM_SLIDE_IN,
            ANIM_BOTTOM_SLIDE_OUT, ANIM_TOP_SLIDE_IN, ANIM_TOP_SLIDE_OUT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AnimType {}

    @IntDef({INTERPOLATOR_SPRING,INTERPOLATOR_LINEAR,INTERPOLATOR_ACCELERATE,
            INTERPOLATOR_DECELERATE,INTERPOLATOR_ACCE_DECE,INTERPOLATOR_OVERSHOT,
            INTERPOLATOR_BOUNCE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface InterpolatorType{}
}
