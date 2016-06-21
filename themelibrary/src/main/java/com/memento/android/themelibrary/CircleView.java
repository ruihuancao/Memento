package com.memento.android.themelibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CircleView extends View {

    private int colorResId = android.R.color.white;

    public CircleView(Context context) {
        super(context);
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //绘制背景
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getResources().getColor(colorResId));
        float radius = getWidth() / 2;
        canvas.drawCircle(radius, radius, radius, paint);
    }

    /**
     * 设置colorResId
     *
     * @param resId
     */
    public void setColorResId(int resId) {
        colorResId = resId;
    }
}
