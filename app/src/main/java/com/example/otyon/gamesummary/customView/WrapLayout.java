package com.example.otyon.gamesummary.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import java.util.Random;

/**
 * 参考サイト
 * http://blog.choilabo.com/20140130/340
 * */
public class WrapLayout extends RelativeLayout {

    public WrapLayout(Context context) {
        super(context);
    }

    public WrapLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void addView(View child) {
        // idが未設定の場合は乱数でどうにかする。
        if (child.getId() == -1) {
            child.setId(new Random(System.currentTimeMillis()).nextInt(Integer.MAX_VALUE));
        }

        super.addView(child);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int l = this.getChildCount();
        if (l > 0) {
        int max = MeasureSpec.getSize(widthMeasureSpec);
        View pline = this.getChildAt(0);
        View prev = this.getChildAt(0);
        prev.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        int currentTotal = pline.getMeasuredWidth();
        for (int i = 1; i < l; i++) {
            View child = this.getChildAt(i);
            child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            int width = child.getMeasuredWidth();
            RelativeLayout.LayoutParams layoutParams =
                    (RelativeLayout.LayoutParams) child.getLayoutParams();
            if (max > currentTotal + width) {
                currentTotal += width;
                layoutParams.addRule(RelativeLayout.ALIGN_TOP, prev.getId());
                layoutParams.addRule(RelativeLayout.RIGHT_OF, prev.getId());
            } else {
                layoutParams.addRule(RelativeLayout.BELOW, pline.getId());
                layoutParams.addRule(RelativeLayout.ALIGN_LEFT, pline.getId());
                pline = child;
                currentTotal = pline.getMeasuredWidth();
            }
            prev = child;
        }
    }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
