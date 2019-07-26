package com.zyyoona7.cozydfrag.helper;

import android.app.Dialog;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.zyyoona7.cozydfrag.callback.OnAnimInterceptCallback;

/**
 * The helper class of IAnimDialog
 *
 * @author zyyoona7
 * @since 2019/07/22
 */
public class AnimDialogHelper {

    private boolean mCancelable;
    private boolean mCanceledOnTouchOutside;
    private Dialog mDialog;
    private OnAnimInterceptCallback mOnAnimInterceptCallback;
    private boolean mDismissByDf;

    public AnimDialogHelper(Dialog dialog) {
        mDialog = dialog;
    }

    public void setCancelable(boolean cancelable) {
        mCancelable = cancelable;
    }

    public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        mCanceledOnTouchOutside = canceledOnTouchOutside;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (mDialog == null) {
            return false;
        } else {
            mDialog.getContext();
        }
        boolean consume = mCancelable && mCanceledOnTouchOutside && mDialog.isShowing()
                && shouldCloseOnTouch(mDialog.getContext(), event);
        if (consume && mOnAnimInterceptCallback != null) {
            mOnAnimInterceptCallback.onTouchOutside(event);
        }
        return consume;
    }

    private boolean shouldCloseOnTouch(Context context, MotionEvent event) {
        if (mDialog == null || mDialog.getWindow() == null) {
            return false;
        }
        final boolean isOutside =
                event.getAction() == MotionEvent.ACTION_DOWN && isOutOfBounds(context, event)
                        || event.getAction() == MotionEvent.ACTION_OUTSIDE;
        return mDialog.getWindow().peekDecorView() != null && isOutside;
    }

    private boolean isOutOfBounds(Context context, MotionEvent event) {
        if (mDialog == null || mDialog.getWindow() == null) {
            return false;
        }
        final int x = (int) event.getX();
        final int y = (int) event.getY();
        final int slop = ViewConfiguration.get(context).getScaledWindowTouchSlop();
        final View decorView = mDialog.getWindow().getDecorView();
        return (x < -slop) || (y < -slop)
                || (x > (decorView.getWidth() + slop))
                || (y > (decorView.getHeight() + slop));
    }

    public void onBackPress() {
        if (mCancelable && mOnAnimInterceptCallback != null) {
            mOnAnimInterceptCallback.onBackPress();
        }
    }

    public boolean onDismissInternal() {
        boolean consume = !mDismissByDf && mOnAnimInterceptCallback != null;
        if (consume) {
            mOnAnimInterceptCallback.onDismissInternal();
        }
        return consume;
    }

    public boolean isIntercept() {
        return mOnAnimInterceptCallback != null;
    }

    public void setOnAnimInterceptCallback(OnAnimInterceptCallback listener) {
        mOnAnimInterceptCallback = listener;
    }

    public void setDismissByDf(boolean dismissByDf) {
        mDismissByDf = dismissByDf;
    }

    public void onDetachFromWindow() {
        mDialog = null;
        mOnAnimInterceptCallback = null;
    }
}
