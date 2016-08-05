package com.ce.game.myapplication.act;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.util.PermissionHelper;
import com.ce.game.myapplication.util.Utilities;

public class PermissionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissoin);

        requestAccountManagerPermissionForRetrievingPasswordIfNecessary();
    }

    private void requestAccountManagerPermissionForRetrievingPasswordIfNecessary() {
        if (hasToRequestGetAccountsPermission())
            ActivityCompat.requestPermissions(this
                    , new String[]{PermissionHelper.GET_ACCOUNTS_PERMISSION}
                    , PermissionHelper.REQUEST_ACCOUNT_MANAGER);
    }

    private boolean hasToRequestGetAccountsPermission() {
        return Utilities.ATLEAST_MARSHMALLOW && PermissionHelper.noGetAccountsPermission(this);
    }

}
