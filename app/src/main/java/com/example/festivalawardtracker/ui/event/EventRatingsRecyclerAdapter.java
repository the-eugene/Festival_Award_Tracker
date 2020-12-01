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

import java.util.List;

public class EventRatingsRecyclerAdapter extends RecyclerView.Adapter<EventRatingsRecyclerAdapter.ViewHolder> {
    List<String> name,age,birthday;
    //used for storing data from recycleView
    private String[] level;
    private Integer[] rating;


    public EventRatingsRecyclerAdapter(List<String> name, List<String> age, List<String> birthday, String[] level, Integer[] rating) {
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
        ViewHolder viewHolder = new ViewHolder(view, new MyCustomLevelEditTextListener(), new MyCustomRatingEditTextListener());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.birthday.setText(birthday.get(position));
        holder.age.setText(age.get(position));
        holder.name.setText(name.get(position));

        //used for storing data from recycleView
        //level set up
        holder.levelTextListener.updatePosition(holder.getAdapterPosition());
        holder.level.setText(level[holder.getAdapterPosition()]);
        //ratings set up
        holder.ratingTextListener.updatePosition(holder.getAdapterPosition());
        holder.rating.setText(rating[holder.getAdapterPosition()]);
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
        public EditText rating;
        public EditText level;
        public MyCustomLevelEditTextListener levelTextListener;
        public MyCustomRatingEditTextListener ratingTextListener;

        public ViewHolder(@NonNull View itemView, MyCustomLevelEditTextListener levelTextListener, MyCustomRatingEditTextListener ratingTextListener) {
            super(itemView);
            name = itemView.findViewById(R.id.StudentName);
            birthday = itemView.findViewById(R.id.Birthday);
            age = itemView.findViewById(R.id.Age);

            //used for storing data from recycleView
            this.rating = (EditText) itemView.findViewById(R.id.editTextNumberRating);
            this.level = (EditText) itemView.findViewById(R.id.editTextNumberLevel);
            this.levelTextListener = levelTextListener;
            this.ratingTextListener = ratingTextListener;
            this.level.addTextChangedListener(levelTextListener);
            this.rating.addTextChangedListener(ratingTextListener);
        }
    }

    //used for storing data from recycleView
    // custom class for level edit text
    private class MyCustomLevelEditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            level[position] = charSequence.toString();

        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }

    //used for storing data from recycleView
    //custom class for rating edit text
    private class MyCustomRatingEditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            rating[position] = Integer.parseInt(charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }
}
