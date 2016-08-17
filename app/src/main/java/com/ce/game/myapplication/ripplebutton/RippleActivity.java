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

        ((RippleBackground) findViewById(R.id.ripple_button)).startRippleAnimationInfinitely();
        ((RippleBackground) findViewById(R.id.ripple_button_two)).startRippleAnimationInfinitely();
        ((RippleBackground) findViewById(R.id.ripple_button_three)).startRippleAnimationInfinitely();
        ((RippleBackground) findViewById(R.id.ripple_button_three_and_half)).startRippleAnimationInfinitely();
        ((RippleBackground) findViewById(R.id.ripple_button_four)).startRippleAnimationInfinitely();

        ((RippleBackground) findViewById(R.id.little_ripple_left)).startRippleAnimationInfinitely();
        ((RippleBackground) findViewById(R.id.little_ripple_right)).startRippleAnimationInfinitely();
        ((RippleBackground) findViewById(R.id.little_ripple_left_two)).startRippleAnimationInfinitely();
        ((RippleBackground) findViewById(R.id.little_ripple_right_two)).startRippleAnimationInfinitely();
        ((RippleBackground) findViewById(R.id.little_ripple_left_three)).startRippleAnimationInfinitely();
        ((RippleBackground) findViewById(R.id.little_ripple_right_three)).startRippleAnimationInfinitely();
        ((RippleBackground) findViewById(R.id.little_ripple_left_four)).startRippleAnimationInfinitely();
        ((RippleBackground) findViewById(R.id.little_ripple_right_four)).startRippleAnimationInfinitely();
        ((RippleBackground) findViewById(R.id.little_ripple_left_five)).startRippleAnimationInfinitely();
        ((RippleBackground) findViewById(R.id.little_ripple_right_five)).startRippleAnimationInfinitely();
    }
}
