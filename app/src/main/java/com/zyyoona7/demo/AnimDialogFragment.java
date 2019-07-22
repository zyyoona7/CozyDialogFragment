package com.zyyoona7.demo;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ExternalAlertDialog;

import com.zyyoona7.easydialogfragment.base.BaseAnimDialogFragment;

public class AnimDialogFragment extends BaseAnimDialogFragment {

    public static AnimDialogFragment newInstance() {
        
        Bundle args = new Bundle();
        
        AnimDialogFragment fragment = new AnimDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        ExternalAlertDialog.Builder builder = new ExternalAlertDialog.Builder(getContext());
        builder.setTitle("Anim");
        builder.setMessage("Anim Dialog");
        return builder.create();
    }

    @Override
    protected Animator onCreateShowAnimator(View targetView) {
        PropertyValuesHolder holder1=PropertyValuesHolder.ofFloat("scaleX",0.5f,1f);
        PropertyValuesHolder holder2=PropertyValuesHolder.ofFloat("scaleY",0.5f,1f);
        PropertyValuesHolder holder3=PropertyValuesHolder.ofFloat("alpha",0f,1f);
        ObjectAnimator animator=ObjectAnimator
                .ofPropertyValuesHolder(targetView,holder1,holder2,holder3);
        animator.setInterpolator(new OvershootInterpolator());
        return animator;
    }

    @Override
    protected Animator onCreateDismissAnimator(View targetView) {
        PropertyValuesHolder holder1=PropertyValuesHolder.ofFloat("scaleX",1f,0.5f);
        PropertyValuesHolder holder2=PropertyValuesHolder.ofFloat("scaleY",1f,0.5f);
        PropertyValuesHolder holder3=PropertyValuesHolder.ofFloat("alpha",1f,0f);
        return ObjectAnimator
                .ofPropertyValuesHolder(targetView,holder1,holder2,holder3);
    }
}
