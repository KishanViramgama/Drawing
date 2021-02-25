package com.app.kids.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;

import com.app.kids.R;
import com.app.kids.interfaces.OnClick;

import java.io.File;

public class Method {

    public Activity activity;
    private OnClick onClick;

    public Method(Activity activity) {
        this.activity = activity;
    }

    public Method(Activity activity, OnClick onClick) {
        this.activity = activity;
        this.onClick = onClick;
    }

    //rtl
    public void forceRTLIfSupported() {
        if (activity.getResources().getString(R.string.isRTL).equals("true")) {
            activity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    public boolean isRtl() {
        return activity.getResources().getString(R.string.isRTL).equals("true");
    }

    public void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public int getScreenWidth() {
        int columnWidth;
        WindowManager wm = (WindowManager) activity
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final Point point = new Point();

        point.x = display.getWidth();
        point.y = display.getHeight();

        columnWidth = point.x;
        return columnWidth;
    }

    public void share(String link) {

        try {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType("image/*");
            shareIntent.putExtra(Intent.EXTRA_TEXT, activity.getResources().getString(R.string.app_name));
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(link)));
            activity.startActivity(Intent.createChooser(shareIntent, activity.getResources().getString(R.string.share_to)));
        } catch (Exception e) {
            Log.d("error", e.toString());
        }

    }

    public void click(final int position, final String type) {
        onClick.click(position, type);
    }

    public void alertBox(String message) {

        if (activity != null) {
            if (!activity.isFinishing()) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity, R.style.DialogTitleTextStyle);
                alertDialogBuilder.setMessage(message);
                alertDialogBuilder.setPositiveButton(activity.getResources().getString(R.string.ok),
                        (arg0, arg1) -> {

                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        }

    }

}
