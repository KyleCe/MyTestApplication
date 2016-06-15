package com.ce.game.myapplication.swipetochangeview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.util.ViewU;
import com.ce.game.myapplication.swipetochangeview.OnSwipeListener.Direction;

import junit.framework.Assert;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ViewStubWithAnimActivityBackup extends AppCompatActivity {

    TextView content;
    GestureDetector mGestureDetector;

    View centerItem;
    View topItem;
    View bottomItem;
    View leftItem;
    View rightItem;

    private View nextView[][] = new View[5][4];
//    private View XViewList[] = new View[3];
//    private View YViewList[] = new View[3];

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

    ShowingItem mShowingItem = ShowingItem.center;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_stub_with_anim);

        content = (TextView) findViewById(R.id.fullscreen_content);
//        content.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                ObjectAnimator.ofFloat(content, "translationX",
////                        0, -content.getWidth()).setDuration(1000).start();
////                AnimatorU.showFromTopToCenter(content);
////                AnimatorU.hideFromCenterToBottom(content);
//            }
//        });

        centerItem = ((ViewStub) findViewById(R.id.viewStub)).inflate();
        topItem = ((ViewStub) findViewById(R.id.viewStubTop)).inflate();
        bottomItem = ((ViewStub) findViewById(R.id.viewStubBottom)).inflate();
        leftItem = ((ViewStub) findViewById(R.id.viewStubLeft)).inflate();
        rightItem = ((ViewStub) findViewById(R.id.viewStubRight)).inflate();
        ViewU.invisible(topItem, bottomItem, leftItem, rightItem);


        View nextView[][] = {/*up, down,left,right*/
            /*center*/ {bottomItem, topItem, rightItem, leftItem},
            /*top   */ {centerItem, centerItem, rightItem, leftItem},
            /*bottom*/ {centerItem, centerItem, rightItem, leftItem},
            /*left  */ {bottomItem, topItem, centerItem, centerItem},
            /*right */ {bottomItem, topItem, centerItem, centerItem},
        };

        this.nextView = nextView;

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

        public EssentialAnimFactors(View v, String property, float from, float to) {
            this.view = v;
            propertyName = property;
            fromValue = from;
            toValue = to;
        }

        public void setView(View view) {
            this.view = view;
        }

        public void setPropertyName(String propertyName) {
            this.propertyName = propertyName;
        }

        public void setFromValue(float fromValue) {
            this.fromValue = fromValue;
        }

        public void setToValue(float toValue) {
            this.toValue = toValue;
        }

    }

    private synchronized void animationDelegate(final ShowingItem item, Direction d) {
        if(item.cannotSwipe(d)) return;

        final EssentialAnimFactors firstEAF = new EssentialAnimFactors();
        final EssentialAnimFactors secondEAF = new EssentialAnimFactors();
        firstEAF.setView(pairView(item));

        View secondView = nextView[item.getIndex()][d.getIndex()];

        secondEAF.setView(secondView);

        Assert.assertNotNull(firstEAF.view);
        Assert.assertNotNull(secondEAF.view);

        switch (d) {
            case up:
                firstEAF.setPropertyName("translationY");
                firstEAF.setFromValue(0);
                firstEAF.setToValue(-firstEAF.view.getHeight());

                secondEAF.setPropertyName("translationY");
                secondEAF.setFromValue(secondEAF.view.getHeight());
                secondEAF.setToValue(0);

                break;
            case down:
                firstEAF.setPropertyName("translationY");
                firstEAF.setFromValue(0);
                firstEAF.setToValue(firstEAF.view.getHeight());

                secondEAF.setPropertyName("translationY");
                secondEAF.setFromValue(-secondEAF.view.getHeight());
                secondEAF.setToValue(0);

                break;
            case left:
                firstEAF.setPropertyName("translationX");
                firstEAF.setFromValue(0);
                firstEAF.setToValue(-firstEAF.view.getWidth());

                secondEAF.setPropertyName("translationX");
                secondEAF.setFromValue(secondEAF.view.getWidth());
                secondEAF.setToValue(0);
                break;
            case right:
                firstEAF.setPropertyName("translationX");
                firstEAF.setFromValue(0);
                firstEAF.setToValue(firstEAF.view.getWidth());

                secondEAF.setPropertyName("translationX");
                secondEAF.setFromValue(-secondEAF.view.getWidth());
                secondEAF.setToValue(0);

                break;
            default:
                break;
        }

        ObjectAnimator animator = ObjectAnimator.ofFloat(firstEAF.view, firstEAF.propertyName,
                firstEAF.fromValue, firstEAF.toValue).setDuration(1000);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                ObjectAnimator.ofFloat(secondEAF.view, secondEAF.propertyName,
                        secondEAF.fromValue, secondEAF.toValue).setDuration(1000).start();
                ViewU.show(firstEAF.view, secondEAF.view);

                mShowingItem = pairItem(secondEAF.view);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                showTheItem(mShowingItem);

                View[] views = {centerItem, topItem, bottomItem, leftItem, rightItem};
                stopAnimation(views);

                ObjectAnimator.ofFloat(firstEAF.view, firstEAF.propertyName,
                        firstEAF.toValue, firstEAF.fromValue).setDuration(1).start();
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

//    private boolean listCannotSwipe(Direction d) {
//        if (mYIndex.get() == 0 && d == Direction.down)
//            return true;
//        if (mYIndex.get() == 2 && d == Direction.up)
//            return true;
//        if (mXIndex.get() == 0 && d == Direction.right)
//            return true;
//        if (mXIndex.get() == 2 && d == Direction.left)
//            return true;
//
//        return false;
//    }

    public void stopAnimation(View... views) {
        for (View v : views) {
            if (v == null) continue;
            if (v.getAnimation() == null) continue;
            v.getAnimation().cancel();
            v.clearAnimation();
            v.setAnimation(null);
        }
    }


    private void showTheItem(ShowingItem item) {
        displayItemView(pairView(item));

        mShowingItem = item;
    }

    private void displayItemView(View v) {
        View[] views = {centerItem, topItem, bottomItem, leftItem, rightItem};
        for (View view : views) {
            if (view.equals(v)) ViewU.show(view);
            else ViewU.invisible(view);
        }
    }

    private View pairView(ShowingItem item) {
        switch (item) {
            case center:
                return centerItem;
            case top:
                return topItem;
            case bottom:
                return bottomItem;
            case left:
                return leftItem;
            case right:
                return rightItem;
            default:
                return centerItem;
        }
    }

    private ShowingItem pairItem(View view) {
        if (view.equals(centerItem))
            return ShowingItem.center;
        if (view.equals(topItem))
            return ShowingItem.top;
        if (view.equals(bottomItem))
            return ShowingItem.bottom;
        if (view.equals(leftItem))
            return ShowingItem.left;
        if (view.equals(rightItem))
            return ShowingItem.right;

        return ShowingItem.center;
    }

    private ShowingItem pairItem(int index) {
        if (index == ShowingItem.center.getIndex())
            return ShowingItem.center;
        if (index == ShowingItem.top.getIndex())
            return ShowingItem.top;
        if (index == ShowingItem.bottom.getIndex())
            return ShowingItem.bottom;
        if (index == ShowingItem.left.getIndex())
            return ShowingItem.left;
        if (index == ShowingItem.right.getIndex())
            return ShowingItem.right;

        return ShowingItem.center;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return mGestureDetector.onTouchEvent(event);
    }
}
