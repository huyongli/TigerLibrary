package cn.ittiger.library.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import cn.ittiger.library.app.AppContext;

/**
 * 列表间距(分割线)
 * @author: ylhu  2017/4/8
 */
public class CommonItemDividerDecoration extends RecyclerView.ItemDecoration {

    private final Drawable mDivider;
    private final int mSize;
    private boolean mBottomDividerEnabled = false;

    public CommonItemDividerDecoration(int spaceRes, int colorRes) {

        mDivider = new ColorDrawable(AppContext.getInstance().getResources().getColor(colorRes));
        mSize = AppContext.getInstance().getResources().getDimensionPixelSize(spaceRes);
    }

    public CommonItemDividerDecoration(int spaceRes, int colorRes, boolean bottomDividerEnabled) {

        mDivider = new ColorDrawable(AppContext.getInstance().getResources().getColor(colorRes));
        mSize = AppContext.getInstance().getResources().getDimensionPixelSize(spaceRes);
        mBottomDividerEnabled = bottomDividerEnabled;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int position = parent.getChildAdapterPosition(view);
        if(position != 0) {
            outRect.set(0, mSize, 0, 0);
        }
        if(mBottomDividerEnabled) {
            if(position == parent.getAdapter().getItemCount() - 1) {
                outRect.set(0, mSize, 0, mSize);
            }
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        super.onDraw(c, parent, state);
        int top = 0;
        int bottom = 0;
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if(i != 0) {
                View lastChild = parent.getChildAt(i - 1);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)lastChild.getLayoutParams();
                top = lastChild.getBottom() + params.bottomMargin;
                bottom = top + mSize;
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
            if(mBottomDividerEnabled) {
                if(i == childCount - 1) {
                    View child = parent.getChildAt(i);
                    final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)child.getLayoutParams();
                    top = child.getBottom() + params.bottomMargin;
                    bottom = top + mSize;
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(c);
                }
            }

        }
    }
}
