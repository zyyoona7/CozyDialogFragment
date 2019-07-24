package com.zyyoona7.easydfrag.listener;

import com.zyyoona7.easydfrag.base.BaseDialogFragment;

/**
 * like {@link android.content.DialogInterface.OnMultiChoiceClickListener}
 *
 * @author zyyoona7
 * @since 2019/07/24
 */
public interface OnDialogMultiChoiceClickListener {

    void onClick(BaseDialogFragment dialogFragment, int which, boolean isChecked, int requestId);
}
