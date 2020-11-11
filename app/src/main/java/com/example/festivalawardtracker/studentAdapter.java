package com.example.festivalawardtracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;



public class studentAdapter extends FirebaseRecyclerAdapter<studentDatabase, DatabaseViewHolder> {

    public studentAdapter(@NonNull FirebaseRecyclerOptions<studentDatabase> options){
        super(options);
    }

    @Override
    public void onBindViewHolder(@NonNull DatabaseViewHolder holder, int position, @NonNull studentDatabase model) {
        holder.setName(model.getFname() + model.getLname());
        holder.setGender(model.getGender());
        holder.setBirthday(model.getBdate());
    }

    @NonNull
    @Override
    public DatabaseViewHolder
    onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_recyclerview_row_studentminiawards, parent, false);
        return new DatabaseViewHolder(view);
    }

}
