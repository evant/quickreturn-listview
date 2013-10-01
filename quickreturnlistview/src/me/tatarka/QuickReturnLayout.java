package me.tatarka;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;

/**
 * User: evantatarka Date: 10/1/13 Time: 10:03 AM
 *
 * A layout that hold a list and a quickReturnView.
 */
public class QuickReturnLayout extends FrameLayout {
    private int mListId;
    private int mQuickReturnId;
    private boolean mAnimate;

    private AbsListView mList;
    private View mQuickReturn;
    private QuickReturnAnimator mAnimator;

    public QuickReturnLayout(Context context) {
        this(context, null);
    }

    public QuickReturnLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuickReturnLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.QuickReturnLayout);
            try {
                mListId = a.getResourceId(R.styleable.QuickReturnLayout_list, -1);
                if (mListId < 0) throw new IllegalArgumentException("Quick return list must be defined.");
                mQuickReturnId = a.getResourceId(R.styleable.QuickReturnLayout_quickReturn, -1);
                if (mQuickReturnId < 0) throw new IllegalArgumentException("Quick return view must be defined.");
            } finally {
                a.recycle();
            }

            mAnimate = a.getBoolean(R.styleable.QuickReturnLayout_animate, false);
        }
    }

    public void setAnimate(boolean value) {
        mAnimate = value;
        if (mAnimator != null) mAnimator.setAnimate(value);
    }

    public boolean isAnimate() {
        return mAnimate;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mList = (AbsListView) findViewById(mListId);
        if (mList == null) throw new IllegalArgumentException("QuickReturnLayout must contain referenced list.");
        mQuickReturn = findViewById(mQuickReturnId);
        if (mQuickReturn == null) throw new IllegalArgumentException("QuickReturnLayout must contain referenced quick return view.");

        mAnimator = new QuickReturnAnimator(mQuickReturn, mAnimate);
        mList.setOnScrollListener(mAnimator);
    }
}
