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

public class SubtleAdjustActivity extends Activity {

    @BindView(R.id.text_for_multi_link)
    protected TextView mLinkText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtle_adjust);

        initView();
    }

    private void initView() {
        ButterKnife.bind(this);

        customTextView(mLinkText);

//        mLinkText.setText(Html.fromHtml("<a href=\"http://www.google.com\">Google</a>"));
//        mLinkText.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void customTextView(TextView view) {
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
        spanTxt.append(" and");
        spanTxt.setSpan(new ForegroundColorSpan(Color.BLACK), 32, spanTxt.length(), 0);
        spanTxt.append(" Privacy Policy");
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(getApplicationContext(), "Privacy Policy Clicked",
                        Toast.LENGTH_SHORT).show();
            }
        }, spanTxt.length() - " Privacy Policy".length(), spanTxt.length(), 0);
        view.setMovementMethod(LinkMovementMethod.getInstance());
        view.setText(spanTxt, TextView.BufferType.SPANNABLE);
    }
}
