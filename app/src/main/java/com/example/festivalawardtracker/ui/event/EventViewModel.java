package com.example.festivalawardtracker.ui.event;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * @author carloswashingtonmercado@gmail.com
 */
public class EventViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public EventViewModel() {
//        mText = new MutableLiveData<>();
//        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}