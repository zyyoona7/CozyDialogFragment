package com.zyyoona7.easydfrag.listener;

import com.zyyoona7.easydfrag.base.BaseDialogFragment;

/**
 * like {@link android.content.DialogInterface.OnClickListener}
 *
 * @author zyyoona7
 * @since 2019/07/24
 */
public interface OnDialogClickListener {

    void onClick(BaseDialogFragment dialogFragment, int which, int requestId);
}
