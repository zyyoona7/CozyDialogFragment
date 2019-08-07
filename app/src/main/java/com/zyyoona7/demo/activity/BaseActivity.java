package com.zyyoona7.demo.activity;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.gyf.immersionbar.ImmersionBar;
import com.zyyoona7.demo.R;

public abstract class BaseActivity<VDB extends ViewDataBinding> extends AppCompatActivity {

    protected VDB mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView();
        ImmersionBar immersionBar=ImmersionBar.with(this);
        if (useStatusDarkFont()) {
            immersionBar.statusBarDarkFont(true,0.2f);
        }
        immersionBar
                .init();
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ImmersionBar.setTitleBar(this, toolbar);
        }
        init();
        initListeners();
    }

    protected void initContentView() {
        if (getLayoutResId() != 0) {
            mBinding = DataBindingUtil.setContentView(this, getLayoutResId());
        }
    }

    protected boolean useStatusDarkFont(){
        return false;
    }

    /**
     * 获取布局id
     *
     * @return 布局id
     */
    @LayoutRes
    protected abstract int getLayoutResId();

    protected abstract void init();

    protected abstract void initListeners();
}
