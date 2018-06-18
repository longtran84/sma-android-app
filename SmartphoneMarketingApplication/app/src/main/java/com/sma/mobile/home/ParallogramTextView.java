package com.sma.mobile.home;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by QLong on 10/13/2017.
 */

public class ParallogramTextView extends TextView {


    Paint mBoarderPaint;
    Paint mInnerPaint;

    public ParallogramTextView(Context context) {
        super(context);
        init();
    }

    public ParallogramTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public ParallogramTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        mBoarderPaint = new Paint();
        mBoarderPaint.setAntiAlias(true);
        mBoarderPaint.setColor(Color.WHITE);
        mBoarderPaint.setStyle(Paint.Style.STROKE);
        mBoarderPaint.setStrokeWidth(0);

        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setColor(0xFF45bdcd);
        mInnerPaint.setStyle(Paint.Style.FILL);
        mInnerPaint.setStrokeJoin(Paint.Join.ROUND);
    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Path path = new Path();
        path.moveTo(getWidth(), 0);
        path.lineTo(48, 0);
        path.lineTo(0, getHeight());
        path.lineTo(getWidth() , getHeight());
        path.lineTo(getWidth(), 0);
        canvas.drawPath(path, mInnerPaint);
        //canvas.drawPath(path, mBoarderPaint);
    }

}
