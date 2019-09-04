package com.zyyoona7.cozydfrag.callback;

/**
 * Dialog show animation Listener
 *
 * @author zyyoona7
 * @since 2019/09/04
 */
public interface OnDialogShowAnimListener {

    /**
     * show animation start
     *
     * @param requestId request id
     */
    void onShowAnimStart(int requestId);

    /**
     * show animation end
     *
     * @param requestId requst id
     */
    void onShowAnimEnd(int requestId);
}
