package com.app.kids.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.app.kids.R;
import com.app.kids.util.TouchImageView;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

public class MyWorkShowAdapter extends PagerAdapter {

    private Activity activity;
    private List<File> string;

    public MyWorkShowAdapter(Activity activity, List<File> string) {
        this.activity = activity;
        this.string = string;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        View view = layoutInflater.inflate(R.layout.show_adapter, container, false);

        TouchImageView touchImageView = view.findViewById(R.id.imageView_image_show_adapter);

        Glide.with(activity).load(string.get(position).toString())
                .placeholder(R.drawable.placeholder_landscap)
                .into(touchImageView);

        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return string.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
        return view == obj;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}

