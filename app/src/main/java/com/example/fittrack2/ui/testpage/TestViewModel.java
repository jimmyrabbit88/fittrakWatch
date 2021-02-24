package com.example.fittrack2.ui.testpage;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fittrack2.MainActivity;
import com.example.fittrack2.R;
import com.example.fittrack2.services.TrackingService;

import java.util.Timer;
import java.util.TimerTask;

public class TestViewModel extends ViewModel {

    private LocationManager lm;

    private boolean liveGpsData = true;
    private double originLong;
    private double originLat;
    private double currentLong;
    private double currentLat;
    private double distance[] = new double[]{.0075, .0100, .01500, .0200};
    private Integer arrows[] = new Integer[]{
            R.drawable.ic_baseline_arrow_upward_24,
            R.drawable.ic_baseline_arrow_forward_24,
            R.drawable.ic_baseline_arrow_upward_24,
            R.drawable.ic_baseline_arrow_forward_24,
    };
    private int counter = 0;

    private MutableLiveData<String> mText;
    private MutableLiveData<Integer> mArrow;

    public TestViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Begin Coutts test");
        mArrow = new MutableLiveData<>();
        mArrow.setValue(R.drawable.ic_baseline_directions_run_24);
    }

    public boolean beginTest(Activity activity) {
        if(ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //Toast.makeText(MainActivity.this, "You granted alredy", Toast.LENGTH_SHORT).show();
            lm = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

            return true;
        }
        else{
            mText.setValue("NO GPS ACCESS");
            return false;
        }

        //originLong = getLongitude();
        //originLat = getLatitude();
        //currentLat = getLatitude();
    }


    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<Integer> getArrow() {
        return mArrow;
    }


    public void startTest() {
        //startService();
    }



    private double pingLatitude() {
        return 0;
    }


    private double getLongitude() {
        if (liveGpsData) {
            return 0.0;
        }else
        {
            return 52.3546387;
        }
    }
    private double getLatitude() {
        if(liveGpsData){
            return 0.0;
        }else{
            return -9.694249;
        }
    }
}