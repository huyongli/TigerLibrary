package cn.ittiger.library.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import cn.ittiger.library.R;
import cn.ittiger.library.util.DisplayUtil;

/**
 * @author: laohu on 2017/6/10
 * @site: http://ittiger.cn
 */

public class LoadingDialog extends Dialog {
    private static final float RATE = 0.32f;
    RelativeLayout mRoot;
    ProgressBar mBar;

    public LoadingDialog(Context context) {

        super(context, R.style.BaseDialog);
        setContentView(R.layout.loading_dialog);
        mRoot = (RelativeLayout) findViewById(R.id.root);
        mBar = (ProgressBar) findViewById(R.id.progressbar);
        int size = (int) (DisplayUtil.getWindowWidth(context) * RATE + 0.5f);
        mRoot.getLayoutParams().width = size;
        mRoot.getLayoutParams().height = size;
        mBar.getLayoutParams().width = (int) (size / 2.0f + 0.5f);
        mBar.getLayoutParams().height = (int) (size / 2.0f + 0.5f);
    }
}
