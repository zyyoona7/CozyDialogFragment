package com.zyyoona7.samplealert;

import android.view.View;

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
        mBinding.btnAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialogFragment.newInstance()
                        .showAllowingStateLoss(getSupportFragmentManager());
            }
        });
    }
}
