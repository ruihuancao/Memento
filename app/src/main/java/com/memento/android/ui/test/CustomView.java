package com.memento.android.ui.test;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.memento.android.R;


/**
 * Created by caoruihuan on 16/8/26.
 */
public class CustomView extends View {

    private Paint paint ;
    float size ;
    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomView);
        size = a.getDimension(R.styleable.CustomView_defaultSize, 0);
        a.recycle();
        init();
    }

    private void init(){



        // 初始化画笔
        setBackgroundColor(Color.BLACK);
        paint = new Paint();
        paint.setColor(Color.BLUE);  // 设置画笔颜色
        paint.setStyle(Paint.Style.STROKE); // 设置画笔模式为填充  STROKE 描边
        paint.setStrokeWidth(10f);  // 设置画笔宽度
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (heightMode) {
            case MeasureSpec.UNSPECIFIED:
                break;
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
                break;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }



    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 画点
//        canvas.drawPoint(200, 200, paint);
//        canvas.drawPoints(new float[]{100, 100, 800, 800, 300, 300, 400, 400}, paint);
//        //画线
//        canvas.drawLine(300, 300, 1000, 500, paint);  // 2点一线  2个坐标一个点
//
//        canvas.drawLines(new float[]{
//                10,600, 100, 20,
//                1300, 500, 700, 1500
//        }, paint);

        //画矩形
        //canvas.drawRect(100, 100, 800, 400, paint);

        //canvas.drawRoundRect(100, 100, 900, 800, 30, 30, paint);

        //canvas.drawOval(600,600,800,400, paint);

        //canvas.drawCircle(400, 400, 300, paint);


//        RectF rectF = new RectF(100,100,800,400);
//        // 绘制背景矩形
//        paint.setColor(Color.GRAY);
//        canvas.drawRect(rectF,paint);
//
//        // 绘制圆弧
//        paint.setColor(Color.BLUE);
//        canvas.drawArc(rectF,0,90,false,paint);

        //-------------------------------------

        canvas.translate(500, 500);
        RectF rectF2 = new RectF(-300,-300,300,300);
//        // 绘制背景矩形
//        paint.setColor(Color.GRAY);
//        canvas.drawRect(rectF2,paint);

        // 绘制圆弧
//        paint.setColor(Color.BLUE);
//        canvas.drawArc(rectF2,0,90,true,paint);
//
//
//        paint.setColor(Color.RED);
//        canvas.drawArc(rectF2,90,80,true,paint);
//
//
//        paint.setColor(Color.GREEN);
//        canvas.drawArc(rectF2,170,80,true,paint);



//        RectF rect = new RectF(-400,-400,400,400);   // 矩形区域
//
//        for (int i=0; i<=20; i++)
//        {
//            canvas.scale(0.9f,0.9f);
//            canvas.drawRect(rect,paint);
//        }

        canvas.drawCircle(0,0,400,paint);          // 绘制两个圆形
        canvas.drawCircle(0,0,380,paint);

        for (int i=0; i<=360; i+=10){               // 绘制圆形之间的连接线
            canvas.drawLine(0,380,0,400,paint);
            canvas.rotate(10);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
