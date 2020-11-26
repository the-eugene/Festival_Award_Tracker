package com.example.festivalawardtracker.ui.student;

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StudentRecyclerAdapter extends RecyclerView.Adapter<StudentRecyclerAdapter.ViewHolder> implements Filterable {

    List<String> studentNames, birthday, age, awardInfo;
    List<String> studentNamesList;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    public StudentRecyclerAdapter(List<String> studentNames, List<String> birthday, List<String> age, List<String> awardInfo, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.studentNames = studentNames;
        this.birthday = birthday;
        this.age = age;
        this.awardInfo = awardInfo;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
        this.studentNamesList = new ArrayList<>(studentNames);
        Log.d("StudentRecyclerAdapter", "These are my students:" + studentNames);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.students_recyclerview_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.studentName.setText(studentNames.get(position));
        holder.birthday.setText(birthday.get(position));
        holder.age.setText(age.get(position));
        holder.awardsInfo.setText(awardInfo.get(position));
    }

    @Override
    public int getItemCount() {
        return studentNames.size();
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
                filteredList.addAll(studentNamesList);
            } else {
                for (String student : studentNamesList) {
                    if (student.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredList.add(student);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            studentNames.clear();
            studentNames.addAll((Collection<? extends String>) results.values);
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView studentName, birthday, age, awardsInfo;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            studentName = itemView.findViewById(R.id.StudentName);
            birthday = itemView.findViewById(R.id.Birthday);
            age = itemView.findViewById(R.id.Age);
            awardsInfo = itemView.findViewById((R.id.AwardsInfo));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition());
                }
            });
        }

    }
}