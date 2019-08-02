package com.zyyoona7.demo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;

import com.gyf.immersionbar.ImmersionBar;
import com.zyyoona7.cozydfrag.helper.AnimatorHelper;
import com.zyyoona7.demo.AnimDialogFragment;
import com.zyyoona7.demo.CozyAnimDialogFragment;
import com.zyyoona7.demo.DemoDialogFragment;
import com.zyyoona7.demo.NormalDialogFragment;
import com.zyyoona7.demo.R;
import com.zyyoona7.demo.databinding.ActivityMainBinding;
import com.zyyoona7.cozydfrag.base.BaseDialogFragment;
import com.zyyoona7.cozydfrag.callback.OnDialogClickListener;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements OnDialogClickListener {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean useStatusDarkFont() {
        return true;
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
                showAnimDialogFragment();
                showAnimDialogFragment();
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

        mBinding.btnCozyDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCozyAnmDialogFragment();
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
//        dialogFragment.setAnimationStyle(R.style.DialogScaleAnim);
        dialogFragment.showAllowingStateLoss(getSupportFragmentManager());
    }

    private DemoDialogFragment mDemoDialogFragment;

    private void showDemoDialogFragment() {
        if (mDemoDialogFragment == null) {
            mDemoDialogFragment = DemoDialogFragment.newInstance();
            mDemoDialogFragment.setMatchWidth();
            mDemoDialogFragment.setHeight(300f);
            mDemoDialogFragment.setBottomGravity();
        }

        mDemoDialogFragment.showAllowingStateLoss(getSupportFragmentManager());


//        DemoDialogFragment demoDialogFragment = DemoDialogFragment.newInstance();
//        demoDialogFragment.setMatchWidth();
//        demoDialogFragment.setHeight(300f);
////        demoDialogFragment.setMatchHeight();
//        demoDialogFragment.setBottomGravity();
//        demoDialogFragment.setFullscreen(true,false,true);
//        demoDialogFragment.setStatusFontFollowDefault(false);
////        demoDialogFragment.setPaddingHorizontal(20f, 20f);
////        demoDialogFragment.setPaddingVertical(20f, 20f);
//        demoDialogFragment.showAllowingStateLoss(getSupportFragmentManager());
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
        normalDialogFragment.setBottomGravity();
        normalDialogFragment.showAllowingStateLoss(getSupportFragmentManager());
    }

    private void showCozyAnmDialogFragment(){
        CozyAnimDialogFragment dialog=CozyAnimDialogFragment.newInstance();
        dialog.setShowAnimType(AnimatorHelper.ANIM_ZOOM_IN);
        dialog.setShowInterpolatorType(AnimatorHelper.INTERPOLATOR_LINEAR);
        dialog.setDismissAnimType(AnimatorHelper.ANIM_ZOOM_OUT);
        dialog.setDismissInterpolatorType(AnimatorHelper.INTERPOLATOR_LINEAR);
        dialog.showAllowingStateLoss(getSupportFragmentManager());
    }

    @Override
    public void onClick(BaseDialogFragment dialogFragment, int which, int requestId) {
        if (requestId == 0 && which == DialogInterface.BUTTON_POSITIVE) {
            showAnimDialogFragment(1);
        }
    }
}
