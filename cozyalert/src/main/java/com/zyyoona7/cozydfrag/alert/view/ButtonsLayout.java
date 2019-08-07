package com.zyyoona7.cozydfrag.alert.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

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

    public ButtonsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
