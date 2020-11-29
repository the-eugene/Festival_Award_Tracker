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
import com.example.festivalawardtracker.Festival;
import com.example.festivalawardtracker.R;
import com.example.festivalawardtracker.ui.student.RecyclerViewClickInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class EventActivityRecyclerAdapter extends RecyclerView.Adapter<EventActivityRecyclerAdapter.ViewHolder> {

    Map<String, Event> events;
    List<String> eventIDs = new ArrayList<>();
    List<String> eventName, startDate, endDate, eventInstruments;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    public EventActivityRecyclerAdapter(Map<String, Event> events) {
//        this.eventName = eventName;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.eventInstruments = eventInstrument;
//        this.recyclerViewClickInterface = recyclerViewClickInterface;
        this.events = events;
        this.eventIDs.addAll(events.keySet());
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.events_recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(eventIDs.size()>0){
            String ID = eventIDs.get(position);
            Event e = events.get(ID);
            holder.eventName.setText(e.getDescription().getName());
            holder.eventInstruments.setText(e.getDescription().getInstrument().toString());
            holder.startDate.setText(e.getStart());
            holder.endDate.setText(e.getEnd());
        }
//        holder.eventInstruments.setText(eventInstruments.get(position));
//        holder.startDate.setText(startDate.get(position));
//        holder.endDate.setText(endDate.get(position));
//        holder.eventName.setText(eventName.get(position));
    }

    public void updateEventList(){
        eventIDs.clear();
        eventIDs.addAll(events.keySet());
    }

    @Override
    public int getItemCount() {

        if (events.size() == 0) {
            return 0;
        } else {
            return events.size();
        }


    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView eventName, startDate, endDate, eventInstruments;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.textView_eventName);
            startDate = itemView.findViewById(R.id.textView_startDate);
            endDate = itemView.findViewById(R.id.textView_endDate);
            eventInstruments = itemView.findViewById(R.id.textViewInstrument);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int p=getAdapterPosition();
                    Log.d("RecycleView Click", eventIDs.get(p));
                    Intent intent = new Intent(v.getContext(),EventsRatingsActivity.class);
                    intent.putExtra("eventIDs", eventIDs.get(p));
                    v.getContext().startActivity(intent);
                }


            });
        }
    }
}