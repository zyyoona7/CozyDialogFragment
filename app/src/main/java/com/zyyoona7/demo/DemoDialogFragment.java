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

import com.zyyoona7.cozydfrag.base.BaseAnimDialogFragment;

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //适配沉浸式可解决 DialogFragment 铺满屏幕导致状态栏变黑
        //但是注意 此方法状态栏和导航栏都会侵入，所以如果内容太大或者不透明那么将会遮住导航栏
//        ImmersionBar.with(this)
//                .statusBarDarkFont(true,0.2f)
//                .fullScreen(true)
////                .navigationBarEnable(true)
//                .init();
        //所有的 FlAG 都是基于 Window 的，比如沉浸式状态栏，全屏等都是基于 Window 操作的

        //Dialog 和 Activity 是两个独立的 Window ，所有想要 Dialog 拥有同样的效果需要给-
        //Dialog 的 Window 设置相同的属性

    }

    @Override
    protected void onCreateShowAnimation(@NonNull View targetView) {
        Log.d(TAG, "onCreateShowAnimation: ");
        //target view 每次执行完生命周期时都会变，因为 SpringAnimation 没有单独的更新 target 方法
        //所以每次都初始化一次
        mSpringAnimation = new SpringAnimation(targetView, DynamicAnimation.TRANSLATION_Y);
        SpringForce springForce = new SpringForce(0);
        springForce.setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY);
        springForce.setStiffness(SpringForce.STIFFNESS_LOW);
        mSpringAnimation.setSpring(springForce);
        mSpringAnimation.setStartValue(targetView.getHeight());
    }

    @Override
    protected void onStartShowAnimation(@NonNull View targetView) {
        Log.d(TAG, "onStartShowAnimation: ");
        if (mSpringAnimation == null) {
            return;
        }
        mSpringAnimation.start();
    }

    @Override
    protected void onCreateDismissAnimation(@NonNull View targetView, final boolean stateLoss) {
        Log.d(TAG, "onCreateDismissAnimation: ");
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
        //target view 每次执行完生命周期时都会变
        mDismissAnimator.setTarget(targetView);
    }

    @Override
    protected void onStartDismissAnimation(@NonNull View targetView, boolean stateLoss) {
        Log.d(TAG, "onStartDismissAnimation: ");
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
