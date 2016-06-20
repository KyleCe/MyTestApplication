package com.ce.game.myapplication.sideindex;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ce.game.myapplication.R;

public class IndexActivity extends AppCompatActivity {


    AutoResizeHeightTextView mIndexTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        // index
        mIndexTextView = (AutoResizeHeightTextView) findViewById(R.id.side_index_text);
        IndexImpl.highLight(mIndexTextView,"DH#$Y");
    }




}
