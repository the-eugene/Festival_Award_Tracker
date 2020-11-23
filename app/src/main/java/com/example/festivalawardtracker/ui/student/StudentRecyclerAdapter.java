package com.example.festivalawardtracker.ui.student;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.festivalawardtracker.R;

import java.util.List;

public class StudentRecyclerAdapter extends RecyclerView.Adapter<StudentRecyclerAdapter.ViewHolder> {

    List<String> studentNames;
    List<String> birthday;
    List<String> age;
    List<String> gender;
    List<String> awardInfo;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    public StudentRecyclerAdapter(List<String> studentNames,List<String> birthday,List<String> age,List<String> gender, List<String> awardInfo, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.studentNames = studentNames;
        this.birthday = birthday;
        this.age = age;
        this.gender = gender;
        this.awardInfo = awardInfo;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
        Log.d("StudentRecyclerAdapter", "These are my students:" + studentNames);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.student_display, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.studentName.setText(studentNames.get(position));
        holder.birthday.setText(birthday.get(position));
        holder.age.setText(age.get(position));
        holder.gender.setText(gender.get(position));
        holder.awardsInfo.setText(awardInfo.get(position));
    }

    @Override
    public int getItemCount() {
        return studentNames.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView studentName, birthday, age, gender, awardsInfo;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            studentName = itemView.findViewById(R.id.StudentName);
            birthday = itemView.findViewById(R.id.Birthday);
            age = itemView.findViewById(R.id.Age);
            gender = itemView.findViewById(R.id.Gender);
            awardsInfo = itemView.findViewById((R.id.AwardsInfo));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition(), v);
                }
            });
        }

    }
}