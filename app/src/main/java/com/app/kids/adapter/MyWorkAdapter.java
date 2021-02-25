package com.app.kids.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.app.kids.R;
import com.app.kids.util.Method;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;


public class MyWorkAdapter extends RecyclerView.Adapter<MyWorkAdapter.ViewHolder> {

    private Method method;
    private Activity activity;
    private int columnWidth;
    private List<File> fileList;

    public MyWorkAdapter(Activity activity, List<File> fileList, Method method) {
        this.activity = activity;
        this.fileList = fileList;
        this.method = method;
        columnWidth = method.getScreenWidth();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(activity).inflate(R.layout.view_adapter, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.imageView.setLayoutParams(new ConstraintLayout.LayoutParams(columnWidth / 2, columnWidth / 2));
        Glide.with(activity).load(fileList.get(position).toString()).into(holder.imageView);

        holder.imageView.setOnClickListener(v -> method.click(position, ""));

    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView_view_adapter);

        }
    }
}
