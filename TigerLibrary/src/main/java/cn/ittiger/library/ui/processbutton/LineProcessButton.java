package cn.ittiger.library.ui.processbutton;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

public class LineProcessButton extends ProcessButton {

    public LineProcessButton(Context context) {
        super(context);
    }

    public LineProcessButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LineProcessButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void drawProgress(Canvas canvas) {
        if(getBackground() != getNormalDrawable()) {
            setBackgroundDrawable(getNormalDrawable());
        }
        drawLineProgress(canvas);
    }

    private void drawLineProgress(Canvas canvas) {
        float scale = (float) getProgress() / (float) getMaxProgress();
        float indicatorWidth = (float) getMeasuredWidth() * scale;

        double indicatorHeightPercent = 0.05; // 5%
        int bottom = (int) (getMeasuredHeight() - getMeasuredHeight() * indicatorHeightPercent);
        getProgressDrawable().setBounds(0, bottom, (int) indicatorWidth, getMeasuredHeight());
        getProgressDrawable().draw(canvas);
    }
}
