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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.ViewHolder>{
    Map<String, Event> events;
    List<String> eventsIDs=new ArrayList<>();
    Map<String, EventDescription> eventDescription;
    List<String> eventsDescriptionIDs=new ArrayList<>();


//    List<String> eventName, startDate, endDate, eventInstruments;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    public EventRecyclerAdapter(Map<String, Event> events, Map<String,EventDescription> eventDescription, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.events = events;
        eventsIDs.addAll(events.keySet());
        this.eventDescription = eventDescription;
        eventsDescriptionIDs.addAll(eventDescription.keySet());
//        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.events_recyclerview_row, parent, false);
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
            //TODO: not sure how the instruments are stored?
            holder.eventInstruments.setText("Voice");
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
        TextView eventName, startDate, endDate, eventInstruments;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.textViewRowEventName);
            startDate = itemView.findViewById(R.id.textViewRowDateStart);
            endDate = itemView.findViewById(R.id.textViewRowDateEnd);
            eventInstruments = itemView.findViewById(R.id.textViewRowEventInstruments);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition());

                }
            });

        }
    }
    }
