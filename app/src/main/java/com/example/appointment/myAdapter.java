package com.example.appointment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class myAdapter extends FirebaseRecyclerAdapter<CreateAppointmentData,myAdapter.myViewHolder>{


    public myAdapter(@NonNull FirebaseRecyclerOptions<CreateAppointmentData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull CreateAppointmentData model) {
        holder.bName.setText(model.getBusiness());
        holder.reason.setText(model.getReason());
        holder.time.setText(model.getSelectedTime());
        holder.date.setText(model.getSelectedDate());
        holder.status.setText(model.getStatus());
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_resource_file_for_list,parent,false);
       return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        TextView bName,reason,time,date,status;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            bName=itemView.findViewById(R.id.busines_name_id);
            reason=itemView.findViewById(R.id.reason_ID);
            time=itemView.findViewById(R.id.time_id);
            date=itemView.findViewById(R.id.date_id);
            status=itemView.findViewById(R.id.status_id);


        }
    }
}
