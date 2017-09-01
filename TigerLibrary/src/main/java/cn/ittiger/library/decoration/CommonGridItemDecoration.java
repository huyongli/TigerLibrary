package cn.ittiger.library.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import cn.ittiger.library.app.AppContext;

/**
 * @author: ylhu
 * @time: 17-5-18
 */
public class CommonGridItemDecoration extends RecyclerView.ItemDecoration {

    private int mSpace;
    private int mColumn;
    private int mBig;
    private int mSmall;

    public CommonGridItemDecoration(int spaceRes, int column) {

        mSpace = AppContext.getInstance().getResources().getDimensionPixelSize(spaceRes);
        this.mColumn = column;

        mBig = mSpace / column * 2;
        mSmall = mSpace / column;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int position = parent.getChildAdapterPosition(view);
        if(position >= mColumn) {
            outRect.top = mSpace;
        }
        int mod = position % mColumn;
        if(mod == 0) {
            outRect.right = mBig;
        } else if(mod == mColumn - 1) {
            outRect.left = mBig;
        } else {
            outRect.left = mSmall;
            outRect.right = mSmall;
        }
    }
}
