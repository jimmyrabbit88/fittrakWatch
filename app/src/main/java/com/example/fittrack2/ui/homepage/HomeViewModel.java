package com.example.fittrack2.ui.homepage;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.fittrack2.DataRepo;

public class HomeViewModel extends ViewModel {

    private DataRepo repo;
    private MutableLiveData<String> mText;
    private MutableLiveData<Integer> mDiff;

    public HomeViewModel() {
        repo = new DataRepo();
    }

    public LiveData<Integer> getText() {
        return repo.getValue();
    }

    public void callRepoGetValue(){
        repo.getAcwrValue();
    }

}