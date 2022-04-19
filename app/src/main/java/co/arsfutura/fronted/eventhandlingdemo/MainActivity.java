package co.arsfutura.fronted.eventhandlingdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import co.arsfutura.fronted.eventhandlingdemo.drawing.DrawingActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button buttonDraw;

    private void navigateToDrawingActivity() {
        startActivity(DrawingActivity.newIntent(this));
    }
}
