package cn.ittiger.library.base;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * @author: ylhu
 * @time: 17-9-1
 */

public class BaseFragment extends Fragment {

    protected AppCompatActivity mActivity;

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        mActivity = (AppCompatActivity) context;
    }
}
