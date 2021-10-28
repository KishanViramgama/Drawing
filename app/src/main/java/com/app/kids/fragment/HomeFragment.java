package com.app.kids.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.kids.R;
import com.app.kids.activity.Drawing;
import com.app.kids.activity.MainActivity;
import com.app.kids.adapter.ViewAdapter;
import com.app.kids.interfaces.OnClick;
import com.app.kids.util.Constant;
import com.app.kids.util.Method;
import com.google.android.material.textview.MaterialTextView;

public class HomeFragment extends Fragment {

    private int[] image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        if (MainActivity.toolbar != null) {
            MainActivity.toolbar.setTitle(getResources().getString(R.string.home));
        }

        OnClick onClick = (position, type) -> {
            Constant.image = image;
            startActivity(new Intent(getActivity(), Drawing.class)
                    .putExtra("position", position));
        };
        Method method = new Method(getActivity(), onClick);

        image = new int[]{R.drawable.image, R.drawable.image_a};

        MaterialTextView textViewNoData = view.findViewById(R.id.textView_noData_home);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_fragment);

        textViewNoData.setVisibility(View.GONE);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);

        ViewAdapter viewAdapter = new ViewAdapter(getActivity(), image, method);
        recyclerView.setAdapter(viewAdapter);

        return view;

    }

}
