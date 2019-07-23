package com.zyyoona7.easydfrag.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * The helper class of ITouchOutsideDialog
 *
 * @author zyyoona7
 */
public class TouchOutsideHelper {

    private boolean mCancelable;
    private boolean mCanceledOnTouchOutside;
    private Dialog mDialog;
    private OnTouchOutsideListener mOnTouchOutsideListener;

    public TouchOutsideHelper(Dialog dialog) {
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
        if (consume && mOnTouchOutsideListener != null) {
            mOnTouchOutsideListener.onTouchOutside(event);
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
        if (mCancelable && mOnTouchOutsideListener != null) {
            mOnTouchOutsideListener.onBackPress();
        }
    }

    public boolean isIntercept() {
        return mOnTouchOutsideListener != null;
    }

    public void setOnTouchOutsideListener(OnTouchOutsideListener listener) {
        mOnTouchOutsideListener = listener;
    }

    public void onDetachFromWindow() {
        mDialog = null;
        mOnTouchOutsideListener = null;
    }
}
