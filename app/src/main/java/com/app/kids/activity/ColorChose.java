package com.app.kids.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.app.kids.R;
import com.app.kids.database.DatabaseHandler;
import com.app.kids.util.Constant;
import com.app.kids.eventbus.Events;
import com.app.kids.eventbus.GlobalBus;
import com.app.kids.util.Method;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import top.defaults.colorpicker.ColorObservable;
import top.defaults.colorpicker.ColorObserver;
import top.defaults.colorpicker.ColorPickerView;


public class ColorChose extends AppCompatActivity implements ColorObservable {

    private View view;
    private DatabaseHandler db;
    private ColorPickerView colorPickerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_chose);

        Method method = new Method(ColorChose.this);
        method.forceRTLIfSupported();

        db = new DatabaseHandler(ColorChose.this);

        MaterialToolbar toolbar = findViewById(R.id.toolbar_color_chose);
        toolbar.setTitle(getResources().getString(R.string.chose_color));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        MaterialButton button = findViewById(R.id.button_color_chose);
        view = findViewById(R.id.pickedColor_color_chose);
        colorPickerView = findViewById(R.id.colorPicker_color_chose);

        colorPickerView.setInitialColor(0x7F313C93);

        colorPickerView.subscribe((color, fromUser, shouldPropagate) -> {
            view.setBackgroundColor(color);// use the color
        });

        button.setOnClickListener(v -> {
            Constant.colorChose = colorPickerView.getColor();
            if (db.isColorCode(Constant.colorChose)) {
                db.addColor(colorPickerView.getColor());
            }
            Events.ColorNotify colorNotify = new Events.ColorNotify(Constant.colorChose);
            GlobalBus.getBus().post(colorNotify);
            onBackPressed();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void subscribe(ColorObserver observer) {

    }

    @Override
    public void unsubscribe(ColorObserver observer) {

    }

    @Override
    public int getColor() {
        return 0;
    }
}
