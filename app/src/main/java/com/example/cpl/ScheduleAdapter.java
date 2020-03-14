package com.example.cpl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    List<ViewSchedule> listOfschedule;
    Context context;
    ViewSchedule scheduleList;
    public ScheduleAdapter(List<ViewSchedule> scheduleList, Context context) {
        this.listOfschedule = scheduleList;
        this.context = context;
    }

    @NonNull
    @Override
    public ScheduleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.schedule_list, parent, false);
        return new ScheduleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleAdapter.ViewHolder holder, int position) {

        scheduleList = listOfschedule.get(position);

        holder.tvMatchNo.setText(Integer.toString(scheduleList.getMatchNo()));
        holder.tvTeamA.setText(scheduleList.getTeamA());
        holder.tvTeamB.setText(scheduleList.getTeamB());
        holder.tvDate.setText(scheduleList.getDate());
        holder.tvVenue.setText(scheduleList.getVenue());
        holder.tvResult.setText(scheduleList.getResult());
        holder.tvResultDescription.setText(scheduleList.getResultDescription());

    }

    @Override
    public int getItemCount() {
        return listOfschedule.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMatchNo,tvTeamA, tvTeamB, tvDate, tvVenue, tvResult, tvResultDescription;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            tvMatchNo=itemView.findViewById(R.id.tv_matchNo);
            tvTeamA = itemView.findViewById(R.id.tv_teamA);
            tvTeamB = itemView.findViewById(R.id.tv_teamB);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvVenue = itemView.findViewById(R.id.tv_venue);
            tvResult = itemView.findViewById(R.id.tv_result);
            tvResultDescription = itemView.findViewById(R.id.tv_resultDescription);

        }
    }
}
