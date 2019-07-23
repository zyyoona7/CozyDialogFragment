package com.zyyoona7.easydfrag.dialog;

import android.view.MotionEvent;

/**
 * Dialog touch outside and back press callback
 *
 * @author zyyoona7
 */
public interface OnTouchOutsideListener {

    void onTouchOutside(MotionEvent event);

    void onBackPress();
}
