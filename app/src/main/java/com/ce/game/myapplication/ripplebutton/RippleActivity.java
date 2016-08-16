package com.ce.game.myapplication.ripplebutton;

import android.app.Activity;
import android.os.Bundle;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.showcase.RippleBackground;

public class RippleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ripple);

        RippleBackground background = (RippleBackground) findViewById(R.id.ripple_button);
        background.startRippleAnimationInfinitely();

        RippleBackground littleBackground = (RippleBackground) findViewById(R.id.little_ripple);
        littleBackground.startRippleAnimationInfinitely();
    }
}
