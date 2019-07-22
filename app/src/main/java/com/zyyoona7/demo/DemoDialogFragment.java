package com.zyyoona7.demo;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.zyyoona7.easydialogfragment.base.BaseDialogFragment;

public class DemoDialogFragment extends BaseDialogFragment implements DialogInterface.OnShowListener {

    private static final String TAG = "DemoDialogFragment";

    public static DemoDialogFragment newInstance() {

        Bundle args = new Bundle();

        DemoDialogFragment fragment = new DemoDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().getDecorView().getRootView()
                    .setBackground(new ColorDrawable(Color.RED));
        }
        Log.d(TAG, "onStart: ");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Demo Test");
        Dialog dialog = builder.create();
        dialog.setOnShowListener(this);
        return dialog;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ");
    }

    @Override
    public void onShow(DialogInterface dialog) {
        Log.d(TAG, "onShow: ");
    }

}
