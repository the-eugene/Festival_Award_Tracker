package com.example.festivalawardtracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;



public class studentAdapter extends FirebaseRecyclerAdapter<
        StudentDisplayFragment, studentAdapter.DatabaseViewHolder> {

    public studentAdapter(@NonNull FirebaseRecyclerOptions<StudentDisplayFragment> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull DatabaseViewHolder holder, int position, @NonNull StudentDisplayFragment model) {
//        holder.name.setText(model.getFname());
//        Log.d("setName", model.getFname());
//        holder.gender.setText(model.getGender());
//        holder.brithdate.setText(model.getBdate());
    }

    @NonNull
    @Override
    public DatabaseViewHolder
    onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_students_recyclerview, parent, false);
        return new studentAdapter.DatabaseViewHolder(view);
    }

    // Sub Class to create references of the views in Crad
    // view (here "person.xml")
    class DatabaseViewHolder
            extends RecyclerView.ViewHolder {
        TextView name, gender, brithdate;

        public DatabaseViewHolder(@NonNull View itemView) {
            super(itemView);

//            name = itemView.findViewById(R.id.StudentName);
//            gender = itemView.findViewById(R.id.Gender);
//            brithdate = itemView.findViewById(R.id.Age);
        }

    }
}
