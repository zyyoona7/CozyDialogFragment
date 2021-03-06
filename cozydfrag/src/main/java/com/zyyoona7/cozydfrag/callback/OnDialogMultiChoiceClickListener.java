package com.zyyoona7.cozydfrag.callback;

import com.zyyoona7.cozydfrag.base.BaseDialogFragment;

/**
 * like {@link android.content.DialogInterface.OnMultiChoiceClickListener}
 *
 * @author zyyoona7
 * @since 2019/07/24
 */
public interface OnDialogMultiChoiceClickListener {

    void onClick(BaseDialogFragment dialogFragment, int which, boolean isChecked, int requestId);
}
