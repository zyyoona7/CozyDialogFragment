package com.zyyoona7.demo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;

import com.gyf.immersionbar.ImmersionBar;
import com.zyyoona7.demo.AnimDialogFragment;
import com.zyyoona7.demo.DemoDialogFragment;
import com.zyyoona7.demo.NormalDialogFragment;
import com.zyyoona7.demo.R;
import com.zyyoona7.demo.databinding.ActivityMainBinding;
import com.zyyoona7.easydfrag.base.BaseDialogFragment;
import com.zyyoona7.easydfrag.callback.OnDialogClickListener;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements OnDialogClickListener {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        ImmersionBar.setTitleBar(this, mBinding.toolbar);
    }

    @Override
    protected void initListeners() {
        mBinding.btnGoFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FullScreenActivity.class);
                startActivity(intent);
            }
        });
        mBinding.btnAnimDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showAnimDialogFragment(1);
                showAnimDialogFragment(0);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                    }
//                },3000);
            }
        });
        mBinding.btnNormalDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNormalDialogFragment();
            }
        });

        mBinding.btnDemoDialog.setOnClickListener(new View.OnClickListener() {
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
        dialogFragment.setDimColor(Color.RED);
        dialogFragment.showAllowingStateLoss(getSupportFragmentManager());
    }

    private void showDemoDialogFragment() {
        DemoDialogFragment demoDialogFragment = DemoDialogFragment.newInstance();
        demoDialogFragment.setFullWidth();
        demoDialogFragment.setHeight(200);
        demoDialogFragment.setBottomGravity();
        demoDialogFragment.setPaddingHorizontal(20, 20);
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

    @Override
    public void onClick(BaseDialogFragment dialogFragment, int which, int requestId) {
        if (requestId==0 && which==DialogInterface.BUTTON_POSITIVE) {
            showAnimDialogFragment(1);
        }
    }
}
