package com.ce.game.myapplication.view;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.util.DisplayUtil;
import com.ce.game.myapplication.util.FontMaster;
import com.ce.game.myapplication.util.ViewU;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by KyleCe on 2016/5/23.
 *
 * @author: KyleCe
 */
public class PhoneViewWithList extends PhoneViewWithText {

    protected RecyclerView mRecyclerView;

    RecyclerViewAdapter mAdapter;

    CopyOnWriteArrayList<Integer> mTextList;

    private RecyclerView.LayoutManager mLayoutManager;
    private int DEFAULT_COUNT = 4;

    private long mAnimationDuration;

    protected FrameLayout mListContentParent;

    protected PinCodeScene mPinCodeScene;

    public PhoneViewWithList(final Context context) {
        this(context, null);
    }

    public PhoneViewWithList(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhoneViewWithList(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(final Context context) {
        mContainer.inflate(context, R.layout.first_anim_phone_view_list_content, mContainer);

        mRecyclerView = (RecyclerView) mContainer.findViewById(R.id.phone_frame_recycler_view);

        mListContentParent = (FrameLayout) findViewById(R.id.list_content_parent);

        mPinCodeScene = (PinCodeScene) findViewById(R.id.pin_code_scene_in_phone);

        mTextList = new CopyOnWriteArrayList<>();
        mTextList.add(0, PHONE_ROLE_ARRAY[0]);

        mContainer.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter = new RecyclerViewAdapter(mContext, mTextList, PHONE_BG_ARRAY, mRecyclerView.getHeight(), mListContentParent);

                mRecyclerView.setItemAnimator(new DefaultItemAnimator());

                mAnimationDuration = mRecyclerView.getItemAnimator().getChangeDuration();

                mLayoutManager = new LinearLayoutManager(context);

                mRecyclerView.setLayoutManager(mLayoutManager);

                mRecyclerView.setAdapter(mAdapter);

                ViewU.hide(mRecyclerView);

                mTextList.clear();
                for (int i = 0; i < DEFAULT_COUNT; i++) mTextList.add(i, PHONE_ROLE_ARRAY[i]);
            }
        }, 200);
    }

    public void showItemCount(int count) {
        if (count < 0 || count > DEFAULT_COUNT)
            throw new UnsupportedOperationException("unsupported index ::" + count);

        if (mRecyclerView.getPaddingLeft() == 0){

            ViewU.hideAndShow(mTextView,mRecyclerView);
            mListContentParent.setBackgroundColor(mContext.getResources().getColor(R.color.guide_view_5_myself));
            mRecyclerView.setPadding(DisplayUtil.dp2px(1), DisplayUtil.dp2px(1), DisplayUtil.dp2px(1), DisplayUtil.dp2px(1));
        }

        mAdapter.addItem(mTextList.get(count));

        mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());

    }

    public long getAnimationDuration() {
        return mAnimationDuration;
    }

    public FrameLayout getListContentParent() {
        return mListContentParent;
    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

        private List<Integer> mTextArray;
        private int[] mColorArray;
        private Context mContext;

        float mCarrierHeight;

        ViewGroup mLayer;

        public<VG extends ViewGroup> RecyclerViewAdapter(Context context, List<Integer> itemList
                , int[] colorArray, float height, VG layer) {
            mTextArray = new ArrayList<>(0);
            mTextArray.addAll(itemList);
            mContext = context;
            mColorArray = colorArray;
            mCarrierHeight = height;

            mLayer = layer;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView mTextView;
            FrameLayout mParent;

            public ViewHolder(View itemView) {
                super(itemView);

                mTextView = (TextView) itemView.findViewById(R.id.item_text);
                mParent = (FrameLayout) itemView.findViewById(R.id.item_parent);

                FontMaster.with(mContext).font(FontMaster.Type.Kautiva).set(mTextView);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.first_anim_phone_view_list_item_content, parent, false);
            ViewHolder holder = new ViewHolder(layoutView);
            return holder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                    , (int) (mCarrierHeight / getItemCount()));
            lp.gravity = Gravity.CENTER;

            holder.mTextView.setLayoutParams(lp);

            holder.mParent.setLayoutParams(lp);

            holder.mTextView.setText(mContext.getString(mTextArray.get(position)));

            holder.mTextView.setBackgroundColor(mContext.getResources().getColor(mColorArray[position]));

            mLayer.setBackgroundColor(mContext.getResources().getColor(mColorArray[position]));
            holder.mParent.setBackgroundColor(mContext.getResources().getColor(mColorArray[position]));
        }

        @Override
        public int getItemCount() {
            return mTextArray.size();
        }

        public void addItem(int index) {
            mTextArray.add(index);
        }
    }
}
