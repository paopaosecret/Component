package com.xbing.app.component.ui.customview.testcustom;

import static android.view.View.MeasureSpec.EXACTLY;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.xbing.app.basic.common.DimenUtil;
import com.xbing.app.component.utils.DisplayUtils;

/**
 * 自定义ViewGroup
 * <p>
 * 1.onMeasure（）中需要测量子View 尺寸
 * 2.onLayout()中需要对子View进行排列布局
 */
public class HorizontalView extends ViewGroup {
    private static final String TAG = "HorizontalView";

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
        //TODO 第一步：测量自己 保证自己在父布局中设置的尺寸正常
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //TODO 第二步：循环测量子View
        //定义子View 的宽高
        int measureWidth = MeasureSpec.makeMeasureSpec(DimenUtil.dip2px(getContext(), 100), EXACTLY);
        int measureHeight = MeasureSpec.makeMeasureSpec(DimenUtil.dip2px(getContext(), 100), EXACTLY);

        int count = getChildCount();  //子布局个数
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                child.measure(measureWidth, measureHeight);  // 子View 的宽高设置上面定义的值
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int left = 0;
        int top = 0;
        View child;
        for (int i = 0; i < count; i++) {
            child = getChildAt(i);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            if (left + width > DisplayUtils.getScreenWidth(getContext())) {
                top = top + height;
                left = 0;
            }
            //TODO 设置子View 在该布局中的位置
            child.layout(left, top, left + width, top + height);
            left = left + width;
        }
    }

    public void swapView(int first, int second) {
        View firstView = getChildAt(first);
        View secondView = getChildAt(second);

        removeView(firstView);
        removeView(secondView);

        addView(secondView, first);
        addView(firstView, second);
    }

    public void swapView(View firstView, View secondView) {
        int first = indexOfChild(firstView);
        int second = indexOfChild(secondView);
        removeView(firstView);
        removeView(secondView);
        //TODO  避免异常 java.lang.IndexOutOfBoundsException: index=5 count=4 add的时候 需要保证 index 小于等于 子View 的数量
        //TODO  当index 小于 count时：往中间插入子View
        //TODO  当index 等于 count时：往最后追加子View
        if (first < second) {
            addView(secondView, first);
            addView(firstView, second);
        } else {
            addView(firstView, second);
            addView(secondView, first);
        }
    }

    /**
     *  TODO 注意几个变量的值含义
     *  1、view的位置由left、top、right、bottom四个属性决定，这四个坐标是相对坐标，即相对于父容器的坐标（View 自身位置，点击就对自身位置有效）
     *  2、x指view左上角的横坐标，当view发生移动时，x会变化；（View 上内容的位置）
     *  3、translationX指view左上角的横坐标相对于父容器的偏移量，当view发生移动时，translationX会变化。 注意：x = left + translationX
     *  4、rawX是绝对坐标，是相对于屏幕左上角的横坐标，view本身没有getRawX的方法，这个方法一般在MotionEvent对象里使用。
     *  5、scrollX指的是view在滑动过程中，view的左边缘和view内容的左边缘在水平方向的距离
     *
     *  TODO 注意：translationX 指的是view本身的移动，scrollX是view的内容移动
     */
    public void swapViewWithAnimation(View firstView, View secondView) {
        float startX1 = firstView.getX();
        float startY1 = firstView.getY();
        float startX2 = secondView.getX();
        float startY2 = secondView.getY();

        //TODO 创建 firstView 的移动动画
        ViewPropertyAnimator animator1 = firstView.animate()
                .setDuration(500)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .x(startX2)
                .y(startY2)
                .withEndAction(() -> {
                    //TODO 重要：移动完成之后需要将移动坐标还原，然后再交换两个View
                    firstView.setTranslationX(0);
                    firstView.setTranslationY(0);
                    secondView.setTranslationX(0);
                    secondView.setTranslationY(0);
                });

        //TODO 创建 secondView 的移动动画
        ViewPropertyAnimator animator2 = secondView.animate()
                .setDuration(500)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .x(startX1)
                .y(startY1)
                .withEndAction(() -> {
                    //TODO 重要：移动完成之后需要将移动坐标还原，然后再交换两个View
                    firstView.setTranslationX(0);
                    firstView.setTranslationY(0);
                    secondView.setTranslationX(0);
                    secondView.setTranslationY(0);
                    swapView(firstView, secondView);
                });

        //TODO 启动动画
        animator1.start();
        animator2.start();
    }

    public void swapViewWithAnimation(int first, int second) {

    }
}
