package com.example.festivalawardtracker.ui.student;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.festivalawardtracker.R;
import com.example.festivalawardtracker.Student;
import com.example.festivalawardtracker.StudentActivity;
import com.example.festivalawardtracker.StudentActivityDisplay;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class StudentRecyclerAdapter extends RecyclerView.Adapter<StudentRecyclerAdapter.ViewHolder> implements Filterable {

    private RecyclerViewClickInterface recyclerViewClickInterface;
    Map<String, Student> students;
    List<String> studentIDs=new ArrayList<>();

    public StudentRecyclerAdapter(Map<String,Student> students, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.students=students;
        studentIDs.addAll(students.keySet());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.student_display_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("Student Recycler: studentIDs.size()", ((Integer) studentIDs.size()).toString());
        if (studentIDs.size()>0) {
            String ID = studentIDs.get(position);
            Student s = students.get(ID);
            String name = s.getFullName();
            holder.studentName.setText(name);
            holder.birthday.setText(s.getBirthday());
            holder.age.setText(s.getAge().toString());
            holder.gender.setText(s.getGenderString());
            holder.awardsInfo.setText("Working on it");
        }
    }

    public void updateStudentList() {
        studentIDs.clear();
        studentIDs.addAll(students.keySet());
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {

        //run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<String> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(students.keySet());
            } else {
                for (String studentID : students.keySet()) {
                    if (students.get(studentID).getFullName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredList.add(studentID);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            studentIDs.clear();
            studentIDs.addAll((Collection<? extends String>) results.values);
            notifyDataSetChanged();
        }
    };

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
//                    recyclerViewClickInterface.onItemClick(getAdapterPosition()); // don't know what this did but it was crashing
                    int p=getAdapterPosition();
                    Log.d("RecyclerView Click",students.get(studentIDs.get(p)).getFullName());
                    Intent intent = new Intent( v.getContext(), StudentActivityDisplay.class);
                    intent.putExtra("StudentID", studentIDs.get(p));
                    v.getContext().startActivity(intent);
                }
            });
        }

    }
}