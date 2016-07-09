package com.ce.game.myapplication.ui.fonttext;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.util.DU;
import com.ce.game.myapplication.util.FontMaster;

public class FontTextActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_font_text);

        FontMaster.with(this).setAsArialFont((TextView) findViewById(R.id.time_text_view));

        FontMaster.with(this).font(FontMaster.Type.Arial).set((TextView) findViewById(R.id.time_text_view));

        ((TextView) findViewById(R.id.time_text_view)).setTypeface(Typeface.createFromAsset(
                this.getAssets(), "fonts/arial.ttf"));
    }

    @Override
    protected void onDestroy() {
        DU.sd("destroy", "on destroy");

        super.onDestroy();
    }
}
