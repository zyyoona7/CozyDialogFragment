package com.zyyoona7.cozydfrag.callback;

/**
 * Dialog dismiss animation Listener
 *
 * @author zyyoona7
 * @since 2019/09/04
 */
public interface OnDialogDismissAnimListener {

    /**
     * dismiss animation start
     *
     * @param requestId request id
     */
    void onDismissAnimStart(int requestId);

    /**
     * dismiss animation end
     *
     * @param requestId request id
     */
    void onDismissAnimEnd(int requestId);
}
