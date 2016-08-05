package com.ce.game.myapplication.connectwithgoogle;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.userguideanim.FloatViewModel;
import com.ce.game.myapplication.userguideanim.GuideViewInterface;
import com.ce.game.myapplication.util.DU;
import com.ce.game.myapplication.util.Permissions;

public class GoogleAccountActivity extends Activity {

    private FloatViewModel mFloatViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_account);

        mFloatViewModel = new FloatViewModel(this);

        final RetrievePasswordView retrievePasswordView = new RetrievePasswordView(this);
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
//                retrievePasswordView.clearCookies();
            }

            @Override
            public void bingo() {
                clearView();
//                retrievePasswordView.clearCookies();
                DU.tsd(getApplicationContext(), "verify ok");
            }
        });

        mFloatViewModel.setView(retrievePasswordView, 0);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            ActivityCompat.requestPermissions(this, new String[]{Permissions.GET_ACCOUNTS_PERMISSION}
                    , Permissions.REQUEST_ACCOUNT_MANAGER);

            int permissionCheck = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.GET_ACCOUNTS);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {

            }
        }
    }

    private void clearView() {
        mFloatViewModel.clearView();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}
