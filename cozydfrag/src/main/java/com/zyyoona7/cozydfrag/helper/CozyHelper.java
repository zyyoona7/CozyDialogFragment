package com.zyyoona7.cozydfrag.helper;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.zyyoona7.cozydfrag.base.BaseDialogFragment;

/**
 * Utils for DialogFragment dismiss
 *
 * @author zyyoona7
 * @since 2019/07/22
 */
public class CozyHelper {

    private CozyHelper() {
    }

    /**
     * find DialogFragment by tag
     *
     * @param manager Fragment Manager
     * @param tag     DialogFragment tag
     * @return DialogFragment
     */
    @Nullable
    public static DialogFragment findDialogFragment(FragmentManager manager, String tag) {
        if (manager == null) {
            return null;
        }
        try {
            return (DialogFragment) manager.findFragmentByTag(tag);
        } catch (ClassCastException e) {
            // class not DialogFragment
            return null;
        }
    }

    /**
     * find DialogFragment by tag
     *
     * @param manager FragmentManager
     * @param dialog  class extends DialogFragment
     * @param <T>     extends DialogFragment
     * @return DialogFragment
     */
    @Nullable
    public static <T extends DialogFragment> DialogFragment findDialogFragment(FragmentManager manager,
                                                                               Class<T> dialog) {
        return findDialogFragment(manager, dialog.getSimpleName());
    }

    /**
     * dismiss DialogFragment by tag
     *
     * @param manager FragmentManager
     * @param tag     DialogFragment tag
     */
    public static void dismiss(FragmentManager manager, String tag) {
        DialogFragment dialogFragment = findDialogFragment(manager, tag);
        if (dialogFragment != null) {
            dialogFragment.dismiss();
        }
    }

    /**
     * dismiss DialogFragment by tag
     *
     * @param manager FragmentManager
     * @param dialog  class extends DialogFragment
     * @param <T>     extends DialogFragment
     */
    public static <T extends DialogFragment> void dismiss(FragmentManager manager,
                                                          Class<T> dialog) {
        dismiss(manager, dialog.getSimpleName());
    }

    /**
     * dismiss DialogFragment allow state loss
     *
     * @param manager FragmentManager
     * @param tag     DialogFragment tag
     */
    public static void dismissAllowingStateLoss(FragmentManager manager, String tag) {
        DialogFragment dialogFragment = findDialogFragment(manager, tag);
        if (dialogFragment != null) {
            dialogFragment.dismissAllowingStateLoss();
        }
    }

    /**
     * dismiss DialogFragment allow state loss
     *
     * @param manager FragmentManager
     * @param dialog  class extends DialogFragment
     * @param <T>     extends DialogFragment
     */
    public static <T extends DialogFragment> void dismissAllowingStateLoss(FragmentManager manager,
                                                                           Class<T> dialog) {
        dismissAllowingStateLoss(manager, dialog.getSimpleName());
    }

    /**
     * dismiss BaseDialogFragment
     *
     * @param manager   FragmentManager
     * @param tag       DialogFragment tag
     * @param requestId requestId
     */
    public static void dismiss(FragmentManager manager, String tag, int requestId) {
        if (manager == null || tag == null) {
            return;
        }
        for (Fragment fragment : manager.getFragments()) {
            if (fragment instanceof BaseDialogFragment
                    && tag.equals(fragment.getTag())
                    && ((BaseDialogFragment) fragment).getRequestId() == requestId) {
                ((BaseDialogFragment) fragment).dismiss();
                break;
            }
        }
    }

    /**
     * dismiss BaseDialogFragment
     *
     * @param manager   FragmentManager
     * @param dialog    class extends BaseDialogFragment
     * @param requestId requestId
     * @param <T>       extends BaseDialogFragment
     */
    public static <T extends BaseDialogFragment> void dismiss(FragmentManager manager,
                                                              Class<T> dialog, int requestId) {
        dismiss(manager, dialog.getSimpleName(), requestId);
    }

    /**
     * dismiss BaseDialogFragment allow state loss
     *
     * @param manager   FragmentManager
     * @param tag       DialogFragment tag
     * @param requestId requestId
     */
    public static void dismissAllowingStateLoss(FragmentManager manager, String tag, int requestId) {
        if (manager == null || tag == null) {
            return;
        }
        for (Fragment fragment : manager.getFragments()) {
            if (fragment instanceof BaseDialogFragment
                    && tag.equals(fragment.getTag())
                    && ((BaseDialogFragment) fragment).getRequestId() == requestId) {
                ((BaseDialogFragment) fragment).dismissAllowingStateLoss();
                break;
            }
        }
    }

    /**
     * dismiss BaseDialogFragment allow state loss
     *
     * @param manager   FragmentManager
     * @param dialog    class extends BaseDialogFragment
     * @param requestId requestId
     * @param <T>       extends BaseDialogFragment
     */
    public static <T extends BaseDialogFragment> void dismissAllowingStateLoss(FragmentManager manager,
                                                                               Class<T> dialog, int requestId) {
        dismissAllowingStateLoss(manager, dialog.getSimpleName(), requestId);
    }

    /**
     * Sets Window status bar light mode
     *
     * @param window               Window
     * @param isStatusBarLightMode Whether status bar font dark
     */
    public static void setStatusBarLightMode(Window window, boolean isStatusBarLightMode) {
        if (window != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = window.getDecorView();
            int flags = decorView.getSystemUiVisibility();
            if (isStatusBarLightMode) {
                flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                flags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            decorView.setSystemUiVisibility(flags);
        }
    }

    /**
     * Sets Window status bar transparent
     *
     * @param window Window
     */
    public static void setTransparentStatusBar(Window window) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT || window == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            View decor = window.getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int flags = window.getDecorView().getSystemUiVisibility()
                        & View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                decor.setSystemUiVisibility(option | flags);
            } else {
                decor.setSystemUiVisibility(option);
            }
            window.setStatusBarColor(Color.TRANSPARENT);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public static void fitNotch(Window window) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            return;
        }
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        window.setAttributes(attributes);
    }
}
