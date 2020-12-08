package com.example.festivalawardtracker.ui.student;

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
import com.example.festivalawardtracker.EventDescription;
import com.example.festivalawardtracker.Performance;
import com.example.festivalawardtracker.R;
import com.example.festivalawardtracker.SchoolYear;
import com.example.festivalawardtracker.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudentSummaryRecyclerAdapter extends RecyclerView.Adapter<StudentSummaryRecyclerAdapter.ViewHolder> {
    private final Student student;
    private List<Performance>performances;

    public StudentSummaryRecyclerAdapter(final Student student) {
        Log.d(this.getClass().getName(),"Constructor");
        this.student=student;
        this.performances=new ArrayList<>(student.performances);
        Collections.sort(performances, new Performance.sortByYear());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.students_summary_recyclerview_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (performances.size()>0){
            Event event =performances.get(position).retrieveEvent();
            EventDescription ed=DBManager.EventDescriptions.get(event.getEventDescriptionID());
            SchoolYear year = event.retrieveYear();
            holder.year.setText(year.getName());
            holder.event.setText(ed.getName());
            holder.info.setText(
                    String.format("Level:%s, Rating:%s, TAP:%d, CCS:%d",
                            performances.get(position).getLevel(),
                            ((Integer)performances.get(position).getRating()).toString(),
                            student.totalAccumulatedPoints(ed),
                            student.findCCS(performances.get(position))
                    )
            );
//            holder.ccs.setText("WIP");
//            holder.tp.setText("WIP");
//            holder.awards.setText();
//            holder.level.setText();
        }
    }

    @Override
    public int getItemCount() {
        return student.performances.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView year,event,awards,info;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            year = itemView.findViewById(R.id.textViewStudentSummaryRecyclerviewYear);
            event = itemView.findViewById(R.id.textViewStudentSummaryRecyclerviewEventName);
            info = itemView.findViewById(R.id.textViewStudentSummaryRecycleviewCcsTpLevelAwards);
        }
    }
}
