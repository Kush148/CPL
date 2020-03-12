package com.example.cpl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PointsTableAdapter extends RecyclerView.Adapter<PointsTableAdapter.ViewHolder> {

    List<PointsTableList> listOfPointsTable;
    Context context;
    PointsTableList PointsList;

    public PointsTableAdapter(List<PointsTableList> PointTablesLists, Context context) {
        this.listOfPointsTable = PointTablesLists;
        this.context = context;
    }

    @NonNull
    @Override
    public PointsTableAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_points_table_list, parent, false);
        return new ViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull PointsTableAdapter.ViewHolder holder, int position) {
        PointsList = listOfPointsTable.get(position);
        holder.tv_teamName.setText(PointsList.getTeamName());
        holder.tv_play.setText(String.valueOf(PointsList.getPlay()));
        holder.tv_win.setText(String.valueOf(PointsList.getWin()));
        holder.tv_lose.setText(String.valueOf(PointsList.getLose()));
        holder.tv_points.setText(String.valueOf(PointsList.getPoints()));
    }

    @Override
    public int getItemCount() {
        return listOfPointsTable.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_teamName,tv_play,tv_win,tv_lose,tv_points;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_teamName = itemView.findViewById(R.id.tv_teamName);
            tv_play = itemView.findViewById(R.id.tv_play);
            tv_win = itemView.findViewById(R.id.tv_win);
            tv_lose = itemView.findViewById(R.id.tv_lose);
            tv_points = itemView.findViewById(R.id.tv_points);

        }
    }
}
