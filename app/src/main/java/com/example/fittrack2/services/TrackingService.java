package com.example.fittrack2.services;


import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.fittrack2.MainActivity;
import com.example.fittrack2.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class TrackingService extends LifecycleService {

    private boolean isFirst = true;
    public MutableLiveData<Boolean> isTracking = new MutableLiveData<Boolean>(false);
    public List<LatLng> myPosList = new ArrayList<>();
    private FusedLocationProviderClient fusedLocationProviderClient;
    public static MutableLiveData<LatLng> mLastPing = new MutableLiveData<>();
    private List<Location> locationList = new ArrayList<>();
    private Location markedLocation;
    public static MutableLiveData<Integer> stageMarker = new MutableLiveData<>(0);
    public static MutableLiveData<Float> rotation = new MutableLiveData<>(0f);
    private float[] routeDistances = new float[]{30f, 10f, 8f, 10f, 8f, 10f, 45f};
    private Float[] arrowRotation = new Float[]{135f, 225f, 135f, 225f, 90f, 135f};




    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.d("hey", "me either");

        if (intent != null) {
            switch (intent.getAction()) {
                case "ACTION_START_OR_RESUME_SERVICE":
                    Log.d("TAG", "start resume");
                    if (isFirst) {
                        startForegroundService();
                        isFirst = false;
                    }else{
                        startForegroundService();
                    }

                    break;
                case "ACTION_PAUSE_SERVICE":
                    Log.d("TAG", "pause");
                    break;
                case "ACTION_STOP_SERVICE":
                    Log.d("TAG", "stop");
                    isTracking.postValue(false);
                    break;
                default:
                    break;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }



    @Override
    public void onCreate() {
        super.onCreate();
        //Log.d("hey", "are you called here at all");
        isTracking.postValue(false);
        mLastPing.postValue(new LatLng(0, 0));
        myPosList.add(new LatLng(0,0));
        fusedLocationProviderClient = new FusedLocationProviderClient(this);

        isTracking.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                updateLocationTracking(aBoolean);
            }
        });
    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult result) {
            super.onLocationResult(result);
            //Log.d("UHH", "work" + isFirst);
            if (isTracking.getValue()) {
                for (Location l : result.getLocations()) {
                    addPathPoint(l);
                    //updateLastPing(l);

                    Log.d("UHH", "work" + l.getLatitude());
                }
            }
        }
    };

    private void updateLastPing(Location l){
        LatLng ll = new LatLng(l.getLatitude(), l.getLongitude());
        mLastPing.setValue(ll);
    }

    public LiveData<LatLng> getLastPing(){ return mLastPing;}

    private void updateLocationTracking(boolean isTracking) {
        if (isTracking) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            else{
                LocationRequest lr = LocationRequest.create();
                lr.setInterval(500L);
                lr.setFastestInterval(250L);
                lr.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                fusedLocationProviderClient.requestLocationUpdates(lr, locationCallback, Looper.getMainLooper());
            }

        }
        else{
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }
    }

    private void addPathPoint(Location location){
        if(location!= null){
            if(myPosList.size() == 1){
                markedLocation = location;
                Log.d("UHH", "markedLocation" + location.getLatitude());
            }
            CheckAgainstRoute(location);
            locationList.add(location);
            LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
            myPosList.add(pos);

        }
    }

    //private float[] routeDistances = new float[]{30f};
    //private Float[] arrowRotation = new Float[]{0f};

    private void CheckAgainstRoute(Location location){
        if(location.distanceTo(markedLocation) >= routeDistances[stageMarker.getValue()]){ //greater than distance
            markedLocation = location; //old location = last location
            stageMarker.setValue(stageMarker.getValue()+1); // stageMarker ++
            if(stageMarker.getValue() >= routeDistances.length){
                Log.d("UHH", "STop NOW");
                rotation.postValue(-1f);
                isTracking.setValue(false);
            }
            else{
                Log.d("UHH", "sM" + stageMarker.getValue());
                rotation.postValue(arrowRotation[stageMarker.getValue() -1]); //rotation = rotation at sm
            }

        }
//        else if(location.distanceTo(markedLocation) >= (routeDistances[stageMarker.getValue()])/2){
//            rotation.postValue(0f);
//        }
        else{
            Log.d("UHH", "distance" + location.distanceTo(markedLocation));
        }
    }

    private void startForegroundService(){
        isTracking.postValue(true);
        resetValues();
        Log.d("UHH", "work" + isFirst);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        createNotificationChannel(notificationManager);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "tracking_channel")
                .setAutoCancel(false)
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_baseline_directions_run_24)
                .setContentTitle("Fittrak")
                .setContentText("00.00.00.00");
        startForeground(1, notificationBuilder.build());
    }

    private void resetValues(){
        stageMarker.setValue(0);
        myPosList.clear();
        myPosList.add(new LatLng(0,0));

    }

    private void getMainActivityPendingIntent(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setAction("ACTION_SHOW_TRACKING_FRAGMENT");
        PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }



    private void createNotificationChannel(NotificationManager notificationManager){
        NotificationChannel channel = new NotificationChannel("tracking_channel", "tracking", NotificationManager.IMPORTANCE_MIN);
        notificationManager.createNotificationChannel(channel);
    }
}
