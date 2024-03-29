package com.example.doctormate.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctormate.R;

import java.text.BreakIterator;
import java.util.ArrayList;

public class heartrateadapter extends RecyclerView.Adapter<heartrateadapter.ViewHolder> {
    private ArrayList<Integer> arrayList;
    private OnNumberClickListener onNumberClickListener;

    public heartrateadapter(ArrayList<Integer> arrayList, OnNumberClickListener onNumberClickListener) {
        this.arrayList = arrayList;
        this.onNumberClickListener = onNumberClickListener;
    }





    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.number,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int num  = arrayList.get(position);
        holder.textView.setText(String.valueOf(num));
        holder.itemView.setOnClickListener(v -> {
            if (onNumberClickListener != null) {
                onNumberClickListener.onNumberClick(num);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.recyclerviewnumber);
        }
    }
    public interface OnNumberClickListener {
        void onNumberClick(int number);
    }
}
