package com.ce.game.myapplication.connectwithgoogle;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ce.game.myapplication.util.DU;
import com.ce.game.myapplication.util.HttpUtils;

import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * browser to process web view
 * Created by KyleCe on 2015/12/23.
 *
 * @author KyleCe
 *         <a href="https://github.com/KyleCe">KyleCe@github</a>
 */
public class SignInClient extends WebViewClient {

    public final String REDIRECT_URI_KEY = "localhost";
    public final String ACCESS_TOKEN = "access_token";
    public final String ACCESS_DENIED = "denied";

    public final String GET_USER_INFO_URI_PREFIX = "https://www.googleapis.com/oauth2/v1/tokeninfo?access_token=";


    private VerifyCallback mVerifyCallback;

    private Handler mHandler;

    private List<String> mEmailList;
    private String mSignInEmail;

    public SignInClient(VerifyCallback callback, Handler handler,
                        List<String> emailList) {
        mVerifyCallback = callback;
        mHandler = handler;
        mEmailList = emailList;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        if (url.contains(REDIRECT_URI_KEY) && url.contains(ACCESS_TOKEN)) {

            String accessToken = url.substring(url.lastIndexOf(ACCESS_TOKEN) + ACCESS_TOKEN.length() + 1, url.length() - 1);

            int otherParamIndex = accessToken.indexOf("&");

            if (otherParamIndex != -1)
                accessToken = accessToken.substring(0, otherParamIndex);

            final String getUserInfoUrl = GET_USER_INFO_URI_PREFIX + accessToken;

            DU.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        String result = HttpUtils.doGet(getUserInfoUrl);

                        DU.sd("get token result:::", result);

                        boolean containEmail = false;

                        mSignInEmail = parseSignInEmail(result);

                        if (!TextUtils.isEmpty(mSignInEmail))
                            for (String email : mEmailList)
                                if (mSignInEmail.contains(email)) {
                                    DU.sd("verify ok");
                                    if (mVerifyCallback != null) mVerifyCallback.bingo();
                                    containEmail = true;
                                    break;
                                }

                        if (!containEmail) {
                            mHandler.sendEmptyMessage(RetrievePasswordView.EMAIL_MISMATCH);
                            DU.sd("error, not match");
                        }

                    } catch (Exception e) {
                        DU.sd(e);
                    }
                }
            });

            return true;
        } else if (url.contains(REDIRECT_URI_KEY) && url.contains(ACCESS_DENIED)) {
            // denied
            mHandler.sendEmptyMessage(RetrievePasswordView.GUIDE_TO_VERY_FIRST_STATE);
            return true;
        } else {
            return false; //Let the system handle it
        }
    }


    @NonNull
    private String parseSignInEmail(String result) {
        Assert.assertNotNull(result);
        String email = "empty";

        try {
            JSONObject json = new JSONObject(result);
            email = (String) json.get("email");
            return email;
        } catch (JSONException e) {
            e.printStackTrace();
            return email;
        }
    }

    public String getSignInEmail() {
        return mSignInEmail;
    }
}

