package com.zyyoona7.demo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;

import com.zyyoona7.easydfrag.base.BaseAnimDialogFragment;

public class DemoDialogFragment extends BaseAnimDialogFragment {

    private static final String TAG = "DemoDialogFragment";
    private SpringAnimation mSpringAnimation;
    private Animator mDismissAnimator;

    public static DemoDialogFragment newInstance() {

        Bundle args = new Bundle();

        DemoDialogFragment fragment = new DemoDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_demo, container, false);
    }

    @Override
    protected void onCreateShowAnimation(@NonNull View targetView) {
        if (mSpringAnimation == null) {
            mSpringAnimation = new SpringAnimation(targetView, DynamicAnimation.TRANSLATION_Y);
            SpringForce springForce = new SpringForce(0);
            springForce.setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY);
            springForce.setStiffness(SpringForce.STIFFNESS_LOW);
            mSpringAnimation.setSpring(springForce);
            mSpringAnimation.setStartValue(targetView.getHeight());
        }
    }

    @Override
    protected void onStartShowAnimation(@NonNull View targetView) {
        if (mSpringAnimation == null) {
            return;
        }
        mSpringAnimation.start();
    }

    @Override
    protected void onCreateDismissAnimation(@NonNull View targetView, final boolean stateLoss) {
        if (mDismissAnimator == null) {
            mDismissAnimator = ObjectAnimator.ofFloat(targetView, "translationY",
                    0, targetView.getHeight());
            mDismissAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    realDismiss(stateLoss);
                }
            });
        }
    }

    @Override
    protected void onStartDismissAnimation(@NonNull View targetView, boolean stateLoss) {
        if (mSpringAnimation != null) {
            mSpringAnimation.cancel();
        }
        if (mDismissAnimator == null) {
            realDismiss(stateLoss);
            Log.d(TAG, "onStartDismissAnimation: dismissAnimator is null");
            return;
        }
        mDismissAnimator.setDuration(getDismissDuration());
        mDismissAnimator.start();
    }

}
