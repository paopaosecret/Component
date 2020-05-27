package com.xbing.app.component.ui.behavior;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.xbing.app.component.R;

public class TabBehavior extends CoordinatorLayout.Behavior<View> {

    /** behavior标记的view */
    private View child;

    /** child 依赖滑动的view */
    private RecyclerView targetView;

    public TabBehavior() {
    }

    public TabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 确定使用Behavior的View要依赖的View的类型
     *
     * @param parent       使用Behavior的子view对应的CoordinatorLayout布局
     * @param child        使用Behavior的子view
     * @param dependency   CoordinatorLayout布局下的其余View
     * @return
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        Log.e("TabBehavior", "layoutDependsOn: parent.tag = " + parent.getTag() +
                ",child.tag = " + child.getTag() + ", dependency.tag = " + dependency.getTag() );
        if(dependency instanceof RecyclerView && dependency.getId() == R.id.rv_list){
            targetView = (RecyclerView) dependency;
            this.child = child;
            targetView.removeOnScrollListener(onScrolled);
            targetView.addOnScrollListener(onScrolled);
            return true;
        }
        return false;
    }

    /**
     * 在layoutDependsOn返回true的基础上之后，及时报告dependency的状态变化
     *
     * @param parent     CoordinatorLayout
     * @param child      该Behavior对应的那个View
     * @param dependency child依赖dependency
     * @return true 处理了, false  没处理
     */
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        return true;
    }

    /**
     * 在layoutDependsOn返回true的基础上之后，报告dependency被移除了
     *
     * @param parent
     * @param child
     * @param dependency
     */
    @Override
    public void onDependentViewRemoved(CoordinatorLayout parent, View child, View dependency) {
        super.onDependentViewRemoved(parent, child, dependency);
    }

    /**
     * 嵌套滑动开始（ACTION_DOWN），确定Behavior是否要监听此次事件
     * 是否接受嵌套滚动,只有它返回true,后面的其他方法才会被调用
     *
     * onNestedPreScroll 在内层view处理滚动事件前先被调用,可以让外层view先消耗部分滚动
     * onNestedScroll    在内层view将剩下的滚动消耗完之后调用,可以在这里处理最后剩下的滚动
     * onNestedPreFling  在内层view的Fling事件处理之前被调用
     * onNestedFling     在内层view的Fling事件处理完之后调用
     * */
    /**
     * 嵌套滑动开始（ACTION_DOWN），确定Behavior是否要监听此次事件
     * @param coordinatorLayout
     * @param child                 该Behavior对应的View
     * @param directTargetChild     嵌套滑动对应的父类的子类(因为嵌套滑动对于的父View不一定是一级就能找到的，可能挑了两级父View的父View， directTargetChild>=target)
     * @param target                具体嵌套滑动的那个子类
     * @param nestedScrollAxes      支持嵌套滚动轴。水平方向，垂直方向，或者不指定
     * @return 是否接受嵌套滚动,只有它返回true,后面的其他方法才会被调用
     */
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        Log.e("TabBehavior", "onNestedScroll: nestedScrollAxes = " + nestedScrollAxes);
        return target.getId() == R.id.rv_list;
    }

    /**
     * 在内层view将剩下的滚动消耗完之后调用,可以在这里处理最后剩下的滚动
     *
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dxConsumed
     * @param dyConsumed
     * @param dxUnconsumed
     * @param dyUnconsumed
     */
    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
//        Log.e("TabBehavior", "onNestedScroll: dxConsumed = " + dxConsumed + ", dyConsumed = " + dyConsumed + ", dxUnconsumed = " + dxUnconsumed + ", dyUnconsumed = " + dyUnconsumed);
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    /**
     * 在内层view的Fling事件处理完之后调用
     *
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param velocityX
     * @param velocityY
     * @param consumed
     * @return
     */
    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY, boolean consumed) {
//        Log.e("TabBehavior", "onNestedScroll: velocityX = " + velocityX + ", velocityY = " + velocityY + ", consumed = " + consumed);
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }



    private RecyclerView.OnScrollListener onScrolled = new RecyclerView.OnScrollListener() {

        int scrollY = 0;
        int position = -1;

        boolean isLock = false;

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            Log.e("TabBehavior", "onScrolled: dx = " + dx + ", dy = " + dy);
            scrollY += dy;
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int pos = layoutManager.findFirstVisibleItemPosition();
            final View first = layoutManager.findViewByPosition(pos);
            if (first instanceof RecyclerView){
                position = pos;
                if (!isLock){
                    isLock = true;
                    ((RecyclerView) first).addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            Log.e("TabBehavior", "first onScrolled: dx = " + dx + ", dy = " + dy);
                            if (recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE)
                                child.scrollBy(dx, dy);
                        }
                    });

                    ((RecyclerView)child).addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            Log.e("TabBehavior", "child onScrolled: dx = " + dx + ", dy = " + dy);
                            if (recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE)
                                first.scrollBy(dx, dy);
                        }
                    });

                }

            }
            Log.e("TabBehavior", "onScrolled: position = " + position + ", pos = " + pos);
            if (position == -1) return;

            if (pos >= position){
                child.setVisibility(View.VISIBLE);
            }else {
                child.setVisibility(View.INVISIBLE);
            }
        }
    };

}
