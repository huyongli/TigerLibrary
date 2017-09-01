package cn.ittiger.library.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * 实现懒加载功能
 * @author: ylhu
 * @time: 17-5-10
 */
public abstract class DelayLoadFragment extends Fragment {
    //懒加载
    private boolean mNeedRefresh = false;
    private boolean mViewCreated = false;
    private boolean mIsRefreshed = false;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        mViewCreated = true;
        if(!isDelayLoadEnable()) {
            //如果不延迟加载，则View创建完成后加载
            startRefresh(false);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        mNeedRefresh = isVisibleToUser;
        refreshIfNeed();
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {

        super.onViewStateRestored(savedInstanceState);
        refreshIfNeed();
    }

    private void refreshIfNeed() {

        if(isDelayLoadEnable() && mIsRefreshed == false && mNeedRefresh && mViewCreated) {
            mNeedRefresh = false;
            mIsRefreshed = true;
            startRefresh(false);
        }
    }


    /**
     * 开始刷新
     * 主线程
     */
    public abstract void startRefresh(boolean pullToRefresh);

    /**
     * 是否开启延迟加载
     * @return
     */
    protected boolean isDelayLoadEnable() {

        return true;
    }
}
