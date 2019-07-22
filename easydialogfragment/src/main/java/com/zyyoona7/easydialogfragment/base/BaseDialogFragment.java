package com.zyyoona7.easydialogfragment.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.ExternalDialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.zyyoona7.easydialogfragment.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BaseDialogFragment extends ExternalDialogFragment {

    private static final String TAG = "BaseDialogFragment";

    private static final String SAVED_DIM_AMOUNT = "SAVED_DIM_AMOUNT";
    private static final String SAVED_GRAVITY = "SAVED_GRAVITY";
    private static final String SAVED_HEIGHT = "SAVED_HEIGHT";
    private static final String SAVED_WIDTH = "SAVED_WIDTH";
    private static final String SAVED_KEYBOARD_ENABLE = "SAVED_KEYBOARD_ENABLE";
    private static final String SAVED_SOFT_INPUT_MODE = "SAVED_SOFT_INPUT_MODE";
    private static final String SAVED_CANCEL_ON_TOUCH_OUTSIDE = "SAVED_CANCEL_ON_TOUCH_OUTSIDE";
    private static final String SAVED_ANIMATION_STYLE = "SAVED_ANIMATION_STYLE";

    private static final String KEY_REQUEST_ID = "KEY_REQUEST_ID";

    protected FragmentActivity mActivity;

    @FloatRange(from = 0f, to = 1.0f)
    private float mDimAmount = 0.5f;
    private int mHeight = 0;
    private int mWidth = 0;
    private int mGravity = Gravity.CENTER;
    private boolean mKeyboardEnable = true;
    private int mSoftInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN;
    @StyleRes
    private int mAnimationStyle = 0;

    //点击外部是否可取消
    private boolean mCancelOnTouchOutside = true;

    //DialogFragment id
    private int mRequestId;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentActivity) {
            mActivity = (FragmentActivity) context;
        } else {
            throw new RuntimeException("Fragment onAttach method context is not FragmentActivity.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置Style 透明背景，No Title
        setStyle(AppCompatDialogFragment.STYLE_NORMAL, R.style.EasyDialogFragment);
        if (savedInstanceState != null) {
            mDimAmount = savedInstanceState.getFloat(SAVED_DIM_AMOUNT, 0.5f);
            mGravity = savedInstanceState.getInt(SAVED_GRAVITY, Gravity.CENTER);
            mWidth = savedInstanceState.getInt(SAVED_WIDTH, 0);
            mHeight = savedInstanceState.getInt(SAVED_HEIGHT, 0);
            mKeyboardEnable = savedInstanceState.getBoolean(SAVED_KEYBOARD_ENABLE, true);
            mSoftInputMode = savedInstanceState.getInt(SAVED_SOFT_INPUT_MODE,
                    WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            mCancelOnTouchOutside = savedInstanceState.getBoolean(SAVED_CANCEL_ON_TOUCH_OUTSIDE,
                    true);
            mAnimationStyle = savedInstanceState.getInt(SAVED_ANIMATION_STYLE, 0);
        }

        if (getArguments() != null) {
            mRequestId = getArguments().getInt(KEY_REQUEST_ID, -1);
        } else {
            mRequestId = -1;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putFloat(SAVED_DIM_AMOUNT, mDimAmount);
        outState.putInt(SAVED_GRAVITY, mGravity);
        outState.putInt(SAVED_WIDTH, mWidth);
        outState.putInt(SAVED_HEIGHT, mHeight);
        outState.putBoolean(SAVED_KEYBOARD_ENABLE, mKeyboardEnable);
        outState.putInt(SAVED_SOFT_INPUT_MODE, mSoftInputMode);
        outState.putBoolean(SAVED_CANCEL_ON_TOUCH_OUTSIDE, mCancelOnTouchOutside);
        outState.putInt(SAVED_ANIMATION_STYLE, mAnimationStyle);
        super.onSaveInstanceState(outState);
    }

    /**
     * 设置 arguments 带有 id，可以通过 {@link BaseDialogFragment#getRequestId()} 获取到
     *
     * @param args      args
     * @param requestId id
     */
    public void setArguments(@Nullable Bundle args, int requestId) {
        if (args == null) {
            args = new Bundle();
        }
        args.putInt(KEY_REQUEST_ID, requestId);
        setArguments(args);
    }

    public int getRequestId() {
        return mRequestId;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getDialog() == null || !getShowsDialog()
                || getDialog().getWindow() == null) {
            return;
        }
        Log.d(TAG, "onActivityCreated: ");
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.dimAmount = mDimAmount;
        layoutParams.gravity = mGravity;
        if (mWidth > 0 || mWidth == ViewGroup.LayoutParams.MATCH_PARENT
                || mWidth == ViewGroup.LayoutParams.WRAP_CONTENT) {
            layoutParams.width = mWidth;
        }
        if (mHeight > 0 || mHeight == ViewGroup.LayoutParams.MATCH_PARENT
                || mHeight == ViewGroup.LayoutParams.WRAP_CONTENT) {
            layoutParams.height = mHeight;
        }

        if (mAnimationStyle != 0) {
            window.setWindowAnimations(mAnimationStyle);
        }
        if (mKeyboardEnable) {
            window.setSoftInputMode(mSoftInputMode);
        }
        if (isCancelable()) {
            getDialog().setCanceledOnTouchOutside(mCancelOnTouchOutside);
        }
    }

    public boolean isCancelOnTouchOutside() {
        return mCancelOnTouchOutside;
    }

    public void setCancelOnTouchOutside(boolean cancelOnTouchOutside) {
        mCancelOnTouchOutside = cancelOnTouchOutside;
    }

    public float getDimAmount() {
        return mDimAmount;
    }

    public void setDimAmount(@FloatRange(from = 0f,to = 1f) float dimAmount) {
        mDimAmount = dimAmount;
    }

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(int width) {
        mWidth = width;
    }

    public void setFullWidth() {
        mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
    }

    public void setWrapWidth() {
        mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setHeight(int height) {
        mHeight = height;
    }

    public void setFullHeight() {
        mHeight = ViewGroup.LayoutParams.MATCH_PARENT;
    }

    public void setWrapHeight() {
        mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    public boolean isKeyboardEnable() {
        return mKeyboardEnable;
    }

    public void setKeyboardEnable(boolean keyboardEnable) {
        mKeyboardEnable = keyboardEnable;
    }

    public int getSoftInputMode() {
        return mSoftInputMode;
    }

    public void setSoftInputMode(int softInputMode) {
        mSoftInputMode = softInputMode;
    }

    public int getGravity() {
        return mGravity;
    }

    public void setGravity(int gravity) {
        mGravity = gravity;
    }

    public void setBottomGravity() {
        mGravity = Gravity.BOTTOM;
    }

    public int getAnimationStyle() {
        return mAnimationStyle;
    }

    public void setAnimationStyle(@StyleRes int animationStyle) {
        mAnimationStyle = animationStyle;
    }

    /**
     * 获取所有符合条件的 dialog 监听器
     *
     * @param listenerInterface listener class
     * @param <T>               listener
     * @return List of listeners
     */
    @NonNull
    @SuppressWarnings("unchecked")
    protected <T> List<T> getDialogListeners(Class<T> listenerInterface) {
        List<T> listeners = new ArrayList<>(3);
        if (listenerInterface.isInstance(getTargetFragment())) {
            listeners.add((T) getTargetFragment());
        }
        if (listenerInterface.isInstance(getParentFragment())) {
            listeners.add((T) getParentFragment());
        }
        if (listenerInterface.isInstance(getActivity())) {
            listeners.add((T) getActivity());
        }
        return Collections.unmodifiableList(listeners);
    }

    /**
     * 获取一个符合条件的 dialog 监听器
     *
     * @param listenerInterface listener class
     * @param <T>               listener
     * @return one of Listeners
     */
    @Nullable
    @SuppressWarnings("unchecked")
    protected <T> T getDialogListener(Class<T> listenerInterface) {
        if (listenerInterface.isInstance(getTargetFragment())) {
            return (T) getTargetFragment();
        }
        if (listenerInterface.isInstance(getParentFragment())) {
            return (T) getParentFragment();
        }
        if (listenerInterface.isInstance(getActivity())) {
            return (T) getActivity();
        }
        return null;
    }
}
