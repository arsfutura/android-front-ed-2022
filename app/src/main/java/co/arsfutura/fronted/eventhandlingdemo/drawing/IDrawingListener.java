package co.arsfutura.fronted.eventhandlingdemo.drawing;

import androidx.annotation.NonNull;

public interface IDrawingListener {

    void onUpdate(@NonNull Iterable<IDrawable> drawables);
}
