package com.zyyoona7.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button demoBtn=findViewById(R.id.btn_demo_dialog);
        demoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DemoDialogFragment demoDialogFragment=DemoDialogFragment.newInstance();
//                demoDialogFragment.show(getSupportFragmentManager(),"1234");
                AnimDialogFragment dialogFragment=AnimDialogFragment.newInstance();
                dialogFragment.setShowDuration(300);
                dialogFragment.setDimShowDuration(150);
                dialogFragment.setDismissDuration(300);
                dialogFragment.setDimAmount(0.6f);
//                dialogFragment.setCancelable(false);
                dialogFragment.showAllowingStateLoss(getSupportFragmentManager());
            }
        });
        Button normalBtn=findViewById(R.id.btn_normal_dialog);
        normalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NormalDialogFragment normalDialogFragment=NormalDialogFragment.newInstance();
                normalDialogFragment.setAnimationStyle(R.style.DialogScaleAnim);
                normalDialogFragment.setDimAmount(0.5f);
                normalDialogFragment.showAllowingStateLoss(getSupportFragmentManager());
            }
        });
    }
}
