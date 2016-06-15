package com.ce.game.myapplication.guide;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.util.DU;

/**
 * Created by KyleCe on 2016/6/3.
 *
 * @author: KyleCe
 */
public class GuideImageAdapter extends PagerAdapter {
    private Integer[] mImageIds = {
            R.drawable.guidepages_bg_01,
            R.drawable.guidepages_bg_02,
            R.drawable.guidepages_bg_03,
            R.drawable.guidepages_bg_04,
    };

    private String[] mTitle = {
            "",
            "title2",
            "title3",
            "title4",
    };
    private String[] mDescription = {
            "",
            "mDescription 2",
            "mDescription 3",
            "mDescription 4",
    };

    public GuideImageAdapter() {
    }

    public int getCount() {
        return mImageIds.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {


        LayoutInflater inflater = (LayoutInflater) container.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View convertView = inflater.inflate(R.layout.gallery_view, null);

        ImageView image = (ImageView) convertView.findViewById(R.id.view_image);
        TextView title = (TextView) convertView.findViewById(R.id.guide_title);
        TextView description = (TextView) convertView.findViewById(R.id.guide_description);

        image.setImageResource(mImageIds[position]);
        if (DU.notEmpty(mTitle[position])) title.setText(mTitle[position]);
        if (DU.notEmpty(mDescription[position])) description.setText(mDescription[position]);

        container.addView(convertView, 0);

        return convertView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ViewGroup) object);
    }
}
