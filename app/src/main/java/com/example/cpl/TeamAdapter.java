package com.example.cpl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {
    List<Team> Listofteam;
    Context context;
    Team teamlist;

    public TeamAdapter(List<Team> listofteam, Context context) {
        Listofteam = listofteam;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_team, parent, false);
        return new TeamAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull TeamAdapter.ViewHolder holder, int position) {
        teamlist = Listofteam.get(position);

        holder.teamName.setText(teamlist.getTeamName());
        holder.teamColor.setText(teamlist.getColor());

    }

    @Override
    public int getItemCount() {
        return Listofteam.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView teamName,teamColor;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            teamName = itemView.findViewById(R.id.teamname);
            teamColor = itemView.findViewById(R.id.tcolor);
        }
    }
}
