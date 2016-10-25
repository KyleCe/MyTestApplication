package com.ce.game.myapplication.act;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ce.game.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OneLineActivity extends Activity {

    @BindView(R.id.one_line_text)
    protected TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_line);
        ButterKnife.bind(this);


        spanText();
    }

    private void spanText() {
        SpannableStringBuilder spanTxt = new SpannableStringBuilder(
                "I agree to the prefix adding to expand to two lines");
        spanTxt.append("Term of services");
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(getApplicationContext(), "Terms of services Clicked",
                        Toast.LENGTH_SHORT).show();
            }
        }, spanTxt.length() - "Term of services".length(), spanTxt.length(), 0);
        spanTxt.append(" and\n\n");
        spanTxt.setSpan(new ForegroundColorSpan(Color.BLACK), 32, spanTxt.length(), 0);
        spanTxt.append(" Privacy Policy");
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(getApplicationContext(), "Privacy Policy Clicked",
                        Toast.LENGTH_SHORT).show();
            }
        }, spanTxt.length() - " Privacy Policy".length(), spanTxt.length(), 0);
        mTextView.setMovementMethod(LinkMovementMethod.getInstance());
        mTextView.setText(spanTxt, TextView.BufferType.SPANNABLE);
    }
}
