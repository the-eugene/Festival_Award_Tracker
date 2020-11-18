package com.example.festivalawardtracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentDisplay extends Fragment {
    private RecyclerView recyclerView;
    DatabaseReference database;

    studentAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment_student, container, false);

        database = FirebaseDatabase.getInstance().getReference();
//        recyclerView = view.findViewById(R.id.student_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        FirebaseRecyclerOptions<studentDatabase> options
                = new FirebaseRecyclerOptions.Builder<studentDatabase>().setQuery(database,studentDatabase.class).build();
//        mAdapter = new studentAdapter(options);
        recyclerView.setAdapter(mAdapter);
        return view;
    }
}
