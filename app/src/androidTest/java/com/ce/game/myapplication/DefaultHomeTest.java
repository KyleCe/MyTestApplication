package com.ce.game.myapplication;

import android.app.Application;
import android.content.Context;
import android.test.ApplicationTestCase;

/**
 * Created by KyleCe on 2016/6/28.
 *
 * @author: KyleCe
 */
public class DefaultHomeTest extends ApplicationTestCase<Application> {

    private Context mContext;

    public DefaultHomeTest() {
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

    public void testHome() {

    }

}
