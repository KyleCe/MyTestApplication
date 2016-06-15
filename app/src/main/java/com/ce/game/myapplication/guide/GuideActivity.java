package com.ce.game.myapplication.guide;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.RadioGroup;

import com.ce.game.myapplication.R;

public class GuideActivity extends Activity {

    private GuideSmoothViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        setViewPagerAndIndicator();
    }

    private void setViewPagerAndIndicator() {

        GuideImageAdapter adapter = new GuideImageAdapter();
        mViewPager = (GuideSmoothViewPager) findViewById(R.id.guide_view_pager);

        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        radioGroup.check(R.id.radioButton);
                        break;
                    case 1:
                        radioGroup.check(R.id.radioButton2);
                        break;
                    case 2:
                        radioGroup.check(R.id.radioButton3);
                        break;
                    case 3:
                        radioGroup.check(R.id.radioButton4);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setAdapter(adapter);
    }
}
