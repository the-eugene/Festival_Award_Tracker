package com.example.festivalawardtracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {
    private View StudentView;
    private RecyclerView myStudentList;
    private DatabaseReference StudentsRef, UserRef;
    private FirebaseAuth mAuth;
    private String CurrentUserID;

    public HomeFragment() {
        //Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        StudentView = inflater.inflate(R.layout.fragment_home, container, false);

//        myStudentList = (RecyclerView) StudentView.findViewById(R.id.student_list);
        myStudentList.setLayoutManager(new LinearLayoutManager(getContext()));

        mAuth = FirebaseAuth.getInstance();
        CurrentUserID = mAuth.getCurrentUser().getUid();
        StudentsRef = FirebaseDatabase.getInstance().getReference().child("Student");
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");

        return StudentView;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<studentDatabase>()
                .setQuery(StudentsRef, studentDatabase.class)
                .build();

        FirebaseRecyclerAdapter<studentDatabase, StudentViewHolder> adapter
                = new FirebaseRecyclerAdapter<studentDatabase, StudentViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final StudentViewHolder holder, int position, @NonNull studentDatabase model) {
                String userIDs = getRef(position).getKey();

                UserRef.child(userIDs).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String studentName = snapshot.child("StudentName").getValue().toString();
                        String studentGender = snapshot.child("Gender").getValue().toString();
                        String studentBirthday = snapshot.child("Bithday").getValue().toString();
                        String studentAwards = snapshot.child("Awards").getValue().toString();
                        String studentAge = snapshot.child("Age").getValue().toString();

                        holder.Name.setText(studentName);
                        holder.Gender.setText(studentGender);
                        holder.Birthday.setText(studentBirthday);
                        holder.Awards.setText(studentAwards);
                        holder.Age.setText(studentAge);



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @NonNull
            @Override
            public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_home_recyclerview_row_studentminiawards, viewGroup, false);
                StudentViewHolder viewHolder = new StudentViewHolder(view);
                return viewHolder;
            }
        };
        myStudentList.setAdapter(adapter);
        adapter.startListening();
    }

    public static  class StudentViewHolder extends  RecyclerView.ViewHolder{
        TextView Name, Gender, Birthday, Age, Awards;
        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.StudentName);
            Gender = itemView.findViewById(R.id.Gender);
            Birthday = itemView.findViewById(R.id.Birthday);
            Age = itemView.findViewById(R.id.Age);
            Awards = itemView.findViewById(R.id.AwardsInfo);
        }
    }
}
