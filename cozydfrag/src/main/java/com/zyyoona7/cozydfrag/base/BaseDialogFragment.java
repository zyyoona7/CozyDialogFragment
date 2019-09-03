package com.zyyoona7.cozydfrag.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
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

import com.zyyoona7.cozydfrag.R;
import com.zyyoona7.cozydfrag.callback.OnDialogClickListener;
import com.zyyoona7.cozydfrag.callback.OnDialogDismissListener;
import com.zyyoona7.cozydfrag.callback.OnDialogMultiChoiceClickListener;
import com.zyyoona7.cozydfrag.helper.CozyHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * base DialogFragment
 *
 * @author zyyoona7
 * @since 2019/07/22
 */
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
    private static final String SAVED_REQUEST_ID = "SAVED_REQUEST_ID";
    private static final String SAVED_PADDING_LEFT = "SAVED_PADDING_LEFT";
    private static final String SAVED_PADDING_TOP = "SAVED_PADDING_TOP";
    private static final String SAVED_PADDING_RIGHT = "SAVED_PADDING_RIGHT";
    private static final String SAVED_PADDING_BOTTOM = "SAVED_PADDING_BOTTOM";
    private static final String SAVED_FULLSCREEN = "SAVED_FULLSCREEN";
    private static final String SAVED_HIDE_STATUS_BAR = "SAVED_HIDE_STATUS_BAR";
    private static final String SAVED_STATUS_BAR_LIGHT_MODE = "SAVED_STATUS_BAR_LIGHT_MODE";

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
    private boolean mCanceledOnTouchOutside = true;
    private int mPaddingLeft = -1;
    private int mPaddingTop = -1;
    private int mPaddingRight = -1;
    private int mPaddingBottom = -1;
    //全屏并且适配导航栏的情况需要设置
    //<item name="android:windowIsFloating">false</item>
    //否则导航栏会被遮挡，如果没有导航栏则不影响
    //PS:windowIsFloating=true 适配导航栏太复杂
    private boolean mFullscreen = false;
    //全屏情况，是否隐藏状态栏 >4.4
    private boolean mHideStatusBar = false;
    //全屏情况，是否是浅色模式，即状态栏字体为深色>6.0
    private boolean mStatusBarLightMode = false;

    //DialogFragment id
    private int mRequestId = -1;

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
        if (savedInstanceState != null) {
            mDimAmount = savedInstanceState.getFloat(SAVED_DIM_AMOUNT, 0.5f);
            mGravity = savedInstanceState.getInt(SAVED_GRAVITY, Gravity.CENTER);
            mWidth = savedInstanceState.getInt(SAVED_WIDTH, 0);
            mHeight = savedInstanceState.getInt(SAVED_HEIGHT, 0);
            mKeyboardEnable = savedInstanceState.getBoolean(SAVED_KEYBOARD_ENABLE, true);
            mSoftInputMode = savedInstanceState.getInt(SAVED_SOFT_INPUT_MODE,
                    WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            mCanceledOnTouchOutside = savedInstanceState.getBoolean(SAVED_CANCEL_ON_TOUCH_OUTSIDE,
                    true);
            mAnimationStyle = savedInstanceState.getInt(SAVED_ANIMATION_STYLE, 0);
            mRequestId = savedInstanceState.getInt(SAVED_REQUEST_ID, -1);
            mPaddingLeft = savedInstanceState.getInt(SAVED_PADDING_LEFT, -1);
            mPaddingTop = savedInstanceState.getInt(SAVED_PADDING_TOP, -1);
            mPaddingRight = savedInstanceState.getInt(SAVED_PADDING_RIGHT, -1);
            mPaddingBottom = savedInstanceState.getInt(SAVED_PADDING_BOTTOM, -1);
            mFullscreen = savedInstanceState.getBoolean(SAVED_FULLSCREEN, false);
            mHideStatusBar = savedInstanceState.getBoolean(SAVED_HIDE_STATUS_BAR, true);
            mStatusBarLightMode = savedInstanceState.getBoolean(SAVED_STATUS_BAR_LIGHT_MODE, false);
        }
        //设置Style 透明背景，No Title
        setStyle(AppCompatDialogFragment.STYLE_NORMAL,
                mFullscreen ? R.style.NoFloatingDialogFragment : R.style.FloatingDialogFragment);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putFloat(SAVED_DIM_AMOUNT, mDimAmount);
        outState.putInt(SAVED_GRAVITY, mGravity);
        outState.putInt(SAVED_WIDTH, mWidth);
        outState.putInt(SAVED_HEIGHT, mHeight);
        outState.putBoolean(SAVED_KEYBOARD_ENABLE, mKeyboardEnable);
        outState.putInt(SAVED_SOFT_INPUT_MODE, mSoftInputMode);
        outState.putBoolean(SAVED_CANCEL_ON_TOUCH_OUTSIDE, mCanceledOnTouchOutside);
        outState.putInt(SAVED_ANIMATION_STYLE, mAnimationStyle);
        outState.putInt(SAVED_REQUEST_ID, mRequestId);
        outState.putInt(SAVED_PADDING_LEFT, mPaddingLeft);
        outState.putInt(SAVED_PADDING_TOP, mPaddingTop);
        outState.putInt(SAVED_PADDING_RIGHT, mPaddingRight);
        outState.putInt(SAVED_PADDING_BOTTOM, mPaddingBottom);
        outState.putBoolean(SAVED_FULLSCREEN, mFullscreen);
        outState.putBoolean(SAVED_HIDE_STATUS_BAR, mHideStatusBar);
        outState.putBoolean(SAVED_STATUS_BAR_LIGHT_MODE, mStatusBarLightMode);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getDialog() == null || !getShowsDialog()
                || getDialog().getWindow() == null) {
            return;
        }
        final Window window = getDialog().getWindow();
        //set WindowManager LayoutParams
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
        //if fullscreen set width,height match_parent
        if (mFullscreen) {
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        }
        window.setAttributes(layoutParams);

        View decor = window.getDecorView();
        decor.setPadding(mPaddingLeft >= 0 ? mPaddingLeft : decor.getPaddingLeft(),
                mPaddingTop >= 0 ? mPaddingTop : decor.getPaddingTop(),
                mPaddingRight >= 0 ? mPaddingRight : decor.getPaddingRight(),
                mPaddingBottom >= 0 ? mPaddingBottom : decor.getPaddingBottom());

        if (mFullscreen) {
            if (mHideStatusBar) {
                window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            } else {
                CozyHelper.setTransparentStatusBar(window);
                if (mStatusBarLightMode) {
                    //设置状态栏字体颜色只对全屏有效，如果不是Match_parent即使设置了也不起作用
                    CozyHelper.setStatusBarLightMode(window, true);
                }
            }
            //全屏时适配刘海屏
            CozyHelper.fitNotch(window);
        }
        if (mAnimationStyle != 0) {
            window.setWindowAnimations(mAnimationStyle);
        }
        if (mKeyboardEnable) {
            window.setSoftInputMode(mSoftInputMode);
        }
        if (isCancelable()) {
            getDialog().setCanceledOnTouchOutside(mCanceledOnTouchOutside);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        for (OnDialogDismissListener dialogDismissListener : getDialogDismissListeners()) {
            dialogDismissListener.onDismiss(this, getRequestId());
        }
    }

    /**
     * @return requestId of you set
     */
    public int getRequestId() {
        return mRequestId;
    }

    /**
     * Sets requestId for DialogFragment, Can use by {@link BaseDialogFragment#getRequestId()}
     *
     * @param requestId requestId
     */
    public void setRequestId(int requestId) {
        mRequestId = requestId;
    }

    /**
     * @return Whether the dialog should be canceled when
     * touched outside the window.
     */
    public boolean isCanceledOnTouchOutside() {
        return mCanceledOnTouchOutside;
    }

    /**
     * Sets whether this dialog is canceled when touched outside the window's
     * bounds.
     *
     * @param canceledOnTouchOutside Whether the dialog should be canceled when
     *                               touched outside the window.
     */
    public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        mCanceledOnTouchOutside = canceledOnTouchOutside;
    }

    /**
     * @return window dim amount
     */
    public float getDimAmount() {
        return mDimAmount;
    }

    /**
     * Sets window dim amount when dialog show.
     *
     * @param dimAmount dim amount
     */
    public void setDimAmount(@FloatRange(from = 0f, to = 1f) float dimAmount) {
        mDimAmount = dimAmount;
    }

    /**
     * @return DialogFragment content already set width
     */
    public int getWidth() {
        return mWidth;
    }

    /**
     * Sets DialogFragment content width
     *
     * @param width DialogFragment content height you want set
     */
    public void setWidth(int width) {
        mWidth = width;
    }

    /**
     * Sets DialogFragment content width with dip unit.
     *
     * @param widthDp width dip
     */
    public void setWidth(float widthDp) {
        mWidth = dp2px(widthDp);
    }

    /**
     * Sets DialogFragment content width MATCH_PARENT.
     */
    public void setMatchWidth() {
        mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
    }

    /**
     * Sets DialogFragment content width WRAP_CONTENT
     */
    public void setWrapWidth() {
        mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    /**
     * Sets DialogFragment content width with screen width percent.
     *
     * @param widthPercent percent of screen width
     */
    public void setWidthPercent(@FloatRange(from = 0, to = 1f) float widthPercent) {
        mWidth = (int) (Resources.getSystem().getDisplayMetrics().widthPixels * widthPercent);
    }

    /**
     * @return DialogFragment content already set height
     */
    public int getHeight() {
        return mHeight;
    }

    /**
     * Sets DialogFragment content height.
     *
     * @param height DialogFragment content height you want set
     */
    public void setHeight(int height) {
        mHeight = height;
    }

    /**
     * Sets DialogFragment content height with dip unit.
     *
     * @param heightDp height dip
     */
    public void setHeight(float heightDp) {
        mHeight = dp2px(heightDp);
    }

    /**
     * Sets DialogFragment content height MATCH_PARENT.
     */
    public void setMatchHeight() {
        mHeight = ViewGroup.LayoutParams.MATCH_PARENT;
    }

    /**
     * Sets DialogFragment content height WRAP_CONTENT.
     */
    public void setWrapHeight() {
        mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    /**
     * Sets DialogFragment content height with screen height percent.
     *
     * @param heightPercent percent of screen height
     */
    public void setHeightPercent(@FloatRange(from = 0f, to = 1f) float heightPercent) {
        mHeight = (int) (Resources.getSystem().getDisplayMetrics().heightPixels * heightPercent);
    }

    /**
     * @return Window content view padding left
     */
    public int getPaddingLeft() {
        return mPaddingLeft;
    }

    /**
     * Sets Window content view padding left.
     *
     * @param paddingLeft Window content view padding left
     */
    public void setPaddingLeft(int paddingLeft) {
        mPaddingLeft = paddingLeft;
    }

    /**
     * Sets Window content view padding left with dip unit.
     *
     * @param paddingLeftDp padding left dip
     */
    public void setPaddingLeft(float paddingLeftDp) {
        mPaddingLeft = dp2px(paddingLeftDp);
    }

    /**
     * @return Window content view padding top
     */
    public int getPaddingTop() {
        return mPaddingTop;
    }

    /**
     * Sets Window content view padding top.
     *
     * @param paddingTop Window content view padding top
     */
    public void setPaddingTop(int paddingTop) {
        mPaddingTop = paddingTop;
    }

    /**
     * Sets Window content view padding top with dip unit.
     *
     * @param paddingTopDp padding top dip
     */
    public void setPaddingTop(float paddingTopDp) {
        mPaddingTop = dp2px(paddingTopDp);
    }

    /**
     * @return Window content view padding top
     */
    public int getPaddingRight() {
        return mPaddingRight;
    }

    /**
     * Sets Window content view padding right.
     *
     * @param paddingRight Window content view padding right
     */
    public void setPaddingRight(int paddingRight) {
        mPaddingRight = paddingRight;
    }

    /**
     * Sets Window content view padding right with dip unit.
     *
     * @param paddingRightDp padding right dip
     */
    public void setPaddingRight(float paddingRightDp) {
        mPaddingRight = dp2px(paddingRightDp);
    }

    /**
     * @return Window content view padding bottom
     */
    public int getPaddingBottom() {
        return mPaddingBottom;
    }

    /**
     * Sets Window content view padding bottom.
     *
     * @param paddingBottom Window content view padding bottom
     */
    public void setPaddingBottom(int paddingBottom) {
        mPaddingBottom = paddingBottom;
    }

    /**
     * Sets Window content view padding bottom with dip unit.
     *
     * @param paddingBottomDp padding bottom dip
     */
    public void setPaddingBottom(float paddingBottomDp) {
        mPaddingBottom = dp2px(paddingBottomDp);
    }

    /**
     * Sets Window content view left and right padding at the same time.
     *
     * @param paddingLeft  padding left
     * @param paddingRight padding right
     */
    public void setPaddingHorizontal(int paddingLeft, int paddingRight) {
        mPaddingLeft = paddingLeft;
        mPaddingRight = paddingRight;
    }

    /**
     * Sets Window content view left and right padding with dip unit at the same time.
     *
     * @param paddingLeftDp  padding left dip
     * @param paddingRightDp padding right dip
     */
    public void setPaddingHorizontal(float paddingLeftDp, float paddingRightDp) {
        mPaddingLeft = dp2px(paddingLeftDp);
        mPaddingRight = dp2px(paddingRightDp);
    }

    /**
     * Sets Window content view top and bottom padding at the same time.
     *
     * @param paddingTop    padding top
     * @param paddingBottom padding bottom
     */
    public void setPaddingVertical(int paddingTop, int paddingBottom) {
        mPaddingTop = paddingTop;
        mPaddingBottom = paddingBottom;
    }

    /**
     * Sets Window content view top and bottom padding with dip unit at the same time.
     *
     * @param paddingTopDp    padding top dip
     * @param paddingBottomDp padding bottom dip
     */
    public void setPaddingVertical(float paddingTopDp, float paddingBottomDp) {
        mPaddingTop = dp2px(paddingTopDp);
        mPaddingBottom = dp2px(paddingBottomDp);
    }

    /**
     * @return Whether keyboard enabled.
     */
    public boolean isKeyboardEnable() {
        return mKeyboardEnable;
    }

    /**
     * Sets keyboard enabled
     *
     * @param keyboardEnable Whether keyboard enabled.
     */
    public void setKeyboardEnable(boolean keyboardEnable) {
        mKeyboardEnable = keyboardEnable;
    }

    /**
     * @return window soft input mode.
     */
    public int getSoftInputMode() {
        return mSoftInputMode;
    }

    /**
     * Sets soft input mode for Window.
     *
     * @param softInputMode soft input mode
     */
    public void setSoftInputMode(int softInputMode) {
        mSoftInputMode = softInputMode;
    }

    /**
     * @return Window content gravity
     */
    public int getGravity() {
        return mGravity;
    }

    /**
     * Sets Window content gravity.
     *
     * @param gravity Window content gravity
     */
    public void setGravity(int gravity) {
        mGravity = gravity;
    }

    /**
     * Sets Window content gravity bottom.
     */
    public void setBottomGravity() {
        mGravity = Gravity.BOTTOM;
    }

    /**
     * @return whether Window fullscreen
     */
    public boolean isFullscreen() {
        return mFullscreen;
    }

    /**
     * @return whether hide status bar
     */
    public boolean isHideStatusBar() {
        return mHideStatusBar;
    }

    /**
     * Sets whether Window fullscreen
     *
     * @param fullscreen
     */
    public void setFullscreen(boolean fullscreen) {
        setFullscreen(fullscreen, true, false);
    }

    /**
     * Sets whether Window fullscreen
     *
     * @param fullscreen    whether Window fullscreen
     * @param hideStatusBar whether hide status bar
     */
    public void setFullscreen(boolean fullscreen, boolean hideStatusBar) {
        setFullscreen(fullscreen, hideStatusBar, false);
    }

    /**
     * Sets whether Window fullscreen
     *
     * @param fullscreen    whether Window fullscreen
     * @param hideStatusBar whether hide status bar
     * @param isLightMode   whether status bar light mode
     */
    public void setFullscreen(boolean fullscreen, boolean hideStatusBar, boolean isLightMode) {
        mFullscreen = fullscreen;
        mHideStatusBar = hideStatusBar;
        mStatusBarLightMode = isLightMode;
    }

    /**
     * @return Window animation style
     */
    public int getAnimationStyle() {
        return mAnimationStyle;
    }

    /**
     * Sets Window animation style
     *
     * @param animationStyle Window animation style
     */
    public void setAnimationStyle(@StyleRes int animationStyle) {
        mAnimationStyle = animationStyle;
    }

    /**
     * @return OnDialogDismissListener list from targetFragment,parentFragment,getActivity
     */
    protected List<OnDialogDismissListener> getDialogDismissListeners() {
        return getDialogListeners(OnDialogDismissListener.class);
    }

    /**
     * @return OnDialogClickListener list from targetFragment,parentFragment,getActivity
     */
    protected List<OnDialogClickListener> getDialogClickListeners() {
        return getDialogListeners(OnDialogClickListener.class);
    }

    /**
     * @return OnDialogClickListener from one of targetFragment,parentFragment,getActivity
     */
    protected OnDialogClickListener getDialogClickListener() {
        return getDialogListener(OnDialogClickListener.class);
    }

    /**
     * @return OnDialogMultiChoiceClickListener list from targetFragment,parentFragment,getActivity
     */
    protected List<OnDialogMultiChoiceClickListener> getDialogMultiChoiceClickListeners() {
        return getDialogListeners(OnDialogMultiChoiceClickListener.class);
    }

    /**
     * @return OnDialogMultiChoiceClickListener from one of targetFragment,parentFragment,getActivity
     */
    protected OnDialogMultiChoiceClickListener getDialogMultiChoiceClickListener() {
        return getDialogListener(OnDialogMultiChoiceClickListener.class);
    }

    /**
     * 获取所有符合条件的 DialogFragment 监听器
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
     * 获取一个符合条件的 DialogFragment 监听器
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

    private static int dp2px(float dp) {
        //向上取整
        return (int) Math.ceil(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, Resources.getSystem().getDisplayMetrics()));
    }
}
