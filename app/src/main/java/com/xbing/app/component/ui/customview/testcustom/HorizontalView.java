package com.xbing.app.component.ui.customview.testcustom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自定义ViewGroup
 *
 * 1.onMeasure（）中需要测量子View 尺寸
 * 2.onLayout()中需要对子View进行排列布局
 */
public class HorizontalView extends ViewGroup {
    private int childWidth;
    public HorizontalView(Context context) {
        super(context);
    }

    public HorizontalView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int left = 0;
        View child;
        for(int i = 0; i < count; i++){
            child = getChildAt(i);
            int width = child.getMeasuredWidth();
            childWidth = width;
            child.layout(left, 0, left + width, child.getMeasuredHeight());
            left = left + width;
        }
    }
}
