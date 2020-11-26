package com.example.festivalawardtracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentRatingsRecyclerAdapter extends RecyclerView.Adapter<StudentRatingsRecyclerAdapter.ViewHolder> {

    List<String> name,age,birthday;

    public StudentRatingsRecyclerAdapter(List<String> name, List<String> age, List<String> birthday) {
        this.name = name;
        this.age = age;
        this.birthday = birthday;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.student_ratings_recyclerview_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.birthday.setText(birthday.get(position));
        holder.age.setText(age.get(position));
        holder.name.setText(name.get(position));


    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView name, birthday, age, rating, level;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.StudentName);
            birthday = itemView.findViewById(R.id.Birthday);
            age = itemView.findViewById(R.id.Age);
            rating = itemView.findViewById(R.id.editTextNumberRating);
            level = itemView.findViewById(R.id.editTextNumberLevel);

        }
    }
}
