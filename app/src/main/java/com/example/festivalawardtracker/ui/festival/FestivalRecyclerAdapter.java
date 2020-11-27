package com.example.festivalawardtracker.ui.festival;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.festivalawardtracker.EventDescription;
import com.example.festivalawardtracker.EventDescriptionsActivity;
import com.example.festivalawardtracker.Festival;
import com.example.festivalawardtracker.FestivalActivity;
import com.example.festivalawardtracker.R;
import com.example.festivalawardtracker.ui.student.RecyclerViewClickInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FestivalRecyclerAdapter extends RecyclerView.Adapter<FestivalRecyclerAdapter.ViewHolder> {

    private RecyclerViewClickInterface recyclerViewClickInterface;
    Map<String, Festival> festivalNames;
    List<String> festivalIDs=new ArrayList<>();



    public FestivalRecyclerAdapter(Map<String, Festival> festivalNames, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.festivalNames = festivalNames;
        festivalIDs.addAll(festivalNames.keySet());
//        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.festivals_recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("Festival Recycler: FestivalID.size()", ((Integer) festivalIDs.size()).toString());
        if(festivalIDs.size()>0){
            String ID = festivalIDs.get(position);
            Festival f = festivalNames.get(ID);
            holder.festivalName.setText(f.getName());
        }
    }

    public void updateFestivalList() {
        festivalIDs.clear();
        festivalIDs.addAll(festivalNames.keySet());
    }

    @Override
    public int getItemCount() {

        if (festivalNames.size() == 0){
            return 0;
        }else{
            return festivalNames.size();
        }


    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView festivalName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            festivalName = itemView.findViewById(R.id.rowFestivalName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    recyclerViewClickInterface.onItemClick(getAdapterPosition());
                    int p=getAdapterPosition();
                    Log.d("RecycleView Click", String.valueOf(festivalNames.get(festivalIDs.get(p))));
                    Intent intent = new Intent(v.getContext(), EventDescriptionsActivity.class);
                    intent.putExtra("festivalIDs",festivalIDs.get(p));
                    v.getContext().startActivity(intent);
                           }
            });
        }
    }
}
