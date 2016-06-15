package com.ce.game.myapplication.sideindex;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;

import com.ce.game.myapplication.R;

public class IndexActivity extends AppCompatActivity {


    AutoResizeHeightTextView mIndexTextView;

    private static final String HTML_NEW_LINE = "<br />";
    private static final String HTML_NORMAL_TEXT = "<p>";
    private static final int DEFAULT_COLOR = 0xBB33AA;
    private String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        // index
        mIndexTextView = (AutoResizeHeightTextView) findViewById(R.id.side_index_text);
//        mIndexTextView.setText(getColoredString("DH#$Y"));
    }



    /**
     * <font color="red">This is some text!</font>
     */
    @NonNull
    private Spanned getColoredString(@NonNull String colorChars) {
        return Html.fromHtml(HTML_NORMAL_TEXT + highLight(mSections, colorChars) + HTML_NORMAL_TEXT);
    }

    private String highLight(@NonNull String wholeString, @NonNull String highLight) {
        char[] charArray = highLight.toCharArray();
        char[] wholeCharArray = wholeString.toCharArray();
        StringBuilder stringBuffer = new StringBuilder();
        for (char c : wholeCharArray) {
            if (charArrayContains(charArray, c))
                stringBuffer.append(textInNewLine(Character.toString(c), true));//high light
            else stringBuffer.append(textInNewLine(Character.toString(c)));// normal
        }
        return stringBuffer.toString();
    }

    private boolean charArrayContains(char[] chars, char c) {
        for (char ch : chars)
            if (ch == c) return true;
        return false;
    }

    private String textInNewLine(@NonNull String text) {
        return HTML_NEW_LINE + text;
    }

    private String textInNewLine(@NonNull String text, boolean colorful) {
        return colorful ? textInNewLine(text, DEFAULT_COLOR, true)
                : textInNewLine(text);
    }

    private String textInNewLine(@NonNull String text, int color, boolean bold) {
        return HTML_NEW_LINE + getHtmlColorText(text, color, bold);
    }

    /**
     * css:  <p style="color:red">This is some text!</p>
     * <p>
     * <p>这是普通文本 - <b>这是粗体文本</b>。</p>
     */
    @NonNull
    private String getHtmlColorText(@NonNull String text, int color, boolean bold) {

        return (bold ? "<b>" : "")
                + "<font color= \"#" + color + "\" >" + text + "</font>"
                + (bold ? "</b>" : "");
    }


}
