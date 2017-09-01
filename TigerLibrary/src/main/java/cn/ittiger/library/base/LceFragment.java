package cn.ittiger.library.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import cn.ittiger.library.R;
import cn.ittiger.library.ui.CommonEmptyView;

/**
 * 初始化的时候需要查询数据，并显示查询等待进度条，查询失败显示失败提示
 * @author: ylhu
 * @time: 17-5-12
 */
public abstract class LceFragment extends DelayLoadFragment {
    protected CommonEmptyView mEmptyView;
    protected FrameLayout mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRootView = (FrameLayout) inflater.inflate(R.layout.base_layout, container, false);
        mEmptyView = (CommonEmptyView) mRootView.findViewById(R.id.empty_view);
        mEmptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startRefresh(false);
            }
        });
        View view = getContentView(inflater, container, savedInstanceState);
        mRootView.addView(view, 0);

        return mRootView;
    }

    /**
     * 点击刷新加载
     */
    private void clickToRefresh(boolean pullToRefresh) {

        startRefresh(pullToRefresh);
    }

    /**
     * 显示开始加载View
     */
    public void showRefreshViewIfNeeded(boolean pullToRefresh) {

        mEmptyView.showRefreshViewIfNeeded();
    }

    /**
     * 显示加载失败View
     */
    public void showRefreshFailedViewIfNeeded(boolean pullToRefresh) {

        mEmptyView.showRefreshFailedViewIfNeeded();
    }

    /**
     * 显示加载成功View
     */
    public void showRefreshSuccessViewIfNeed(boolean pullToRefresh) {

        mEmptyView.showRefreshSuccessViewIfNeed();
    }

    /**
     * Fragment数据视图
     * @param inflater
     * @param savedInstanceState
     * @return
     */
    public abstract View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);
}
