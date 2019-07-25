package com.zyyoona7.easydfrag.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * custom show/dismiss animation by Animator
 *
 * @author zyyoona7
 * @since 2019/07/23
 */
public abstract class BaseAnimatorDialogFragment extends BaseAnimDialogFragment {

    private Animator mShowAnimator;
    private Animator mDismissAnimator;

    @Override
    protected void onCreateShowAnimation(@NonNull final View targetView) {
        if (mShowAnimator == null) {
            mShowAnimator = onCreateShowAnimator(targetView);
            if (mShowAnimator != null) {
                mShowAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        onShowAnimationStart(targetView);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        onShowAnimationEnd(targetView);
                    }
                });
            }
        }
    }

    @Override
    protected void onStartShowAnimation(@NonNull View targetView) {
        if (mShowAnimator == null) {
            return;
        }
        startDimShowAnimation();
        mShowAnimator.setDuration(getShowDuration());
        mShowAnimator.start();
    }

    @Override
    protected void onCreateDismissAnimation(@NonNull final View targetView, final boolean stateLoss) {
        if (mDismissAnimator == null) {
            mDismissAnimator = onCreateDismissAnimator(targetView);
            if (mDismissAnimator != null) {
                mDismissAnimator.addListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        onDismissAnimationStart(targetView, stateLoss);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        onDismissAnimationEnd(targetView, stateLoss);
                        superDismissInternal(stateLoss);
                    }
                });
            }
        }
    }

    @Override
    protected void onStartDismissAnimation(@NonNull View targetView, boolean stateLoss) {
        if (mShowAnimator != null && mShowAnimator.isRunning()) {
            mShowAnimator.end();
        }
        if (mDismissAnimator == null) {
            superDismissInternal(stateLoss);
            return;
        }
        startDimDismissAnimation();
        mDismissAnimator.setDuration(getDismissDuration());
        mDismissAnimator.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mShowAnimator != null) {
            mShowAnimator.cancel();
            mShowAnimator.removeAllListeners();
            mShowAnimator = null;
        }

        if (mDismissAnimator != null) {
            mDismissAnimator.cancel();
            mDismissAnimator.removeAllListeners();
            mDismissAnimator = null;
        }
    }

    /**
     * create show animation used to DialogFragment show
     *
     * @param targetView Window content view
     * @return show animation
     */
    @Nullable
    protected abstract Animator onCreateShowAnimator(@NonNull View targetView);

    /**
     * create dismiss animation used to DialogFragment dismiss
     *
     * @param targetView Window content view
     * @return dismiss animation
     */
    @Nullable
    protected abstract Animator onCreateDismissAnimator(@NonNull View targetView);
}
