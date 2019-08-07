package com.zyyoona7.samplealert;

import android.os.Bundle;

import com.zyyoona7.cozydfrag.alert.CozyAlertDialogFragment;

public class AlertDialogFragment extends CozyAlertDialogFragment {

    public static AlertDialogFragment newInstance() {
        
        Bundle args = new Bundle();
        
        AlertDialogFragment fragment = new AlertDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
