package com.xbing.app.component.ui.customview.testcustom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.xbing.app.basic.common.DpPxUtil;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * 自定义View
 *
 * 一般重写onDraw()方法即可
 */
public class LineTextView extends AppCompatTextView {
    Paint mPaint;
    public LineTextView(Context context) {
        super(context);
        init(context);
    }

    public LineTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LineTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mPaint = new Paint();
        mPaint.setARGB(255,255,0,0);
        mPaint.setStrokeWidth(DpPxUtil.dip2px(context,1));
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(0, getHeight()/2, getWidth(), getHeight()/2, mPaint);
    }
}
