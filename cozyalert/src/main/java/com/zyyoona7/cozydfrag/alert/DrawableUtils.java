package com.zyyoona7.cozydfrag.alert;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;

import java.util.Arrays;

public class DrawableUtils {

    private DrawableUtils() {

    }

    public static Drawable getAdaptiveRippleDrawable(int normalColor, int pressedColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return new RippleDrawable(ColorStateList.valueOf(pressedColor),
                   null,getRippleMask(pressedColor));
        } else {
            return getStateListDrawable(normalColor, pressedColor);
        }
    }

    public static ColorStateList getPressedColorSelector(int normalColor, int pressedColor) {
        return new ColorStateList(
                new int[][]{new int[]{android.R.attr.state_pressed},
                        new int[]{android.R.attr.state_focused},
                        new int[]{android.R.attr.state_activated}, new int[]{}
                        },
                new int[]{pressedColor, pressedColor, pressedColor, normalColor}
        );
    }

    public static ColorDrawable getColorDrawableFromColor(int color)
    {
        return new ColorDrawable(color);
    }

    private static Drawable getRippleMask(int color) {
        float[] outerRadii = new float[8];
        // 3 is radius of final ripple,
        // instead of 3 you can give required final radius
        Arrays.fill(outerRadii, 3);

        RoundRectShape r = new RoundRectShape(outerRadii, null, null);
        ShapeDrawable shapeDrawable = new ShapeDrawable(r);
        shapeDrawable.getPaint().setColor(color);
        return shapeDrawable;
    }

    public static StateListDrawable getStateListDrawable(
            int normalColor, int pressedColor) {
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_pressed},
                new ColorDrawable(pressedColor));
        states.addState(new int[]{android.R.attr.state_focused},
                new ColorDrawable(pressedColor));
        states.addState(new int[]{android.R.attr.state_activated},
                new ColorDrawable(pressedColor));
        states.addState(new int[]{},
                new ColorDrawable(normalColor));
        return states;
    }
}
