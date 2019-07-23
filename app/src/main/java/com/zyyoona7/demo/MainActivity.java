package com.zyyoona7.demo;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button animBtn = findViewById(R.id.btn_anim_dialog);
        animBtn.setOnClickListener(new View.OnClickListener() {
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

        Button demoBtn=findViewById(R.id.btn_demo_dialog);
        demoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDemoDialogFragment();
            }
        });
    }

    private void showAnimDialogFragment() {
        showAnimDialogFragment(-1);
    }

    private void showAnimDialogFragment(int requestId) {
        final AnimDialogFragment dialogFragment = AnimDialogFragment.newInstance();
        dialogFragment.setShowDuration(1500);
        dialogFragment.setDimShowDuration(150);
        dialogFragment.setDismissDuration(300);
        dialogFragment.setDimAmount(0.5f);
        dialogFragment.setRequestId(requestId);
        dialogFragment.setOkClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimDialogFragment(1);
            }
        });
        dialogFragment.showAllowingStateLoss(getSupportFragmentManager());
    }

    private void showDemoDialogFragment(){
        DemoDialogFragment demoDialogFragment=DemoDialogFragment.newInstance();
        demoDialogFragment.setFullWidth();
        demoDialogFragment.setHeight(200);
        demoDialogFragment.setBottomGravity();
        demoDialogFragment.setPaddingHorizontal(20,20);
        demoDialogFragment.setPaddingBottom(20);
        demoDialogFragment.showAllowingStateLoss(getSupportFragmentManager());
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
