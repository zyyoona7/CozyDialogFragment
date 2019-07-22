package com.zyyoona7.easydialogfragment.dialog;

import android.content.Context;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialog;

public class TouchOutsideDialog extends AppCompatDialog implements ITouchOutsideDialog {

    private final TouchOutsideHelper mTouchOutsideHelper;

    public TouchOutsideDialog(Context context) {
        super(context);
        mTouchOutsideHelper = new TouchOutsideHelper(this);
    }

    public TouchOutsideDialog(Context context, int theme) {
        super(context, theme);
        mTouchOutsideHelper = new TouchOutsideHelper(this);
    }

    public TouchOutsideDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mTouchOutsideHelper = new TouchOutsideHelper(this);
    }

    @Override
    public void setCancelable(boolean flag) {
        mTouchOutsideHelper.setCancelable(flag);
        super.setCancelable(flag);
    }

    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        mTouchOutsideHelper.setCanceledOnTouchOutside(cancel);
        super.setCanceledOnTouchOutside(cancel);
    }

    @Override
    public void setOnTouchOutsideListener(OnTouchOutsideListener listener) {
        mTouchOutsideHelper.setOnTouchOutsideListener(listener);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (mTouchOutsideHelper.isIntercept()) {
            return mTouchOutsideHelper.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        if (mTouchOutsideHelper.isIntercept()) {
            mTouchOutsideHelper.onBackPress();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mTouchOutsideHelper != null) {
            mTouchOutsideHelper.onDetachFromWindow();
        }
    }
}
