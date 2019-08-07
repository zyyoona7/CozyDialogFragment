package com.zyyoona7.samplealert;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AnimAlertDialog;

import com.zyyoona7.cozydfrag.alert.CozyAlertDialogFragment;

public class AlertDialogFragment extends CozyAlertDialogFragment {

    public static AlertDialogFragment newInstance() {
        
        Bundle args = new Bundle();
        
        AlertDialogFragment fragment = new AlertDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AnimAlertDialog.Builder builder=new AnimAlertDialog.Builder(mActivity);
        builder.setMessage("xixixixi");
        return builder.create();
    }
}
