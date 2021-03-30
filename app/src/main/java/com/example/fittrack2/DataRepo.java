package com.example.fittrack2;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fittrack2.models.WorkloadDiff;
import com.example.fittrack2.models.WorkloadSteps;
import com.example.fittrack2.services.VolleySingleton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class DataRepo {
    private Application application;
    public String valuea = "AAA";
    public MutableLiveData<Integer> value = new MutableLiveData<>(0);
    public MutableLiveData<Integer> diff = new MutableLiveData<>(0);

    public DataRepo(){

    }

    public MutableLiveData getValue() {
        return value;
    }

    public MutableLiveData getDiff() { return diff; }

    public void getAcwrValue(){
        final RequestQueue rq = VolleySingleton.getInstance().getRequestQueue();

        String url = "https://6tpmyhkglc.execute-api.eu-west-1.amazonaws.com/live/workload?username=d6417be4-b4a7-4d79-ba3d-a2fe593dd12e";

        final StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        Gson gson = new Gson();
                        WorkloadDiff diff = gson.fromJson(response, WorkloadDiff.class);
                        System.out.println(diff.getAct_diff());
                        int currentDifferece = diff.getAct_diff();
                        //System.out.println(val[0]);
                        value.postValue(currentDifferece);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        value.postValue(0);
                    }
                }
        );

        rq.add(stringRequest);
    }
}
