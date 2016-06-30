
package com.ce.game.myapplication.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.ce.game.myapplication.R;

public class HomeActivity extends Activity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_home);

        mContext = HomeActivity.this;

        SettingHelper.fakeLauncherInstalledAndOpenChooser(mContext);
//        SettingHelper.triggerHomeSetting(mContext);
    }
}
