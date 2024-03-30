package com.example.doctormate.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctormate.R;
import com.example.doctormate.chatscreen;
import com.example.doctormate.contactdoctor;
import com.example.doctormate.models.contactmodel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class contactadapter extends RecyclerView.Adapter<contactadapter.ViewHolder> {
    ArrayList<contactmodel> arrayList;
    Context context;

    public contactadapter(ArrayList<contactmodel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacttodisplay,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        contactmodel contactmodel =arrayList.get(position);
        holder.name.setText("Name: " + contactmodel.getFname());
        holder.role.setText("Role: "+ contactmodel.getRole());
        holder.id.setText("Id: " + contactmodel.getRanuid());
        String uid = contactmodel.getUidofdoc();
        holder.messagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redirect = new Intent(context, chatscreen.class);
                redirect.putExtra("UserId",uid);
                redirect.putExtra("UserName",contactmodel.getFname());
                context.startActivity(redirect);
            }
        });
        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    contactmodel deletedItem = arrayList.get(adapterPosition);
                    arrayList.remove(adapterPosition);
                    notifyItemRemoved(adapterPosition);
                    ((contactdoctor) context).removedata(deletedItem);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,role,id;
        ImageView messagebtn,deletebtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nametobeupdated);
            role = itemView.findViewById(R.id.roletobeupdate);
            id = itemView.findViewById(R.id.idtobeupdated);
            deletebtn = itemView.findViewById(R.id.deletechat);
            messagebtn = itemView.findViewById(R.id.startmessaging);
        }
    }
}
