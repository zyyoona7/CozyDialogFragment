package com.zyyoona7.easydfrag.base;

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

import com.zyyoona7.easydfrag.dialog.ITouchOutsideDialog;
import com.zyyoona7.easydfrag.dialog.OnTouchOutsideListener;
import com.zyyoona7.easydfrag.dialog.TouchOutsideDialog;

public class BaseAnimDialogFragment extends BaseDialogFragment
        implements DialogInterface.OnShowListener, OnTouchOutsideListener {

    private static final String SAVED_SHOW_DURATION = "SAVED_SHOW_DURATION";
    private static final String SAVED_DISMISS_DURATION = "SAVED_DISMISS_DURATION";
    private static final String SAVED_DIM_SHOW_DURATION = "SAVED_DIM_SHOW_DURATION";
    private static final String SAVED_DIM_DISMISS_DURATION = "SAVED_DIM_DISMISS_DURATION";
    private static final String SAVED_DIM_COLOR = "SAVED_DIM_COLOR";

    private Drawable mDimDrawable;
    private ObjectAnimator mDimAnimator;
    private Animator mShowAnimator;
    private Animator mDismissAnimator;

    private int mShowDuration = 250;
    private int mDismissDuration = 250;
    private int mDimShowDuration = -1;
    private int mDimDismissDuration = -1;
    private int mDimColor = Color.BLACK;

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
        return new TouchOutsideDialog(getContext());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getDialog() == null || !getShowsDialog()
                || getDialog().getWindow() == null) {
            return;
        }
        getDialog().setOnShowListener(this);
        if (getDialog() instanceof ITouchOutsideDialog) {
            ((ITouchOutsideDialog) getDialog()).setOnTouchOutsideListener(this);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            getDialog().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            clearDim();

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
        if (mShowAnimator == null) {
            if (getDialog() != null && getDialog().getWindow() != null) {
                mShowAnimator = onCreateShowAnimator(getDialog().getWindow().getDecorView());
            }
            if (mShowAnimator != null) {
                mShowAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                });
            }
        }
        if (mShowAnimator == null) {
            return;
        }
        mShowAnimator.setDuration(mShowDuration);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            applyDim(0);
            mDimAnimator = onCreateDimAnimator();
            int duration = mDimShowDuration > 0 && mDimShowDuration < mShowDuration
                    ? mDimShowDuration : mShowDuration;
            mDimAnimator.setDuration(duration);
            mDimAnimator.start();
        }
        mShowAnimator.start();
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
    public void dismissAllowingStateLoss() {
        dismissWithAnimator(true);
    }

    @Override
    public void dismiss() {
        dismissWithAnimator(false);
    }

    private void superDismissAllowingStateLoss() {
        super.dismissAllowingStateLoss();
    }

    private void superDismiss() {
        super.dismiss();
    }

    private void dismissWithAnimator(final boolean stateLoss) {
        if (mDismissAnimator == null) {
            if (getDialog() != null && getDialog().getWindow() != null) {
                mDismissAnimator = onCreateDismissAnimator(getDialog().getWindow().getDecorView());
            }
            if (mDismissAnimator != null) {
                mDismissAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (stateLoss) {
                            superDismissAllowingStateLoss();
                        } else {
                            superDismiss();
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                            if (mDimAnimator != null) {
                                mDimAnimator.cancel();
                            }
                            clearDim();
                        }
                    }
                });
            }
        }
        if (mDismissAnimator == null) {
            if (stateLoss) {
                superDismissAllowingStateLoss();
            } else {
                superDismiss();
            }
            return;
        }

        mDismissAnimator.setDuration(mDismissDuration);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            //重建后，Animator 会是 null
            if (mDimAnimator == null) {
                mDimAnimator = onCreateDimAnimator();
            }
            int duration = mDimDismissDuration > 0 && mDimDismissDuration < mDismissDuration
                    ? mDimDismissDuration : mDismissDuration;
            mDimAnimator.setDuration(duration);
            mDimAnimator.reverse();
        }
        mDismissAnimator.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void applyDim(int alpha) {
        if (mActivity == null) {
            return;
        }

        initDimDrawableIfNull();

        mDimDrawable.setAlpha(alpha);
        ViewGroup rootView = (ViewGroup) mActivity.getWindow().getDecorView().getRootView();
        mDimDrawable.setBounds(0, 0, rootView.getWidth(), rootView.getHeight());
        ViewGroupOverlay overlay = rootView.getOverlay();
        //先clear再添加
        overlay.clear();
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void clearDim() {
        if (mActivity == null) {
            return;
        }
        ViewGroup rootView = (ViewGroup) mActivity.getWindow().getDecorView().getRootView();
        ViewGroupOverlay overlay = rootView.getOverlay();
        overlay.clear();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mDimAnimator != null) {
            mDimAnimator.cancel();
            mDimAnimator = null;
        }

        if (mShowAnimator != null) {
            mShowAnimator.cancel();
            mShowAnimator = null;
        }

        if (mDismissAnimator != null) {
            mDismissAnimator.cancel();
            mDismissAnimator = null;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            clearDim();
        }
    }

    @Nullable
    protected Drawable onCreateDimDrawable() {
        return null;
    }

    @NonNull
    private ObjectAnimator onCreateDimAnimator() {
        initDimDrawableIfNull();
        ObjectAnimator animator = ObjectAnimator.ofInt(mDimDrawable, "alpha",
                0, Math.round(getDimAmount() * 255));
        animator.setInterpolator(new LinearInterpolator());
        return animator;
    }

    @Nullable
    protected Animator onCreateShowAnimator(View targetView) {
        return null;
    }

    @Nullable
    protected Animator onCreateDismissAnimator(View targetView) {
        return null;
    }

    public void setShowDuration(int showDuration) {
        mShowDuration = showDuration;
    }

    public void setDimShowDuration(int dimShowDuration) {
        mDimShowDuration = dimShowDuration;
    }

    public void setDismissDuration(int dismissDuration) {
        mDismissDuration = dismissDuration;
    }

    public void setDimDismissDuration(int dimDismissDuration) {
        mDimDismissDuration = dimDismissDuration;
    }

    public void setDimColor(int dimColor) {
        mDimColor = dimColor;
    }
}