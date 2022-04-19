package co.arsfutura.fronted.eventhandlingdemo.drawing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.ArrayList;
import java.util.List;

import co.arsfutura.fronted.eventhandlingdemo.R;

public class DrawingActivity extends AppCompatActivity implements IDrawingModel {

    private static final String TAG = "DrawingActivity";

    public static Intent newIntent(@NonNull Context context) {
        return new Intent(context, DrawingActivity.class);
    }

    private interface IState {

        void down(float x, float y);

        void up(float x, float y);

        void move(float x, float y);
    }

    private class DrawLinesState implements IState {

        @NonNull
        private static final String TAG = "DrawLinesState";

        @Nullable
        private Line line = null;

        @Override
        public void down(float x, float y) {
            Log.d(TAG, "down() called with: x = [" + x + "], y = [" + y + "]");
            line = new Line(new Point(x, y), new Point(x, y));
            addDrawable(line);
        }

        @Override
        public void up(float x, float y) {
            Log.d(TAG, "up() called with: x = [" + x + "], y = [" + y + "]");
            line = null;
        }

        @Override
        public void move(float x, float y) {
            Log.d(TAG, "move() called with: x = [" + x + "], y = [" + y + "]");
            Log.d(TAG, String.format("move: line=%s", line));
            if (line == null) {
                return;
            }
            line.setEnd(new Point(x, y));
            refresh();
        }
    }

    private class DrawCirclesState implements IState {

        @NonNull
        private static final String TAG = "DrawCirclesState";

        @Nullable
        private Circle circle = null;

        @Override
        public void down(float x, float y) {
            Log.d(TAG, "down() called with: x = [" + x + "], y = [" + y + "]");
            circle = new Circle(new Point(x, y), 0f);
            addDrawable(circle);
        }

        @Override
        public void up(float x, float y) {
            Log.d(TAG, "up() called with: x = [" + x + "], y = [" + y + "]");
            circle = null;
        }

        @Override
        public void move(float x, float y) {
            Log.d(TAG, "move() called with: x = [" + x + "], y = [" + y + "]");
            Log.d(TAG, String.format("move: circle=%s", circle));
            if (circle == null) {
                return;
            }
            circle.setRadius(distance(circle.getCenter().getX(), circle.getCenter().getY(), x, y));
            refresh();
        }

        private float distance(float x1, float y1, float x2, float y2) {
            return (float) Math.hypot(x2 - x1, y2 - y1);
        }
    }

    private MaterialToolbar toolbar;
    private DrawingView drawingView;
    private MaterialButtonToggleGroup groupToolSelection;

    private final List<IDrawable> drawables = new ArrayList<>();
    private final List<IDrawable> undoStack = new ArrayList<>();

    @Nullable
    private IDrawingListener listener = null;

    @NonNull
    private IState state = new DrawLinesState();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);
        toolbar = findViewById(R.id.toolbar);
        drawingView = findViewById(R.id.drawing_view);
        groupToolSelection = findViewById(R.id.group_tool_selection);
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.setOnMenuItemClickListener(item -> {
            final int id = item.getItemId();
            if (id == R.id.action_undo) {
                undo();
                return true;
            } else if (id == R.id.action_redo) {
                redo();
                return true;
            } else {
                return false;
            }
        });
        drawingView.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    state.down(event.getX(), event.getY());
                    return true;
                case MotionEvent.ACTION_UP:
                    state.up(event.getX(), event.getY());
                    return true;
                case MotionEvent.ACTION_MOVE:
                    state.move(event.getX(), event.getY());
                    return true;
                default:
                    return false;
            }
        });
        groupToolSelection.addOnButtonCheckedListener((button, id, isChecked) -> {
            Log.d(TAG, String.format("onButtonChecked: button=%s id=%d isChecked=%b", button, id, isChecked));
            if (!isChecked) {
                return;
            }
            if (id == R.id.button_tool_line) {
                state = new DrawLinesState();
            } else if (id == R.id.button_tool_circle) {
                state = new DrawCirclesState();
            }
        });
        groupToolSelection.check(R.id.button_tool_line);
        setDrawingListener(drawingView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setDrawingListener(null);
        groupToolSelection.clearOnButtonCheckedListeners();
        groupToolSelection = null;
        drawingView = null;
        toolbar.setNavigationOnClickListener(null);
        toolbar.setOnMenuItemClickListener(null);
        toolbar = null;
    }

    @Override
    public void addDrawable(@NonNull IDrawable drawable) {
        Log.d(TAG, "addDrawable() called with: drawable = [" + drawable + "]");
        drawables.add(drawable);
        undoStack.clear();
        refresh();
    }

    @Override
    public void refresh() {
        if (listener == null) {
            return;
        }
        listener.onUpdate(drawables);
    }

    @Override
    public void undo() {
        Log.d(TAG, "undo() called");
        if (drawables.isEmpty()) {
            return;
        }
        undoStack.add(drawables.remove(drawables.size() - 1));
        refresh();
    }

    @Override
    public void redo() {
        Log.d(TAG, "redo() called");
        if (undoStack.isEmpty()) {
            return;
        }
        drawables.add(undoStack.remove(undoStack.size() - 1));
        refresh();
    }

    @Override
    public void setDrawingListener(@Nullable IDrawingListener listener) {
        Log.d(TAG, "setDrawingListener() called with: listener = [" + listener + "]");
        this.listener = listener;
        refresh();
    }
}
