package cn.ittiger.library.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.DialogInterface.OnShowListener;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.ittiger.library.R;
import cn.ittiger.library.util.DisplayUtil;

/**
 * @author ympeng
 * @description base dialog
 *
 */
public class BaseDialog extends Dialog
    implements View.OnClickListener, OnDismissListener
        , OnKeyListener, OnShowListener {
    private static final float RATE = 0.85f;
    View mParentLayout;
    TextView mTitleTextView;
    FrameLayout mContentLayout;
    RelativeLayout mBottomLayout;
    Button mOkButton;
    Button mCancelButton;

    private ButtonClickedListener mButtonClickedListener;
    private ShowListener mShowListener;
    private DismissListener mDismissListener;
    private KeyListener mKeyListener;

    public BaseDialog(Context context) {

        super(context, R.style.BaseDialog);
        setContentView(R.layout.base_dialog_layout);

        mParentLayout = findViewById(R.id.parent_layout);
        mTitleTextView = (TextView) findViewById(R.id.title);
        mContentLayout = (FrameLayout) findViewById(R.id.content_layout);
        mBottomLayout = (RelativeLayout) findViewById(R.id.bottom_layout);
        mOkButton = (Button) findViewById(R.id.dialog_base_ok);
        mCancelButton = (Button) findViewById(R.id.dialog_base_cancel);

        int width = (int) (DisplayUtil.getWindowWidth(context) * RATE + 0.5f);
        mParentLayout.getLayoutParams().width = width;

        setOnDismissListener(this);
        setOnKeyListener(this);
        setOnShowListener(this);
    }

    public void setTitle(CharSequence title) {

        mTitleTextView.setText(title);
    }

    public void setTitle(int resId) {

        mTitleTextView.setText(resId);
    }

    public void setTitleColor(int resId) {

        mTitleTextView.setTextColor(getContext().getResources().getColor(resId));
    }

    public void setContentView(View contentView) {

        mContentLayout.removeAllViews();
        mContentLayout.addView(contentView);
    }

    public void setTopLayoutVisibility(int visibility) {

        mTitleTextView.setVisibility(visibility);
    }

    public void setBottomLayoutVisibility(int visibility) {

        mBottomLayout.setVisibility(visibility);
    }

    public void setOkButtonVisibility(int visibility) {

        mOkButton.setVisibility(visibility);
    }

    public void setOkButtonClickable(boolean clickable) {

        mOkButton.setClickable(clickable);
    }

    public void setOkButtonText(CharSequence text) {

        mOkButton.setText(text);
    }

    public void setOkButtonText(int resId) {

        mOkButton.setText(resId);
    }

    public void setOkButtonTextColor(int resId) {

        mOkButton.setTextColor(getContext().getResources().getColor(resId));
    }

    public void setOkButtonBackground(int resId) {

        mOkButton.setBackgroundResource(resId);
    }

    public void setCancelButtonVisibility(int visibility) {

        mCancelButton.setVisibility(visibility);
    }

    public void setCancelButtonClickable(boolean clickable) {

        mCancelButton.setClickable(clickable);
    }

    public void setCancelButtonText(CharSequence text) {

        mCancelButton.setText(text);
    }

    public void setCancelButtonText(int resId) {

        mCancelButton.setText(resId);
    }

    public void setCancelButtonTextColor(int resId) {

        mCancelButton.setTextColor(getContext().getResources().getColor(resId));
    }

    public void setCancelButtonBackground(Drawable drawable) {

        mCancelButton.setBackground(drawable);
    }

    public void setButtonClickedListener(ButtonClickedListener listener) {

        if (listener == null) {

            return;
        }

        mButtonClickedListener = listener;
        mOkButton.setOnClickListener(this);
        mCancelButton.setOnClickListener(this);
    }

    /**
     * Set mParentLayout's background from its child
     */
    public void setParentLayoutBackground(Drawable drawable) {

        mParentLayout.setBackground(drawable);
    }

    @Override
    public void onClick(View v) {

        if (v.equals(mOkButton)) {

            mButtonClickedListener.onOkButtonClicked(this);
            return;
        }

        if (v.equals(mCancelButton)) {

            mButtonClickedListener.onCancelButtonClicked(this);
            return;
        }
    }

    public void setShowListener(ShowListener listener) {

        mShowListener = listener;
    }

    @Override
    public void onShow(DialogInterface dialog) {

        if (mShowListener != null) {
            mShowListener.onShow(dialog);
        }
    }

    public void setDismissListener(DismissListener listener) {

        mDismissListener = listener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {

        if (mDismissListener != null) {
            mDismissListener.onDismiss(dialog);
        }
    }

    public void setKeyListener(KeyListener listener) {

        mKeyListener = listener;
    }

    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

        if (mKeyListener != null) {
            return mKeyListener.onKey(dialog, keyCode, event);
        }
        return false;
    }

    /**
     * callback interface
     */
    public interface ButtonClickedListener {

        public abstract void onOkButtonClicked(BaseDialog dialog);
        public abstract void onCancelButtonClicked(BaseDialog dialog);
    }

    public interface ShowListener {

        public abstract void onShow(DialogInterface dialog);
    }

    public interface DismissListener {

        public abstract void onDismiss(DialogInterface dialog);
    }

    public interface KeyListener {

        public abstract boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event);
    }
}
