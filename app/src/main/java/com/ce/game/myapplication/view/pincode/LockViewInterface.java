package com.ce.game.myapplication.view.pincode;

/**
 * Created by KyleCe on 2016/7/12.
 *
 * @author: KyleCe
 */
public class LockViewInterface {
    public interface ChangeViewState{
        void refreshBattery();
    }

    public interface Clickable{
        boolean clickable();
    }

    public interface DirectionSlidability {

        boolean leftSlidable();

        boolean rightSlidable();

        boolean upSlidable();

        boolean downSlidable();
    }

    public interface ResetPinCodeView {
        void reset(@PinCodeView.ResetType int type);
    }
}
