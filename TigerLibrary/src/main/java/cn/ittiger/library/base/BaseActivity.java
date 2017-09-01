package cn.ittiger.library.base;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;

import cn.ittiger.library.R;

/**
 * 通用Activity基类
 * @author: laohu
 * @site: http://ittiger.cn
 */
public abstract class BaseActivity extends AppCompatActivity {
    static {
        /**
         * Vector支持需引用com.android.support:appcompat-v7:23.2.0以上的版本
         *
         * Button类控件使用vector必须使用selector进行包装才会起作用，不然会crash
         * 并且使用selector时必须调用下面的方法进行设置，否则也会crash
         * */
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    protected BaseActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mActivity = BaseActivity.this;
    }

    @Override
    public void onBackPressed() {

        if(doubleExitAppEnable()) {
            exitAppDoubleClick();
        } else {
            super.onBackPressed();
        }
    }

    protected boolean doubleExitAppEnable() {

        return false;
    }

    /**
     * 双击退出函数变量
     */
    private long exitTime = 0;
    private View mRoot;

    /**
     * 双击退出APP
     */
    private void exitAppDoubleClick() {

        if (System.currentTimeMillis() - exitTime > 2000) {

            if(mRoot == null) {
                mRoot = findViewById(android.R.id.content);
            }
            Snackbar.make(mRoot, R.string.exit_app, Snackbar.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            exitApp();
        }
    }

    /**
     * 退出APP
     */
    protected void exitApp() {

        super.onBackPressed();
        System.exit(0);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
