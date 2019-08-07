package com.zyyoona7.cozydfrag.alert.helper;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;

public class DrawableHelper {

    private DrawableHelper() {

    }

    public static Drawable createRippleDrawable(int pressedColor, float corners) {
        return createRippleDrawable(Color.TRANSPARENT, pressedColor, corners);
    }

    public static Drawable createRippleDrawable(int normalColor,
                                                int pressedColor, float corners) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return new RippleDrawable(ColorStateList.valueOf(pressedColor),
                    null, createRoundDrawable(Color.WHITE, corners));
        } else {
            return createRoundSelector(normalColor, pressedColor, corners);
        }
    }

    public static StateListDrawable createRoundSelector(int normalColor, int pressedColor, float corners) {
        return createStateListDrawable(normalColor == Color.TRANSPARENT
                        ? new ColorDrawable(Color.TRANSPARENT) : createRoundDrawable(normalColor, corners),
                createRoundDrawable(pressedColor, corners), createRoundDrawable(pressedColor, corners),
                createRoundDrawable(pressedColor, corners));
    }

    public static StateListDrawable createRoundSelector(int normalColor, int pressedColor,
                                                        float bottomLeftRadius, float bottomRightRadius) {
        return createStateListDrawable(normalColor == Color.TRANSPARENT
                        ? new ColorDrawable(Color.TRANSPARENT)
                        : createRoundDrawable(normalColor, bottomLeftRadius, bottomRightRadius),
                createRoundDrawable(pressedColor, bottomLeftRadius, bottomRightRadius),
                createRoundDrawable(pressedColor, bottomLeftRadius, bottomRightRadius),
                createRoundDrawable(pressedColor, bottomLeftRadius, bottomRightRadius));
    }

    public static Drawable createRoundDrawable(int color, float radius) {
        float[] outerRadii = new float[]{radius, radius, radius,
                radius, radius, radius, radius, radius};
        return createRoundDrawable(color, outerRadii);
    }

    public static Drawable createRoundDrawable(int color, float bottomLeftRadius, float bottomRightRadius) {
        float[] radii = new float[]{0f, 0f, 0f, 0f,
                bottomRightRadius, bottomRightRadius, bottomLeftRadius, bottomLeftRadius};
        return createRoundDrawable(color, radii);
    }

    public static Drawable createRoundDrawable(int color, float[] radii) {
        if (radii == null || radii.length < 8) {
            radii = new float[8];
        }
        RoundRectShape r = new RoundRectShape(radii, null, null);
        ShapeDrawable shapeDrawable = new ShapeDrawable(r);
        shapeDrawable.getPaint().setColor(color);
        return shapeDrawable;
    }

    public static StateListDrawable createStateListDrawable(Drawable normalDrawable,
                                                            Drawable pressedDrawable,
                                                            Drawable focusedDrawable,
                                                            Drawable activatedDrawable) {
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_pressed},
                pressedDrawable);
        states.addState(new int[]{android.R.attr.state_focused},
                focusedDrawable);
        states.addState(new int[]{android.R.attr.state_activated},
                activatedDrawable);
        states.addState(new int[]{},
                normalDrawable);
        return states;
    }

    public static StateListDrawable createStateListDrawable(
            int normalColor, int pressedColor) {
        return createStateListDrawable(new ColorDrawable(normalColor),
                new ColorDrawable(pressedColor), new ColorDrawable(pressedColor),
                new ColorDrawable(pressedColor));
    }

    public static ColorStateList createColorSelector(int normalColor, int pressedColor) {
        return new ColorStateList(
                new int[][]{new int[]{android.R.attr.state_pressed},
                        new int[]{android.R.attr.state_focused},
                        new int[]{android.R.attr.state_activated}, new int[]{}
                },
                new int[]{pressedColor, pressedColor, pressedColor, normalColor}
        );
    }
}
