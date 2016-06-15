package com.ce.game.myapplication.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.ce.game.myapplication.util.DU;
import com.ce.game.myapplication.util.DisplayUtil;

/**
 * Created by KyleCe on 2016/4/15.
 *
 * @author: KyleCe
 */
public class PageRecyclerView extends RecyclerView {

    private Context mContext = null;

    private MyRecyclerViewAdapter myAdapter = null;

    private int shortestDistance; // 超过此距离的滑动才有效
    private float slideDistance = 0; // 滑动的距离
    private float scrollX = 0; // X轴当前的位置

    private int spanRow = 1; // 行数
    private int totalPage = 3; // 总页数
    private int currentPage = 1; // 当前页

    public static int edgeIndicatorWidth = DisplayUtil.dp2px(19);
    public static int pageMargin = DisplayUtil.dp2px(3);
    public static int firstOrLastItemReduceWidth = edgeIndicatorWidth + pageMargin;
    public static int middleItemReduceWidth = firstOrLastItemReduceWidth << 1;

    public static int itemHeight = DisplayUtil.dp2px(81);

    /*
     * 0: 停止滚动且手指移开; 1: 开始滚动; 2: 手指做了抛的动作（手指离开屏幕前，用力滑了一下）
	 */
    private int scrollState = 0; // 滚动状态

    public PageRecyclerView(Context context) {
        this(context, null);
    }

    public PageRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        defaultInit(context);
    }

    // 默认初始化
    private void defaultInit(Context context) {
        this.mContext = context;
        setLayoutManager(new LinearLayoutManager(
                mContext, LinearLayoutManager.HORIZONTAL, false));
        setOverScrollMode(OVER_SCROLL_NEVER);

    }

    /**
     * 设置页间距
     *
     * @param pageMargin 间距(px)
     */
    public void setPageMargin(int pageMargin) {
        this.pageMargin = pageMargin;
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        shortestDistance = getMeasuredWidth() / 3;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        this.myAdapter = (MyRecyclerViewAdapter) adapter;
    }

    @Override
    public void onScrollStateChanged(int state) {
        switch (state) {
            case 2:
                scrollState = 2;
                break;
            case 1:
                scrollState = 1;
                break;
            case 0:
                if (slideDistance == 0) break;

                scrollState = 0;
                if (slideDistance < 0) { // 上页
                    currentPage = (int) Math.ceil(scrollX / getWidth());
                    if (currentPage * getWidth() - scrollX < shortestDistance) {
                        currentPage += 1;
                    }
                } else { // 下页
                    currentPage = (int) Math.ceil(scrollX / getWidth()) + 1;
                    if (currentPage <= totalPage) {
                        if (scrollX - (currentPage - 2) * getWidth() < shortestDistance) {
                            // 如果这一页滑出距离不足，则定位到前一页
                            currentPage -= 1;
                        }
                    } else {
                        currentPage = totalPage;
                    }
                }

                int cursorAdapter = 0;
                if (currentPage != 1 && currentPage != totalPage)
                    cursorAdapter = middleItemReduceWidth;

                smoothScrollBy((int) ((currentPage - 1) * (getWidth() - pageMargin) - cursorAdapter - scrollX), 0);

                DU.sd("distance", "current page=" + currentPage, "page margin=" + pageMargin, "scroll x=" + scrollX
                        , "cursor adapter" + cursorAdapter
                );

                slideDistance = 0;
                break;
        }
//        super.onScrollStateChanged(state);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        scrollX += dx;
        if (scrollState == 1) {
            slideDistance += dx;
        }

        super.onScrolled(dx, dy);
    }

}