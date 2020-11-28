package com.example.festivalawardtracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.festivalawardtracker.ui.student.RecyclerViewClickInterface;

import java.util.List;


public class EventActivityRecyclerAdapter extends RecyclerView.Adapter<EventActivityRecyclerAdapter.ViewHolder> {


    List<String> eventName, startDate, endDate, eventInstruments;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    public EventActivityRecyclerAdapter(List<String> eventName, List<String> startDate, List<String> endDate, List<String> eventInstrument, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.eventName = eventName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.eventInstruments = eventInstrument;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
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
        holder.eventInstruments.setText(eventInstruments.get(position));
        holder.startDate.setText(startDate.get(position));
        holder.endDate.setText(endDate.get(position));
        holder.eventName.setText(eventName.get(position));
    }

    @Override
    public int getItemCount() {

        if (eventName.size() == 0) {
            return 0;
        } else {
            return eventName.size();
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
                    recyclerViewClickInterface.onItemClick(getAdapterPosition());

                }
            });
        }
    }
}