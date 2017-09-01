package cn.ittiger.library.ui.processbutton;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

public class VerticalProcessButton extends ProcessButton {

    public VerticalProcessButton(Context context) {
        super(context);
    }

    public VerticalProcessButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VerticalProcessButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void drawProgress(Canvas canvas) {
        float scale = (float) getProgress() / (float) getMaxProgress();
        float indicatorHeight = (float) getMeasuredHeight() * scale;

        getProgressDrawable().setBounds(0, 0, getMeasuredWidth(), (int) indicatorHeight);
        getProgressDrawable().draw(canvas);
    }

}
