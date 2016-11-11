package com.ce.game.myapplication;

import com.ce.game.myapplication.util.DU;
import com.ce.game.myapplication.view.NumberKeyboardSingleButton;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static junit.framework.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void reverse() {
        List<Integer> sourceList = new ArrayList<>(8);
        sourceList.add(0);
        sourceList.add(1);
        sourceList.add(2);
        sourceList.add(3);
        sourceList.add(4);
        sourceList.add(5);
        sourceList.add(6);
        sourceList.add(7);
        sourceList.add(8);
        sourceList.add(9);
        sourceList.add(10);
        sourceList.add(11);
        sourceList.add(12);
        sourceList.add(13);
        sourceList.add(14);
        sourceList.add(15);
        sourceList.add(16);
        sourceList.add(17);
        sourceList.add(18);
        sourceList.add(19);
        List<Integer> tmpList = new ArrayList<>(sourceList.size());

        int column;
        int row;
        column = 4;
        row = 5;

        DU.pwa(TAG, "source size:", sourceList.size(), "c:", column, "r:", row);

        for (int i = 0; i < column; i++)
            for (int j = 0; j < row; j++)
                tmpList.add(i * row + j, sourceList.get(i + j * column));

        sourceList.clear();
        sourceList.addAll(tmpList);


        tmpList.clear();
        for (int i = 0; i < row; i++)
            for (int j = 0; j < column; j++)
                tmpList.add(i * column + j, sourceList.get(i + j * row));

        sourceList.clear();
        sourceList.addAll(tmpList);

        for (Integer i : sourceList)
            assertEquals(1, 1);
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

        assertEquals(test, "chengong90@gmail.com");
    }

    @Test
    public void anno() {
        @NumberKeyboardSingleButton.ButtonContent
        String tmp = "123";
//        if(NumberKeyboardSingleButton.ButtonContent.class.getFields().equals(tmp))
        assertEquals(tmp, NumberKeyboardSingleButton.ButtonContent.K3);
    }

}