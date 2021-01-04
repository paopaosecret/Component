package com.xbing.app.component.ui.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.xbing.app.basic.common.DpPxUtil;
import com.xbing.app.component.R;

import java.util.ArrayList;

/**
 * Created by zhaobing04 on 2018/6/19.
 */

public class IndicatorView extends LinearLayout {
    private Context mContext;
    private ArrayList<View> mImageViews;//所有指示器集合
    private int size = 6;
    private int marginSize = 15;
    private int pointSize;//指示器的大小
    private int marginLeft;//间距
    private int selectDrawableId = -1;//选中资源ID
    private int unselectDrwableId = -1;//未选中的资源id

    public IndicatorView(Context context) {
        this(context, null);
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        pointSize = DpPxUtil.dip2px(context, size);
        marginLeft = DpPxUtil.dip2px(context, marginSize);
    }

    /**
     * 初始化指示器
     *
     * @param count 指示器的数量
     */
    public void initIndicator(int count) {
        mImageViews = new ArrayList<>();
        this.removeAllViews();
        LayoutParams lp;
        for (int i = 0; i < count; i++) {
            View v = new View(mContext);
            lp = new LayoutParams(pointSize, pointSize);
            if (i != 0)
                lp.leftMargin = marginLeft;
            v.setLayoutParams(lp);
            if (i == 0) {
                v.setBackgroundResource(R.drawable.bg_circle_red);
            } else {
                v.setBackgroundResource(R.drawable.bg_circle_gary);
            }
            mImageViews.add(v);
            this.addView(v);
        }
    }

    /**
     * 初始化指示器
     *
     * @param count 指示器的数量
     */
    public void initIndicator(int count,  int selectDrawableId, int unselectDrawableId ) {
        this.selectDrawableId = selectDrawableId;
        this.unselectDrwableId = unselectDrawableId;
        mImageViews = new ArrayList<>();
        this.removeAllViews();
        LayoutParams lp;
        for (int i = 0; i < count; i++) {
            View v = new View(mContext);
            lp = new LayoutParams(pointSize, pointSize);
            if (i != 0)
                lp.leftMargin = marginLeft;
            v.setLayoutParams(lp);
            if (i == 0) {
                v.setBackgroundResource(selectDrawableId);
            } else {
                v.setBackgroundResource(unselectDrawableId);
            }
            mImageViews.add(v);
            this.addView(v);
        }
    }

    /**
     * 页面移动时切换指示器
     */
    public void playByStartPointToNext(int startPosition, int nextPosition) {
        if (startPosition < 0 || nextPosition < 0 || nextPosition == startPosition) {
            startPosition = nextPosition = 0;
        }
        final View ViewStrat = mImageViews.get(startPosition);
        final View ViewNext = mImageViews.get(nextPosition);
        if(unselectDrwableId != -1 && selectDrawableId != -1){
            ViewNext.setBackgroundResource(selectDrawableId);
            ViewStrat.setBackgroundResource(unselectDrwableId);
        }else{
            ViewNext.setBackgroundResource(R.drawable.bg_circle_red);
            ViewStrat.setBackgroundResource(R.drawable.bg_circle_gary);
        }
    }

    public void setMarginLeft(int marginLeft) {
        this.marginLeft = marginLeft;
    }
}
