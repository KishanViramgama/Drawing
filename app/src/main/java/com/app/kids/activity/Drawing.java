package com.app.kids.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.app.drawing.Util.CanvasView;
import com.app.kids.R;
import com.app.kids.adapter.ColorAdapter;
import com.app.kids.database.DatabaseHandler;
import com.app.kids.item.ColorList;
import com.app.kids.util.Constant;
import com.app.kids.util.Events;
import com.app.kids.util.GlobalBus;
import com.app.kids.util.Method;
import com.google.android.material.appbar.MaterialToolbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Drawing extends AppCompatActivity {

    private Method method;
    private int position;
    private Dialog dialog;
    private DatabaseHandler db;
    private MaterialToolbar toolbar;
    private SeekBar seekBar;
    private CanvasView canvasView;
    private RecyclerView recyclerView;
    private ColorAdapter colorAdapter;
    private List<ColorList> colorLists;
    private ProgressDialog progressDialog;
    private ConstraintLayout constraintLayout;
    private boolean isShare = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);

        method = new Method(Drawing.this);
        method.forceRTLIfSupported();

        GlobalBus.getBus().register(this);

        db = new DatabaseHandler(Drawing.this);
        colorLists = new ArrayList<>();

        progressDialog = new ProgressDialog(Drawing.this);

        toolbar = findViewById(R.id.toolbar_drawing);
        toolbar.setTitle(getResources().getString(R.string.drawing));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dialog = new Dialog(Drawing.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (method.isRtl()) {
            dialog.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setLayout(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.WRAP_CONTENT);
        seekBar = dialog.findViewById(R.id.seekBarVolume_dialog);
        seekBar.setProgress(20);

        canvasView = findViewById(R.id.canvas_drawing);
        canvasView.paintStork(20);
        canvasView.paintColor(Constant.color_chose);
        if (db.isColorCode(Constant.color_chose)) {
            db.addColor(Constant.color_chose);
        }
        colorLists = db.getColorDetail();

        position = getIntent().getIntExtra("position", 0);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), Constant.image[position]);

        constraintLayout = findViewById(R.id.constraintLayout_imageView_drawing);
        ImageView imageView = findViewById(R.id.imageView_drawing);
        ImageView imageViewEraser = findViewById(R.id.imageView_eraser_drawing);
        ImageView imageViewPaintPalette = findViewById(R.id.imageView_paintPalette_drawing);
        ImageView imageViewPaintBrush = findViewById(R.id.imageView_paintBrush_drawing);
        imageView.setImageBitmap(bitmap);

        recyclerView = findViewById(R.id.recyclerView_drawing);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Drawing.this, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        colorAdapter = new ColorAdapter(Drawing.this, colorLists, canvasView);
        recyclerView.setAdapter(colorAdapter);

        imageViewEraser.setOnClickListener(v -> canvasView.paintColor(Color.parseColor("#FFFFFF")));

        imageViewPaintPalette.setOnClickListener(v -> startActivity(new Intent(Drawing.this, ColorChose.class)));

        imageViewPaintBrush.setOnClickListener(v -> {
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    canvasView.paintStork(progress / 2);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
            dialog.show();
        });

    }

    @Subscribe
    public void getData(Events.ColorNotify colorNotify) {
        colorLists.clear();
        colorLists = db.getColorDetail();
        colorAdapter = new ColorAdapter(Drawing.this, colorLists, canvasView);
        recyclerView.setAdapter(colorAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawing_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ic_share_main:
                isShare = true;
                new SaveShare().execute();
                Toast.makeText(Drawing.this, getResources().getString(R.string.share), Toast.LENGTH_SHORT).show();
                break;

            case R.id.ic_restart:
                canvasView.clear(getResources().getColor(R.color.canvasBg_drawing));
                break;

            case R.id.screen_short:
                Dexter.withContext(this)
                        .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                new SaveShare().execute();
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                                // check for permanent denial of permission
                                method.alertBox(getResources().getString(R.string.cannot_use_save_share));
                                if (response.isPermanentlyDenied()) {
                                    // navigate user to app settings
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
                break;

            case android.R.id.home:
                onBackPressed();
                break;

            default:
                break;
        }

        return true;
    }

    @SuppressLint("StaticFieldLeak")
    public class SaveShare extends AsyncTask<String, String, String> {

        String filePath;
        Bitmap bitmap;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage(getResources().getString(R.string.loading));
            progressDialog.setCancelable(false);
            progressDialog.show();

            // create bitmap screen capture
            constraintLayout.setDrawingCacheEnabled(true);
            bitmap = Bitmap.createBitmap(constraintLayout.getDrawingCache());
            constraintLayout.setDrawingCacheEnabled(false);

        }

        @Override
        protected String doInBackground(String... strings) {

            String mPath;
            if (isShare) {
                mPath = getExternalCacheDir().getAbsolutePath();
            } else {
                mPath = Environment.getExternalStorageDirectory() + getResources().getString(R.string.saveDataPath);
            }
            File imageFile = new File(mPath);

            if (!imageFile.exists()) {
                imageFile.mkdirs();
            }

            Random generator = new Random();
            filePath = imageFile.toString() + "/" + "Image-" + generator.nextInt(1000) + ".jpg";

            try {

                FileOutputStream outputStream = new FileOutputStream(filePath);
                int quality = 100;
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                outputStream.flush();
                outputStream.close();

            } catch (Throwable e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            if (isShare) {
                isShare = false;
                method.share(filePath);
            } else {
                method.alertBox(getResources().getString(R.string.save));
            }

            super.onPostExecute(s);
        }

    }

    @Override
    protected void onResume() {
        if (canvasView != null) {
            canvasView.paintColor(Constant.color_chose);
        }
        super.onResume();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        GlobalBus.getBus().unregister(this);
        super.onDestroy();
    }

}


