package com.example.fittrack2.ui.homepage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("95%");
    }

    public LiveData<String> getText() {
        return mText;
    }

    //Logic for the page goes in here

}