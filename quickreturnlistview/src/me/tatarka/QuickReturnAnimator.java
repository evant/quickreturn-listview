package me.tatarka;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;

/**
 * User: evantatarka Date: 9/3/13 Time: 2:50 PM
 * <p/>
 * Shows and hides a footer as you scroll. Set this as the {@code OnScrollListener} of your listView
 * and the given view will be shown or hidden as your scroll.
 */
public class QuickReturnAnimator implements AbsListView.OnScrollListener {

    private AbsListView mListView;
    private View mQuickReturnView;
    private int mQuickReturnHeight = -1;

    private int mLastOffset;
    private View mLastVisibleItem;
    private int mLastViewPosition;
    private boolean mAnimate;
    private boolean mMovingUp;

    /**
     * Constructs a new quick return animator. The quickReturnView should be shown overlaying the
     * bottom of the list. If animate is true, the quickReturnView will animate into either the
     * shown or hidden state depending on scroll direction when the user finishes scrolling.
     * Otherwise, it will will remain partially shown. If you are targeting android < 4.0 you should
     * probably set this to true since touch targets don't move with animations.
     *
     * @param quickReturnView the footer to animate
     * @param animate         whether or not to animate when the user stops scrolling
     */
    public QuickReturnAnimator(View quickReturnView, boolean animate) {
        mQuickReturnView = quickReturnView;
        mAnimate = animate;
    }

    /**
     * Constructs a new quick return animator. The quickReturnView should be shown overlaying the
     * bottom of the list. Animate will be set to false.
     *
     * @param quickReturnView
     */
    public QuickReturnAnimator(View quickReturnView) {
        this(quickReturnView, false);
    }

    /**
     * Sets the quick return view. Must not be null.
     *
     * @param quickReturnView the view to animate while scrolling
     */
    public void setQuickReturnView(View quickReturnView) {
        mQuickReturnView = quickReturnView;
        mQuickReturnHeight = -1;
    }

    /**
     * Sets if the quickReturnView will animate to either the shown or hidden state when the user
     * stops scrolling.
     *
     * @param value true to animate the quickReturnView, false otherwise
     */
    public void setAnimate(boolean value) {
        mAnimate = value;
    }

    /**
     * Returns if the quickReturnView will animate to either the shown or hidden state when the user
     * stops scrolling.
     *
     * @return true to animate the quickReturnView, false otherwise
     */
    public boolean isAnimate() {
        return mAnimate;
    }

    /**
     * Animates the quickReturnView to the hidden state.
     */
    public void hide() {
        mQuickReturnView.animate()
                .translationY(mQuickReturnHeight)
                .setInterpolator(new AccelerateInterpolator())
                .start();
    }

    /**
     * Animates the quickReturnView to the shown state.
     */
    public void show() {
        mQuickReturnView.animate()
                .translationY(0)
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }

    @Override
    public void onScrollStateChanged(AbsListView listView, int scrollState) {
        if (!mAnimate) return;

        if (scrollState == SCROLL_STATE_IDLE) {
            if (mMovingUp) {
                hide();
            } else {
                show();
            }
        }
    }

    @Override
    public void onScroll(AbsListView listView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (totalItemCount == 0) return;

        if (mListView != listView || mQuickReturnHeight <= 0) {
            mListView = listView;
            mLastOffset = 0;
            mQuickReturnHeight = mQuickReturnView.getHeight();
        }

        if (mLastVisibleItem == null) {
            mLastVisibleItem = mListView.getChildAt(visibleItemCount / 2);
            if (mLastVisibleItem == null) return;
            mLastOffset = mLastVisibleItem.getTop();
            mLastViewPosition = mListView.getPositionForView(mLastVisibleItem);
        } else {
            int offset = mLastVisibleItem.getTop();
            if (mLastVisibleItem.getParent() == mListView && mListView.getPositionForView(mLastVisibleItem) == mLastViewPosition) {
                int translationY = (int) (mQuickReturnView.getTranslationY() + mLastOffset - offset);
                if (translationY < 0) translationY = 0;
                if (translationY > mQuickReturnHeight) translationY = mQuickReturnHeight;

                if (Math.abs(mLastOffset - offset) > 2) {
                    mMovingUp = (mLastOffset - offset > 0);
                }
                mQuickReturnView.setTranslationY(translationY);
                mLastOffset = offset;
            } else {
                mLastVisibleItem = null;
            }
        }
    }
}