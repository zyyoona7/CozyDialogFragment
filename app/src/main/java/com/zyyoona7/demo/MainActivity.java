package com.zyyoona7.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.zyyoona7.easydfrag.utils.EasyUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button demoBtn = findViewById(R.id.btn_anim_dialog);
        demoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showAnimDialogFragment(1);
                showAnimDialogFragment(0);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        EasyUtils.dismissAllowingStateLoss(getSupportFragmentManager(),
//                                AnimDialogFragment.class,0);
//                    }
//                },3000);
            }
        });
        Button normalBtn = findViewById(R.id.btn_normal_dialog);
        normalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNormalDialogFragment();
            }
        });
    }

    private void showAnimDialogFragment(){
        showAnimDialogFragment(-1);
    }

    private void showAnimDialogFragment(int requestId) {
        final AnimDialogFragment dialogFragment = AnimDialogFragment.newInstance(requestId);
        dialogFragment.setShowDuration(500);
        dialogFragment.setDimShowDuration(150);
        dialogFragment.setDismissDuration(300);
        dialogFragment.setDimAmount(0.5f);
        dialogFragment.setOkClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimDialogFragment();
            }
        });
        dialogFragment.showAllowingStateLoss(getSupportFragmentManager());
    }

    private void showNormalDialogFragment() {
        NormalDialogFragment normalDialogFragment = NormalDialogFragment.newInstance();
        normalDialogFragment.setAnimationStyle(R.style.DialogScaleAnim);
        normalDialogFragment.setDimAmount(0.5f);
        normalDialogFragment.setConfirmClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showNormalDialogFragment();
            }
        });
        normalDialogFragment.showAllowingStateLoss(getSupportFragmentManager());
    }
}
