package com.example.festivalawardtracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.festivalawardtracker.ui.student.RecyclerViewClickInterface;

import java.util.List;

public class EventDescriptionsRecyclerAdapter extends RecyclerView.Adapter<EventDescriptionsRecyclerAdapter.ViewHolder> {

    List<String> Name,Instrument;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    public EventDescriptionsRecyclerAdapter(List<String> name, List<String> instrument, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.Name = name;
        this.Instrument = instrument;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.event_descriptions_recyclerview_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.Name.setText(Name.get(position));
        holder.Instrument.setText(Instrument.get(position));
    }

    @Override
    public int getItemCount() {

        if (Name.size() == 0){
            return 0;
        }else{
            return Name.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView Name, Instrument;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            Name = itemView.findViewById(R.id.textViewEventName);
            Instrument = itemView.findViewById(R.id.textViewInstrument);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition());
                }
            });
        }

    }
}
