package com.ce.game.myapplication.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by KyleCe on 2016/4/19.
 *
 * @author: KyleCe
 */
public class HorizontalSpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public HorizontalSpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() - 1)
            return;

        outRect.right = space;
    }
}
