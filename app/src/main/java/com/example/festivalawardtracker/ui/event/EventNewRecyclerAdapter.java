package com.example.festivalawardtracker.ui.event;


import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.festivalawardtracker.DBManager;
import com.example.festivalawardtracker.Event;
import com.example.festivalawardtracker.R;
import com.example.festivalawardtracker.Student;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Inside the new or edit event activity there is a recycle view full of students that can be added to events
 * This is populated with this adapter
 * The students are filtered by the instrument that the event is for
 * Then displayed next to check boxes to be added to events
 * @author Cayla, Jimmy, Eugene
 */
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
        MaterialCheckBox checkbox=holder.itemView.findViewById(R.id.checkBox_selectStudent);
        TextView studentName = holder.itemView.findViewById(R.id.StudentName);
        TextView birthday = holder.itemView.findViewById(R.id.Birthday);
        TextView age = holder.itemView.findViewById(R.id.Age);

        studentName.setText(students.get(position).getFullName());
        birthday.setText(students.get(position).getBirthday());
        age.setText(students.get(position).getAge(event.end).toString());
        checkbox.setEnabled(false);
        checkbox.setChecked(event.studentIDs.contains(students.get(position).ID));
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    /**
     * adds students to events when they are clicked
     */
    class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String sID=students.get(getAdapterPosition()).ID;
                    Log.d("Event Edit Recycler Click", students.get(getAdapterPosition()).getFullName()+" "+event.studentIDs.contains(sID));
                    if (event.studentIDs.contains(sID))
                        event.studentIDs.remove(sID);
                    else
                        event.studentIDs.add(sID);
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }

    /**
     * updates student list based on what instruments they play
     */
    public void update() {
        students.clear();
        for(Student s:DBManager.Students.values()){
            if(s.instruments.contains(event.retrieveDescription().instrument))
                students.add(s);
        }

        /**
         * sorts students
         */
        students.sort(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return (o1.lastName+o1.firstName).compareTo(o2.lastName+o2.firstName);
            }
        });
        notifyDataSetChanged();
    }
}
