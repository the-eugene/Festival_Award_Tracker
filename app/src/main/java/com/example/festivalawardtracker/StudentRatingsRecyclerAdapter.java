package com.example.festivalawardtracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentRatingsRecyclerAdapter extends RecyclerView.Adapter<StudentRatingsRecyclerAdapter.ViewHolder> {

    List<String> name,age,birthday,level,gender;
    RatingBar ratingBar;

    public StudentRatingsRecyclerAdapter(List<String> name, List<String> age, List<String> birthday, List<String> level, List<String> gender) {
        this.name = name;
        this.age = age;
        this.birthday = birthday;
        this.level = level;
        this.gender = gender;
//        this.ratingBar = ratingBar;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_student_ratings_recyclerview, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.gender.setText(gender.get(position));
        holder.birthday.setText(birthday.get(position));
        holder.age.setText(age.get(position));
        holder.level.setText(level.get(position));
        holder.name.setText(name.get(position));
//        holder.ratingBar.

    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView name, birthday, age, gender, level;
//        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.StudentName);
            birthday = itemView.findViewById(R.id.Birthday);
            age = itemView.findViewById(R.id.Age);
            gender = itemView.findViewById(R.id.Gender);
            level = itemView.findViewById(R.id.Level);
//            ratingBar = itemView.findViewById(R.id.Rating);
        }
    }
}
