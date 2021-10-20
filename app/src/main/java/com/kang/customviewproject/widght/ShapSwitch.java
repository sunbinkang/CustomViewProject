package com.kang.customviewproject.widght;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by BinKang on 2021/10/20.
 * Des :
 */
public class ShapSwitch extends View {

    private Path path;
    private Paint mpaint;
    private Shap mCurrtenShap = Shap.CIRCLE;

    enum Shap {
        CIRCLE, SQUARE, TRIGON;
    }

    public ShapSwitch(Context context) {
        this(context, null);
    }

    public ShapSwitch(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapSwitch(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ShapSwitch(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.min(width, height), Math.min(width, height));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switchShap(canvas);
    }

    private void switchShap(Canvas canvas) {
        switch (mCurrtenShap) {
            case CIRCLE:
                mpaint = new Paint();
                mpaint.setAntiAlias(true);
                mpaint.setColor(Color.YELLOW);
                canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2, mpaint);
                mCurrtenShap = Shap.SQUARE;
                break;
            case SQUARE:
                mpaint = new Paint();
                mpaint.setAntiAlias(true);
                mpaint.setColor(Color.RED);
                Rect rect = new Rect(0, 0, getWidth(), getHeight());
                canvas.drawRect(rect, mpaint);
                mCurrtenShap = Shap.TRIGON;
                break;
            case TRIGON:
                mpaint.setAntiAlias(true);
                mpaint.setColor(Color.BLUE);
                mCurrtenShap = Shap.CIRCLE;
                if (path == null) {
                    path = new Path();
                }
                path.moveTo(getWidth() / 2, 0);
                path.lineTo(0, (float) (getWidth() / 2 * Math.sqrt(3)));
                path.lineTo(getWidth(), (float) (getWidth() / 2 * Math.sqrt(3)));
                path.close();
                canvas.drawPath(path, mpaint);
                break;
        }

    }

    public Shap getmCurrtenShap() {
        return mCurrtenShap;
    }

    public void exchange() {
        switch (mCurrtenShap) {
            case CIRCLE:
                mCurrtenShap = Shap.SQUARE;
                break;
            case SQUARE:
                mCurrtenShap = Shap.TRIGON;
                break;
            case TRIGON:
                mCurrtenShap = Shap.CIRCLE;
                break;
        }
        invalidate();
    }
}
