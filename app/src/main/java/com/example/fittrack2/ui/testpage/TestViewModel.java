package com.example.fittrack2.ui.testpage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TestViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TestViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Begin Coutts test");
    }

    public LiveData<String> getText() {
        return mText;
    }
}