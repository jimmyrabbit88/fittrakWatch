package com.example.fittrack2;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.amazonaws.mobileconnectors.cognitoauth.Auth;
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession;
import com.amplifyframework.core.Amplify;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fittrack2.models.WorkloadDiff;
import com.example.fittrack2.models.WorkloadSteps;
import com.example.fittrack2.services.VolleySingleton;
import com.example.fittrack2.ui.homepage.HomeFragment;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class DataRepo {
    private Application application;
    public String valuea = "AAA";
    public String username = "";
    public MutableLiveData<Integer> value = new MutableLiveData<>(0);
    public MutableLiveData<Integer> diff = new MutableLiveData<>(0);

    public DataRepo(){

    }

    public MutableLiveData getValue() {
        return value;
    }

    public MutableLiveData getDiff() { return diff; }

    public void getAcwrValue(){


        Amplify.Auth.fetchAuthSession(
                result -> {
                    AWSCognitoAuthSession cognitoAuthSession = (AWSCognitoAuthSession) result;
                    switch(cognitoAuthSession.getIdentityId().getType()) {
                        case SUCCESS:
                            buildRequest(cognitoAuthSession.getUserSub().getValue());
                            Log.i("AuthQuickStart", "IdentityId: " + cognitoAuthSession.getUserSub().getValue());
                            break;
                        case FAILURE:
                            Log.i("AuthQuickStart", "IdentityId not present because: " + cognitoAuthSession.getIdentityId().getError().toString());
                    }
                },
                error -> Log.e("AuthQuickStart", error.toString())
        );



    }

    private void buildRequest(String username){
        System.out.println("ready " + username);
        String url = "https://6tpmyhkglc.execute-api.eu-west-1.amazonaws.com/live/workload?username=" + username;

        final RequestQueue rq = VolleySingleton.getInstance().getRequestQueue();

        final StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                response -> {
                    System.out.println(response);
                    Gson gson = new Gson();
                    WorkloadDiff diff = gson.fromJson(response, WorkloadDiff.class);
                    System.out.println(diff.getAct_diff());
                    int currentDifferece = diff.getAct_diff();
                    //System.out.println(val[0]);
                    value.postValue(currentDifferece);
                },
                error -> value.postValue(0)
        );

        rq.add(stringRequest);
    }
}
