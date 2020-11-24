package com.example.festivalawardtracker.ui.festival;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.festivalawardtracker.R;
import com.example.festivalawardtracker.ui.event.EventRecyclerAdapter;
import com.example.festivalawardtracker.ui.student.RecyclerViewClickInterface;
import com.example.festivalawardtracker.ui.student.StudentRecyclerAdapter;

import java.util.List;

public class FestivalRecyclerAdapter extends RecyclerView.Adapter<FestivalRecyclerAdapter.ViewHolder> {

    List<String> festivalNames;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    public FestivalRecyclerAdapter(List<String> festivalNames, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.festivalNames = festivalNames;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_festivals_recyclerview, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.festivalName.setText(festivalNames.get(position));
    }

    @Override
    public int getItemCount() {
        return festivalNames.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView festivalName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            festivalName = itemView.findViewById(R.id.rowFestivalName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition());
                }
            });

        }
    }
}
