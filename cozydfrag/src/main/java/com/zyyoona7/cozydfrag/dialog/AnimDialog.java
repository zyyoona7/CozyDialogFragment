package com.zyyoona7.cozydfrag.dialog;

import android.content.Context;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialog;

import com.zyyoona7.cozydfrag.helper.AnimDialogHelper;
import com.zyyoona7.cozydfrag.callback.OnAnimInterceptCallback;

/**
 * custom Dialog for BaseAnimDialogFragment.
 *
 * @author zyyoona7
 * @since 2019/07/22
 */
public class AnimDialog extends AppCompatDialog implements IAnimDialog {

    private final AnimDialogHelper mAnimDialogHelper;

    public AnimDialog(Context context) {
        super(context);
        mAnimDialogHelper = new AnimDialogHelper(this);
    }

    public AnimDialog(Context context, int theme) {
        super(context, theme);
        mAnimDialogHelper = new AnimDialogHelper(this);
    }

    public AnimDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mAnimDialogHelper = new AnimDialogHelper(this);
    }

    @Override
    public void setCancelable(boolean flag) {
        mAnimDialogHelper.setCancelable(flag);
        super.setCancelable(flag);
    }

    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        mAnimDialogHelper.setCanceledOnTouchOutside(cancel);
        super.setCanceledOnTouchOutside(cancel);
    }

    @Override
    public void setDismissByDf(boolean dismissByDf) {
        mAnimDialogHelper.setDismissByDf(dismissByDf);
    }

    @Override
    public void setOnAnimInterceptCallback(OnAnimInterceptCallback listener) {
        mAnimDialogHelper.setOnAnimInterceptCallback(listener);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (mAnimDialogHelper.isIntercept()) {
            return mAnimDialogHelper.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        if (mAnimDialogHelper.isIntercept()) {
            mAnimDialogHelper.onBackPress();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void dismiss() {
        if (mAnimDialogHelper.onDismissInternal()) {
            return;
        }
        super.dismiss();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mAnimDialogHelper != null) {
            mAnimDialogHelper.onDetachFromWindow();
        }
    }
}
