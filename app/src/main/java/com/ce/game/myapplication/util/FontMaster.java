package com.ce.game.myapplication.util;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.IntDef;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * desc: use the fluent interface style
 * Created by KyleCe on 2015/10/22.
 */
public class FontMaster {

    public static FontMaster instance;
    private Context mContext;
    private static Typeface sCustomFont;
    private static Typeface sKautivaFont;
    private static Typeface mArialFont;
    private static Typeface mRobotoFont;

    @IntDef({FontType.Default, FontType.Kautiva, FontType.Arial, FontType.Roboto})
    public @interface FontType {
         int Default = 0;
         int Kautiva = 1;
         int Arial = 2;
         int Roboto = 3;
    }

    public FontMaster(Context context) {
        mContext = context;
    }

    private synchronized static FontMaster getInstance(Context context) {
        if (instance == null)
            synchronized (new Object()) {
                if (instance == null) {
                    instance = new FontMaster(context);
                    sKautivaFont = Typeface.createFromAsset(instance.mContext.getAssets(), "fonts/kautiva.ttf");
                    mArialFont = Typeface.createFromAsset(instance.mContext.getAssets(), "fonts/arial.ttf");
                    mRobotoFont = Typeface.createFromAsset(instance.mContext.getAssets(), "fonts/roboto.ttf");
                    sCustomFont = sKautivaFont;
                }
            }
        return instance;
    }


    /**
     * desc: animate the glide api:  Glide.with(context)...
     */
    public static FontMaster with(Context context) {
        return getInstance(context);
    }

    /**
     * desc: set the font, not concerning the aim components
     */
    public FontMaster set(Object... objects) {
        for (Object object : objects) {
            if (object == null) continue;
            if (object.getClass() == TextView.class) {
                ((TextView) object).setTypeface(sCustomFont);
            } else if (object.getClass() == EditText.class) {
                ((EditText) object).setTypeface(sCustomFont);
            } else if (object.getClass() == Button.class) {
                ((Button) object).setTypeface(sCustomFont);
            } else if (object.getClass() == RadioButton.class) {
                ((RadioButton) object).setTypeface(sCustomFont);
            }
        }
        return this;
    }


    public static void setFont(TextView... tvs) {
        for (TextView tv : tvs) if (tv != null) tv.setTypeface(sCustomFont);
    }

    public static void setAsArialFont(TextView... tvs) {
        for (TextView tv : tvs) if (tv != null) tv.setTypeface(mArialFont);
    }

    public static void setAsRobotoFont(TextView... tvs) {
        for (TextView tv : tvs) if (tv != null) tv.setTypeface(mRobotoFont);
    }

    public static void setFont(EditText... ets) {
        for (EditText et : ets) if (et != null) et.setTypeface(sCustomFont);
    }

    public static void setFont(Button... btns) {
        for (Button btn : btns) if (btn != null) btn.setTypeface(sCustomFont);
    }

    public static void setFont(RadioButton... rbs) {
        for (RadioButton rb : rbs) if (rb != null) rb.setTypeface(sCustomFont);
    }

    public static Typeface getCustomFont() {
        return sCustomFont;
    }

    public static void setCustomFont(Typeface customFont) {
        FontMaster.sCustomFont = customFont;
    }


}
