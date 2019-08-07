package com.zyyoona7.cozydfrag;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Interpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zyyoona7.cozydfrag.base.BaseAnimatorDialogFragment;
import com.zyyoona7.cozydfrag.helper.AnimatorHelper;

/**
 * DialogFragment for common animation.
 *
 * @author zyyoona7
 * @since 2019/07/30
 */
public class CozyDialogFragment extends BaseAnimatorDialogFragment {

    private static final String SAVED_SHOW_ANIM_TYPE = "SAVED_SHOW_ANIM_TYPE";
    private static final String SAVED_SHOW_INTERPOLATOR_TYPE = "SAVED_SHOW_INTERPOLATOR_TYPE";
    private static final String SAVED_DISMISS_ANIM_TYPE = "SAVED_DISMISS_ANIM_TYPE";
    private static final String SAVED_DISMISS_INTERPOLATOR_TYPE = "SAVED_DISMISS_INTERPOLATOR_TYPE";

    @AnimatorHelper.AnimType
    private int mShowAnimType=AnimatorHelper.ANIM_ZOOM_IN;
    @AnimatorHelper.InterpolatorType
    private int mShowInterpolatorType=AnimatorHelper.INTERPOLATOR_ACCE_DECE;
    @AnimatorHelper.AnimType
    private int mDismissAnimType=AnimatorHelper.ANIM_ZOOM_OUT;
    @AnimatorHelper.InterpolatorType
    private int mDismissInterpolatorType=AnimatorHelper.INTERPOLATOR_ACCE_DECE;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mShowAnimType = savedInstanceState.getInt(SAVED_SHOW_ANIM_TYPE, AnimatorHelper.ANIM_ZOOM_IN);
            mShowInterpolatorType = savedInstanceState
                    .getInt(SAVED_SHOW_INTERPOLATOR_TYPE, AnimatorHelper.INTERPOLATOR_ACCE_DECE);
            mDismissAnimType = savedInstanceState.getInt(SAVED_DISMISS_ANIM_TYPE, AnimatorHelper.ANIM_ZOOM_OUT);
            mDismissInterpolatorType = savedInstanceState
                    .getInt(SAVED_DISMISS_INTERPOLATOR_TYPE, AnimatorHelper.INTERPOLATOR_ACCE_DECE);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(SAVED_SHOW_ANIM_TYPE, mShowAnimType);
        outState.putInt(SAVED_SHOW_INTERPOLATOR_TYPE, mShowInterpolatorType);
        outState.putInt(SAVED_DISMISS_ANIM_TYPE, mDismissAnimType);
        outState.putInt(SAVED_DISMISS_INTERPOLATOR_TYPE, mDismissInterpolatorType);
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    protected Animator onCreateShowAnimator(@NonNull View targetView) {
        return getAnimator(targetView, mShowAnimType, mShowInterpolatorType);
    }

    @Nullable
    @Override
    protected Animator onCreateDismissAnimator(@NonNull View targetView) {
        return getAnimator(targetView, mDismissAnimType, mDismissInterpolatorType);
    }

    private ObjectAnimator getAnimator(@NonNull View targetView, int animType,
                                       int interpolatorType) {
        ObjectAnimator animator = getAnimatorByType(targetView, animType);
        animator.setInterpolator(getInterpolatorByType(interpolatorType));
        return animator;
    }

    private ObjectAnimator getAnimatorByType(@NonNull View targetView, int animType) {
        ObjectAnimator animator;
        switch (animType) {
            case AnimatorHelper.ANIM_ZOOM_OUT:
                animator = AnimatorHelper.zoomOut(targetView);
                break;
            case AnimatorHelper.ANIM_BOTTOM_SLIDE_IN:
                animator = AnimatorHelper.bottomSlideIn(targetView);
                break;
            case AnimatorHelper.ANIM_BOTTOM_SLIDE_OUT:
                animator = AnimatorHelper.bottomSlideOut(targetView);
                break;
            case AnimatorHelper.ANIM_TOP_SLIDE_IN:
                animator = AnimatorHelper.topSlideIn(targetView);
                break;
            case AnimatorHelper.ANIM_TOP_SLIDE_OUT:
                animator = AnimatorHelper.topSlideOut(targetView);
                break;
            case AnimatorHelper.ANIM_ZOOM_IN:
            default:
                animator = AnimatorHelper.zoomIn(targetView);
                break;
        }
        return animator;
    }

    private Interpolator getInterpolatorByType(int interpolatorType) {
        Interpolator interpolator;
        switch (interpolatorType) {
            case AnimatorHelper.INTERPOLATOR_SPRING:
                interpolator = AnimatorHelper.spring();
                break;
            case AnimatorHelper.INTERPOLATOR_LINEAR:
                interpolator = AnimatorHelper.linear();
                break;
            case AnimatorHelper.INTERPOLATOR_ACCELERATE:
                interpolator = AnimatorHelper.accelerate();
                break;
            case AnimatorHelper.INTERPOLATOR_DECELERATE:
                interpolator = AnimatorHelper.decelerate();
                break;
            case AnimatorHelper.INTERPOLATOR_OVERSHOT:
                interpolator = AnimatorHelper.overshot();
                break;
            case AnimatorHelper.INTERPOLATOR_BOUNCE:
                interpolator = AnimatorHelper.bounce();
                break;
            case AnimatorHelper.INTERPOLATOR_ACCE_DECE:
            default:
                interpolator = AnimatorHelper.accelerateDecelerate();
                break;
        }
        return interpolator;
    }

    public void setShowAnimType(@AnimatorHelper.AnimType int showAnimType) {
        mShowAnimType = showAnimType;
    }

    public void setShowInterpolatorType(@AnimatorHelper.InterpolatorType int showInterpolatorType) {
        mShowInterpolatorType = showInterpolatorType;
    }

    public void setDismissAnimType(@AnimatorHelper.AnimType int dismissAnimType) {
        mDismissAnimType = dismissAnimType;
    }

    public void setDismissInterpolatorType(@AnimatorHelper.InterpolatorType int dismissInterpolatorType) {
        mDismissInterpolatorType = dismissInterpolatorType;
    }
}
