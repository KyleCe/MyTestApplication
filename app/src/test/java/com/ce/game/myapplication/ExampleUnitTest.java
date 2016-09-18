package com.ce.game.myapplication;

import com.ce.game.myapplication.view.NumberKeyboardSingleButton;

import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
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

    @Test
    public void anno() {
        @NumberKeyboardSingleButton.ButtonContent
        String tmp = "123";
//        if(NumberKeyboardSingleButton.ButtonContent.class.getFields().equals(tmp))
        assertEquals(tmp, NumberKeyboardSingleButton.ButtonContent.K3);
    }

}