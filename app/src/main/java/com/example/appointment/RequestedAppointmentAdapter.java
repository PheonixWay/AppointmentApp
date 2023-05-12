package com.example.appointment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class RequestedAppointmentAdapter extends FirebaseRecyclerAdapter<CreateAppointmentData,RequestedAppointmentAdapter.myViewHolder> {

    private Context context;
    public RequestedAppointmentAdapter(@NonNull FirebaseRecyclerOptions<CreateAppointmentData> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull RequestedAppointmentAdapter.myViewHolder holder, int position, @NonNull CreateAppointmentData model) {

        holder.pb.setVisibility(View.INVISIBLE);
        holder.businessName.setText(model.getBusiness());
        holder.senderName.setText(model.getName());
        holder.email.setText(model.getEmail());
        holder.reason.setText(model.getReason());
        holder.status.setText(model.getStatus());
        holder.date.setText(model.getSelectedDate());
        holder.time.setText(model.getSelectedTime());
        holder.id.setText(model.getId());
        Integer positionOf=holder.getAbsoluteAdapterPosition();
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("AppointmentApproved/"+model.getName());
        databaseReference.getRef().getKey();
        getRef(position).getKey();
        String decision="Confirmed";
        String decision1="Rejected";
        CreateAppointmentData dataForRejected=new CreateAppointmentData(model.getName(), model.email, model.getPhone(), model.reason, model.getBusiness(), decision1, model.getSelectedDate(), model.getSelectedTime(), model.getId());
        CreateAppointmentData dataForConfirmed=new CreateAppointmentData(model.getName(), model.email, model.getPhone(), model.reason, model.getBusiness(), decision, model.getSelectedDate(), model.getSelectedTime(), model.getId());

       holder.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.pb.setVisibility(View.VISIBLE);
                Map<String,Object> map=new HashMap<>();
                map.put("status",decision);
                FirebaseDatabase.getInstance().getReference("Appointment/"+model.getName()+"/"+model.getId())
                        .updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                FirebaseDatabase.getInstance().getReference("BusinessAppointmentConfirmed/"+model.getBusiness())
                                        .push().setValue(dataForRejected).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                FirebaseDatabase.getInstance().getReference("AppointmentRequested/"+model.getBusiness())
                                                        .child(getRef(positionOf).getKey())
                                                        .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                Toast.makeText(context.getApplicationContext(), "Successful Confirmed pls check your status Category From DashBoard", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                            }
                                        });

                            }
                        });
            }
        });
        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.pb.setVisibility(View.VISIBLE);

                Map<String,Object> map=new HashMap<>();
                map.put("status",decision1);
                FirebaseDatabase.getInstance().getReference("Appointment/"+model.getName()+"/"+model.getId())
                        .updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                FirebaseDatabase.getInstance().getReference("BusinessAppointmentRejected/"+model.getBusiness())
                                        .push().setValue(dataForRejected).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                FirebaseDatabase.getInstance().getReference("AppointmentRequested/"+model.getBusiness())
                                                        .child(getRef(positionOf).getKey())
                                                        .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                holder.pb.setVisibility(View.INVISIBLE);
                                                                Toast.makeText(context.getApplicationContext(), "Successful Rejected pls check your status Category From DashBoard", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                            }
                                        });


                            }
                        });

            }
        });
    }

    @NonNull
    @Override
    public RequestedAppointmentAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.resource_for_requested_appointment,parent,false);
        return new RequestedAppointmentAdapter.myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView businessName,senderName,email,reason,time,date,status,id;
        ProgressBar pb;
        Button confirm,reject;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            businessName=itemView.findViewById(R.id.busines_name_id);
            senderName=itemView.findViewById(R.id.sender_ID);
            email=itemView.findViewById(R.id.email_id);
            reason=itemView.findViewById(R.id.reason_ID);
            time=itemView.findViewById(R.id.time_id);
            date=itemView.findViewById(R.id.date_id);
            status=itemView.findViewById(R.id.status_id);
            confirm=itemView.findViewById(R.id.confirmId);
            reject=itemView.findViewById(R.id.RejectId);
            id=itemView.findViewById(R.id.appointmentid);
            pb=itemView.findViewById(R.id.pbid);



        }
    }
}
