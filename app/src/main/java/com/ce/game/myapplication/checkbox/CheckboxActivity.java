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

        findViewById(R.id.parent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DU.t(CheckboxActivity.this, "click parent");
            }
        });
    }
}
