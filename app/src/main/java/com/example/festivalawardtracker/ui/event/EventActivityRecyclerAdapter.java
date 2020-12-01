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
import com.example.festivalawardtracker.Festival;
import com.example.festivalawardtracker.R;
import com.example.festivalawardtracker.ui.eventDescription.EventDescriptionsRecyclerAdapter;
import com.example.festivalawardtracker.ui.student.RecyclerViewClickInterface;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class EventActivityRecyclerAdapter extends RecyclerView.Adapter<EventActivityRecyclerAdapter.ViewHolder> {

    List <Event> events=new ArrayList<>();;
    Activity activity;
    EventDescription eventDescription;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    public EventActivityRecyclerAdapter(EventDescription eventDescription, Activity activity) {
        this.eventDescription=eventDescription;
        this.activity=activity;
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
        if(events.size()>0){
            Event e = events.get(position);
            holder.startDate.setText(e.getStart());
            holder.endDate.setText(e.getEnd());
        }
    }

    @Override
    public int getItemCount() {
            return events.size();
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
                    Log.d("RecycleView Click", events.get(p).ID);
                    Intent intent = new Intent(v.getContext(),EventNewActivity.class);
                    intent.putExtra("EVENT_ID", events.get(p).ID);
                    intent.putExtra("EVENT_DESCRIPTION_ID", eventDescription.ID);
                    v.getContext().startActivity(intent);
                }


            });
        }
    }

    public void update() {
        new Thread(new EventActivityRecyclerAdapter.queryThread(activity,eventDescription.ID)).start();
    }

    private void updateList(List<Event> r) {
        r.sort(new Event.sortByYear());
        events=r;
        notifyDataSetChanged();
    }

    class queryThread implements Runnable{
        final Activity activity;
        final String edID;

        queryThread(Activity activity,String ID){
            this.activity=activity;
            this.edID=ID;
        }
        @Override
        public void run(){
            Log.d(this.getClass().getName(),"Running Query on "+edID);
            Query query= DBManager.currentDB.child(Event.class.getSimpleName()).orderByChild("eventDescriptionID").equalTo(edID);
            final Map<String,Event> result=DBManager.Events.getMapByQuery(query);
            DBManager.Events.putAll(result); //not strictly necessary, just keeps cache fresh
            activity.runOnUiThread(new Runnable() {@Override public void run() {updateList(new ArrayList<>( result.values()));}});
        }
    }
}