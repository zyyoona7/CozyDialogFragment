package com.zyyoona7.samplealert;

import android.view.View;

import com.zyyoona7.cozydfrag.alert.CozyAlertDialog;
import com.zyyoona7.samplealert.activity.BaseActivity;
import com.zyyoona7.samplealert.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initListeners() {
        mBinding.btnCupertino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CozyAlertDialog.create()
                        .setNegativeText("取消")
                        .setPositiveText("确定")
                        .setNeutralText("中立")
                        .showAllowingStateLoss(getSupportFragmentManager());
            }
        });

        mBinding.btnMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CozyAlertDialog.create(R.style.DefaultMaterialAlert)
                        .setNegativeText("CANCEL")
                        .setPositiveText("OK")
                        .setNeutralText("NEUTRAL")
                        .showAllowingStateLoss(getSupportFragmentManager());
            }
        });
    }
}
