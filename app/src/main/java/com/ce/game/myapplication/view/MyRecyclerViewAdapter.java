package com.ce.game.myapplication.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ce.game.myapplication.util.DU;
import com.ce.game.myapplication.R;
import com.ce.game.myapplication.util.DisplayUtil;


/**
 * Created by KyleCe on 2016/4/15.
 *
 * @author: KyleCe
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    int[] resArray = {
            R.drawable.one,
            R.drawable.two,
            R.drawable.three,
    };

    Bitmap defaultBitmap;

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        LinearLayout itemParent;

        public ViewHolder(View v) {
            super(v);

            image = (ImageView) v.findViewById(R.id.image_view);
            itemParent = (LinearLayout) v.findViewById(R.id.item_parent);
        }
    }

    private Context context;

    public MyRecyclerViewAdapter(Context context) {
        this.context = context;
        defaultBitmap = BitmapFactory.decodeResource(context.getResources(), resArray[1]);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);

        int itemWidth = DisplayUtil.getScreenWH(context)[0] - PageRecyclerView.middleItemReduceWidth;

        LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(
                itemWidth, PageRecyclerView.itemHeight);

        v.setLayoutParams(params);

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (DU.isNull(defaultBitmap)) return;

        // last or first one, longer the mView
        if (holder.getAdapterPosition() == 0 || holder.getAdapterPosition() == getItemCount() - 1) {

            int itemWidth = DisplayUtil.getScreenWH(context)[0] - PageRecyclerView.firstOrLastItemReduceWidth;

            LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(
                    itemWidth, PageRecyclerView.itemHeight);

            holder.itemParent.setLayoutParams(params);
        }

        holder.image.setImageBitmap(defaultBitmap);
    }

    @Override
    public int getItemCount() {
        return resArray.length;
    }
}
