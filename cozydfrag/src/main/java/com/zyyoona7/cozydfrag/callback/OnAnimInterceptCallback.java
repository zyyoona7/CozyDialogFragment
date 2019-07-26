package com.zyyoona7.cozydfrag.callback;

import android.view.MotionEvent;

/**
 * Dialog touch outside and back press callback
 *
 * @author zyyoona7
 * @since 2019/07/22
 */
public interface OnAnimInterceptCallback {

    void onTouchOutside(MotionEvent event);

    void onBackPress();

    void onDismissInternal();
}
