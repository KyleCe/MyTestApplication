package com.ce.game.myapplication.view.newpincode;

import com.ce.game.myapplication.view.NumberKeyboardSingleButton;
import com.ce.game.myapplication.view.pincode.KeyboardButtonView;

/**
 * Created on 2016/9/18
 * in BlaBla by Kyle
 */

public class PinCodeAdapter {
    @KeyboardButtonView.KeyType
    public static int convertToOld(@NumberKeyboardSingleButton.ButtonContent String newPinCode) {
        switch (newPinCode) {
            case NumberKeyboardSingleButton.ButtonContent.K0:
                return KeyboardButtonView.KeyType.K0;
            case NumberKeyboardSingleButton.ButtonContent.K1:
                return KeyboardButtonView.KeyType.K1;
            case NumberKeyboardSingleButton.ButtonContent.K2:
                return KeyboardButtonView.KeyType.K2;
            case NumberKeyboardSingleButton.ButtonContent.K3:
                return KeyboardButtonView.KeyType.K3;
            case NumberKeyboardSingleButton.ButtonContent.K4:
                return KeyboardButtonView.KeyType.K4;
            case NumberKeyboardSingleButton.ButtonContent.K5:
                return KeyboardButtonView.KeyType.K5;
            case NumberKeyboardSingleButton.ButtonContent.K6:
                return KeyboardButtonView.KeyType.K6;
            case NumberKeyboardSingleButton.ButtonContent.K7:
                return KeyboardButtonView.KeyType.K7;
            case NumberKeyboardSingleButton.ButtonContent.K8:
                return KeyboardButtonView.KeyType.K8;
            case NumberKeyboardSingleButton.ButtonContent.K9:
                return KeyboardButtonView.KeyType.K9;
            case NumberKeyboardSingleButton.ButtonContent.K_BACK:
                return KeyboardButtonView.KeyType.K_BACK;
            case NumberKeyboardSingleButton.ButtonContent.K_BACKSPACE:
                return KeyboardButtonView.KeyType.K_BACKSPACE;
            default:
                return KeyboardButtonView.KeyType.K_BACKSPACE;
        }
    }
}
