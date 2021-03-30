package com.example.fittrack2.ui.homepage;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.amplifyframework.core.Amplify;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fittrack2.R;
import com.progress.progressview.ProgressView;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    TextView txtAcwr;
    TextView textTip;
    ImageView img_arrow;
    ProgressView pg;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        homeViewModel.callRepoGetValue();
        View root = inflater.inflate(R.layout.home_layout, container, false);
        txtAcwr = root.findViewById(R.id.txt_acwr);
        textTip = root.findViewById(R.id.txt_tip);
        pg = root.findViewById(R.id.progressView);

        txtAcwr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeViewModel.callRepoGetValue();
            }
        });



        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Integer val = Math.abs(integer);
                pg.setProgress(val/200f);
                txtAcwr.setText(val.toString() + "%");
                txtAcwr.setVisibility(View.VISIBLE);

                if(integer > 150){
                    textTip.setText("Reduce Workload");
                }
                else if(integer < 50){
                    textTip.setText("Increase Workload");
                    //img_arrow.setRotation(180f);
                    //img_arrow.setVisibility(View.VISIBLE);
                }
                else {
                    textTip.setText("Maintain Workload");
                    //img_arrow.setVisibility(View.INVISIBLE);
                }

            }
        });

//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                txtAcwr.setText(s);
//                pg.setProgress(.33f);
//            }
//        });
        return root;
    }
}
