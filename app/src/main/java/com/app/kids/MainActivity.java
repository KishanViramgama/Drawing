package com.app.kids;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.app.drawing.Util.CanvasView;
import com.app.kids.drawing.R;

public class MainActivity extends AppCompatActivity {

    public Toolbar toolbar;
    private CanvasView customCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);

        customCanvas = findViewById(R.id.canvas_drawing);
        ImageView imageView = findViewById(R.id.imageView);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image);
        imageView.setImageBitmap(bitmap);

        customCanvas.paintColor(getResources().getColor(R.color.colorAccent));
        customCanvas.paintStork(20);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.ic_drawing:
                customCanvas.paintColor(getResources().getColor(R.color.colorAccent));
                break;

            case R.id.ic_eraser:
                customCanvas.paintColor(getResources().getColor(R.color.white));
                break;

            case R.id.ic_restart:
                customCanvas.clear();
                break;

            default:
                break;
        }

        return true;
    }

}
