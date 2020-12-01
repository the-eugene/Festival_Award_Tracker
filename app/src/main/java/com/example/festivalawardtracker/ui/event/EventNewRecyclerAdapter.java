package com.example.festivalawardtracker.ui.event;


import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.festivalawardtracker.DBManager;
import com.example.festivalawardtracker.Event;
import com.example.festivalawardtracker.R;
import com.example.festivalawardtracker.Student;
import com.example.festivalawardtracker.ui.student.RecyclerViewClickInterface;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class EventNewRecyclerAdapter extends RecyclerView.Adapter<EventNewRecyclerAdapter.ViewHolder> {

    Event event;
    Activity activity;
    List<Student> students=new ArrayList<>();

    public EventNewRecyclerAdapter(Event event, Activity activity) {
        this.event = event;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.events_new_recyclerview_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("BindViewHolder", Integer.valueOf(position).toString());
        holder.studentName.setText(students.get(position).getFullName());
        holder.birthday.setText(students.get(position).getBirthday());
        holder.age.setText(students.get(position).getAge(event.end).toString());
        holder.checkbox.setSelected(event.studentIDs.contains(students.get(position).ID));
    }

    @Override
    public int getItemCount() {
        return students.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        TextView studentName, birthday, age;
        CheckBox checkbox;

        public ViewHolder(@NonNull final View itemView) {

            super(itemView);
            studentName = itemView.findViewById(R.id.StudentName);
            birthday = itemView.findViewById(R.id.Birthday);
            age = itemView.findViewById(R.id.Age);
            checkbox=itemView.findViewById(R.id.checkBox_selectStudent);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Event Edit Recycler Click", students.get(getAdapterPosition()).getFullName());
                    String sID=students.get(getAdapterPosition()).ID;
                    if (!event.studentIDs.contains(sID))
                        event.studentIDs.add(sID);
                    else
                        event.studentIDs.remove(sID);
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
    public void update() {
        students.clear();
        for(Student s:DBManager.Students.values()){
            if(s.instruments.contains(event.getDescription().instrument))
                students.add(s);
        }
        students.sort(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return (o1.lastName+o1.firstName).compareTo(o2.lastName+o2.firstName);
            }
        });
        notifyDataSetChanged();
    }
}
