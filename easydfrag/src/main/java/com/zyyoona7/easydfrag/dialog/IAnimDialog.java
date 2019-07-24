package com.zyyoona7.easydfrag.dialog;

import com.zyyoona7.easydfrag.listener.OnAnimInterceptCallback;

/**
 * To use BaseAnimDialogFragment must be implements IAnimDialog for Dialog
 *
 * @author zyyoona7
 * @since 2019/07/22
 */
public interface IAnimDialog {

    void setDismissByDf(boolean dismissByDf);

    void setOnAnimInterceptCallback(OnAnimInterceptCallback listener);
}
