package co.arsfutura.fronted.eventhandlingdemo.drawing;

import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.NonNull;

import java.util.Objects;

public final class Line implements IDrawable {

    @NonNull
    private Point start;

    @NonNull
    private Point end;

    public Line(@NonNull Point start, @NonNull Point end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return start.equals(line.start) && end.equals(line.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @NonNull
    public Point getStart() {
        return start;
    }

    public void setStart(@NonNull Point start) {
        this.start = start;
    }

    @NonNull
    public Point getEnd() {
        return end;
    }

    public void setEnd(@NonNull Point end) {
        this.end = end;
    }

    @Override
    public void draw(@NonNull Canvas canvas, @NonNull Paint paint) {
        canvas.drawLine(start.getX(), start.getY(), end.getX(), end.getY(), paint);
    }
}
