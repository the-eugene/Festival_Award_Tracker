package com.example.festivalawardtracker.ui.eventDescription;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.festivalawardtracker.DBManager;
import com.example.festivalawardtracker.EventDescription;
import com.example.festivalawardtracker.R;
import com.example.festivalawardtracker.ui.event.EventActivity;
import com.example.festivalawardtracker.ui.student.StudentEditActivity;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EventDescriptionsRecyclerAdapter extends RecyclerView.Adapter<EventDescriptionsRecyclerAdapter.ViewHolder> {

    private final String TAG = this.getClass().toString();
    private final String fID;
    private List<EventDescription> eventDescriptions=new ArrayList<>();
    private Activity activity;

    public EventDescriptionsRecyclerAdapter(final String fID, Activity activity) {
        Log.d(this.getClass().getName(),"Festival ID Passed: "+fID);
        this.fID=fID;
        this.activity=activity;
//        new Thread(new queryThread(activity,fID)).start();
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

    public void update() {
        new Thread(new queryThread(activity,fID)).start();
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
                    Intent intent = new Intent(v.getContext(), EventActivity.class);
                    intent.putExtra("EVENT_DESCRIPTION_ID", eventDescriptions.get(getAdapterPosition()).ID);
                    v.getContext().startActivity(intent);
                }
            });

            /* Long press for event descriptions */
            // @author: Carlos
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    // TODO This long press is not working. There's a problem with the event descriptions IDs
//                    int adapterPosition = getAdapterPosition();
//                    Intent intent = new Intent( view.getContext(), EventDescriptionsNewActivity.class);
//                    intent.putExtra("EVENT_DESCRIPTION_ID", eventDescriptions.get(adapterPosition));
//                    view.getContext().startActivity(intent);
                    Toast.makeText(view.getContext(), "This long-press is not working.", Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
        }

    }

    class queryThread implements Runnable{
        final Activity activity;
        final String fID;

        queryThread(Activity activity,String fID){
            this.activity=activity;
            this.fID=fID;
        }
        @Override
        public void run(){
            Log.d(this.getClass().getName(),"Running Query on "+fID);
            Query query= DBManager.currentDB.child(EventDescription.class.getSimpleName()).orderByChild("festivalID").equalTo(fID);
            final Map<String,EventDescription> result=DBManager.EventDescriptions.getMapByQuery(query);
            DBManager.EventDescriptions.putAll(result); //not strictly necessary, just keeps cache fresh
            activity.runOnUiThread(new Runnable() {@Override public void run() {updateList(new ArrayList<>( result.values()));}});
        }
    }
}
