package com.example.appointment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ConfirmedAppointmentAdapter extends FirebaseRecyclerAdapter<CreateAppointmentData, ConfirmedAppointmentAdapter.myViewHolder> {

    public ConfirmedAppointmentAdapter(@NonNull FirebaseRecyclerOptions<CreateAppointmentData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ConfirmedAppointmentAdapter.myViewHolder holder, int position, @NonNull CreateAppointmentData model) {

        holder.bname.setText(model.getBusiness());
        holder.sName.setText(model.getName());
        holder.date.setText(model.getSelectedDate());
        holder.time.setText(model.getSelectedTime());
        holder.reason.setText(model.getReason());
        holder.status.setText(model.getStatus());
        holder.email.setText(model.getEmail());

    }

    @NonNull
    @Override
    public ConfirmedAppointmentAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.confirmed_appointment_resource,parent,false);
        return new ConfirmedAppointmentAdapter.myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView bname,sName,email,reason,time,date,status;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            bname=itemView.findViewById(R.id.busines_name_id);
            sName=itemView.findViewById(R.id.sender_ID);
            email=itemView.findViewById(R.id.email_id);
            reason=itemView.findViewById(R.id.reason_ID);
            time=itemView.findViewById(R.id.time_id);
            date=itemView.findViewById(R.id.date_id);
            status=itemView.findViewById(R.id.status_id);



        }
    }
}
