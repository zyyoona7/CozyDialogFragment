package androidx.fragment.app;

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
 * @since 2019/06/05
 */
public class ExternalDialogFragment extends DialogFragment {

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
        if (!isAdded()) {
            show(manager, getSimpleTag());
        } else {
            dismissAllowingStateLoss();
            show(manager, getSimpleTag());
        }
    }

    /**
     * 带 tag 的显示 DialogFragment
     *
     * @param manager FragmentManager
     * @param tag     tag
     */
    @Override
    public void show(@NonNull FragmentManager manager, String tag) {
        if (manager.isDestroyed() || manager.isStateSaved()) {
            return;
        }
        super.show(manager, tag);
    }

    /**
     * 根据 FragmentTransaction 显示 DialogFragment
     *
     * @param transaction transaction
     * @return BackStackId
     */
    public int show(@NonNull FragmentTransaction transaction) {
        if (!isAdded()) {
            return show(transaction, getSimpleTag());
        } else {
            dismissAllowingStateLoss();
            return show(transaction, getSimpleTag());
        }
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
        return super.show(transaction, tag);
    }

    /**
     * 根据 FragmentTransaction 显示 DialogFragment 允许状态丢失
     *
     * @param transaction transaction
     * @return BackStackId
     */
    public int showAllowingStateLoss(@NonNull FragmentTransaction transaction) {
        if (!isAdded()) {
            return showAllowingStateLoss(transaction, getSimpleTag());
        } else {
            dismissAllowingStateLoss();
            return showAllowingStateLoss(transaction, getSimpleTag());
        }
    }

    /**
     * 根据 FragmentTransaction 显示 DialogFragment 允许状态丢失
     *
     * @param transaction transaction
     * @param tag         tag
     * @return BackStackId
     */
    public int showAllowingStateLoss(@NonNull FragmentTransaction transaction, @Nullable String tag) {
        mDismissed = false;
        mShownByMe = true;
        transaction.add(this, tag);
        mViewDestroyed = false;
        mBackStackId = transaction.commitAllowingStateLoss();
        return mBackStackId;
    }

    /**
     * 简单的显示 DialogFragment 允许状态丢失
     *
     * @param manager FragmentManager
     */
    public void showAllowingStateLoss(@NonNull FragmentManager manager) {
        if (!isAdded()) {
            showAllowingStateLoss(manager, getSimpleTag());
        } else {
            dismissAllowingStateLoss();
            showAllowingStateLoss(manager, getSimpleTag());
        }
    }

    /**
     * 带 Tag 显示 DialogFragment 允许状态丢失
     *
     * @param manager FragmentManager
     * @param tag     Tag
     */
    public void showAllowingStateLoss(@NonNull FragmentManager manager, String tag) {
        mDismissed = false;
        mShownByMe = true;
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        ft.commitAllowingStateLoss();
    }

    /**
     * 更加安全的 dismiss
     */
    @Override
    public void dismiss() {
        if (getFragmentManager() == null) {
            return;
        }
        super.dismiss();
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

    /**
     * 获取简单的 Tag
     *
     * @return simple name for tag
     */
    protected String getSimpleTag() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void onDestroyView() {
        // bug in the compatibility library
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();
    }
}
