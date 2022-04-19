package co.arsfutura.fronted.eventhandlingdemo.drawing;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface IDrawingModel {

    void addDrawable(@NonNull IDrawable drawable);
    void refresh();
    void undo();
    void redo();
    void setDrawingListener(@Nullable IDrawingListener listener);
}
