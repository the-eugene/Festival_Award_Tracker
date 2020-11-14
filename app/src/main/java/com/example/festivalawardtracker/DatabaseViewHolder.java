package com.example.festivalawardtracker;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class DatabaseViewHolder extends RecyclerView.ViewHolder {
    View mView;
    public DatabaseViewHolder(View itemView)
    {
        super(itemView);
        mView=itemView;
    }
    public void setName(String name)
    {
        TextView student_name=(TextView)mView.findViewById(R.id.rowStudentName);
        student_name.setText(name);
    }
    public void setGender(String gender)
    {
        TextView student_gender=(TextView)mView.findViewById(R.id.rowStudentGender);
        student_gender.setText(gender);
    }
    public void setBirthday(String birthday)
    {
        TextView student_birthday=(TextView)mView.findViewById(R.id.rowStudentBirthday);
        student_birthday.setText(birthday);
    }
}
