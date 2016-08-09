package com.ce.game.myapplication.userguideanim;

import android.view.View;

/**
 * Created by KyleCe on 2016/7/15.
 *
 * @author: KyleCe
 */
public class GuideViewInterface {
    public interface StartSeriesAnimation {
        void onStartSeries();

        void onStartOnlySecond();
    }

    public interface GuideCallback {
        void onButtonClick();
    }

    public interface ReplayCallback {
        void onReplay();
    }

    public interface KeyEventCallback {
        void onBackPressed();
    }

    public interface MinimumToRightEndCallback {
        void onAnimationEnd();
    }

    public interface MinimumToRight {
        void onMinimumToRight();
    }

    public interface SwitchLayoutParams {
        void setView(View v, int flag, @FloatViewModelTip.WidthHeight int widthHeight);
    }
}
