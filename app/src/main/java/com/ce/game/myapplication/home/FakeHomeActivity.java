
package com.ce.game.myapplication.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.ce.game.myapplication.R;

public class FakeHomeActivity extends Activity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_home);

        mContext = FakeHomeActivity.this;

        SettingHelper.clearDefaultHome(mContext);
        SettingHelper.fakeLauncherInstalledAndOpenChooser(mContext);
//        SettingHelper.triggerHomeSetting(mContext);
    }
}
