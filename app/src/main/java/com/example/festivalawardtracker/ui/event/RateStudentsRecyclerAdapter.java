package com.example.festivalawardtracker.ui.event;

import android.app.Activity;
import android.content.Intent;
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
import com.example.festivalawardtracker.R;
import com.example.festivalawardtracker.SchoolYear;
import com.example.festivalawardtracker.ui.Utilities;

import java.util.List;


/**
 * Adapter for the Rate Student fragment
 * Pulls the event information and preps it to be displayed
 * @see RateStudentsFragment
 */
public class RateStudentsRecyclerAdapter extends RecyclerView.Adapter<RateStudentsRecyclerAdapter.ViewHolder>{
    List<String> eventIDs;
    public RateStudentsRecyclerAdapter(SchoolYear year) {
        eventIDs=year.getEventIDs();
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
        Log.d("Event Recycler: eventIDs.size()", ((Integer) eventIDs.size()).toString());
        if(eventIDs.size()>0){
            String ID = eventIDs.get(position);
            Event e=DBManager.Events.get(ID);
            EventDescription ed=e.retrieveDescription();
            holder.eventName.setText(ed.getName());
            holder.startDate.setText(e.getStart());
            holder.endDate.setText(e.getEnd());
        }
    }

    @Override
    public int getItemCount() {
        return eventIDs.size();
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
                    Log.d(this.getClass().getName(), "Clicked: "+eventIDs.get(adapterPosition));
                    Intent intent = new Intent( v.getContext(), EventsRatingsActivity.class);
                    intent.putExtra(Utilities.EVENT_ID, eventIDs.get(adapterPosition));
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
