package com.zyyoona7.cozydfrag.alert.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatButton;

import com.zyyoona7.cozydfrag.alert.DrawableUtils;
import com.zyyoona7.cozydfrag.alert.R;

public class ButtonsLayout extends ViewGroup {

    private static final int STYLE_MATERIAL = 0;
    private static final int STYLE_CUPERTINO = 1;

    private static final int ORIENTATION_HORIZONTAL = 0;
    private static final int ORIENTATION_VERTICAL = 1;

    private static final int DIRECTION_LTR = 0;
    private static final int DIRECTION_RTL = 1;

    private AppCompatButton mNeutralBtn;
    private AppCompatButton mNegativeBtn;
    private AppCompatButton mPositiveBtn;

    private int mStyle = STYLE_MATERIAL;
    private int mOrientation = ORIENTATION_HORIZONTAL;
    private int mDirection;
    private int mDividerSize;
    private int mDividerColor;
    private int mDividerPaddingHorizontal;
    private int mDividerPaddingVertical;
    private int mButtonSpacing;

    public ButtonsLayout(Context context) {
        this(context, null);
    }

    public ButtonsLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ButtonsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mButtonSpacing = dp2Px(4f);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mNeutralBtn = findViewById(R.id.btn_neutral);
        mNegativeBtn = findViewById(R.id.btn_negative);
        mPositiveBtn = findViewById(R.id.btn_positive);

        mPositiveBtn.setBackgroundDrawable(DrawableUtils
                .getAdaptiveRippleDrawable(Color.TRANSPARENT, Color.RED));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mStyle == STYLE_CUPERTINO) {
            measureCupertino(widthMeasureSpec, heightMeasureSpec);
        } else {
            measureMaterial(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private void measureCupertino(int widthMeasureSpec, int heightMeasureSpec) {

        measureChild(mNeutralBtn, widthMeasureSpec, heightMeasureSpec);
        measureChild(mNegativeBtn, widthMeasureSpec, heightMeasureSpec);
        measureChild(mPositiveBtn, widthMeasureSpec, heightMeasureSpec);
    }

    private void measureMaterial(int widthMeasureSpec, int heightMeasureSpec) {

        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        if (mOrientation == ORIENTATION_VERTICAL) {
            mNeutralBtn.measure(MeasureSpec.makeMeasureSpec(measureWidth, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            mNegativeBtn.measure(MeasureSpec.makeMeasureSpec(measureWidth, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            mPositiveBtn.measure(MeasureSpec.makeMeasureSpec(measureWidth, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        } else {
            measureChild(mNeutralBtn, widthMeasureSpec, heightMeasureSpec);
            measureChild(mNegativeBtn, widthMeasureSpec, heightMeasureSpec);
            measureChild(mPositiveBtn, widthMeasureSpec, heightMeasureSpec);
        }
        int height;
        int positiveWidth = mPositiveBtn.getVisibility() == GONE
                ? 0 : mPositiveBtn.getMeasuredWidth();
        int positiveHeight = mPositiveBtn.getVisibility() == GONE
                ? 0 : mPositiveBtn.getMeasuredHeight();
        int negativeWidth = mNegativeBtn.getVisibility() == GONE
                ? 0 : mNegativeBtn.getMeasuredWidth();
        int negativeHeight = mNegativeBtn.getVisibility() == GONE
                ? 0 : mNegativeBtn.getMeasuredHeight();
        int neutralWidth = mNeutralBtn.getVisibility() == GONE
                ? 0 : mNeutralBtn.getMeasuredWidth();
        int neutralHeight = mNeutralBtn.getVisibility() == GONE
                ? 0 : mNeutralBtn.getMeasuredHeight();

        if (positiveWidth + mButtonSpacing
                + negativeWidth + mButtonSpacing + neutralWidth > measureWidth) {
            //纵向排列
            mOrientation = ORIENTATION_VERTICAL;
            mNeutralBtn.measure(MeasureSpec.makeMeasureSpec(measureWidth, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            mNegativeBtn.measure(MeasureSpec.makeMeasureSpec(measureWidth, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            mPositiveBtn.measure(MeasureSpec.makeMeasureSpec(measureWidth, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int positiveHeight1 = mPositiveBtn.getVisibility() == GONE
                    ? 0 : mPositiveBtn.getMeasuredHeight();
            int negativeHeight1 = mNegativeBtn.getVisibility() == GONE
                    ? 0 : mNegativeBtn.getMeasuredHeight();
            int neutralHeight1 = mNeutralBtn.getVisibility() == GONE
                    ? 0 : mNeutralBtn.getMeasuredHeight();
            height = positiveHeight1 + negativeHeight1
                    + neutralHeight1 + mButtonSpacing * 2;
        } else {
            height = Math.max(Math.max(positiveHeight, negativeHeight), neutralHeight);
        }

        height += getPaddingTop() + getPaddingBottom();

        setMeasuredDimension(measureWidth, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        if (mStyle == STYLE_CUPERTINO) {
            layoutCupertino(l, t, r, b);
        } else {
            layoutMaterial(l, t, r, b);
        }
    }

    private void layoutCupertino(int l, int t, int r, int b) {

    }

    private void layoutMaterial(int l, int t, int r, int b) {
        if (mOrientation == ORIENTATION_VERTICAL) {
            int top = getPaddingTop();
            int left = l + getPaddingLeft();
            int right = r - getPaddingRight();
            mNeutralBtn.layout(left, top, right, mNeutralBtn.getMeasuredHeight());
            top += mNeutralBtn.getMeasuredHeight() + mButtonSpacing;
            mNegativeBtn.layout(left, top, right, top + mNegativeBtn.getMeasuredHeight());
            top += mNegativeBtn.getMeasuredHeight() + mButtonSpacing;
            mPositiveBtn.layout(left, top, right, top + mPositiveBtn.getMeasuredHeight());
        } else {
            int realHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
            int neuLeft = l + getPaddingLeft();
            int neutralTop = (realHeight - mNeutralBtn.getMeasuredHeight()) / 2 + getPaddingTop();
            mNeutralBtn.layout(neuLeft, neutralTop, neuLeft + mNeutralBtn.getMeasuredWidth(),
                    neutralTop + mNeutralBtn.getMeasuredHeight());

            int positiveTop = (realHeight - mPositiveBtn.getMeasuredHeight()) / 2 + getPaddingTop();
            int posRight = r - getPaddingRight();
            mPositiveBtn.layout(posRight - mPositiveBtn.getMeasuredWidth(), positiveTop,
                    posRight,
                    positiveTop + mPositiveBtn.getMeasuredHeight());

            int negativeTop = (realHeight - mNegativeBtn.getMeasuredHeight()) / 2 + getPaddingTop();
            int negativeRight = posRight - mPositiveBtn.getMeasuredWidth() - mButtonSpacing;
            mNegativeBtn.layout(negativeRight - mNegativeBtn.getMeasuredWidth(),
                    negativeTop, negativeRight, positiveTop + mNegativeBtn.getMeasuredHeight());
        }
    }

    private int dp2Px(float dp) {
        return (int) Math.ceil(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, Resources.getSystem().getDisplayMetrics()));
    }
}
