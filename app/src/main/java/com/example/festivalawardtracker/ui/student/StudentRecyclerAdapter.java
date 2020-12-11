package com.example.festivalawardtracker.ui.student;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.festivalawardtracker.R;
import com.example.festivalawardtracker.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Preps the information and passes it to the student pages
 * @author  Cayla, Carlos, Eugene, Jimmy
 */
public class StudentRecyclerAdapter extends RecyclerView.Adapter<StudentRecyclerAdapter.ViewHolder> {

    Map<String, Student> studentMap;
    //List<String> studentIDs=new ArrayList<>();
    List<Student> students=new ArrayList<>();

    public StudentRecyclerAdapter(Map<String,Student> students) {
        Log.d(this.getClass().getName(),"Constructor. Size:"+students.size());
        studentMap=students;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.students_recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (students.size()>0) {
            Student s = students.get(position);
            String name = s.getFullName();
            holder.studentName.setText(name);
            holder.birthday.setText(s.getBirthday());
            holder.age.setText(s.getAge().toString());
            String summary=s.retrieveAwardSummary();
            if (summary.length()>0) {
                holder.awardsInfo.setVisibility(View.VISIBLE);
                holder.awardsInfo.setText(summary);
            }
            else
                holder.awardsInfo.setVisibility(View.GONE);
        }
    }

    public void updateStudentList() {
        Log.d(this.getClass().getName(),"updateStudentList Size:"+studentMap.size());
        students.clear();
        students.addAll(studentMap.values());
        Collections.sort(students);
    }

    @Override
    public int getItemCount() {
            return students.size();
    }

    /* Search has been dropped */
//    @Override
//    public Filter getFilter() {
//        return filter;
//    }
//
//    Filter filter = new Filter() {
//
//        //run on background thread
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            List<String> filteredList = new ArrayList<>();
//
//            if (constraint == null || constraint.length() == 0) {
//                filteredList.addAll(students.keySet());
//            } else {
//                for (String studentID : students.keySet()) {
//                    if (students.get(studentID).getFullName().toLowerCase().contains(constraint.toString().toLowerCase())) {
//                        filteredList.add(studentID);
//                    }
//                }
//            }
//
//            FilterResults filterResults = new FilterResults();
//            filterResults.values = filteredList;
//            return filterResults;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            studentIDs.clear();
//            studentIDs.addAll((Collection<? extends String>) results.values);
//            notifyDataSetChanged();
//        }
//    };

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView studentName, birthday, age, awardsInfo;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            studentName = itemView.findViewById(R.id.StudentName);
            birthday = itemView.findViewById(R.id.Birthday);
            age = itemView.findViewById(R.id.Age);
            awardsInfo = itemView.findViewById((R.id.AwardsInfo));

            /* Single press for StudentSummaryActivity */
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int adapterPosition = getAdapterPosition();
                    Log.d("RecyclerView single click", students.get(adapterPosition).getFullName());
                    Intent intent = new Intent( view.getContext(), StudentSummaryActivity.class);
                    intent.putExtra("StudentID", students.get(adapterPosition).ID);
                    view.getContext().startActivity(intent);
                }
            });

            /* Long press for students */
            // @author: Carlos
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int adapterPosition = getAdapterPosition();
                    Intent intent = new Intent( view.getContext(), StudentEditActivity.class);
                    intent.putExtra("StudentID", students.get(adapterPosition).ID);
                    view.getContext().startActivity(intent);
                    return false;
                }
            });
        }

    }
}