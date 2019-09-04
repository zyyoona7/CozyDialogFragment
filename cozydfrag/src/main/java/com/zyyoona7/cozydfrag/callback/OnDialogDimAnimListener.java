package com.zyyoona7.cozydfrag.callback;

/**
 * Dialog dim animation Listener
 *
 * @author zyyoona7
 * @since 2019/09/04
 */
public interface OnDialogDimAnimListener {

    /**
     * dim animation start
     *
     * @param isDismiss whether dismiss operation
     * @param requestId request id
     */
    void onDimAnimStart(boolean isDismiss, int requestId);

    /**
     * dim animation end
     *
     * @param isDismiss whether dismiss operation
     * @param requestId request id
     */
    void onDimAnimEnd(boolean isDismiss, int requestId);
}
