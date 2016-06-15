package com.ce.game.myapplication.scrollingblurtext;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.scrollingblurtext.userguideanim.AssistGuideView;
import com.ce.game.myapplication.scrollingblurtext.userguideanim.FloatViewModel;
import com.ce.game.myapplication.util.DU;

public class RollingAnimActivity extends AppCompatActivity {

    static ListView listView;
    final int SCROLLING_DURATION = 3000;
    private static final int HAND_REPEAT_TIMES = 3;

    // len = 10
    String[] appNames = {
            "Attention Fuel", "King Locker Ann", "Judging machine", "King of fighter", "Tencent wechat", "Sina Weibo", "AZ Recorder", "Smooth Shooter", "Smart Cleaner Pro", "Zombie park",
            "Attention Fuel", "King Locker Ann", "Judging machine", "King of fighter", "Tencent wechat", "Sina Weibo", "AZ Recorder", "Smooth Shooter", "Smart Cleaner Pro", "Zombie park",
            "Attention Fuel", "King Locker Ann", "Judging machine", "King of fighter", "Tencent wechat", "Sina Weibo", "AZ Recorder", "Smooth Shooter", "Smart Cleaner Pro", "Zombie park",
            "Attention Fuel", "King Locker Ann", "Judging machine", "King of fighter", "Tencent wechat", "Sina Weibo", "AZ Recorder", "Smooth Shooter", "Smart Cleaner Pro", "Zombie park",
            "Attention Fuel", "King Locker Ann", "Judging machine", "King of fighter", "Tencent wechat", "Sina Weibo", "AZ Recorder", "Smooth Shooter", "Smart Cleaner Pro", "Zombie park",
            "Attention Fuel", "King Locker Ann", "Judging machine", "King of fighter", "Tencent wechat", "Sina Weibo", "AZ Recorder", "Smooth Shooter", "Smart Cleaner Pro", "Zombie park",
            "Attention Fuel", "King Locker Ann", "Judging machine", "King of fighter", "Tencent wechat", "Sina Weibo", "AZ Recorder", "Smooth Shooter", "Smart Cleaner Pro", "Zombie park",
            "Attention Fuel", "King Locker Ann", "Judging machine", "King of fighter", "Tencent wechat", "Sina Weibo", "AZ Recorder", "Smooth Shooter", "Smart Cleaner Pro", "Zombie park",
            "Attention Fuel", "King Locker Ann", "Judging machine", "King of fighter", "Tencent wechat", "Sina Weibo", "AZ Recorder", "Smooth Shooter", "Smart Cleaner Pro", "Zombie park",
            "Attention Fuel", "King Locker Ann", "Judging machine", "King of fighter", "Tencent wechat", "Sina Weibo", "AZ Recorder", "Smooth Shooter", "Smart Cleaner Pro", "Zombie park",
            "Attention Fuel", "King Locker Ann", "Judging machine", "King of fighter", "Tencent wechat", "Sina Weibo", "AZ Recorder", "Smooth Shooter", "Smart Cleaner Pro", "Zombie park",
            "Attention Fuel", "King Locker Ann", "Judging machine", "King of fighter", "Tencent wechat", "Sina Weibo", "AZ Recorder", "Smooth Shooter", "Smart Cleaner Pro", "Zombie park",
            "Attention Fuel", "King Locker Ann", "Judging machine", "King of fighter", "Tencent wechat", "Sina Weibo", "AZ Recorder", "Smooth Shooter", "Smart Cleaner Pro", "Zombie park",
            "Attention Fuel", "King Locker Ann", "Judging machine", "King of fighter", "Tencent wechat", "Sina Weibo", "AZ Recorder", "Smooth Shooter", "Smart Cleaner Pro", "Zombie park",
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rolling_anim);
        Context context = RollingAnimActivity.this;

        listView = (ListView) findViewById(R.id.list_view);

        MyCustomAdapter customAdapter = new MyCustomAdapter(context);
        customAdapter.addData(appNames);

        listView.setAdapter(customAdapter);

        listView.post(new Runnable() {
            @Override
            public void run() {
            }
        });


        final FloatViewModel mAssistGuideModel = new FloatViewModel(this);

        AssistGuideView guideView = new AssistGuideView(RollingAnimActivity.this);
        guideView.attachCallBack(new AssistGuideView.GuideCallback() {
            @Override
            public void onTouch() {
            }

            @Override
            public void onButtonClick() {
                if (null == mAssistGuideModel) return;
                mAssistGuideModel.clearView();
            }
        });
//        guideView.displaySecondSceneOnly();
        guideView.startAnimation();

        mAssistGuideModel.setView(guideView, 0);
    }


    private void scroll(int DURATION) {
        listView.smoothScrollBy(0, 0); // Stops the listview from overshooting.
        listView.setSelection(0);

        listView.smoothScrollBy(listView.getChildAt(0).getHeight() * appNames.length,
                DURATION);
        listView.postDelayed(new Runnable() {
            public void run() {
                DU.t(RollingAnimActivity.this, "done");
//                mVerticalHand.clearAnimation();
            }
        }, DURATION);
    }
}
