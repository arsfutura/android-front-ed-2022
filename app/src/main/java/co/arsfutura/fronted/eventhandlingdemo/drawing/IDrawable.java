package co.arsfutura.fronted.eventhandlingdemo.drawing;

import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.NonNull;

public interface IDrawable {

    void draw(@NonNull Canvas canvas, @NonNull Paint paint);
}
