package com.ce.game.myapplication.view.pincode;



/**
 * Created by KyleCe on 2016/5/25.
 *
 * @author: KyleCe
 */
public interface KeyboardButtonClickedListener {

    /**
     * Receive the click of a button, just after a {@link android.view.View.OnClickListener} has fired.
     * Called before {@link #onRippleAnimationEnd()}.
     *
     * @param #keyboardButtonEnum The organized int of the clicked button
     */
    void onKeyboardClick(@KeyboardButtonView.KeyType int keyboardButtonEnum);

    /**
     */
    void onRippleAnimationEnd();

}
