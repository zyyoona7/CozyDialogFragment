package com.zyyoona7.cozydfrag.alert.view.button;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatButton;

import com.zyyoona7.cozydfrag.alert.R;
import com.zyyoona7.cozydfrag.alert.helper.UIHelper;

public abstract class ButtonsLayout extends ViewGroup {

    public static final int ORIENTATION_HORIZONTAL = 0;
    public static final int ORIENTATION_VERTICAL = 1;

    protected AppCompatButton mNeutralBtn;
    protected AppCompatButton mNegativeBtn;
    protected AppCompatButton mPositiveBtn;

    private OnClickListener mNeutralOnClickListener;
    private OnClickListener mNegativeOnClickListener;
    private OnClickListener mPositiveOnClickListener;
    protected int mOrientation = ORIENTATION_VERTICAL;
    private int mNeutralTextAppearance;
    private int mNegativeTextAppearance;
    private int mPositiveTextAppearance;
    protected int mSelectedColor;
    @StringRes
    private int mNeutralTextRes;
    private CharSequence mNeutralText;
    @StringRes
    private int mNegativeTextRes;
    private CharSequence mNegativeText;
    @StringRes
    private int mPositiveTextRes;
    private CharSequence mPositiveText;
    private int mButtonsHeight;

    public ButtonsLayout(Context context) {
        this(context, null);
    }

    public ButtonsLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ButtonsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mButtonsHeight = LayoutParams.WRAP_CONTENT;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mNeutralBtn = new AppCompatButton(getContext());
        mNeutralBtn.setId(R.id.btn_neutral);
        mNegativeBtn = new AppCompatButton(getContext());
        mNegativeBtn.setId(R.id.btn_negative);
        mPositiveBtn = new AppCompatButton(getContext());
        mPositiveBtn.setId(R.id.btn_positive);

        setButtonsDefault();
        addView(mNeutralBtn, new LayoutParams(LayoutParams.WRAP_CONTENT, mButtonsHeight));
        addView(mNegativeBtn, new LayoutParams(LayoutParams.WRAP_CONTENT, mButtonsHeight));
        addView(mPositiveBtn, new LayoutParams(LayoutParams.WRAP_CONTENT, mButtonsHeight));

        if (mNeutralTextAppearance != 0) {
            mNeutralBtn.setTextAppearance(getContext(), mNeutralTextAppearance);
        } else {
            mNeutralBtn.setTextAppearance(getContext(), R.style.CozyAlertButtonDefaultTextAppearance);
        }
        if (mNegativeTextAppearance != 0) {
            mNegativeBtn.setTextAppearance(getContext(), mNegativeTextAppearance);
        } else {
            mNegativeBtn.setTextAppearance(getContext(), R.style.CozyAlertButtonDefaultTextAppearance);
        }
        if (mPositiveTextAppearance != 0) {
            mPositiveBtn.setTextAppearance(getContext(), mPositiveTextAppearance);
        } else {
            mPositiveBtn.setTextAppearance(getContext(), R.style.CozyAlertButtonDefaultTextAppearance);
        }
        addButtonsOnClickListener();
    }

    private void addButtonsOnClickListener() {
        mNeutralBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNeutralOnClickListener != null) {
                    mNeutralOnClickListener.onClick(v);
                }
            }
        });
        mNegativeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNegativeOnClickListener != null) {
                    mNegativeOnClickListener.onClick(v);
                }
            }
        });
        mPositiveBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPositiveOnClickListener != null) {
                    mPositiveOnClickListener.onClick(v);
                }
            }
        });
    }

    private void setButtonsDefault() {
        setButtonDefault(mPositiveBtn);
        setButtonDefault(mNegativeBtn);
        setButtonDefault(mNeutralBtn);
    }

    private void setButtonDefault(@NonNull AppCompatButton button) {
        //set minimumWidth and minWidth together,otherwise not worked.
        int dp64 = UIHelper.dp2Px(64);
        button.setMinimumWidth(dp64);
        button.setMinWidth(dp64);
        button.setGravity(Gravity.CENTER);
        int dp12 = UIHelper.dp2Px(12f);
        int dp2 = UIHelper.dp2Px(2);
        button.setPadding(dp12, dp2, dp12, dp2);
        button.setSingleLine(true);
        button.setEllipsize(TextUtils.TruncateAt.END);
        button.setTypeface(Typeface.SANS_SERIF);
    }

    private void setButtonsTextOrGone() {
        setButtonsTextOrGone(mPositiveBtn, mPositiveText, mPositiveTextRes);
        setButtonsTextOrGone(mNegativeBtn, mNegativeText, mNegativeTextRes);
        setButtonsTextOrGone(mNeutralBtn, mNeutralText, mNeutralTextRes);
    }

    private void setButtonsTextOrGone(@NonNull AppCompatButton button,
                                      CharSequence text, @StringRes int resId) {
        if (TextUtils.isEmpty(text) && resId == 0) {
            button.setVisibility(GONE);
        } else {
            button.setVisibility(VISIBLE);
        }
        if (!TextUtils.isEmpty(text)) {
            button.setText(text);
            return;
        }
        if (resId != 0) {
            button.setText(resId);
        }
    }

    protected void measureChildrenWithSpecWidth(int childWidth, int heightMeasureSpec) {
        if (isVisible(mNeutralBtn)) {
            final LayoutParams lp = mNeutralBtn.getLayoutParams();
            mNeutralBtn.measure(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY),
                    getChildMeasureSpec(heightMeasureSpec,
                            getPaddingTop() + getPaddingBottom(), lp.height));
        }
        if (isVisible(mNegativeBtn)) {
            final LayoutParams lp = mNegativeBtn.getLayoutParams();
            mNegativeBtn.measure(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY),
                    getChildMeasureSpec(heightMeasureSpec,
                            getPaddingTop() + getPaddingBottom(), lp.height));
        }
        if (isVisible(mPositiveBtn)) {
            final LayoutParams lp = mNegativeBtn.getLayoutParams();
            mPositiveBtn.measure(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY),
                    getChildMeasureSpec(heightMeasureSpec,
                            getPaddingTop() + getPaddingBottom(), lp.height));
        }
    }

    protected void measureChildrenSelf(int widthSpec, int heightSpec) {
        if (isVisible(mNeutralBtn)) {
            measureChild(mNeutralBtn, widthSpec, heightSpec);
        }
        if (isVisible(mNegativeBtn)) {
            measureChild(mNegativeBtn, widthSpec, heightSpec);
        }
        if (isVisible(mPositiveBtn)) {
            measureChild(mPositiveBtn, widthSpec, heightSpec);
        }
    }

    protected boolean isVisible(View view) {
        return view != null && view.getVisibility() != GONE;
    }

    protected int visibleCount() {
        int visibleCount = 0;
        for (int i = 0; i < getChildCount(); i++) {
            if (isVisible(getChildAt(i))) {
                visibleCount++;
            }
        }
        return visibleCount;
    }

    public void setOrientation(int orientation) {
        mOrientation = orientation;
        requestLayout();
    }

    public void setButtonsHeight(int buttonsHeight) {
        mButtonsHeight = buttonsHeight;
        if (mPositiveBtn != null) {
            mPositiveBtn.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, mButtonsHeight));
        }
        if (mNegativeBtn != null) {
            mNegativeBtn.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, mButtonsHeight));
        }
        if (mNeutralBtn != null) {
            mNeutralBtn.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, mButtonsHeight));
        }
    }

    public void setNeutralTextAppearance(int textAppearance) {
        mNeutralTextAppearance = textAppearance;
        if (mNeutralBtn != null) {
            mNeutralBtn.setTextAppearance(getContext(), textAppearance);
        }
    }

    public void setNegativeTextAppearance(int textAppearance) {
        mNegativeTextAppearance = textAppearance;
        if (mNegativeBtn != null) {
            mNegativeBtn.setTextAppearance(getContext(), textAppearance);
        }
    }

    public void setPositiveTextAppearance(int textAppearance) {
        mPositiveTextAppearance = textAppearance;
        if (mPositiveBtn != null) {
            mPositiveBtn.setTextAppearance(getContext(), textAppearance);
        }
    }

    public void setNeutralOnClickListener(OnClickListener onClickListener) {
        mNeutralOnClickListener = onClickListener;
    }

    public void setNegativeOnClickListener(OnClickListener onClickListener) {
        mNegativeOnClickListener = onClickListener;
    }

    public void setPositiveOnClickListener(OnClickListener onClickListener) {
        mPositiveOnClickListener = onClickListener;
    }

    public void setNeutralText(CharSequence neutralText, @StringRes int resId) {
        mNeutralText = neutralText;
        mNeutralTextRes = resId;
        if (mNeutralBtn != null) {
            setButtonsTextOrGone(mNeutralBtn, neutralText, resId);
        }
    }


    public void setNegativeText(CharSequence negativeText, @StringRes int resId) {
        mNegativeText = negativeText;
        mNegativeTextRes = resId;
        if (mNegativeBtn != null) {
            setButtonsTextOrGone(mNegativeBtn, negativeText, resId);
        }
    }

    public void setPositiveText(CharSequence positiveText, @StringRes int resId) {
        mPositiveText = positiveText;
        mPositiveTextRes = resId;
        if (mPositiveBtn != null) {
            setButtonsTextOrGone(mPositiveBtn, positiveText, resId);
        }
    }

    public abstract void setSelectedColor(@ColorInt int color, int cornerRadius);
}
