package com.ce.game.myapplication.connectwithgoogle;

import android.app.Activity;
import android.os.Bundle;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.scrollingblurtext.userguideanim.FloatViewModel;
import com.ce.game.myapplication.scrollingblurtext.userguideanim.GuideViewInterface;
import com.ce.game.myapplication.util.DU;

public class GoogleAccountActivity extends Activity {

    private FloatViewModel mFloatViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_account);

        mFloatViewModel = new FloatViewModel(this);

        RetrievePasswordView retrievePasswordView = new RetrievePasswordView(this);
        retrievePasswordView.attachKeyEventCallback(new GuideViewInterface.KeyEventCallback() {
            @Override
            public void onBackPressed() {
                clearView();
            }
        });

        retrievePasswordView.attachVerifyCallback(new VerifyCallback() {
            @Override
            public void cancel() {
                clearView();
            }

            @Override
            public void bingo() {
                clearView();
                DU.tsd(getApplicationContext(), "verify ok");
            }
        });

        mFloatViewModel.setView(retrievePasswordView, 0);
    }

    private void clearView() {
        mFloatViewModel.clearView();
    }
}
