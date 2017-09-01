package cn.ittiger.library.decoration;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import cn.ittiger.library.app.AppContext;

/**
 * @author: ylhu
 * @time: 17-5-18
 */
public class CommonItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public CommonItemDecoration(int spaceRes) {

        this.space = AppContext.getInstance().getResources().getDimensionPixelSize(spaceRes);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if(!(layoutManager instanceof LinearLayoutManager)) {
            throw new IllegalStateException("This item decoration only support LinearLayoutManager");
        }
        int orientation = ((LinearLayoutManager) layoutManager).getOrientation();

        int position = parent.getChildAdapterPosition(view);

        if(position > 0) {
            if(orientation == LinearLayoutManager.HORIZONTAL) {
                outRect.left = space;
            } else {
                outRect.top = space;
            }
        }
    }
}
