package com.ce.game.myapplication.swipetochangeview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.swipetochangeview.OnSwipeListener.Direction;
import com.ce.game.myapplication.util.ViewU;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ViewStubWithAnimActivity extends AppCompatActivity {

    private GestureDetector mGestureDetector;

    private View mCenterItem;
    private View mTopItem;
    private View mBottomItem;
    private View mLeftItem;
    private View mRightItem;

    private View nextView[][] = new View[5][4];

    final EssentialAnimFactors mFirstEAF = new EssentialAnimFactors();
    final EssentialAnimFactors mSecondEAF = new EssentialAnimFactors();

    float dX, dY;
    float oldX;
    float oldY;

    private ShowingItem mShowingItem = ShowingItem.center;
    private static final int DEFAULT_ANIM_DURATION = 793;

    enum ShowingItem {
        center(0), top(1), bottom(2), left(3), right(4);

        private int index;

        ShowingItem(int i) {
            this.index = i;
        }

        public int getIndex() {
            return index;
        }

        public boolean cannotSwipe(Direction d) {
            return !canSwipe(d);
        }

        public boolean canSwipe(Direction d) {
            switch (this) {
                case center:
                    return true;
                case top:
                    return d != Direction.down;
                case bottom:
                    return d != Direction.up;
                case left:
                    return d != Direction.right;
                case right:
                    return d != Direction.left;
                default:
                    return false;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_stub_with_anim);

        mCenterItem = ((ViewStub) findViewById(R.id.viewStub)).inflate();
        mTopItem = ((ViewStub) findViewById(R.id.viewStubTop)).inflate();
        mBottomItem = ((ViewStub) findViewById(R.id.viewStubBottom)).inflate();
        mLeftItem = ((ViewStub) findViewById(R.id.viewStubLeft)).inflate();
        mRightItem = ((ViewStub) findViewById(R.id.viewStubRight)).inflate();

        ScreenModeHelper.requestImmersiveFullScreen(mCenterItem
                , mTopItem, mBottomItem, mLeftItem, mRightItem
        );

        ViewU.invisible(
                mTopItem, mBottomItem, mLeftItem, mRightItem);

        View nextView[][] = {/*up, down,left,right*/
            /*center*/ {mBottomItem, mTopItem, mRightItem, mLeftItem},
            /*top   */ {mCenterItem, mCenterItem, mRightItem, mLeftItem},
            /*bottom*/ {mCenterItem, mCenterItem, mRightItem, mLeftItem},
            /*left  */ {mBottomItem, mTopItem, mCenterItem, mCenterItem},
            /*right */ {mBottomItem, mTopItem, mCenterItem, mCenterItem},
        };

        this.nextView = nextView;

        oldX = mCenterItem.getX();
        oldY = mCenterItem.getY();

        SwipeWithAnimListener swipeWithAnimListener = new SwipeWithAnimListener(new SwipeWithAnimListener.DirectionOperator() {
            @Override
            public void up() {
                animationDelegate(mShowingItem, Direction.up);
            }

            @Override
            public void down() {
                animationDelegate(mShowingItem, Direction.down);
            }

            @Override
            public void left() {
                animationDelegate(mShowingItem, Direction.left);
            }

            @Override
            public void right() {
                animationDelegate(mShowingItem, Direction.right);
            }
        });

        mGestureDetector = new GestureDetector(this, swipeWithAnimListener);
    }

    private class EssentialAnimFactors {
        View view;
        String propertyName;
        float fromValue;
        float toValue;

        public EssentialAnimFactors() {
        }

        public void setParams(View view, String propertyName, float fromValue, float toValue) {
            this.view = view;
            this.propertyName = propertyName;
            this.fromValue = fromValue;
            this.toValue = toValue;
        }
    }

    private void animationDelegate(final ShowingItem item, Direction d) {
        if (item.cannotSwipe(d)) return;


        View firstView = pairView(item);
        final View secondView = nextView[item.getIndex()][d.getIndex()];

        if (firstView == null || secondView == null) return;

        switch (d) {
            case up:
                mFirstEAF.setParams(firstView, "translationY", firstView.getY(), -firstView.getHeight());
                mSecondEAF.setParams(secondView, "translationY", secondView.getHeight(), 0);
                break;
            case down:
                mFirstEAF.setParams(firstView, "translationY", firstView.getY(), firstView.getHeight());
                mSecondEAF.setParams(secondView, "translationY", -secondView.getHeight(), 0);
                break;
            case left:
                mFirstEAF.setParams(firstView, "translationX", firstView.getX(), -firstView.getWidth());
                mSecondEAF.setParams(secondView, "translationX", secondView.getWidth(), 0);
                break;
            case right:
                mFirstEAF.setParams(firstView, "translationX", firstView.getX(), firstView.getWidth());
                mSecondEAF.setParams(secondView, "translationX", -secondView.getWidth(), 0);
                break;
            default:
                break;
        }

        mShowingItem = pairItem(mSecondEAF.view);

        ObjectAnimator animator = ObjectAnimator.ofFloat(mFirstEAF.view, mFirstEAF.propertyName,
                mFirstEAF.fromValue, mFirstEAF.toValue).setDuration(DEFAULT_ANIM_DURATION);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                cancelAnimation(mSecondEAF.view);

                ObjectAnimator anim = ObjectAnimator.ofFloat(mSecondEAF.view, mSecondEAF.propertyName,
                        mSecondEAF.fromValue, mSecondEAF.toValue).setDuration(DEFAULT_ANIM_DURATION);

                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        ViewU.show(mSecondEAF.view);
                    }
                });
                anim.start();
                ViewU.show(mFirstEAF.view, mSecondEAF.view);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!pairView(mShowingItem).equals(mFirstEAF.view))
                    ViewU.invisible(mFirstEAF.view);

                // reverse the view position
                ObjectAnimator.ofFloat(mFirstEAF.view, mFirstEAF.propertyName,
                        mFirstEAF.toValue, mFirstEAF.fromValue).setDuration(1).start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animator.start();
    }

    private void cancelAnimation(View view) {
        if (view.getAnimation() == null) return;
        view.getAnimation().cancel();
        view.getAnimation().setAnimationListener(null);
        view.clearAnimation();
        view.setAnimation(null);
    }

    public void stopAnimation() {
        View[] views = {mCenterItem, mTopItem, mBottomItem, mLeftItem, mRightItem};
        for (View v : views) {
            if (v != null)
                cancelAnimation(v);
        }
    }

    private View pairView(ShowingItem item) {
        switch (item) {
            case center:
                return mCenterItem;
            case top:
                return mTopItem;
            case bottom:
                return mBottomItem;
            case left:
                return mLeftItem;
            case right:
                return mRightItem;
            default:
                return mCenterItem;
        }
    }

    private ShowingItem pairItem(View view) {
        if (view.equals(mCenterItem))
            return ShowingItem.center;
        if (view.equals(mTopItem))
            return ShowingItem.top;
        if (view.equals(mBottomItem))
            return ShowingItem.bottom;
        if (view.equals(mLeftItem))
            return ShowingItem.left;
        if (view.equals(mRightItem))
            return ShowingItem.right;

        return ShowingItem.center;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        View view = pairView(mShowingItem);

        strategyAnim(event, view);

        return mGestureDetector.onTouchEvent(event);
    }

    private static final int UNSET = 584;
    private static final int SWIPE_X = 456;
    private static final int SWIPE_Y = 416;

    private void strategyAnim(MotionEvent event, View view) {
        float startX = 0f;
        float startY = 0f;

        int mSwipeType = UNSET;// 0 for x; 1 for y

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                dX = view.getX() - event.getRawX();
                dY = view.getY() - event.getRawY();

                startX = event.getRawX();
                startY = event.getRawY();
                mSwipeType = UNSET;

                break;

            case MotionEvent.ACTION_MOVE:
                if (mSwipeType == UNSET) {
                    float alterX = event.getRawX() - startX;
                    float alterY = event.getRawY() - startY;

                    mSwipeType = Math.abs(alterX) >= Math.abs(alterY)
                            ? SWIPE_X : SWIPE_Y;
                }


                if (mSwipeType == SWIPE_X)
                    view.animate()
                            .x(event.getRawX() + dX)
                            .setDuration(0)
                            .start();
                else if (mSwipeType == SWIPE_Y)
                    view.animate()
                            .y(event.getRawY() + dY)
                            .setDuration(0)
                            .start();
                break;
            case MotionEvent.ACTION_UP:
                view.animate()
                        .x(oldX)
                        .y(oldY)
                        .setDuration(500)
                        .start();
                break;
        }
    }
}
