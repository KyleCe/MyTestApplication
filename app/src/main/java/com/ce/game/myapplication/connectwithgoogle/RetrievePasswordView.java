package com.ce.game.myapplication.connectwithgoogle;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStub;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.scrollingblurtext.userguideanim.GuideViewInterface;
import com.ce.game.myapplication.util.DU;
import com.ce.game.myapplication.util.ViewU;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KyleCe on 2016/7/20.
 *
 * @author: KyleCe
 */
public class RetrievePasswordView extends FrameLayout {
    private Context mContext;

    private WebView mWebView;

    private GuideViewInterface.KeyEventCallback mKeyEventCallback;

    private VerifyCallback mVerifyCallback;

    public static final int GUIDE_TO_VERY_FIRST_STATE = 621;
    public static final int EMAIL_MISMATCH = 123;
    public static final int CLEAR_WEB_VIEW_AND_OPEN_IT = 124;

    private ViewStub mMismatchStub;
    private ViewStub mNoAvailableAccountStub;
    private View mMismatchCard;
    private SignInClient mSignInClient;

    private String mHintEmail;
    private List<String> mEmailList;

    private final int POSSIBLE_EMAIL_NUM = 3;

    public RetrievePasswordView(Context context) {
        this(context, null);
    }

    public RetrievePasswordView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RetrievePasswordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context.getApplicationContext();

        init(context);
    }

    public void attachKeyEventCallback(final GuideViewInterface.KeyEventCallback keyEventCallback) {
        mKeyEventCallback = keyEventCallback;
    }

    public void attachVerifyCallback(final VerifyCallback verifyCallback) {
        mVerifyCallback = verifyCallback;
    }

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GUIDE_TO_VERY_FIRST_STATE:
                    goBackToVeryFirstPage();
                    break;
                case CLEAR_WEB_VIEW_AND_OPEN_IT:
                    clearWebViewCacheAndOpenIt();
                    break;

                case EMAIL_MISMATCH:
                    clearCacheDirectToSignIn();

                    goBackToVeryFirstPage();

                    mMismatchCard = mMismatchStub.inflate();

                    ((TextView) mMismatchCard.findViewById(R.id.rpd_title)).setText(mContext.getString(R.string.retrieve_password_title));

                    ((TextView) mMismatchCard.findViewById(R.id.rpd_hint)).setText(mSignInClient.getSignInEmail() + " " +
                            mContext.getString(R.string.retrieve_password_mismatch_hint) + " " + mEmailList.get(0));

                    mMismatchCard.findViewById(R.id.rpd_confirm).setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mHandler.sendEmptyMessage(CLEAR_WEB_VIEW_AND_OPEN_IT);
                            ViewU.hide(mMismatchCard);
                        }
                    });

                    mMismatchCard.findViewById(R.id.rpd_cancel).setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ViewU.hide(mMismatchStub);
                            mVerifyCallback.cancel();
                        }
                    });

                    ViewU.show(mMismatchCard);
                    break;
                default:
                    break;
            }
        }
    };

    private void init(final Context context) {
        final View view = inflate(context, R.layout.retrieve_password_view, this);

        mMismatchStub = (ViewStub) view.findViewById(R.id.mismatch_layout);
        mNoAvailableAccountStub = (ViewStub) view.findViewById(R.id.no_account_available_layout);

        view.findViewById(R.id.retrieve_password_title).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                backPressed();
            }
        });

        mWebView = (WebView) view.findViewById(R.id.login_web_view);

        view.findViewById(R.id.to_login).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginClick();
            }
        });

        mEmailList = getUserGoogleAccountEmail(mContext);

        if (mEmailList.size() > 0) mHintEmail = mEmailList.get(0);
        else {
            view.findViewById(R.id.to_login).setClickable(false);
            mNoAvailableAccountStub.inflate();

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mVerifyCallback.cancel();
                }
            }, 1300);
        }
    }

    private void onLoginClick() {
        DU.sd("on click");

        clearWebViewHistoryAndCache();

        mWebView.getSettings().setJavaScriptEnabled(true);

        mSignInClient = new SignInClient(mVerifyCallback, mHandler, mEmailList);

        mWebView.setWebViewClient(mSignInClient);

        clearWebViewCacheAndOpenIt();
    }


    private void clearWebViewCacheAndOpenIt() {
        clearCacheDirectToSignIn();

        ViewU.show(mWebView);
    }

    private void clearCacheDirectToSignIn() {
        clearWebViewHistoryAndCache();

        directWebViewToSignInPage();
    }

    private void directWebViewToSignInPage() {
        StringBuilder url = new StringBuilder();

        url.append(Const.OAuth2ClientCredentials.URL_START)
                .append("redirect_uri=").append(Const.OAuth2ClientCredentials.REDIRECT_URI)
                .append("&response_type=token+code")
                .append("&client_id=").append(Const.OAuth2ClientCredentials.CLIENT_ID)
                .append("&scope=").append(Const.OAuth2ClientCredentials.SCOPE)
                .append("&approval_prompt=force")
                .append("&access_type=offline");

        if (!TextUtils.isEmpty(mHintEmail))
            url.append("&login_hint").append("=" + mHintEmail);

        mWebView.loadUrl(url.toString());
    }


    private void goBackToVeryFirstPage() {
        clearWebViewHistoryAndCache();

        ViewU.hide(mWebView);
    }

    private void clearWebViewHistoryAndCache() {
        mWebView.clearHistory();
        mWebView.clearCache(true);
    }

    protected List<String> getUserGoogleAccountEmail(Context context) {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            int permissionCheck = ContextCompat.checkSelfPermission(context,
                    Manifest.permission.GET_ACCOUNTS);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {

            }
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED)
                return new ArrayList<>(0);
        }


        AccountManager manager = AccountManager.get(context);
        manager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        Account[] allAccounts = manager.getAccounts();

        Account[] accounts = manager.getAccountsByType("com.google");

        List<String> allEmail = new ArrayList<>(5);
        for (Account account : allAccounts) {
            DU.sd("accounts:::", account);
            allEmail.add(account.name);
        }

        List<String> possibleEmails = new ArrayList<>(POSSIBLE_EMAIL_NUM);

        for (String email : possibleEmails)
            DU.sd("emails:::" + email);

        // account.name as an email address only for certain account.type values.
        for (Account account : accounts)
            possibleEmails.add(account.name);

        return possibleEmails;
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) backPressed();

        return super.dispatchKeyEvent(event);
    }

    public void backPressed() {
        if (mWebView != null) {
            if (ViewU.isVisible(mWebView))
                if (mWebView.canGoBack()) mWebView.goBack();
                else mHandler.sendEmptyMessage(GUIDE_TO_VERY_FIRST_STATE);
            else if (mVerifyCallback != null) mVerifyCallback.cancel();

        } else if (mKeyEventCallback != null) mKeyEventCallback.onBackPressed();

    }

    public void onWindowFocusChanged(boolean hasFocus)
    {
        try
        {
            if(!hasFocus)
            {
                Object service  = mContext.getApplicationContext().getSystemService("statusbar");
                Class<?> statusbarManager = Class.forName("android.app.StatusBarManager");
                Method collapse = statusbarManager.getMethod("collapse");
                collapse .setAccessible(true);
                collapse .invoke(service);
            }
        }
        catch(Exception ex)
        {
        }
    }
}
