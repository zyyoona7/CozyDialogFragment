package com.zyyoona7.cozydfrag.callback;

import com.zyyoona7.cozydfrag.base.BaseDialogFragment;

/**
 * dismiss action when Dialog dismiss.
 *
 * @author zyyoona7
 * @since 2019/09/17
 */
public interface DismissAction {

    //default dismiss type
    int TYPE_DEFAULT = -1;
    //dismissed by back press
    int TYPE_BACK_PRESS = -2;
    //dismissed by touch outside
    int TYPE_TOUCH_OUTSIDE = -3;

    /**
     * execute action
     *
     * @param dialogFragment DialogFragment
     * @param dismissType    dismissed type
     */
    void run(BaseDialogFragment dialogFragment, int dismissType);
}
