package com.example.fittrack2.ui.testpage;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.fittrack2.MainActivity;
import com.example.fittrack2.R;

public class TestFragment extends Fragment {

    private int GPS_PERMISSION = 1;
    private LocationManager locationManager;

    private TestViewModel testViewModel;
    private Button btnBegin;
    private ImageView arrow;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        testViewModel =
                new ViewModelProvider(this).get(TestViewModel.class);
        View root = inflater.inflate(R.layout.test_layout, container, false);
        final TextView textView = root.findViewById(R.id.test_text);

        btnBegin = root.findViewById(R.id.btn_test_begin_test);
        arrow = root.findViewById(R.id.test_arrow);

        btnBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                    //Location gps = locationManager.getLastKnownLocation((LocationManager.GPS_PROVIDER));
                    if(testViewModel.beginTest(getActivity()));{
                        btnBegin.setVisibility(View.INVISIBLE);
                        arrow.setVisibility(View.VISIBLE);
                        testViewModel.startTest();
                    }
                }
                else{
                    requestGPSPermission();
                }
            }
        });

        testViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        testViewModel.getArrow().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer d) {
                arrow.setImageResource(d);
            }
        });
        return root;
    }

    private void requestGPSPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)){
            new AlertDialog.Builder(getActivity()).setTitle("Permission needed")
                    .setMessage("do it")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, GPS_PERMISSION);
                        }
                    }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();
        }
        else{
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, GPS_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == GPS_PERMISSION) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "PGranted", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getActivity(), "PNotGranted", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
