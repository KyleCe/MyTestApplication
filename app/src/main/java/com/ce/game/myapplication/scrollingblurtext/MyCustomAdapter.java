package com.ce.game.myapplication.scrollingblurtext;

/**
 * Created by KyleCe on 2016/4/20.
 *
 * @author: KyleCe
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.util.ViewU;


public class MyCustomAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private String[] strArray;

    public MyCustomAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addData(String[] datas) {
        if (datas == null) return;

        strArray = new String[datas.length];
        strArray = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return strArray.length;
    }

    @Override
    public String getItem(int position) {
        return strArray[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_view_blur_item, null);
            holder.textView = (TextView) convertView.findViewById(R.id.text_view);
            holder.blurTextView = (BlurTextView) convertView.findViewById(R.id.blur_text_view);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position == strArray.length - 2){
            holder.textView.setText(strArray[position]);
            ViewU.showAndHide(holder.textView,holder.blurTextView);
        }else {
            holder.blurTextView.setText(strArray[position]);
            ViewU.hideAndShow(holder.textView,holder.blurTextView);
        }

        return convertView;
    }

    private static class ViewHolder {
        public TextView textView;
        public BlurTextView blurTextView;
    }
}
