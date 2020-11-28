package com.example.festivalawardtracker;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.festivalawardtracker.ui.student.RecyclerViewClickInterface;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventDescriptionsRecyclerAdapter extends RecyclerView.Adapter<EventDescriptionsRecyclerAdapter.ViewHolder> {


    private RecyclerViewClickInterface recyclerViewClickInterface;
    private List<EventDescription> eventDescriptions=new ArrayList<>();

    public EventDescriptionsRecyclerAdapter(final String fID, Activity activity) {
        Log.d(this.getClass().getName(),"Festival ID Passed: "+fID);
        class queryThread implements Runnable{
            final Activity activity;
            queryThread(Activity activity){
                this.activity=activity;
            }
            @Override
            public void run(){
                Query query=DBManager.currentDB.child(EventDescription.class.getSimpleName()).orderByChild("festivalID").equalTo(fID);
                final Map<String,EventDescription> result=DBManager.EventDescriptions.getMapByQuery(query);
                DBManager.EventDescriptions.putAll(result); //not strictly necessary, just keeps cache fresh
                activity.runOnUiThread(new Runnable() {@Override public void run() {updateList(new ArrayList<>( result.values()));}});
            }
        }
        new Thread(new queryThread(activity)).start();
    }

    private void updateList(List<EventDescription> r) {
        eventDescriptions=r;
        notifyDataSetChanged();
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
        if (eventDescriptions.size()>0) {
            holder.Name.setText(eventDescriptions.get(position).name);
            holder.Instrument.setText(eventDescriptions.get(position).instrument.toString());
        }
    }

    @Override
    public int getItemCount() {
       return eventDescriptions.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView Name, Instrument;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            Name = itemView.findViewById(R.id.textView_eventName);
            Instrument = itemView.findViewById(R.id.textViewInstrument);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    recyclerViewClickInterface.onItemClick(getAdapterPosition());
                    Intent Intent = new Intent(v.getContext(), EventDescriptionsActivity.class);
                    Intent.putExtra("EventID",eventDescriptions.get(getAdapterPosition()).ID);
                    v.getContext().startActivity(Intent);
                }
            });
        }

    }
}
