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

public class DrawingActivity extends AppCompatActivity {

    private static final String TAG = "DrawingActivity";

    public static Intent newIntent(@NonNull Context context) {
        return new Intent(context, DrawingActivity.class);
    }

    private MaterialToolbar toolbar;
    private DrawingView drawingView;
    private MaterialButtonToggleGroup groupToolSelection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);
        toolbar = findViewById(R.id.toolbar);
        drawingView = findViewById(R.id.drawing_view);
        groupToolSelection = findViewById(R.id.group_tool_selection);
        toolbar.setNavigationOnClickListener(v -> finish());
        groupToolSelection.check(R.id.button_tool_line);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        groupToolSelection = null;
        drawingView = null;
        toolbar.setNavigationOnClickListener(null);
        toolbar = null;
    }
}
