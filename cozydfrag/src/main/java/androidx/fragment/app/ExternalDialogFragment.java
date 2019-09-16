package androidx.fragment.app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * DialogFragment 扩展，
 * 增加
 * {@link ExternalDialogFragment#showAllowingStateLoss},
 * {@link ExternalDialogFragment#isShowing)}
 * 等一系列方法
 *
 * @author zyyoona7
 * @since 2019/07/22
 */
public class ExternalDialogFragment extends DialogFragment {

    private static final String SAVED_ANTI_SHAKE_DURATION = "SAVED_ANTI_SHAKE_DURATION";
    private static final long DEFAULT_ANTI_SHAKE_DURATION = 100;

    //Prevent multiple operations in a short time
    // fix Fragment already added.
    private long mAntiShakeDuration = DEFAULT_ANTI_SHAKE_DURATION;
    private long mLashShowTime = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mAntiShakeDuration = savedInstanceState.getLong(SAVED_ANTI_SHAKE_DURATION, DEFAULT_ANTI_SHAKE_DURATION);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putLong(SAVED_ANTI_SHAKE_DURATION, mAntiShakeDuration);
        super.onSaveInstanceState(outState);
    }

    /**
     * 是否正在显示
     *
     * @return is showing
     */
    public boolean isShowing() {
        return getDialog() != null && getDialog().isShowing();
    }

    /**
     * 简单的显示 DialogFragment
     *
     * @param manager FragmentManager
     */
    public void show(FragmentManager manager) {
        show(manager, getSimpleTag());
    }

    /**
     * 带 tag 的显示 DialogFragment
     *
     * @param manager FragmentManager
     * @param tag     tag
     */
    @Override
    public void show(@NonNull FragmentManager manager, String tag) {
        long current = System.currentTimeMillis();
        if (current - mLashShowTime <= mAntiShakeDuration) {
            return;
        }
        if (manager.isDestroyed() || manager.isStateSaved()) {
            return;
        }
        if (isAdded()) {
            dismissInternal(true, false);
        }
        try {
            super.show(manager, tag);
        } catch (Exception e) {
            //catch java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
        }
        mLashShowTime = current;
    }

    /**
     * 根据 FragmentTransaction 显示 DialogFragment
     *
     * @param transaction transaction
     * @return BackStackId
     */
    public int show(@NonNull FragmentTransaction transaction) {
        return show(transaction, getSimpleTag());
    }

    /**
     * 根据 FragmentTransaction 显示 DialogFragment
     *
     * @param transaction transaction
     * @param tag         tag
     * @return BackStackId
     */
    @Override
    public int show(@NonNull FragmentTransaction transaction, String tag) {
        long current = System.currentTimeMillis();
        if (current - mLashShowTime <= mAntiShakeDuration) {
            return mBackStackId;
        }
        if (isAdded()) {
            dismissInternal(true, false);
        }
        try {
            return super.show(transaction, tag);
        } catch (Exception e) {
            //catch java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
        }
        mLashShowTime = current;
        return mBackStackId;
    }

    /**
     * 根据 FragmentTransaction 显示 DialogFragment 允许状态丢失
     *
     * @param transaction transaction
     * @return BackStackId
     */
    public int showAllowingStateLoss(@NonNull FragmentTransaction transaction) {
        return showAllowingStateLoss(transaction, getSimpleTag());
    }

    /**
     * 根据 FragmentTransaction 显示 DialogFragment 允许状态丢失
     *
     * @param transaction transaction
     * @param tag         tag
     * @return BackStackId
     */
    public int showAllowingStateLoss(@NonNull FragmentTransaction transaction, @Nullable String tag) {
        long current = System.currentTimeMillis();
        if (current - mLashShowTime <= mAntiShakeDuration) {
            return mBackStackId;
        }
        if (isAdded()) {
            dismissInternal(true, false);
        }
        mDismissed = false;
        mShownByMe = true;
        transaction.add(this, tag);
        mViewDestroyed = false;
        mBackStackId = transaction.commitAllowingStateLoss();
        mLashShowTime = current;
        return mBackStackId;
    }

    /**
     * 简单的显示 DialogFragment 允许状态丢失
     *
     * @param manager FragmentManager
     */
    public void showAllowingStateLoss(@NonNull FragmentManager manager) {
        showAllowingStateLoss(manager, getSimpleTag());
    }

    /**
     * 带 Tag 显示 DialogFragment 允许状态丢失
     *
     * @param manager FragmentManager
     * @param tag     Tag
     */
    public void showAllowingStateLoss(@NonNull FragmentManager manager, String tag) {
        long current = System.currentTimeMillis();
        if (current - mLashShowTime <= mAntiShakeDuration) {
            return;
        }
        if (manager.isDestroyed() || manager.isStateSaved()) {
            return;
        }
        if (isAdded()) {
            dismissInternal(true, false);
        }
        mDismissed = false;
        mShownByMe = true;
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        ft.commitAllowingStateLoss();
        mLashShowTime = current;
    }

    /**
     * 更加安全的 dismiss
     */
    @Override
    public void dismiss() {
        if (getFragmentManager() == null) {
            return;
        }
        try {
            super.dismiss();
        } catch (Exception e) {
            //catch java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
        }
    }

    /**
     * 更加安全的 dismiss
     */
    @Override
    public void dismissAllowingStateLoss() {
        if (getFragmentManager() == null) {
            return;
        }
        super.dismissAllowingStateLoss();
    }

    @Override
    protected void dismissInternal(boolean allowStateLoss, boolean fromOnDismiss) {
        super.dismissInternal(allowStateLoss, fromOnDismiss);
    }

    /**
     * 获取简单的 Tag
     *
     * @return simple name for tag
     */
    @NonNull
    public String getSimpleTag() {
        //如果混淆的话可能导致不同的类混淆成相同的名字导致奇葩的问题
        return this.getClass().getName();
    }

    public long getAntiShakeDuration() {
        return mAntiShakeDuration;
    }

    /**
     * Sets how long prevent multiple operations in a short time
     *
     * @param antiShakeDuration anti shake duration
     */
    public void setAntiShakeDuration(long antiShakeDuration) {
        mAntiShakeDuration = antiShakeDuration;
    }
}
