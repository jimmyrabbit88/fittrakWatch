package com.example.fittrack2.ui.runpage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fittrack2.R;

public class RunFragment extends Fragment {

    private RunViewModel runViewModel;
    String date[], acwr[];
    private RecyclerView recyclerView;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        runViewModel = new ViewModelProvider(this).get(RunViewModel.class);
        View root = inflater.inflate(R.layout.run_layout, container, false);

        //Added for RV
        recyclerView = root.findViewById(R.id.rec_view);
        date = root.getResources().getStringArray(R.array.dateArray);
        acwr = root.getResources().getStringArray(R.array.acwrArray);

        RunHistoryAdapter runHistoryAdapter = new RunHistoryAdapter(root.getContext(), date, acwr);

        recyclerView.setAdapter(runHistoryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        //final TextView textView = root.findViewById(R.id.run_text);
//        runViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });



        return root;
    }
}
