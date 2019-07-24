package com.zyyoona7.demo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.BarUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.zyyoona7.demo.AnimDialogFragment;
import com.zyyoona7.demo.R;
import com.zyyoona7.demo.activity.BaseActivity;
import com.zyyoona7.demo.databinding.ActivityFullScreenBinding;
import com.zyyoona7.demo.utils.PNotchUtils;

public class FullScreenActivity extends BaseActivity<ActivityFullScreenBinding> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_full_screen;
    }

    @Override
    protected void init() {
        ImmersionBar.setTitleBar(this, mBinding.toolbar);
        BarUtils.setStatusBarVisibility(this, false);
        PNotchUtils.fillNotchForFullScreen(this);
    }

    @Override
    protected void initListeners() {
        mBinding.btnFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimDialogFragment(1);
            }
        });
    }

    private void showAnimDialogFragment(int requestId) {
        final AnimDialogFragment dialogFragment = AnimDialogFragment.newInstance();
        dialogFragment.setShowDuration(500);
        dialogFragment.setDimShowDuration(150);
        dialogFragment.setDismissDuration(300);
        dialogFragment.setDimAmount(0.5f);
        dialogFragment.setRequestId(requestId);
        dialogFragment.setDimColor(Color.CYAN);
        dialogFragment.setFullWidth();
        dialogFragment.setFullHeight();
        dialogFragment.setOkClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimDialogFragment(1);
            }
        });
        dialogFragment.showAllowingStateLoss(getSupportFragmentManager());
    }
}
