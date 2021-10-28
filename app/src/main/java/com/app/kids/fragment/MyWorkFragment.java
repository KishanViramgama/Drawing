package com.app.kids.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.kids.R;
import com.app.kids.activity.MainActivity;
import com.app.kids.activity.MyWorkShow;
import com.app.kids.adapter.MyWorkAdapter;
import com.app.kids.interfaces.OnClick;
import com.app.kids.util.Constant;
import com.app.kids.util.Method;
import com.google.android.material.textview.MaterialTextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MyWorkFragment extends Fragment {

    private Method method;
    private File file;
    private MaterialTextView textViewNoData;
    private RecyclerView recyclerView;
    private MyWorkAdapter myWorkAdapter;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        if (MainActivity.toolbar != null) {
            MainActivity.toolbar.setTitle(getResources().getString(R.string.my_work));
        }

        Constant.fileList = new ArrayList<>();

        String root = getActivity().getExternalFilesDir(getResources().getString(R.string.saveDataPath)).toString();
        file = new File(root);

        OnClick onClick = (OnClick) (position, type) ->
                startActivity(new Intent(getActivity(), MyWorkShow.class)
                        .putExtra("position", position));

        method = new Method(getActivity(), onClick);

        new MyData().execute();

        textViewNoData = view.findViewById(R.id.textView_noData_home);
        recyclerView = view.findViewById(R.id.recyclerView_fragment);

        textViewNoData.setVisibility(View.GONE);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);

        return view;

    }

    @SuppressLint("StaticFieldLeak")
    public class MyData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

            Constant.fileList.clear();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setCancelable(false);
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            Constant.fileList = getListFiles(file);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            if (Constant.fileList.size() != 0) {
                myWorkAdapter = new MyWorkAdapter(getActivity(), Constant.fileList, method);
                recyclerView.setAdapter(myWorkAdapter);
            } else {
                textViewNoData.setVisibility(View.VISIBLE);
            }

            progressDialog.dismiss();

            super.onPostExecute(s);
        }
    }

    private List<File> getListFiles(File parentDir) {

        List<File> inFiles = new ArrayList<>();
        try {
            Queue<File> files = new LinkedList<>(Arrays.asList(parentDir.listFiles()));
            while (!files.isEmpty()) {
                File file = files.remove();
                if (file.isDirectory()) {
                    files.addAll(Arrays.asList(file.listFiles()));
                } else if (file.getName().endsWith(".jpg")) {
                    inFiles.add(file);
                }
            }
        } catch (Exception e) {
            Log.d("error", e.toString());
        }
        return inFiles;
    }

    @Override
    public void onResume() {
        if (myWorkAdapter != null) {
            myWorkAdapter.notifyDataSetChanged();
        }
        super.onResume();
    }
}
