package com.zyyoona7.demo;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.zyyoona7.easydfrag.base.BaseDialogFragment;

public class NormalDialogFragment extends BaseDialogFragment {

    private DialogInterface.OnClickListener mConfirmClickListener;

    public static NormalDialogFragment newInstance() {

        Bundle args = new Bundle();

        NormalDialogFragment fragment = new NormalDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setMessage("Normal Dialog");
        builder.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mConfirmClickListener != null) {
                    mConfirmClickListener.onClick(dialog, which);
                }
            }
        });
        return builder.create();
    }

    public void setConfirmClickListener(DialogInterface.OnClickListener confirmClickListener) {
        mConfirmClickListener = confirmClickListener;
    }
}
