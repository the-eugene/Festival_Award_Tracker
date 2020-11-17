package com.example.festivalawardtracker.ui.student;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * @author carloswashingtonmercado@gmail.com
 */
public class StudentViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public StudentViewModel() {
//        mText = new MutableLiveData<>();
//        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}