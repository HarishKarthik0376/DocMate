package com.example.doctormate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctormate.Hearthistory;
import com.example.doctormate.R;
import com.example.doctormate.models.heartmodel;

import java.util.ArrayList;

public class hearthistoryadapter extends RecyclerView.Adapter<hearthistoryadapter.ViewHolder> {
    RecyclerView recyclerView;
    ArrayList<heartmodel> arrayList;
    Context context;

    public hearthistoryadapter(Context context,ArrayList<heartmodel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.heartcontent,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        heartmodel heartmodel = arrayList.get(position);
        holder.type.setText(heartmodel.getType());
        holder.value.setText(heartmodel.getValue());
        holder.status.setText(heartmodel.getStatus());
        holder.date.setText(heartmodel.getDate());
        holder.time.setText(heartmodel.getTime());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    heartmodel deletedItem = arrayList.get(adapterPosition);
                    arrayList.remove(adapterPosition);
                    notifyItemRemoved(adapterPosition);
                    ((Hearthistory) context).removedata(deletedItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView type,value,status,date,time;
        ImageView delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.typeanswer);
            value = itemView.findViewById(R.id.valueanswer);
            status = itemView.findViewById(R.id.statusanswer);
            date = itemView.findViewById(R.id.datetextview);
            time = itemView.findViewById(R.id.timetextview);
            delete = itemView.findViewById(R.id.recyclerdelete);
        }
    }
}
