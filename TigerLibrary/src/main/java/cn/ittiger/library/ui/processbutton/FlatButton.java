package cn.ittiger.library.ui.processbutton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;

import cn.ittiger.library.R;

public class FlatButton extends Button {

    private StateListDrawable mNormalDrawable;
    private CharSequence mNormalText;
    private float cornerRadius;

    public FlatButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public FlatButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FlatButton(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        mNormalDrawable = new StateListDrawable();
        if (attrs != null) {
            initAttributes(context, attrs);
        }
        mNormalText = getText().toString();
        setBackgroundCompat(mNormalDrawable);
    }

    private void initAttributes(Context context, AttributeSet attributeSet) {
        TypedArray attr = getTypedArray(context, attributeSet, R.styleable.FlatButton);
        if (attr == null) {
            return;
        }

        try {

            float defValue = getDimension(R.dimen.process_button_corner_radius);
            cornerRadius = attr.getDimension(R.styleable.FlatButton_pb_cornerRadius, defValue);

            mNormalDrawable.addState(new int[]{android.R.attr.state_pressed},
                    createPressedDrawable(attr));
            mNormalDrawable.addState(new int[]{android.R.attr.state_focused},
                    createPressedDrawable(attr));
            mNormalDrawable.addState(new int[]{android.R.attr.state_selected},
                    createPressedDrawable(attr));
            mNormalDrawable.addState(new int[]{}, createNormalDrawable(attr));

        } finally {
            try {
                attr.recycle();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private GradientDrawable createNormalDrawable(TypedArray attr) {
        GradientDrawable drawableNormal =
            (GradientDrawable) getDrawable(R.drawable.process_button_rect_normal).mutate();

        int blueNormal = getColor(R.color.process_button_blue_normal);
        int colorNormal = attr.getColor(R.styleable.FlatButton_pb_colorNormal, blueNormal);

        drawableNormal.setCornerRadius(getCornerRadius());
        drawableNormal.setColor(colorNormal);

        boolean strokeEnabled = attr.getBoolean(R.styleable.FlatButton_pb_normalStrokeEnabled, false);
        if(strokeEnabled) {
            int strokeColor = attr.getColor(R.styleable.FlatButton_pb_normalStrokeColor, colorNormal);
            int strokeWidth = attr.getDimensionPixelSize(R.styleable.FlatButton_pb_normalStrokeWidth, 0);
            if(strokeWidth != 0) {
                drawableNormal.setStroke(strokeWidth, strokeColor);
            }
        }

        return drawableNormal;
    }

    private Drawable createPressedDrawable(TypedArray attr) {
        GradientDrawable drawablePressed =
                (GradientDrawable) getDrawable(R.drawable.process_button_rect_pressed).mutate();
        drawablePressed.setCornerRadius(getCornerRadius());

        int blueDark = getColor(R.color.process_button_blue_pressed);
        int colorPressed = attr.getColor(R.styleable.FlatButton_pb_colorPressed, blueDark);
        drawablePressed.setColor(colorPressed);

        return drawablePressed;
    }

    protected Drawable getDrawable(int id) {
        return getResources().getDrawable(id);
    }

    protected float getDimension(int id) {
        return getResources().getDimension(id);
    }

    protected int getColor(int id) {
        return getResources().getColor(id);
    }

    protected TypedArray getTypedArray(Context context, AttributeSet attributeSet, int[] attr) {
        return context.obtainStyledAttributes(attributeSet, attr, 0, 0);
    }

    public float getCornerRadius() {
        return cornerRadius;
    }

    public StateListDrawable getNormalDrawable() {
        return mNormalDrawable;
    }

    public CharSequence getNormalText() {
        return mNormalText;
    }

    public void setNormalText(CharSequence normalText) {
        mNormalText = normalText;
    }

    /**
     * Set the View's background. Masks the API changes made in Jelly Bean.
     *
     * @param drawable
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public void setBackgroundCompat(Drawable drawable) {
        int pL = getPaddingLeft();
        int pT = getPaddingTop();
        int pR = getPaddingRight();
        int pB = getPaddingBottom();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
        setPadding(pL, pT, pR, pB);
    }
}
