package com.zyyoona7.demo;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AnimAlertDialog;

import com.zyyoona7.demo.interpolator.SpringInterpolator;
import com.zyyoona7.cozydfrag.base.BaseAnimatorDialogFragment;
import com.zyyoona7.cozydfrag.callback.OnDialogClickListener;

public class AnimDialogFragment extends BaseAnimatorDialogFragment {

    public static AnimDialogFragment newInstance() {
        return new AnimDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AnimAlertDialog.Builder builder = new AnimAlertDialog.Builder(mActivity);
        builder.setTitle("Anim");
        builder.setMessage("Anim Dialog" + getRequestId());
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, final int which) {
                final OnDialogClickListener listener = getDialogClickListener();
                if (listener == null) {
                    return;
                }
                postOnDismiss(new Runnable() {
                    @Override
                    public void run() {
                        OnDialogClickListener clickListener = getDialogClickListener();
                        if (clickListener != null) {
                            clickListener.onClick(AnimDialogFragment.this, which, getRequestId());
                        }
                    }
                });
            }
        });
        return builder.create();
    }

    @Override
    protected Animator onCreateShowAnimator(@NonNull View targetView) {
        PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat("scaleX", 0.5f, 1f);
        PropertyValuesHolder holder2 = PropertyValuesHolder.ofFloat("scaleY", 0.5f, 1f);
        PropertyValuesHolder holder3 = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
        ObjectAnimator animator = ObjectAnimator
                .ofPropertyValuesHolder(targetView, holder1, holder2, holder3);
        animator.setInterpolator(new SpringInterpolator(0.6f));
        return animator;
    }

    @Override
    protected Animator onCreateDismissAnimator(@NonNull View targetView) {
        PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat("scaleX", 1f, 0.5f);
        PropertyValuesHolder holder2 = PropertyValuesHolder.ofFloat("scaleY", 1f, 0.5f);
        PropertyValuesHolder holder3 = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);
        return ObjectAnimator
                .ofPropertyValuesHolder(targetView, holder1, holder2, holder3);
    }
}
