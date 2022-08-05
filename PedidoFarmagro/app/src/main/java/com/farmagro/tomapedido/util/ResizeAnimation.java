package com.farmagro.tomapedido.util;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class ResizeAnimation extends Animation {
    private float mFromHeight;
    private float mFromWidth;
    private float mToHeight;
    private float mToWidth;
    private View mView;

    public ResizeAnimation(View v, float fromWidth, float fromHeight, float toWidth, float toHeight) {
        this.mToHeight = toHeight;
        this.mToWidth = toWidth;
        this.mFromHeight = fromHeight;
        this.mFromWidth = fromWidth;
        this.mView = v;
        setDuration(250);
    }

    /* access modifiers changed from: protected */
    public void applyTransformation(float interpolatedTime, Transformation t) {
        float f = this.mToHeight;
        float f2 = this.mFromHeight;
        float height = ((f - f2) * interpolatedTime) + f2;
        float f3 = this.mToWidth;
        float f4 = this.mFromWidth;
        float width = ((f3 - f4) * interpolatedTime) + f4;
        ViewGroup.LayoutParams p = this.mView.getLayoutParams();
        p.height = (int) height;
        p.width = (int) width;
        this.mView.requestLayout();
    }
}
