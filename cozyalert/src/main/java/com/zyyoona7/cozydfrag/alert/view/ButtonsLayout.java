package com.zyyoona7.cozydfrag.alert.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;

import com.zyyoona7.cozydfrag.alert.R;

public class ButtonsLayout extends ViewGroup {

    private static final int STYLE_MATERIAL = 0;
    private static final int STYLE_CUPERTINO = 1;

    private static final int ORIENTATION_HORIZONTAL=0;
    private static final int ORIENTATION_VERTICAL=1;

    private static final int DIRECTION_LTR=0;
    private static final int DIRECTION_RTL=1;

    private int mStyle;
    private int mOrientation;
    private int mDirection;

    public ButtonsLayout(Context context) {
        this(context,null);
    }

    public ButtonsLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    @SuppressLint("ResourceType")
    public ButtonsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //get style from theme
        int[] styleAttrs = {R.attr.cozy_alert_style,R.attr.cozy_alert_button_orientation,
        R.attr.cozy_alert_button_direction};
        TypedArray ta = context.getTheme().obtainStyledAttributes(styleAttrs);
        try {
            mStyle = ta.getInt(0, STYLE_MATERIAL);
            mOrientation=ta.getInt(1,ORIENTATION_HORIZONTAL);
            mDirection=ta.getInt(2,DIRECTION_LTR);
        } catch (Exception e){
            Log.d("ButtonsLayout", "get attr cause exception:"+e);
        }finally {
            ta.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mStyle==STYLE_CUPERTINO){
            measureCupertino(widthMeasureSpec, heightMeasureSpec);
        }else {
            measureMaterial(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private void measureMaterial(int widthMeasureSpec,int heightMeasureSpec){

    }

    private void measureCupertino(int widthMeasureSpec,int heightMeasureSpec){

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
