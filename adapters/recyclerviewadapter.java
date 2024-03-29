package com.example.doctormate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctormate.History;
import com.example.doctormate.models.bmimodels;
import com.example.doctormate.R;

import java.util.ArrayList;

public class recyclerviewadapter extends RecyclerView.Adapter<recyclerviewadapter.ViewHolder> {
    RecyclerView recyclerView;
    ArrayList<bmimodels> arrayList;
    Context context;

    public recyclerviewadapter(Context context,ArrayList<bmimodels> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }



    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_hitsorycontent,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        bmimodels bmimodels = arrayList.get(position);
        holder.type.setText(bmimodels.getType());
        holder.value.setText(bmimodels.getValue());
        holder.status.setText(bmimodels.getStatus());
        holder.date.setText(bmimodels.getDate());
        holder.time.setText(bmimodels.getTime());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    bmimodels deletedItem = arrayList.get(adapterPosition);
                    arrayList.remove(adapterPosition);
                    notifyItemRemoved(adapterPosition);
                    ((History) context).removedata(deletedItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView type,value,status,date,time,nodata;

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
