package com.zyyoona7.cozydfrag.callback;

import com.zyyoona7.cozydfrag.base.BaseDialogFragment;

/**
 * like {@link android.content.DialogInterface.OnDismissListener}
 *
 * @author zyyoona7
 * @since 2019/09/03
 */
public interface OnDialogDismissListener {

    void onDismiss(BaseDialogFragment dialogFragment,int requestId);
}
