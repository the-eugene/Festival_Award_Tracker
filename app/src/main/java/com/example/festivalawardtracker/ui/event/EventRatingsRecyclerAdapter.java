package com.example.festivalawardtracker.ui.event;

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

import com.example.festivalawardtracker.R;

import java.util.ArrayList;
import java.util.List;

public class EventRatingsRecyclerAdapter extends RecyclerView.Adapter<EventRatingsRecyclerAdapter.ViewHolder> {
    List<String> name,age,birthday;
    //used for storing data from recycleView
    public static ArrayList<EventStudentRatingEditTextBoxes> level;
    public static ArrayList<EventStudentRatingEditTextBoxes> rating;


    public EventRatingsRecyclerAdapter(List<String> name, List<String> age, List<String> birthday, ArrayList<EventStudentRatingEditTextBoxes> level, ArrayList<EventStudentRatingEditTextBoxes> rating) {
        this.name = name;
        this.age = age;
        this.birthday = birthday;
        this.level = level;
        this.rating = rating;
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
        //used for storing data from recycleView
        //level set up
        holder.editLevel.setText(level.get(position).getEditLevelValue());
        //ratings set up
        holder.editRating.setText(rating.get(position).getEditRatingValue());
    }

    @Override
    public int getItemCount() {

        if (name.size() == 0){
            return 0;
        }else {
            return name.size();
        }
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

            editRating.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    rating.get(getAdapterPosition()).setEditLevelValue(editRating.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            editLevel.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    level.get(getAdapterPosition()).setEditRatingValue(editLevel.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
    }

}
