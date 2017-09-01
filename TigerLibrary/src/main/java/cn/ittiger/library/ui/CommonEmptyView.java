package cn.ittiger.library.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.ittiger.library.R;

/**
 * @author: laohu on 2017/5/10
 * @site: http://ittiger.cn
 */
public class CommonEmptyView extends RelativeLayout {
    private LinearLayout mLoadingContainer;
    private View mLoadingView;
    private View mLoadingFailedContainer;
    private ImageView mLoadFailedImage;
    private TextView mLoadFailedView;
    private TextView mLoadTextView;
    protected TextView mLoadFailedDescribeView;

    public CommonEmptyView(Context context) {

        this(context, null);
    }

    public CommonEmptyView(Context context, AttributeSet attrs) {

        this(context, attrs, 0);
    }

    public CommonEmptyView(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        initView(context);
    }

    protected void initView(Context context) {

        inflate(context, R.layout.common_empty_view, this);
        mLoadingContainer = (LinearLayout) findViewById(R.id.loading_container);
        mLoadingView = findViewById(R.id.loadingView);
        mLoadTextView = (TextView) findViewById(R.id.loading_text_view);
        mLoadingFailedContainer = findViewById(R.id.loading_failed_container);
        mLoadFailedImage = (ImageView) findViewById(R.id.load_failed_image);
        mLoadFailedView = (TextView) findViewById(R.id.load_failed);
        mLoadFailedDescribeView = (TextView) findViewById(R.id.loading_failed_describe);
        setLoadingFailedInfo(R.string.empty_common_title, R.string.empty_common_description, R.drawable.ic_empty_error);
    }

    public void setLoadingFailedInfo(int loadFailedMsg, int loadFailedImage) {

        mLoadFailedImage.setImageResource(loadFailedImage);
        mLoadFailedView.setText(loadFailedMsg);
    }

    public void setLoadingFailedInfo(int loadFailedMsg, int loadFailedDescribeMsg, int loadFailedImage) {

        mLoadFailedImage.setImageResource(loadFailedImage);
        mLoadFailedView.setText(loadFailedMsg);
        mLoadFailedDescribeView.setText(loadFailedDescribeMsg);
    }

    public void setLoadFailedDescribeTextColor(int secondColor) {

        mLoadFailedDescribeView.setTextColor(secondColor);
    }

    public void setLoadFailedTextColor(int color) {

        if(mLoadTextView != null) {
            mLoadTextView.setTextColor(color);
        }
        mLoadFailedView.setTextColor(color);
    }

    public void setLoadingView(View loadingView) {

        mLoadingContainer.removeView(mLoadingView);
        mLoadingView = loadingView;
        mLoadingContainer.addView(loadingView, 0);
    }

    public void showRefreshViewIfNeeded() {

        if(getVisibility() == GONE) {
            setVisibility(VISIBLE);
        }
        startLoadingAnimate();
        mLoadingFailedContainer.setVisibility(GONE);
        mLoadingContainer.setVisibility(VISIBLE);
        setClickable(false);
    }

    public void showRefreshFailedViewIfNeeded() {

        if(getVisibility() == GONE) {
            setVisibility(VISIBLE);
        }
        stopLoadingAnimate();
        mLoadingFailedContainer.setVisibility(VISIBLE);
        mLoadingContainer.setVisibility(GONE);
        setClickable(true);
    }

    public void showRefreshSuccessViewIfNeed() {

        setVisibility(GONE);
        stopLoadingAnimate();
    }

    protected void startLoadingAnimate() {

        if(mLoadingView instanceof LoadingView) {
            ((LoadingView) mLoadingView).start();
        }
    }

    protected void stopLoadingAnimate() {

        if(mLoadingView instanceof LoadingView) {
            ((LoadingView) mLoadingView).stop();
        }
    }
}
