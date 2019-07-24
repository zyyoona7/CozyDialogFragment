package com.zyyoona7.easydfrag;

import android.animation.Animator;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zyyoona7.easydfrag.base.BaseAnimatorDialogFragment;

public class AnimatorDialogFragment extends BaseAnimatorDialogFragment {

    @Nullable
    @Override
    protected Animator onCreateShowAnimator(@NonNull View targetView) {
        return null;
    }

    @Nullable
    @Override
    protected Animator onCreateDismissAnimator(@NonNull View targetView) {
        return null;
    }
}
