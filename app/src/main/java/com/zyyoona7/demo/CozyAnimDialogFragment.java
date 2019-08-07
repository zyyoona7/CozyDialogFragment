package com.zyyoona7.demo;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AnimAlertDialog;

import com.zyyoona7.cozydfrag.CozyDialogFragment;
import com.zyyoona7.cozydfrag.callback.OnDialogClickListener;

public class CozyAnimDialogFragment extends CozyDialogFragment {

    public static CozyAnimDialogFragment newInstance() {

        Bundle args = new Bundle();

        CozyAnimDialogFragment fragment = new CozyAnimDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AnimAlertDialog.Builder builder = new AnimAlertDialog.Builder(mActivity);
        builder.setTitle("Anim");
        builder.setMessage("Anim Dialog" + getRequestId());
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, final int which) {
                OnDialogClickListener clickListener = getDialogClickListener();
                if (clickListener != null) {
                    clickListener.onClick(CozyAnimDialogFragment.this, which, getRequestId());
                }
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNeutralButton("Neutral", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }
}
