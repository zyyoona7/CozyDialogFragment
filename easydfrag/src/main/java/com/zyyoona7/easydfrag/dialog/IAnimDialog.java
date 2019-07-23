package com.zyyoona7.easydfrag.dialog;

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
