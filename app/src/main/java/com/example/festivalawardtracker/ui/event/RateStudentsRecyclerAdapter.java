package com.example.festivalawardtracker.ui.event;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.festivalawardtracker.Event;
import com.example.festivalawardtracker.EventDescription;
import com.example.festivalawardtracker.R;
import com.example.festivalawardtracker.ui.student.RecyclerViewClickInterface;
import com.example.festivalawardtracker.ui.student.StudentSummaryActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RateStudentsRecyclerAdapter extends RecyclerView.Adapter<RateStudentsRecyclerAdapter.ViewHolder>{
    Map<String, Event> events;
    List<String> eventsIDs = new ArrayList<>();
    Map<String, EventDescription> eventDescription;
    List<String> eventsDescriptionIDs = new ArrayList<>();


    public RateStudentsRecyclerAdapter(Map<String, Event> events, Map<String,EventDescription> eventDescription, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.events = events;
        eventsIDs.addAll(events.keySet());
        this.eventDescription = eventDescription;
        eventsDescriptionIDs.addAll(eventDescription.keySet());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.rate_students_recyclerview_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("Event Recycler: eventIDs.size()", ((Integer) eventsIDs.size()).toString());
        if(eventsIDs.size()>0){
            String ID = eventsIDs.get(position);
            String dID = eventsDescriptionIDs.get(position);
            Event e = events.get(ID);
            EventDescription ed = eventDescription.get(dID);
            holder.eventName.setText(ed.getName());
            holder.startDate.setText(e.getStart());
            holder.endDate.setText(e.getEnd());
        }
    }

    public void updateEventsList(){
        eventsIDs.clear();
        eventsIDs.addAll(events.keySet());
    }

    @Override
    public int getItemCount() {
        if (events.size() == 0){
            return 0;
        }else{
            return events.size();
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        TextView eventName, startDate, endDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.textView_eventNameF);
            startDate = itemView.findViewById(R.id.textView_startDateF);
            endDate = itemView.findViewById(R.id.textView_endDateF);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = getAdapterPosition();
//                    Log.d("RecyclerView single click", events.get(eventsIDs.get(adapterPosition)));
                    Intent intent = new Intent( v.getContext(), EventsRatingsActivity.class);
                    intent.putExtra("EventID", eventsIDs.get(adapterPosition));
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
