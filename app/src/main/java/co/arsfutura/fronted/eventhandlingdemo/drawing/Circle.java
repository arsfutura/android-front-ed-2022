package co.arsfutura.fronted.eventhandlingdemo.drawing;

import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.NonNull;

import java.util.Objects;

public final class Circle implements IDrawable {

    @NonNull
    private Point center;

    private float radius;

    public Circle(@NonNull Point center, float radius) {
        this.center = center;
        this.radius = radius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Circle circle = (Circle) o;
        return Double.compare(circle.radius, radius) == 0 && center.equals(circle.center);
    }

    @Override
    public int hashCode() {
        return Objects.hash(center, radius);
    }

    @NonNull
    public Point getCenter() {
        return center;
    }

    public void setCenter(@NonNull Point center) {
        this.center = center;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public void draw(@NonNull Canvas canvas, @NonNull Paint paint) {
        canvas.drawCircle(center.getX(), center.getY(), radius, paint);
    }
}
