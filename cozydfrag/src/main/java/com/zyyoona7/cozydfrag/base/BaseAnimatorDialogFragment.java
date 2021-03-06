package com.zyyoona7.cozydfrag.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zyyoona7.cozydfrag.callback.OnDialogDismissAnimListener;
import com.zyyoona7.cozydfrag.callback.OnDialogShowAnimListener;

import java.util.List;

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
        if (mShowAnimator != null) {
            //update target view.
            //the target view will changed by onCreateView,different from before.
            mShowAnimator.setTarget(targetView);
        }
    }

    @Override
    protected void onStartShowAnimation(@NonNull View targetView) {
        if (mShowAnimator == null) {
            return;
        }
        mShowAnimator.setDuration(getShowDuration());
        mShowAnimator.start();
    }

    @Override
    protected void onCreateDismissAnimation(@NonNull final View targetView, final boolean stateLoss) {
        if (mDismissAnimator == null) {
            mDismissAnimator = onCreateDismissAnimator(targetView);
            if (mDismissAnimator != null) {
                mDismissAnimator.setTarget(targetView);
                mDismissAnimator.addListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        onDismissAnimationStart(targetView);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        onDismissAnimationEnd(targetView);
                        realDismiss(stateLoss);
                    }
                });
            }
        }
        if (mDismissAnimator != null) {
            //update target view.
            //the target view will changed by onCreateView,different from before.
            mDismissAnimator.setTarget(targetView);
        }
    }

    @Override
    protected void onStartDismissAnimation(@NonNull View targetView, boolean stateLoss) {
        if (mShowAnimator != null && mShowAnimator.isRunning()) {
            mShowAnimator.cancel();
        }
        if (mDismissAnimator == null) {
            if (!isUseDimAnimation()) {
                realDismiss(stateLoss);
            }
            return;
        }
        mDismissAnimator.setDuration(getDismissDuration());
        mDismissAnimator.start();
    }

    @Override
    protected void onDimAnimationEnd(Drawable dimDrawable, boolean isDismiss) {
        super.onDimAnimationEnd(dimDrawable, isDismiss);
        if (isDismiss
                && isUseDismissAnimation() && mDismissAnimator == null) {
            realDismiss(true);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mShowAnimator != null) {
            mShowAnimator.setTarget(null);
            mShowAnimator.cancel();
            mShowAnimator.removeAllListeners();
            mShowAnimator = null;
        }

        if (mDismissAnimator != null) {
            mDismissAnimator.setTarget(null);
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
     * show animation start callback
     *
     * @param targetView Window content view
     */
    protected void onShowAnimationStart(@NonNull View targetView) {
        for (OnDialogShowAnimListener dialogShowAnimListener : getDialogShowAnimListeners()) {
            dialogShowAnimListener.onShowAnimStart(getRequestId());
        }
    }

    /**
     * show animation end callback
     *
     * @param targetView Window content view
     */
    protected void onShowAnimationEnd(@NonNull View targetView) {
        for (OnDialogShowAnimListener dialogShowAnimListener : getDialogShowAnimListeners()) {
            dialogShowAnimListener.onShowAnimEnd(getRequestId());
        }
    }

    /**
     * create dismiss animation used to DialogFragment dismiss
     *
     * @param targetView Window content view
     * @return dismiss animation
     */
    @Nullable
    protected abstract Animator onCreateDismissAnimator(@NonNull View targetView);

    /**
     * dismiss animation start callback
     *
     * @param targetView Window content view
     */
    protected void onDismissAnimationStart(@NonNull View targetView) {
        for (OnDialogDismissAnimListener dialogDismissAnimListener : getDialogDismissAnimListeners()) {
            dialogDismissAnimListener.onDismissAnimStart(getRequestId());
        }
    }

    /**
     * dismiss animation end callback
     *
     * @param targetView Window content view
     */
    protected void onDismissAnimationEnd(@NonNull View targetView) {
        for (OnDialogDismissAnimListener dialogDismissAnimListener : getDialogDismissAnimListeners()) {
            dialogDismissAnimListener.onDismissAnimEnd(getRequestId());
        }
    }

    /**
     * @return OnDialogShowAnimListener list from targetFragment,parentFragment,getActivity
     */
    @NonNull
    private List<OnDialogShowAnimListener> getDialogShowAnimListeners() {
        return getDialogListeners(OnDialogShowAnimListener.class);
    }

    /**
     * @return OnDialogDismissAnimListener list from targetFragment,parentFragment,getActivity
     */
    @NonNull
    private List<OnDialogDismissAnimListener> getDialogDismissAnimListeners() {
        return getDialogListeners(OnDialogDismissAnimListener.class);
    }

}
