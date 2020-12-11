package com.example.festivalawardtracker.ui.event;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.festivalawardtracker.DBManager;
import com.example.festivalawardtracker.Event;
import com.example.festivalawardtracker.Performance;
import com.example.festivalawardtracker.R;
import com.example.festivalawardtracker.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for the Event Ratings activity
 * Pulls the event and student information and preps it to be displayed
 * @author Cayla, Jimmy, Eugene
 */
public class EventRatingsRecyclerAdapter extends RecyclerView.Adapter<EventRatingsRecyclerAdapter.ViewHolder> {
    public List<EditText> level=new ArrayList<>();
    public List<EditText> rating=new ArrayList<>();
    public List<String> studentIDs;
    public List<Performance> performances;

    Activity activity;
    Event event;

    public EventRatingsRecyclerAdapter(String EventID, Activity activity) {
        event = DBManager.Events.get(EventID);
        studentIDs=event.studentIDs;
        performances=new ArrayList<>(studentIDs.size());
        this.activity=activity;
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
        if (studentIDs.size() > 0) {
            //tie studentID list, level and rating by position
            Student s = DBManager.Students.get(studentIDs.get(position));
            level.add(position, holder.editLevel);
            rating.add(position, holder.editRating);
            //disable students that already have performances and therefore awards
            performances.add(position, null);
            for (Performance p: s.performances) {
                if(p.getEventID().equals(event.ID)){
                    performances.add(position,p);
                    holder.editLevel.setText(p.getLevel());
                    holder.editLevel.setEnabled(false);
                    holder.editRating.setText(Integer.toString(p.getRating()));
                    holder.editRating.setEnabled(false);
                }
            }

            holder.birthday.setText(s.getBirthday());
            holder.age.setText(Integer.toString(s.getAge(event.end))); // their age at the end of the event
            holder.name.setText(s.getFullName());
        }
    }

    @Override
    public int getItemCount() {
        return studentIDs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, birthday, age;

        //used for storing data from recycleView
        protected EditText editRating;
        protected EditText editLevel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.StudentName);
            birthday = itemView.findViewById(R.id.Birthday);
            age = itemView.findViewById(R.id.Age);

            //used for storing data from recycleView
            editRating = (EditText) itemView.findViewById(R.id.editTextNumberRating);
            editLevel = (EditText) itemView.findViewById(R.id.editTextNumberLevel);

//            editRating.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    rating.get(getAdapterPosition()).setEditLevelValue(editRating.getText().toString());
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                }
//            });
//
//            editLevel.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    level.get(getAdapterPosition()).setEditRatingValue(editLevel.getText().toString());
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                }
//            });
        }
    }

}
