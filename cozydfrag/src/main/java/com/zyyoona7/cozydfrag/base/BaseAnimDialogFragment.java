package com.zyyoona7.cozydfrag.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.zyyoona7.cozydfrag.callback.OnAnimInterceptCallback;
import com.zyyoona7.cozydfrag.dialog.AnimDialog;
import com.zyyoona7.cozydfrag.dialog.IAnimDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * custom show/dismiss animation
 *
 * @author zyyoona7
 * @since 2019/07/22
 */
public abstract class BaseAnimDialogFragment extends BaseDialogFragment
        implements DialogInterface.OnShowListener, OnAnimInterceptCallback {

    private static final String SAVED_SHOW_DURATION = "SAVED_SHOW_DURATION";
    private static final String SAVED_DISMISS_DURATION = "SAVED_DISMISS_DURATION";
    private static final String SAVED_DIM_SHOW_DURATION = "SAVED_DIM_SHOW_DURATION";
    private static final String SAVED_DIM_DISMISS_DURATION = "SAVED_DIM_DISMISS_DURATION";
    private static final String SAVED_DIM_COLOR = "SAVED_DIM_COLOR";
    private static final String SAVED_USE_SHOW_ANIMATION = "SAVED_USE_SHOW_ANIMATION";
    private static final String SAVED_USE_DISMISS_ANIMATION = "SAVED_USE_DISMISS_ANIMATION";
    private static final String SAVED_USE_DIM_ANIMATION = "SAVED_USE_DIM_ANIMATION";

    private static final int ALPHA_MAX = 255;

    private Drawable mDimDrawable;
    private ObjectAnimator mDimAnimator;

    private int mShowDuration = 250;
    private int mDismissDuration = 250;
    private int mDimShowDuration = -1;
    private int mDimDismissDuration = -1;
    private int mDimColor = Color.BLACK;
    private boolean mUseShowAnimation = true;
    private boolean mUseDismissAnimation = true;
    private boolean mUseDimAnimation = true;
    //show or dismiss state used to dim animation callback
    private boolean mDimDismiss = false;

    //runnable list execute on animation end
    private List<Runnable> mDismissActions;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mShowDuration = savedInstanceState.getInt(SAVED_SHOW_DURATION, 250);
            mDismissDuration = savedInstanceState.getInt(SAVED_DISMISS_DURATION, 250);
            mDimShowDuration = savedInstanceState.getInt(SAVED_DIM_SHOW_DURATION, -1);
            mDimDismissDuration = savedInstanceState.getInt(SAVED_DIM_DISMISS_DURATION, -1);
            mDimColor = savedInstanceState.getInt(SAVED_DIM_COLOR, Color.BLACK);
            mUseShowAnimation = savedInstanceState.getBoolean(SAVED_USE_SHOW_ANIMATION, true);
            mUseDismissAnimation = savedInstanceState.getBoolean(SAVED_USE_DISMISS_ANIMATION, true);
            mUseDimAnimation = savedInstanceState.getBoolean(SAVED_USE_DIM_ANIMATION, true);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(SAVED_SHOW_DURATION, mShowDuration);
        outState.putInt(SAVED_DISMISS_DURATION, mDismissDuration);
        outState.putInt(SAVED_DIM_SHOW_DURATION, mDismissDuration);
        outState.putInt(SAVED_DIM_DISMISS_DURATION, mDimDismissDuration);
        outState.putInt(SAVED_DIM_COLOR, mDimColor);
        outState.putBoolean(SAVED_USE_SHOW_ANIMATION, mUseShowAnimation);
        outState.putBoolean(SAVED_USE_DISMISS_ANIMATION, mUseDismissAnimation);
        outState.putBoolean(SAVED_USE_DIM_ANIMATION, mUseDimAnimation);
        super.onSaveInstanceState(outState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AnimDialog(getContext(), getTheme());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getDialog() == null || !getShowsDialog()
                || getDialog().getWindow() == null) {
            return;
        }
        getDialog().setOnShowListener(this);
        if (getDialog() instanceof IAnimDialog) {
            ((IAnimDialog) getDialog()).setOnAnimInterceptCallback(this);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            getDialog().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            removeDim();

            //状态恢复，直接设置阴影，需通过 post 方法否则可能不起作用
            if (savedInstanceState != null) {
                if (getActivity() == null) {
                    return;
                }
                View rootView = getActivity().getWindow().getDecorView().getRootView();
                if (rootView == null) {
                    return;
                }
                rootView.post(new Runnable() {
                    @Override
                    public void run() {
                        applyDim((int) (getDimAmount() * ALPHA_MAX));
                    }
                });
            }
        }
    }

    @Override
    public void onShow(DialogInterface dialog) {
        if (mUseShowAnimation) {
            if (getDialog() != null && getDialog().getWindow() != null) {
                View targetView = getDialog().getWindow().getDecorView();
                onCreateShowAnimation(targetView);
                //start dim show animation first
                startDimShowAnimation();
                //start show animation
                onStartShowAnimation(targetView);
            }
        } else {
            if (mUseDimAnimation) {
                startDimShowAnimation();
            }
        }
    }

    @Override
    public void onTouchOutside(MotionEvent event) {
        dismissAllowingStateLoss();
    }

    @Override
    public void onBackPress() {
        dismissAllowingStateLoss();
    }

    @Override
    public void onDismissInternal() {
        dismissAllowingStateLoss();
    }

    @Override
    public void dismissAllowingStateLoss() {
        dismissWithAnimation(true);
    }

    @Override
    public void dismiss() {
        dismissWithAnimation(false);
    }

    @Override
    protected void dismissInternal(boolean allowStateLoss) {
        //AlertDialog internal will dismiss dialog when click buttons.
        //we need intercept it.
        if (getDialog() instanceof IAnimDialog) {
            ((IAnimDialog) getDialog()).setDismissByDf(true);
        }
        super.dismissInternal(allowStateLoss);
        if (getDialog() instanceof IAnimDialog) {
            ((IAnimDialog) getDialog()).setDismissByDf(false);
        }
    }

    /**
     * dismiss DialogFragment when custom dismiss Animator end
     *
     * @param stateLoss AllowingStateLoss
     */
    private void dismissWithAnimation(final boolean stateLoss) {
        if (mUseDismissAnimation) {
            if (getDialog() != null && getDialog().getWindow() != null) {
                View targetView = getDialog().getWindow().getDecorView();
                onCreateDismissAnimation(targetView, stateLoss);
                //start dim dismiss animation first
                startDimDismissAnimation();
                //start dismiss animation
                onStartDismissAnimation(targetView, stateLoss);
            }
        } else {
            if (mUseDimAnimation) {
                startDimDismissAnimation();
            } else {
                realDismiss(stateLoss);
            }
        }
    }

    /**
     * this method is real dismiss DialogFragment.
     *
     * @param stateLoss whether allowing state loss
     */
    protected void realDismiss(boolean stateLoss) {
        if (stateLoss) {
            super.dismissAllowingStateLoss();
        } else {
            super.dismiss();
        }
        //cancel dimAnimator if running.
        if (mDimAnimator != null && mDimAnimator.isRunning()) {
            mDimAnimator.cancel();
        }
        safeRemoveDim();
        //execute dismiss actions if not empty
        if (mDismissActions != null) {
            for (int i = 0; i < mDismissActions.size(); i++) {
                Runnable runnable = mDismissActions.get(i);
                if (runnable != null) {
                    runnable.run();
                }
            }
            mDismissActions.clear();
        }
    }

    /**
     * add action, they will execute when dismiss animation end.
     *
     * @param runnable runnable
     */
    protected void addAction(Runnable runnable) {
        if (mDismissActions == null) {
            mDismissActions = new ArrayList<>(1);
        }
        if (runnable == null) {
            return;
        }
        mDismissActions.add(runnable);
    }

    private void safeRemoveDim() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            removeDim();
        }
    }

    /**
     * custom dim for sync custom animator
     *
     * @param alpha dim initial value
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void applyDim(int alpha) {
        if (mActivity == null) {
            return;
        }
        initDimDrawableIfNull();

        mDimDrawable.setAlpha(alpha);
        ViewGroup rootView = (ViewGroup) mActivity.getWindow()
                .getDecorView().getRootView();
        mDimDrawable.setBounds(0, 0, rootView.getWidth(), rootView.getHeight());
        ViewGroupOverlay overlay = rootView.getOverlay();
        overlay.remove(mDimDrawable);
        overlay.add(mDimDrawable);
    }

    private void initDimDrawableIfNull() {
        if (mDimDrawable == null) {
            mDimDrawable = onCreateDimDrawable();
            if (mDimDrawable == null) {
                mDimDrawable = new ColorDrawable();
                ((ColorDrawable) mDimDrawable).setColor(mDimColor);
            }
        }
    }

    /**
     * clear dim
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void removeDim() {
        if (mActivity == null || mDimDrawable == null) {
            return;
        }
        ViewGroup rootView = (ViewGroup) mActivity.getWindow()
                .getDecorView().getRootView();
        ViewGroupOverlay overlay = rootView.getOverlay();
        overlay.remove(mDimDrawable);
        mDimDrawable = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mDimAnimator != null) {
            mDimAnimator.cancel();
            mDimAnimator.removeAllListeners();
            mDimAnimator = null;
        }
        safeRemoveDim();
    }

    /**
     * start dim show animation when onStartShowAnimation execute.
     */
    private void startDimShowAnimation() {
        mDimDismiss = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            //un use dim animation
            if (!mUseDimAnimation) {
                applyDim((int) Math.ceil(getDimAmount() * ALPHA_MAX));
                return;
            }
            applyDim(0);
            mDimAnimator = onCreateDimAnimator();
            int duration = mDimShowDuration > 0 && mDimShowDuration < mShowDuration
                    ? mDimShowDuration : mShowDuration;
            mDimAnimator.setDuration(duration);
            mDimAnimator.start();
        }
    }

    /**
     * start dim dismiss animation when onStartDismissAnimation execute.
     */
    private void startDimDismissAnimation() {
        mDimDismiss = true;
        if (mUseDimAnimation
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            //重建后，Animator 会是 null
            if (mDimAnimator == null) {
                mDimAnimator = onCreateDimAnimator();
            } else {
                if (mDimAnimator.isRunning()) {
                    mDimAnimator.cancel();
                }
            }
            int duration = mDimDismissDuration > 0 && mDimDismissDuration < mDismissDuration
                    ? mDimDismissDuration : mDismissDuration;
            mDimAnimator.setDuration(duration);
            mDimAnimator.reverse();
        }
    }

    /**
     * override this method can custom dim drawable
     *
     * @return dim drawable
     */
    @Nullable
    protected Drawable onCreateDimDrawable() {
        return null;
    }

    /**
     * create dim animation
     *
     * @return dim animation
     */
    @NonNull
    private ObjectAnimator onCreateDimAnimator() {
        initDimDrawableIfNull();
        ObjectAnimator animator = ObjectAnimator.ofInt(mDimDrawable, "alpha",
                0, (int) Math.ceil(getDimAmount() * ALPHA_MAX));
        animator.setInterpolator(new LinearInterpolator());
        //add animation callback
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                onDimAnimationStart(mDimDrawable, mDimDismiss);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                onDimAnimationEnd(mDimDrawable, mDimDismiss);
                //!mUseDismissAnimation && mUseDimAnimation && mDimDismiss
                //should dismiss DialogFragment when animation end.
                if (!mUseDismissAnimation && mDimDismiss) {
                    realDismiss(true);
                }
            }
        });
        return animator;
    }

    /**
     * dim animation start callback
     *
     * @param dimDrawable dim drawable
     * @param isDismiss   whether dismiss dialog
     */
    protected void onDimAnimationStart(Drawable dimDrawable, boolean isDismiss) {

    }

    /**
     * dim animation end callback
     *
     * @param dimDrawable dim drawable
     * @param isDismiss   whether dismiss dialog
     */
    protected void onDimAnimationEnd(Drawable dimDrawable, boolean isDismiss) {

    }

    /**
     * create show animation used to DialogFragment show
     *
     * @param targetView Window content view
     */
    protected abstract void onCreateShowAnimation(@NonNull View targetView);

    /**
     * show animation start
     *
     * @param targetView Window content view
     */
    protected abstract void onStartShowAnimation(@NonNull View targetView);

    /**
     * create dismiss animation used to DialogFragment dismiss
     *
     * @param targetView Window content view
     * @param stateLoss  whether allowing state loss
     */
    protected abstract void onCreateDismissAnimation(@NonNull View targetView, boolean stateLoss);

    /**
     * dismiss animation start
     *
     * @param targetView Window content view
     * @param stateLoss  whether allowing state loss
     */
    protected abstract void onStartDismissAnimation(@NonNull View targetView, boolean stateLoss);

    /**
     * use custom animation so prevent window animation style.
     *
     * @param animationStyle Window animation style
     */
    @Override
    public void setAnimationStyle(int animationStyle) {
//        super.setAnimationStyle(animationStyle);
    }

    /**
     * @return show animation duration
     */
    public int getShowDuration() {
        return mShowDuration;
    }

    /**
     * Sets show animation duration
     *
     * @param showDuration animation duration
     */
    public void setShowDuration(int showDuration) {
        mShowDuration = showDuration;
    }

    /**
     * @return dim animation duration used to DialogFragment show
     */
    public int getDimShowDuration() {
        return mDimShowDuration;
    }

    /**
     * Sets dim animation duration used to DialogFragment show
     *
     * @param dimShowDuration animation duration
     */
    public void setDimShowDuration(int dimShowDuration) {
        mDimShowDuration = dimShowDuration;
    }

    /**
     * @return dismiss animation duration
     */
    public int getDismissDuration() {
        return mDismissDuration;
    }

    /**
     * Sets dismiss animation duration
     *
     * @param dismissDuration dismiss animation duration
     */
    public void setDismissDuration(int dismissDuration) {
        mDismissDuration = dismissDuration;
    }

    /**
     * @return dim animation duration used to DialogFragment dismiss
     */
    public int getDimDismissDuration() {
        return mDimDismissDuration;
    }

    /**
     * Sets dim animation duration used to DialogFragment dismiss
     *
     * @param dimDismissDuration animation duration
     */
    public void setDimDismissDuration(int dimDismissDuration) {
        mDimDismissDuration = dimDismissDuration;
    }

    /**
     * Sets dim color
     *
     * @param dimColor dim color
     */
    public void setDimColor(int dimColor) {
        mDimColor = dimColor;
    }

    /**
     * @return whether use show animation
     */
    public boolean isUseShowAnimation() {
        return mUseShowAnimation;
    }

    /**
     * Sets whether use show animation
     *
     * @param useShowAnimation whether use show animation
     */
    public void setUseShowAnimation(boolean useShowAnimation) {
        mUseShowAnimation = useShowAnimation;
    }

    /**
     * @return whether use dismiss animation
     */
    public boolean isUseDismissAnimation() {
        return mUseDismissAnimation;
    }

    /**
     * Sets whether use dismiss animation
     *
     * @param useDismissAnimation whether use dismiss animation
     */
    public void setUseDismissAnimation(boolean useDismissAnimation) {
        mUseDismissAnimation = useDismissAnimation;
    }

    /**
     * @return whether use dim animation
     */
    public boolean isUseDimAnimation() {
        return mUseDimAnimation;
    }

    /**
     * Sets whether use dim animation
     *
     * @param useDimAnimation whether use dim animation
     */
    public void setUseDimAnimation(boolean useDimAnimation) {
        mUseDimAnimation = useDimAnimation;
    }
}
