package com.example.festivalawardtracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentDisplayRecyclerAdapter extends RecyclerView.Adapter<StudentDisplayRecyclerAdapter.ViewHolder> {

    List<String> year,event,ccs,tp,awards,level;

    public StudentDisplayRecyclerAdapter(List<String> year, List<String> event, List<String> ccs, List<String> tp, List<String> awards, List<String> level) {
        this.year = year;
        this.event = event;
        this.ccs = ccs;
        this.tp = tp;
        this.awards = awards;
        this.level = level;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.students_display_recyclerview_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.year.setText(year.get(position));
        holder.event.setText(event.get(position));
        holder.ccs.setText(ccs.get(position));
        holder.tp.setText(tp.get(position));
        holder.awards.setText(awards.get(position));
        holder.level.setText(level.get(position));
    }

    @Override
    public int getItemCount() {

        if (event.size() == 0){
            return 0;
        }else {
            return event.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView year,event,ccs,tp,awards,level;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            year = itemView.findViewById(R.id.textViewStudentDisplayRecyclerviewYear);
            event = itemView.findViewById(R.id.textViewStudentDisplayRecyclerviewEvent);
            ccs = itemView.findViewById(R.id.textViewStudentDisplayRecyclerviewCCS);
            tp = itemView.findViewById(R.id.textViewStudentDisplayRecyclerviewTP);
            awards = itemView.findViewById(R.id.textViewStudentDisplayRecyclerviewAwards);
            level = itemView.findViewById(R.id.textViewStudentDisplayRecyclerviewLevel);

        }
    }
}
