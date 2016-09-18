package com.ce.game.myapplication.act;

import android.app.Activity;
import android.os.Bundle;

import com.ce.game.myapplication.R;

public class AllKeyboardActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_all_keyboard);

//        NumberKeyboardForUnlock keyboardForUnlock = (NumberKeyboardForUnlock) findViewById(R.id.keyboard);
//        keyboardForUnlock.setPressCallback(new PressCallback() {
//            @Override
//            public void onPress(@NumberKeyboardSingleButton.ButtonContent String key) {
//                DU.pwa("press", key);
//            }
//        });
    }
}
