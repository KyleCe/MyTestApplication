package com.ce.game.myapplication;

import android.app.Application;
import android.content.Context;
import android.test.ApplicationTestCase;

import com.ce.game.myapplication.pinyin.PinyinHelper;
import com.ce.game.myapplication.util.DU;

import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by KyleCe on 2016/6/28.
 *
 * @author: KyleCe
 */
public class AppSearchChineseTest extends ApplicationTestCase<Application> {

    private String TAG = AppSearchChineseTest.class.getSimpleName();

    private Locale mLocale;
    private Context mContext;
    private static final Pattern SPLIT_PATTERN = Pattern.compile("[\\s|\\p{javaSpaceChar}]+");

    public AppSearchChineseTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mContext = getContext();

        assertNotNull(mContext);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testMatch() {

        mLocale = mContext.getResources().getConfiguration().locale;

        assertNotNull(mLocale);

        DU.sd(TAG, mLocale.getCountry());

        assertTrue(mLocale.getLanguage().equals("zh"));

        String title = "智明星通";

        PinyinHelper pinyinHelper = new PinyinHelper(mContext);

        assertTrue(pinyinHelper.containsChinese(title));

        title = pinyinHelper.getShortPinyinWithSeparator(title, ' ');

        assertEquals(title, "z m x t");

        assertTrue(matches(title, new String[]{"z"}));
        assertTrue(matches(title, new String[]{"x"}));
        assertTrue(!matches(title, new String[]{"q"}));
    }

    protected boolean matches(String title, String[] queryWords) {

        String[] words = SPLIT_PATTERN.split(title.toLowerCase());

        for (int qi = 0; qi < queryWords.length; qi++) {
            boolean foundMatch = false;
            for (int i = 0; i < words.length; i++) {
                if (words[i].startsWith(queryWords[qi])) {
                    foundMatch = true;
                    break;
                }
            }
            if (!foundMatch) {
                // If there is a word in the query that does not match any words in this
                // title, so skip it.
                return false;
            }
        }
        return true;
    }
}
