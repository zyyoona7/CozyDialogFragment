package com.zyyoona7.demo.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Build;
import android.view.DisplayCutout;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;

/**
 * Android P 刘海屏适配工具类
 *
 * @author zhuoyue
 */
public class PNotchUtils {

    private PNotchUtils() {
    }

    /**
     * 是否是刘海屏
     *
     * @param window window
     * @return not Android P return false
     */
    public static boolean isNotch(Window window) {
        if (isNotVersionP()) {
            return false;
        }
        WindowInsets windowInsets = window.getDecorView().getRootWindowInsets();
        if (windowInsets == null) {
            return false;
        }

        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        return displayCutout != null && displayCutout.getBoundingRects() != null;
    }

    /**
     * 获取刘海屏刘海高度
     *
     * @param window window
     * @return notch height
     */
    public static int getNotchHeight(Window window) {
        if (isNotVersionP()) {
            return 0;
        }
        int notchHeight;
        WindowInsets windowInsets = window.getDecorView().getRootWindowInsets();
        if (windowInsets == null) {
            return 0;
        }

        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        if (displayCutout == null || displayCutout.getBoundingRects() == null) {
            return 0;
        }

        notchHeight = displayCutout.getSafeInsetTop();

        return notchHeight;
    }

    /**
     * 设置全屏时占用刘海区域
     *
     * @param activity activity
     */
    public static void fillNotchForFullScreen(Activity activity) {
        if (isNotVersionP()) {
            return;
        }
        fillNotchForFullScreen(activity.getWindow());
    }

    public static void fillNotchForFullScreen(Window window){
        if (isNotVersionP()) {
            return;
        }
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        window.setAttributes(attributes);
    }

    /**
     * 沉浸式状态栏时，为 TitleBar 加 Padding 适配 StatusBar 高度
     *
     * @param titleView titleView
     */
    public static void fitStatusBar(final View titleView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && titleView != null) {
            final ViewGroup.LayoutParams layoutParams = titleView.getLayoutParams();
            if (layoutParams.height == ViewGroup.LayoutParams.WRAP_CONTENT ||
                    layoutParams.height == ViewGroup.LayoutParams.MATCH_PARENT) {
                titleView.post(new Runnable() {
                    @Override
                    public void run() {
                        int statusBarHeight = getStatusBarHeight();
                        layoutParams.height = titleView.getHeight() + statusBarHeight;
                        titleView.setPadding(titleView.getPaddingLeft(),
                                titleView.getPaddingTop() + statusBarHeight,
                                titleView.getPaddingRight(),
                                titleView.getPaddingBottom());
                    }
                });
            } else {
                int statusBarHeight = getStatusBarHeight();
                layoutParams.height += statusBarHeight;
                titleView.setPadding(titleView.getPaddingLeft(), titleView.getPaddingTop() + statusBarHeight,
                        titleView.getPaddingRight(), titleView.getPaddingBottom());
            }
        }
    }

    public static int getStatusBarHeight() {
        Resources resources = Resources.getSystem();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    /**
     * 不是 Android P 版本
     *
     * @return 是不是 Android P
     */
    private static boolean isNotVersionP() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.P;
    }
}