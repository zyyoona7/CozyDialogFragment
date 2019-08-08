package com.zyyoona7.cozydfrag.alert;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;

import com.zyyoona7.cozydfrag.CozyDialogFragment;
import com.zyyoona7.cozydfrag.alert.helper.DrawableHelper;
import com.zyyoona7.cozydfrag.alert.helper.UIHelper;
import com.zyyoona7.cozydfrag.alert.view.button.ButtonsLayout;
import com.zyyoona7.cozydfrag.callback.OnDialogClickListener;

public class CozyAlertDialog extends CozyDialogFragment {

    private static final String TAG = "CozyAlertDialog";
    private static final String SAVED_THEME_STYLE = "SAVED_THEME_STYLE";
    private static final String SAVED_STYLE="SAVED_STYLE";
    private static final String SAVED_BACKGROUND_COLOR="SAVED_BACKGROUND_COLOR";
    private static final String SAVED_CORNER_RADIUS="SAVED_CORNER_RADIUS";
    private static final String SAVED_BUTTON_ORIENTATION="SAVED_BUTTON_ORIENTATION";
    private static final String SAVED_BUTTON_HEIGHT="SAVED_BUTTON_HEIGHT";
    private static final String SAVED_NEU_TEXT_APPEAR="SAVED_NEU_TEXT_APPEAR";
    private static final String SAVED_NEG_TEXT_APPEAR="SAVED_NEG_TEXT_APPEAR";
    private static final String SAVED_POS_TEXT_APPEAR="SAVED_POS_TEXT_APPEAR";
    private static final String SAVED_BUTTON_SELECTED_COLOR="SAVED_BUTTON_SELECTED_COLOR";
    private static final String SAVED_NEU_TEXT_RES="SAVED_NEU_TEXT_RES";
    private static final String SAVED_NEU_TEXT="SAVED_NEU_TEXT";
    private static final String SAVED_NEG_TEXT_RES="SAVED_NEG_TEXT_RES";
    private static final String SAVED_NEG_TEXT="SAVED_NEG_TEXT";
    private static final String SAVED_POS_TEXT_RES="SAVED_POS_TEXT_RES";
    private static final String SAVED_POS_TEXT="SAVED_POS_TEXT";

    public static final int STYLE_MATERIAL = 0;
    public static final int STYLE_CUPERTINO = 1;

    public static final int WHICH_POSITIVE = -100;
    public static final int WHICH_NEGATIVE = -101;
    public static final int WHICH_NEUTRAL = -102;

    //单独设置
    private int mThemeStyle = -1;
    private int mStyle = -1;
    private int mBackgroundColor = Color.WHITE;
    private ButtonsLayout mButtonsLayout;
    private int mCornerRadius = UIHelper.dp2Px(10f);
    private int mButtonOrientation = ButtonsLayout.ORIENTATION_HORIZONTAL;
    private int mButtonHeight;
    private int mNeutralTextAppearance;
    private int mNegativeTextAppearance;
    private int mPositiveTextAppearance;
    private int mButtonSelectedColor=Color.LTGRAY;
    @StringRes
    private int mNeutralTextRes;
    private CharSequence mNeutralText;
    @StringRes
    private int mNegativeTextRes;
    private CharSequence mNegativeText;
    @StringRes
    private int mPositiveTextRes;
    private CharSequence mPositiveText;

    public static CozyAlertDialog create() {
        return create(-1);
    }

    public static CozyAlertDialog create(@StyleRes int themeStyle) {
        Bundle args = new Bundle();
        CozyAlertDialog fragment = new CozyAlertDialog();
        args.putInt(SAVED_THEME_STYLE, themeStyle);
        fragment.setArguments(args);
        return fragment;
    }

    public CozyAlertDialog() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mThemeStyle = getArguments().getInt(SAVED_THEME_STYLE, -1);
        }
        if (savedInstanceState != null) {
            restoreSavedState(savedInstanceState);
        }
        resolveTheme();
    }

    private void restoreSavedState(@NonNull Bundle savedInstanceState){
        mStyle=savedInstanceState.getInt(SAVED_STYLE,-1);
        mBackgroundColor=savedInstanceState.getInt(SAVED_BACKGROUND_COLOR,Color.WHITE);
        mCornerRadius=savedInstanceState.getInt(SAVED_CORNER_RADIUS,UIHelper.dp2Px(2));
        mButtonOrientation=savedInstanceState.getInt(SAVED_BUTTON_ORIENTATION,ButtonsLayout.ORIENTATION_HORIZONTAL);
        mButtonHeight=savedInstanceState.getInt(SAVED_BUTTON_HEIGHT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mNeutralTextAppearance=savedInstanceState.getInt(SAVED_NEU_TEXT_APPEAR,0);
        mNegativeTextAppearance=savedInstanceState.getInt(SAVED_NEG_TEXT_APPEAR,0);
        mPositiveTextAppearance=savedInstanceState.getInt(SAVED_POS_TEXT_APPEAR,0);
        mButtonSelectedColor=savedInstanceState.getInt(SAVED_BUTTON_SELECTED_COLOR,Color.LTGRAY);
        mNeutralTextRes=savedInstanceState.getInt(SAVED_NEU_TEXT_RES,0);
        mNeutralText=savedInstanceState.getCharSequence(SAVED_NEU_TEXT,"");
        mNegativeTextRes=savedInstanceState.getInt(SAVED_NEG_TEXT_RES,0);
        mNegativeText=savedInstanceState.getCharSequence(SAVED_NEG_TEXT,"");
        mPositiveTextRes=savedInstanceState.getInt(SAVED_POS_TEXT_RES,0);
        mPositiveText=savedInstanceState.getCharSequence(SAVED_POS_TEXT,"");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(SAVED_STYLE,mStyle);
        outState.putInt(SAVED_BACKGROUND_COLOR,mBackgroundColor);
        outState.putInt(SAVED_CORNER_RADIUS,mCornerRadius);
        outState.putInt(SAVED_BUTTON_ORIENTATION,mButtonOrientation);
        outState.putInt(SAVED_BUTTON_HEIGHT, mButtonHeight);
        outState.putInt(SAVED_NEU_TEXT_APPEAR,mNeutralTextAppearance);
        outState.putInt(SAVED_NEG_TEXT_APPEAR,mNegativeTextAppearance);
        outState.putInt(SAVED_POS_TEXT_APPEAR,mPositiveTextAppearance);
        outState.putInt(SAVED_BUTTON_SELECTED_COLOR,mButtonSelectedColor);
        outState.putInt(SAVED_NEU_TEXT_RES,mNeutralTextRes);
        outState.putCharSequence(SAVED_NEU_TEXT,mNeutralText);
        outState.putInt(SAVED_NEG_TEXT_RES,mNegativeTextRes);
        outState.putCharSequence(SAVED_NEG_TEXT,mNegativeText);
        outState.putInt(SAVED_POS_TEXT_RES,mPositiveTextRes);
        outState.putCharSequence(SAVED_POS_TEXT,mPositiveText);
        super.onSaveInstanceState(outState);
    }

    private void resolveTheme() {
        if (mActivity == null) {
            return;
        }
        TypedArray typedArray = null;
        try {
            if (mThemeStyle == -1) {
                typedArray = mActivity.obtainStyledAttributes(R.styleable.CozyAlertDialog);
            } else {
                typedArray = mActivity.obtainStyledAttributes(mThemeStyle, R.styleable.CozyAlertDialog);
            }
            mStyle = typedArray.getInt(R.styleable.CozyAlertDialog_cozy_alert_style, -1);
            mBackgroundColor = typedArray.getColor(
                    R.styleable.CozyAlertDialog_cozy_alert_backgroundColor, Color.WHITE);
            mCornerRadius = typedArray.getDimensionPixelOffset(
                    R.styleable.CozyAlertDialog_cozy_alert_cornerRadius, UIHelper.dp2Px(2f));
            mButtonOrientation = typedArray.getInt(
                    R.styleable.CozyAlertDialog_cozy_alert_btn_orientation, ButtonsLayout.ORIENTATION_HORIZONTAL);
            mButtonHeight=typedArray.getDimensionPixelOffset(
                    R.styleable.CozyAlertDialog_cozy_alert_btn_height, ViewGroup.LayoutParams.WRAP_CONTENT);
            mNeutralTextAppearance = typedArray.getResourceId(
                    R.styleable.CozyAlertDialog_cozy_alert_btn_neu_textAppearance, 0);
            mNegativeTextAppearance = typedArray.getResourceId(
                    R.styleable.CozyAlertDialog_cozy_alert_btn_neg_textAppearance, 0);
            mPositiveTextAppearance = typedArray.getResourceId(
                    R.styleable.CozyAlertDialog_cozy_alert_btn_pos_textAppearance, 0);
            mButtonSelectedColor = typedArray.getColor(
                    R.styleable.CozyAlertDialog_cozy_alert_btn_selected_color, Color.LTGRAY);
            resolveThemeStyle(typedArray);
            Log.d(TAG, "onCreateView: style=" + mStyle);
        } catch (Exception e) {
            Log.d(TAG, "resolveTheme: Exception:" + e);
        } finally {
            if (typedArray != null) {
                typedArray.recycle();
            }
        }
    }

    protected void resolveThemeStyle(@NonNull TypedArray typedArray) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutRes;
        if (mStyle == 1) {
            layoutRes = R.layout.cozy_dialog_fragment_alert_cupertino;
        } else {
            layoutRes = R.layout.cozy_layout_alert_buttons_material;
        }
        return inflater.inflate(layoutRes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setBackground(DrawableHelper
                .createRoundDrawable(mBackgroundColor, mCornerRadius));
        mButtonsLayout = view.findViewById(R.id.buttons_layout);
        resolveButtonStyle(mButtonsLayout);
        resolveButtonListener(mButtonsLayout);
        resolveButtonText(mButtonsLayout);
    }

    private void resolveButtonStyle(@NonNull ButtonsLayout buttonsLayout) {
        buttonsLayout.setOrientation(mButtonOrientation);
        buttonsLayout.setButtonsHeight(mButtonHeight);
        buttonsLayout.setNeutralTextAppearance(mNeutralTextAppearance);
        buttonsLayout.setNegativeTextAppearance(mNegativeTextAppearance);
        buttonsLayout.setPositiveTextAppearance(mPositiveTextAppearance);
        buttonsLayout.setSelectedColor(mButtonSelectedColor,mCornerRadius);
    }

    private void resolveButtonListener(@NonNull ButtonsLayout buttonsLayout) {
        buttonsLayout.setNeutralOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnDialogClickListener listener = getDialogClickListener();
                if (listener != null) {
                    listener.onClick(CozyAlertDialog.this,
                            WHICH_NEUTRAL, getRequestId());
                }
                dismissAllowingStateLoss();
            }
        });
        buttonsLayout.setNegativeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnDialogClickListener listener = getDialogClickListener();
                if (listener != null) {
                    listener.onClick(CozyAlertDialog.this,
                            WHICH_NEGATIVE, getRequestId());
                }
                dismissAllowingStateLoss();
            }
        });
        buttonsLayout.setPositiveOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnDialogClickListener listener = getDialogClickListener();
                if (listener != null) {
                    listener.onClick(CozyAlertDialog.this,
                            WHICH_POSITIVE, getRequestId());
                }
                dismissAllowingStateLoss();
            }
        });
    }

    private void resolveButtonText(@NonNull ButtonsLayout buttonsLayout) {
        buttonsLayout.setNeutralText(mNeutralText, mNeutralTextRes);
        buttonsLayout.setNegativeText(mNegativeText, mNegativeTextRes);
        buttonsLayout.setPositiveText(mPositiveText, mPositiveTextRes);
    }

    public CozyAlertDialog setNeutralText(CharSequence neutralText) {
        mNeutralText = neutralText;
        mNeutralTextRes = 0;
        if (mButtonsLayout != null) {
            mButtonsLayout.setNeutralText(mNeutralText, mNeutralTextRes);
        }
        return this;
    }

    public CozyAlertDialog setNeutralText(@StringRes int neutralTextRes) {
        mNeutralTextRes = neutralTextRes;
        mNeutralText = "";
        if (mButtonsLayout != null) {
            mButtonsLayout.setNeutralText(mNeutralText, mNeutralTextRes);
        }
        return this;
    }

    public CozyAlertDialog setNegativeText(CharSequence negativeText) {
        mNegativeText = negativeText;
        mNegativeTextRes = 0;
        if (mButtonsLayout != null) {
            mButtonsLayout.setNegativeText(mNegativeText, mNegativeTextRes);
        }
        return this;
    }

    public CozyAlertDialog setNegativeText(@StringRes int negativeTextRes) {
        mNegativeTextRes = negativeTextRes;
        mNegativeText = "";
        if (mButtonsLayout != null) {
            mButtonsLayout.setNegativeText(mNegativeText, mNegativeTextRes);
        }
        return this;
    }

    public CozyAlertDialog setPositiveText(CharSequence positiveText) {
        mPositiveText = positiveText;
        mPositiveTextRes = 0;
        if (mButtonsLayout != null) {
            mButtonsLayout.setPositiveText(mPositiveText, mPositiveTextRes);
        }
        return this;
    }

    public CozyAlertDialog setPositiveText(@StringRes int positiveTextRes) {
        mPositiveTextRes = positiveTextRes;
        mPositiveText = "";
        if (mButtonsLayout != null) {
            mButtonsLayout.setPositiveText(mPositiveText, mPositiveTextRes);
        }
        return this;
    }
}
