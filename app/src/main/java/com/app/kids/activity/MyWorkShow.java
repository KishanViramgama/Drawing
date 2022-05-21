package com.app.kids.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.app.kids.R;
import com.app.kids.adapter.MyWorkShowAdapter;
import com.app.kids.eventbus.Events;
import com.app.kids.eventbus.GlobalBus;
import com.app.kids.util.Constant;
import com.app.kids.util.Method;
import com.app.kids.util.ZoomOutTransformation;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;

public class MyWorkShow extends AppCompatActivity {

    private Method method;
    private int indexPosition;
    private ViewPager viewPager;
    private MyWorkShowAdapter myWorkShowAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mywork_show);

        method = new Method(MyWorkShow.this);

        Intent in = getIntent();
        int selectedPosition = in.getIntExtra("position", 0);
        indexPosition = selectedPosition;

        MaterialToolbar toolbar = findViewById(R.id.toolbar_myWork_show);
        viewPager = findViewById(R.id.viewpager_myWork_show);

        toolbar.setTitle(getResources().getString(R.string.my_work));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ZoomOutTransformation zoomOutTransformation = new ZoomOutTransformation();
        viewPager.setPageTransformer(true, zoomOutTransformation);

        myWorkShowAdapter = new MyWorkShowAdapter(MyWorkShow.this, Constant.fileList);
        viewPager.setAdapter(myWorkShowAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        setCurrentItem(selectedPosition);

    }

    private void setCurrentItem(int position) {
        viewPager.setCurrentItem(position, false);
    }

    //	page change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            indexPosition = position;
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_work_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ic_delete:
                if (Constant.fileList.size() != 0) {
                    MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(MyWorkShow.this, R.style.DialogTitleTextStyle);
                    alertDialogBuilder.setMessage(getResources().getString(R.string.delete_message));
                    alertDialogBuilder.setPositiveButton(getResources().getString(R.string.yes),
                            (arg0, arg1) -> {
                                File files = new File(Constant.fileList.get(indexPosition).toString());
                                files.delete();
                                GlobalBus.getBus().post(new Events.DeleteNotify(indexPosition));
                                Constant.fileList.remove(indexPosition);
                                myWorkShowAdapter.notifyDataSetChanged();
                                if (Constant.fileList.size() == 0) {
                                    onBackPressed();
                                }
                                Toast.makeText(MyWorkShow.this, getResources().getString(R.string.delete), Toast.LENGTH_SHORT).show();
                            });

                    alertDialogBuilder.setNegativeButton(getResources().getString(R.string.no), (dialog, which) -> {

                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }
                break;

            case R.id.ic_share:
                method.share(Constant.fileList.get(indexPosition).toString());
                Toast.makeText(this, getResources().getString(R.string.share), Toast.LENGTH_SHORT).show();
                break;

            case android.R.id.home:
                onBackPressed();
                break;

            default:
                break;
        }

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
