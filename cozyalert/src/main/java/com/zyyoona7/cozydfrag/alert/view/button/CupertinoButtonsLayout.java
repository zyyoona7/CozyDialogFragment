package com.zyyoona7.cozydfrag.alert.view.button;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatButton;

import com.zyyoona7.cozydfrag.alert.helper.DrawableHelper;

public class CupertinoButtonsLayout extends ButtonsLayout {

    private int mDividerSize;
    private int mDividerColor;
    private int mDividerPaddingHorizontal;
    private int mDividerPaddingVertical;
    private int mCornerRadius;

    private AppCompatButton mCornerBtn;
    private AppCompatButton mLeftCornerBtn;
    private AppCompatButton mRightCornerBtn;

    public CupertinoButtonsLayout(Context context) {
        super(context);
    }

    public CupertinoButtonsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CupertinoButtonsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setSelectedColor(int color, int cornerRadius) {
        mSelectedColor = color;
        mCornerRadius = cornerRadius;
        if (mOrientation == ORIENTATION_VERTICAL) {
            setButtonsBackgroundForVer();
        } else {
            setButtonsBackgroundForHor();
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
        if (mOrientation == ORIENTATION_VERTICAL
                || visibleCount > 2 || visibleCount == 1) {
            mOrientation = ORIENTATION_VERTICAL;
            measureChildrenWithSpecWidth(measureWidth,heightMeasureSpec);
        } else {
            measureChildrenWithSpecWidth(measureWidth / 2,heightMeasureSpec);
        }

        int positiveHeight = !isPositiveVisible ? 0 : mPositiveBtn.getMeasuredHeight();
        int negativeHeight = !isNegativeVisible ? 0 : mNegativeBtn.getMeasuredHeight();
        int neutralHeight = !isNeutralVisible ? 0 : mNeutralBtn.getMeasuredHeight();
        int height;
        if (mOrientation == ORIENTATION_VERTICAL) {
            height = positiveHeight + negativeHeight + neutralHeight;
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
        int left = l + getPaddingLeft();
        int top = t + getPaddingTop();
        int right = r - getPaddingRight();
        int bottom = b - getPaddingBottom();
        if (mOrientation == ORIENTATION_VERTICAL || visibleCount == 1) {
            if (isNeutralVisible) {
                mNeutralBtn.layout(left, top, right, top + mNeutralBtn.getMeasuredHeight());
                top += mNeutralBtn.getMeasuredHeight();
                mCornerBtn = mNeutralBtn;
            }
            if (isPositiveVisible) {
                mPositiveBtn.layout(left, top, right, top + mPositiveBtn.getMeasuredHeight());
                top += mPositiveBtn.getMeasuredHeight();
                mCornerBtn = mPositiveBtn;
            }
            if (isNegativeVisible) {
                mNegativeBtn.layout(left, top, right, top + mNegativeBtn.getMeasuredHeight());
                mCornerBtn = mNegativeBtn;
            }
            setButtonsBackgroundForVer();
        } else {
            if (isPositiveVisible && isNegativeVisible) {
                layoutCupertinoHor2Btn(mNegativeBtn, mPositiveBtn, left, top, right, bottom);
            } else if (isPositiveVisible && isNeutralVisible) {
                layoutCupertinoHor2Btn(mNeutralBtn, mPositiveBtn, left, top, right, bottom);
            } else if (isNegativeVisible && isNeutralVisible) {
                layoutCupertinoHor2Btn(mNeutralBtn, mNegativeBtn, left, top, right, bottom);
            }
            setButtonsBackgroundForHor();
        }
    }

    private void layoutCupertinoHor2Btn(AppCompatButton lView, AppCompatButton rView,
                                        int left, int top, int right, int bottom) {
        lView.layout(left, top, left + lView.getMeasuredWidth(), bottom);
        rView.layout(right - rView.getMeasuredWidth(), top, right, bottom);
        mLeftCornerBtn = lView;
        mRightCornerBtn = rView;
    }

    private void setButtonsBackgroundForVer() {
        int pressedColor = mSelectedColor == 0 ? Color.LTGRAY : mSelectedColor;
        if (mCornerBtn == null) {
            Log.d("CupertinoButtonsLayout", "setButtonsBackgroundForVer: onLayout not complete.");
            return;
        }
        mCornerBtn.setBackgroundDrawable(
                DrawableHelper.createRoundSelector(Color.TRANSPARENT,
                        pressedColor, mCornerRadius, mCornerRadius)
        );
        if (isVisible(mNeutralBtn) && mNeutralBtn != mCornerBtn) {
            mNeutralBtn.setBackgroundDrawable(
                    DrawableHelper.createRoundSelector(Color.TRANSPARENT,
                            pressedColor, 0f)
            );
        }
        if (isVisible(mNegativeBtn) && mNegativeBtn != mCornerBtn) {
            mNegativeBtn.setBackgroundDrawable(
                    DrawableHelper.createRoundSelector(Color.TRANSPARENT,
                            pressedColor, 0f)
            );
        }
        if (isVisible(mPositiveBtn) && mPositiveBtn != mCornerBtn) {
            mPositiveBtn.setBackgroundDrawable(
                    DrawableHelper.createRoundSelector(Color.TRANSPARENT,
                            pressedColor, 0f)
            );
        }
    }

    private void setButtonsBackgroundForHor() {
        int pressedColor = mSelectedColor == 0 ? Color.LTGRAY : mSelectedColor;
        if (mLeftCornerBtn == null && mRightCornerBtn == null) {
            Log.d("CupertinoButtonsLayout", "setButtonsBackgroundForHor: onLayout not complete.");
            return;
        }
        if (mLeftCornerBtn != null) {
            mLeftCornerBtn.setBackgroundDrawable(
                    DrawableHelper.createRoundSelector(Color.TRANSPARENT,
                            pressedColor, mCornerRadius, 0f)
            );
        }
        if (mRightCornerBtn != null) {
            mRightCornerBtn.setBackgroundDrawable(
                    DrawableHelper.createRoundSelector(Color.TRANSPARENT,
                            pressedColor, 0f, mCornerRadius)
            );
        }
    }
}
