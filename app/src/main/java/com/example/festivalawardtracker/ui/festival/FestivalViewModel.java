package com.example.festivalawardtracker.ui.festival;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FestivalViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FestivalViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}