package com.zyyoona7.cozydfrag.alert.view.button;

import android.content.Context;
import android.util.AttributeSet;

import com.zyyoona7.cozydfrag.alert.helper.DrawableHelper;
import com.zyyoona7.cozydfrag.alert.helper.UIHelper;

public class MaterialButtonsLayout extends ButtonsLayout {

    private int mButtonSpacing;

    public MaterialButtonsLayout(Context context) {
        super(context);
    }

    public MaterialButtonsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaterialButtonsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mButtonSpacing = UIHelper.dp2Px(4f);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

    @Override
    public void setSelectedColor(int color, int cornerRadius) {
        mSelectedColor = color;
        setButtonsDrawable(color);
    }

    private void setButtonsDrawable(int color) {
        if (mPositiveBtn != null) {
            mPositiveBtn.setBackgroundDrawable(DrawableHelper
                    .createRippleDrawable(color, UIHelper.dp2Px(2f)));
        }
        if (mNegativeBtn != null) {
            mNegativeBtn.setBackgroundDrawable(DrawableHelper
                    .createRippleDrawable(color, UIHelper.dp2Px(2f)));
        }
        if (mNeutralBtn != null) {
            mNeutralBtn.setBackgroundDrawable(DrawableHelper
                    .createRippleDrawable(color, UIHelper.dp2Px(2f)));
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int visibleCount = visibleCount();
        if (visibleCount == 0) {
            setMeasuredDimension(0, 0);
            return;
        }
        boolean isPositiveVisible = isVisible(mPositiveBtn);
        boolean isNeutralVisible = isVisible(mNeutralBtn);
        boolean isNegativeVisible = isVisible(mNegativeBtn);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        if (mOrientation == ORIENTATION_VERTICAL) {
            measureChildrenWithSpecWidth(measureWidth, heightMeasureSpec);
        } else {
            measureChildrenSelf(widthMeasureSpec, heightMeasureSpec);
        }

        int positiveWidth = !isPositiveVisible ? 0 : mPositiveBtn.getMeasuredWidth();
        int positiveHeight = !isPositiveVisible ? 0 : mPositiveBtn.getMeasuredHeight();
        int negativeWidth = !isNegativeVisible ? 0 : mNegativeBtn.getMeasuredWidth();
        int negativeHeight = !isNegativeVisible ? 0 : mNegativeBtn.getMeasuredHeight();
        int neutralWidth = !isNeutralVisible ? 0 : mNeutralBtn.getMeasuredWidth();
        int neutralHeight = !isNeutralVisible ? 0 : mNeutralBtn.getMeasuredHeight();

        int totalSpacing = mButtonSpacing * (visibleCount - 1);
        int childWidth = positiveWidth + negativeWidth + neutralWidth
                + totalSpacing + getPaddingLeft() + getPaddingRight();
        int height;
        if (childWidth > measureWidth) {
            //纵向排列
            mOrientation = ORIENTATION_VERTICAL;
            measureChildrenWithSpecWidth(measureWidth, heightMeasureSpec);
            int positiveHeight1 = !isPositiveVisible ? 0 : mPositiveBtn.getMeasuredHeight();
            int negativeHeight1 = !isNegativeVisible ? 0 : mNegativeBtn.getMeasuredHeight();
            int neutralHeight1 = !isNeutralVisible ? 0 : mNeutralBtn.getMeasuredHeight();
            height = positiveHeight1 + negativeHeight1
                    + neutralHeight1 + totalSpacing;
        } else {
            height = Math.max(Math.max(positiveHeight, negativeHeight), neutralHeight);
        }

        height += getPaddingTop() + getPaddingBottom();

        setMeasuredDimension(measureWidth, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int visibleCount = visibleCount();
        if (visibleCount == 0) {
            return;
        }
        boolean isPositiveVisible = isVisible(mPositiveBtn);
        boolean isNeutralVisible = isVisible(mNeutralBtn);
        boolean isNegativeVisible = isVisible(mNegativeBtn);
        int top = t + getPaddingTop();
        int left = l + getPaddingLeft();
        int right = r - getPaddingRight();
        int bottom = b - getPaddingBottom();
        if (mOrientation == ORIENTATION_VERTICAL) {
            if (isNeutralVisible) {
                mNeutralBtn.layout(left, top, right, top + mNeutralBtn.getMeasuredHeight());
                top += mNeutralBtn.getMeasuredHeight() + mButtonSpacing;
            }
            if (isNegativeVisible) {
                mNegativeBtn.layout(left, top, right, top + mNegativeBtn.getMeasuredHeight());
                top += mNegativeBtn.getMeasuredHeight() + mButtonSpacing;
            }
            if (isPositiveVisible) {
                mPositiveBtn.layout(left, top, right, top + mPositiveBtn.getMeasuredHeight());
            }
        } else {
            int realHeight = bottom - top;
            if (isNeutralVisible) {
                int neuLeft = l + getPaddingLeft();
                int neutralTop = (realHeight - mNeutralBtn.getMeasuredHeight()) / 2 + getPaddingTop();
                mNeutralBtn.layout(neuLeft, neutralTop, neuLeft + mNeutralBtn.getMeasuredWidth(),
                        neutralTop + mNeutralBtn.getMeasuredHeight());
            }

            int rightStart = r - getPaddingRight();
            if (isPositiveVisible) {
                int positiveTop = (realHeight - mPositiveBtn.getMeasuredHeight()) / 2 + getPaddingTop();
                mPositiveBtn.layout(rightStart - mPositiveBtn.getMeasuredWidth(), positiveTop,
                        rightStart,
                        positiveTop + mPositiveBtn.getMeasuredHeight());
                rightStart -= mPositiveBtn.getMeasuredWidth() - mButtonSpacing;
            }
            if (isNegativeVisible) {
                int negativeTop = (realHeight - mNegativeBtn.getMeasuredHeight()) / 2 + getPaddingTop();
                mNegativeBtn.layout(rightStart - mNegativeBtn.getMeasuredWidth(),
                        negativeTop, rightStart, negativeTop + mNegativeBtn.getMeasuredHeight());
            }
        }
    }
}
