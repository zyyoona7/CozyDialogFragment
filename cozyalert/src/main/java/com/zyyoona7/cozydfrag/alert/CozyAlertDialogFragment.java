package com.zyyoona7.cozydfrag.alert;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zyyoona7.cozydfrag.CozyDialogFragment;

public class CozyAlertDialogFragment extends CozyDialogFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cozy_dialog_fragment_alert,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TypedArray typedArray=mActivity.obtainStyledAttributes(R.style.DefaultCozyAlertStyle,
                R.styleable.CozyAlertDialogFragment);
        int style=typedArray.getInt(R.styleable.CozyAlertDialogFragment_cozy_alert_style,-1);
        Log.d("CozyAlertDialogFragment", "onCreateView: style="+style);
        typedArray.recycle();
    }
}
