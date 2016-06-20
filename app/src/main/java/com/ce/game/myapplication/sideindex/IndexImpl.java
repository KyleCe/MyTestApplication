package com.ce.game.myapplication.sideindex;

import android.support.annotation.NonNull;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.TextView;

/**
 * Created by KyleCe on 2016/6/20.
 *
 * @author: KyleCe
 */
public class IndexImpl {
    public static final String INDEX_TABLE = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final String HTML_NEW_LINE = "<br />";
    private static final String HTML_NORMAL_TEXT = "<p>";
    private static final String DEFAULT_COLOR = "555851";
    private static final String HIGH_LIGHT_COLOR = "ffffff";

    public static void highLight(TextView tv, String colored) {
        if (tv == null) return;
        tv.setText(getColoredHtmlString(colored));
    }

    /**
     * <font color="red">This is some text!</font>
     */
    @NonNull
    public static Spanned getColoredHtmlString(String colorChars) {
        if (TextUtils.isEmpty(colorChars)) colorChars = " ";
        return Html.fromHtml(HTML_NORMAL_TEXT + highLightHtmlString(INDEX_TABLE, colorChars) + HTML_NORMAL_TEXT);
    }

    private static String highLightHtmlString(@NonNull String wholeString, @NonNull String highLight) {
        char[] highLightArray = highLight.toCharArray();
        char[] wholeCharArray = wholeString.toCharArray();
        StringBuilder stringBuffer = new StringBuilder();
        for (char c : wholeCharArray) {
            if (charArrayContains(highLightArray, c))
                stringBuffer.append(textNormalInNewLine(Character.toString(c), HIGH_LIGHT_COLOR, false));//high light
            else
                stringBuffer.append(textNormalInNewLine(Character.toString(c), DEFAULT_COLOR, false));// normal
        }
        return stringBuffer.toString();
    }

    private static boolean charArrayContains(char[] chars, char c) {
        for (char ch : chars)
            if (ch == c) return true;
        return false;
    }

    private static String textNormalInNewLine(@NonNull String text, String color, boolean bold) {
        return HTML_NEW_LINE + getHtmlColorText(text, color, bold);
    }

    /**
     * css:  <p style="color:red">This is some text!</p>
     * <p>
     * <p>这是普通文本 - <b>这是粗体文本</b>。</p>
     */
    @NonNull
    private static String getHtmlColorText(@NonNull String text, String color, boolean bold) {

        String htmlColorText = String.format("<font color = \"#%s\">%s</font>", color, text);
        if (!bold) return htmlColorText;
        else return String.format("<b>%s</b>", htmlColorText);
    }

}
