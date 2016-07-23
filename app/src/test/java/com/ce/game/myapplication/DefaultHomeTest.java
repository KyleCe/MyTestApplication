package com.ce.game.myapplication;


import junit.framework.Assert;
import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

/**
 * Created by KyleCe on 2016/6/28.
 *
 * @author: KyleCe
 */
public class DefaultHomeTest extends TestCase {

    public DefaultHomeTest() {
    }

    @Test
    public void email() throws JSONException {

        String result = "{" +
                "  \"issued_to\": \"294554247866-47jje3ufmkkvtlhkioqtcf43vi7c51to.apps.googleusercontent.com\"," +
                "  \"audience\": \"294554247866-47jje3ufmkkvtlhkioqtcf43vi7c51to.apps.googleusercontent.com\"," +
                "  \"user_id\": \"110139751856206626834\"," +
                "  \"scope\": \"https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile\"," +
                "  \"expires_in\": 3572," +
                "  \"email\": \"chengong90@gmail.com\"," +
                "  \"verified_email\": true," +
                "  \"access_type\": \"offline\"" +
                "}";

        JSONObject json = new JSONObject(result);
        String test = (String) json.get("email");

        Assert.assertEquals(test, "chengong90@gmail.com");
    }

}
