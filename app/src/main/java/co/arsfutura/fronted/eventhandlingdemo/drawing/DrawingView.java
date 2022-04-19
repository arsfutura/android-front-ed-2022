package co.arsfutura.fronted.eventhandlingdemo.drawing;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import co.arsfutura.fronted.eventhandlingdemo.R;

public class DrawingView extends View implements IDrawingListener {

    @NonNull
    private final Paint paint = new Paint();

    @ColorInt
    private int backgroundColor = 0;

    @Nullable
    private Iterable<IDrawable> drawables = null;

    public DrawingView(Context context) {
        this(context, null);
    }

    public DrawingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.drawingViewStyle);
    }

    public DrawingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, R.style.Widget_FrontEdEventHandlingDemo_DrawingView_White_OldImperial);
    }

    public DrawingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.DrawingViewAppearance,
                defStyleAttr,
                defStyleRes
        );
        for (int i = 0; i < a.getIndexCount(); i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.DrawingViewAppearance_android_background) {
                backgroundColor = a.getColor(attr, backgroundColor);
            } else if (attr == R.styleable.DrawingViewAppearance_strokeColor) {
                paint.setColor(a.getColor(attr, paint.getColor()));
            } else if (attr == R.styleable.DrawingViewAppearance_strokeWidth) {
                paint.setStrokeWidth(a.getDimension(attr, paint.getStrokeWidth()));
            }
        }
        a.recycle();
    }

    @ColorInt
    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(@ColorInt int backgroundColor) {
        this.backgroundColor = backgroundColor;
        invalidate();
    }

    @ColorInt
    public int getStrokeColor() {
        return paint.getColor();
    }

    public void setStrokeColor(@ColorInt int foregroundColor) {
        paint.setColor(foregroundColor);
        invalidate();
    }

    public float getStrokeWidth() {
        return paint.getStrokeWidth();
    }

    public void setStrokeWidth(float strokeWidth) {
        paint.setStrokeWidth(strokeWidth);
        invalidate();
    }

    @Override
    public void onUpdate(@NonNull Iterable<IDrawable> drawables) {
        this.drawables = drawables;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (canvas == null || drawables == null) {
            return;
        }
        canvas.drawColor(backgroundColor);
        for (IDrawable drawable : drawables) {
            drawable.draw(canvas, paint);
        }
    }
}
