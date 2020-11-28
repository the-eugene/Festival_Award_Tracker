package com.example.festivalawardtracker.ui.event;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.festivalawardtracker.R;
import com.example.festivalawardtracker.ui.student.RecyclerViewClickInterface;
import java.util.List;

public class EventNewRecyclerAdapter extends RecyclerView.Adapter<EventNewRecyclerAdapter.ViewHolder> {

    List<String> studentNames, birthday, age;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    public EventNewRecyclerAdapter(List<String> studentNames, List<String> birthday, List<String> age, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.studentNames = studentNames;
        this.birthday = birthday;
        this.age = age;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
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

        holder.studentName.setText(studentNames.get(position));
        holder.birthday.setText(birthday.get(position));
        holder.age.setText(age.get(position));
    }

    @Override
    public int getItemCount() {

        if (studentNames.size() == 0){
            return 0;
        }else{
            return studentNames.size();
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        TextView studentName, birthday, age;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            studentName = itemView.findViewById(R.id.StudentName);
            birthday = itemView.findViewById(R.id.Birthday);
            age = itemView.findViewById(R.id.Age);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition());
                }
            });
        }

    }
}
