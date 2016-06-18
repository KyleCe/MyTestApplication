package com.ce.game.myapplication.checkbox;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.util.DU;

public class CheckboxActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkbox);

        final View cover = findViewById(R.id.cover);
        cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DU.sd("cover", "cover");
            }
        });
        final View layout = findViewById(R.id.checkbox);

        findViewById(R.id.parent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DU.sd("parent", "click parent");

                cover.setClickable(!cover.isClickable());
            }
        });

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DU.sd("child", "click child");

            }
        });


    }
}
