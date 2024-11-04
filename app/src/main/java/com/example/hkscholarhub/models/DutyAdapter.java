package com.example.hkscholarhub.models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hkscholarhub.R;

import java.util.List;

public class DutyAdapter extends RecyclerView.Adapter<DutyAdapter.DutyViewHolder> {
    private final List<Duty> duties;

    public DutyAdapter(List<Duty> duties) {
        this.duties = duties;
    }

    @NonNull
    @Override
    public DutyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_duty, parent, false);
        return new DutyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DutyViewHolder holder, int position) {
        Duty duty = duties.get(position);

        // Set the time range as start - end time
        String timeRange = duty.getDutyStart() + " - " + duty.getDutyEnd();
        holder.timeTextView.setText(timeRange);

        // Set the duty date
        holder.dateTextView.setText(duty.getDutyDate());

        // Set the task description
        holder.taskTextView.setText(duty.getTask());

        // Set the total hours (status)
        holder.totalHoursTextView.setText(duty.getStatus()); // Assuming status represents total hours worked
    }

    @Override
    public int getItemCount() {
        return duties.size();
    }

    static class DutyViewHolder extends RecyclerView.ViewHolder {
        TextView timeTextView, dateTextView, taskTextView, totalHoursTextView;

        DutyViewHolder(@NonNull View itemView) {
            super(itemView);
            // Updated to match the new layout IDs
            timeTextView = itemView.findViewById(R.id.timeTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            taskTextView = itemView.findViewById(R.id.taskTextView);
            totalHoursTextView = itemView.findViewById(R.id.totalHoursTextView);
        }
    }
}
