package com.zyyoona7.cozydfrag.alert.helper;

import android.content.res.Resources;
import android.util.TypedValue;

public class UIHelper {

    private UIHelper() {

    }

    public static int dp2Px(float dp) {
        return (int) Math.ceil(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, Resources.getSystem().getDisplayMetrics()));
    }

    public static int sp2Px(float sp){
        return (int) Math.ceil(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                sp, Resources.getSystem().getDisplayMetrics()));
    }
}
