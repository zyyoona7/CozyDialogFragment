package com.zyyoona7.demo;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.zyyoona7.easydfrag.base.BaseDialogFragment;

public class NormalDialogFragment extends BaseDialogFragment {

    public static NormalDialogFragment newInstance() {

        Bundle args = new Bundle();

        NormalDialogFragment fragment = new NormalDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Normal Dialog");
        return builder.create();
    }
}
