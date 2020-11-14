package com.example.festivalawardtracker.ui.festival;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.festivalawardtracker.R;

public class FestivalFragment extends Fragment {

    private FestivalViewModel festivalViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        festivalViewModel =
                new ViewModelProvider(this).get(FestivalViewModel.class);
        View root = inflater.inflate(R.layout.main_fragment_festival, container, false);
        return root;
    }
}