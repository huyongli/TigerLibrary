package cn.ittiger.library.ui.processbutton;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

public class HorizontalProcessButton extends ProcessButton {

    public HorizontalProcessButton(Context context) {
        super(context);
    }

    public HorizontalProcessButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalProcessButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void drawProgress(Canvas canvas) {
        float scale = (float) getProgress() / (float) getMaxProgress();
        float indicatorWidth = (float) getMeasuredWidth() * scale;

        getProgressDrawable().setBounds(0, 0, (int) indicatorWidth, getMeasuredHeight());
        getProgressDrawable().draw(canvas);
    }

}
