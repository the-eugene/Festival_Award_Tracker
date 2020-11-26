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

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.ViewHolder>{

    List<String> eventName, startDate, endDate, eventInstruments;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    public EventRecyclerAdapter(List<String> eventName, List<String> startDate, List<String> endDate, List<String> eventInstruments, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.eventName = eventName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.eventInstruments = eventInstruments;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
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

        holder.eventInstruments.setText(eventInstruments.get(position));
        holder.endDate.setText(endDate.get(position));
        holder.startDate.setText(startDate.get(position));
        holder.eventName.setText(eventName.get(position));
    }

    @Override
    public int getItemCount() {
        return eventName.size();
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
