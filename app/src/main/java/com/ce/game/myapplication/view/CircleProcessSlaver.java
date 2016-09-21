package com.ce.game.myapplication.view;

import android.content.Context;

import com.ce.game.myapplication.R;

import junit.framework.Assert;

/**
 * Created on 2016/9/20
 * in BlaBla by Kyle
 */

public class CircleProcessSlaver {
    protected int[] PROCESS_RANGE_FOR_COLOR_STAGE = {60, 85};
    protected int[] STAGE_COLOR;
    public static final int DEFAULT_COMPLETE_PROCESS = 100;

    protected Context mContext;

    public CircleProcessSlaver(Context context) {
        Assert.assertNotNull(context);

        mContext = context.getApplicationContext();

        init();
    }

    private void init() {
        STAGE_COLOR = new int[]{
                mContext.getResources().getColor(R.color.privacy_detect_progress_control_color_stage1),
                mContext.getResources().getColor(R.color.privacy_detect_progress_control_color_stage1_end),
                mContext.getResources().getColor(R.color.privacy_detect_progress_control_color_stage2),
                mContext.getResources().getColor(R.color.privacy_detect_progress_control_color_stage2_end),
                mContext.getResources().getColor(R.color.privacy_detect_progress_control_color_stage3),
                mContext.getResources().getColor(R.color.privacy_detect_progress_control_color_stage3_end)};
    }

    public int[] parseColorToDraw(float progress) {
        if (progress < PROCESS_RANGE_FOR_COLOR_STAGE[0]) return new int[]{STAGE_COLOR[0], STAGE_COLOR[1]};
        else if (PROCESS_RANGE_FOR_COLOR_STAGE[0] <= progress && progress <= PROCESS_RANGE_FOR_COLOR_STAGE[1])
            return new int[]{STAGE_COLOR[2], STAGE_COLOR[3]};
        else return new int[]{STAGE_COLOR[4], STAGE_COLOR[5]};
    }

    public int[] getSTAGE_COLOR() {
        return STAGE_COLOR;
    }

    public int[] getPROCESS_RANGE_FOR_COLOR_STAGE() {
        return PROCESS_RANGE_FOR_COLOR_STAGE;
    }
}
