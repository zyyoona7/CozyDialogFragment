package com.zyyoona7.easydfrag.base;

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

import com.zyyoona7.easydfrag.dialog.AnimDialog;
import com.zyyoona7.easydfrag.dialog.IAnimDialog;
import com.zyyoona7.easydfrag.dialog.OnAnimInterceptCallback;

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

    private Drawable mDimDrawable;
    private ObjectAnimator mDimAnimator;

    private int mShowDuration = 250;
    private int mDismissDuration = 250;
    private int mDimShowDuration = -1;
    private int mDimDismissDuration = -1;
    private int mDimColor = Color.BLACK;

    //runnable list execute on animation end
    private List<Runnable> mDismissRunnables;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mShowDuration = savedInstanceState.getInt(SAVED_SHOW_DURATION, 250);
            mDismissDuration = savedInstanceState.getInt(SAVED_DISMISS_DURATION, 250);
            mDimShowDuration = savedInstanceState.getInt(SAVED_DIM_SHOW_DURATION, -1);
            mDimDismissDuration = savedInstanceState.getInt(SAVED_DIM_DISMISS_DURATION, -1);
            mDimColor = savedInstanceState.getInt(SAVED_DIM_COLOR, Color.BLACK);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(SAVED_SHOW_DURATION, mShowDuration);
        outState.putInt(SAVED_DISMISS_DURATION, mDismissDuration);
        outState.putInt(SAVED_DIM_SHOW_DURATION, mDismissDuration);
        outState.putInt(SAVED_DIM_DISMISS_DURATION, mDimDismissDuration);
        outState.putInt(SAVED_DIM_COLOR, mDimColor);
        super.onSaveInstanceState(outState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AnimDialog(getContext(),getTheme());
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
                        applyDim((int) (getDimAmount() * 255));
                    }
                });
            }
        }
    }

    @Override
    public void onShow(DialogInterface dialog) {
        if (getDialog() != null && getDialog().getWindow() != null) {
            View targetView = getDialog().getWindow().getDecorView();
            onCreateShowAnimation(targetView);
            onStartShowAnimation(targetView);
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
     * call dismissAllowingStateLoss super method
     */
    private void superDismissAllowingStateLoss() {
        super.dismissAllowingStateLoss();
    }

    /**
     * call dismiss super method
     */
    private void superDismiss() {
        super.dismiss();
    }

    protected void superDismissInternal(boolean stateLoss) {
        if (stateLoss) {
            superDismissAllowingStateLoss();
        } else {
            superDismiss();
        }
        safeRemoveDim();
        //execute dismiss actions if not empty
        if (mDismissRunnables != null) {
            for (int i = 0; i < mDismissRunnables.size(); i++) {
                Runnable runnable = mDismissRunnables.get(i);
                if (runnable != null) {
                    runnable.run();
                }
            }
            mDismissRunnables.clear();
        }
    }

    /**
     * add action, they will execute when dismiss animation end.
     *
     * @param runnable runnable
     */
    protected void addAction(Runnable runnable) {
        if (mDismissRunnables == null) {
            mDismissRunnables = new ArrayList<>(1);
        }
        if (runnable == null) {
            return;
        }
        mDismissRunnables.add(runnable);
    }

    /**
     * dismiss DialogFragment when custom dismiss Animator end
     *
     * @param stateLoss AllowingStateLoss
     */
    private void dismissWithAnimation(final boolean stateLoss) {
        if (getDialog() != null && getDialog().getWindow() != null) {
            View targetView = getDialog().getWindow().getDecorView();
            onCreateDismissAnimation(targetView, stateLoss);
            onStartDismissAnimation(targetView, stateLoss);
        }
    }

    /**
     * start dim show animation when onStartShowAnimation execute.
     */
    protected void startDimShowAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
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
    protected void startDimDismissAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            //重建后，Animator 会是 null
            if (mDimAnimator == null) {
                mDimAnimator = onCreateDimAnimator();
            }else {
                if (mDimAnimator.isRunning()) {
                    mDimAnimator.end();
                }
            }
            int duration = mDimDismissDuration > 0 && mDimDismissDuration < mDismissDuration
                    ? mDimDismissDuration : mDismissDuration;
            mDimAnimator.setDuration(duration);
            mDimAnimator.reverse();
        }
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
            mDimAnimator = null;
        }
        safeRemoveDim();
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
                0, (int) Math.ceil(getDimAmount() * 255));
        animator.setInterpolator(new LinearInterpolator());
        return animator;
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
}
