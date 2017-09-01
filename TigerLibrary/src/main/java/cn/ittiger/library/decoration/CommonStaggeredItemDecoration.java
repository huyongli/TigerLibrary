package cn.ittiger.library.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import cn.ittiger.library.R;
import cn.ittiger.library.app.AppContext;
import cn.ittiger.library.ui.recycler.CommonRecyclerViewAdapter;

/**
 * @author laohu
 */
public class CommonStaggeredItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public CommonStaggeredItemDecoration() {

        this.space = AppContext.getInstance().getResources().getDimensionPixelSize(R.dimen.divider_size);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        int position = lp.getViewAdapterPosition();
        boolean isDataPosition = ((CommonRecyclerViewAdapter)parent.getAdapter()).isValidDataPosition(position);
        if(!isDataPosition) {
            return;
        }
        int index = lp.getSpanIndex();
        if(index == 0) {//左边列
            outRect.left = space * 2;
            outRect.right = space;
        } else {//右边列表
            outRect.left = space;
            outRect.right = space * 2;
        }
        outRect.top = space * 2;
    }
}
