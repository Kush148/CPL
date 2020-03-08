package com.example.cpl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewSingleTeamAdapter extends RecyclerView.Adapter<ViewSingleTeamAdapter.ViewHolder> {

    List<ViewSingleTeamInfo> listOfteam;
    Context context;
    ViewSingleTeamInfo teamList;
    // static int scheduleId = -1;

    public ViewSingleTeamAdapter(List<ViewSingleTeamInfo> teamList, Context context) {
        this.listOfteam = teamList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewSingleTeamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_team_player_list, parent, false);
        return new ViewSingleTeamAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewSingleTeamAdapter.ViewHolder holder, int position) {

        teamList = listOfteam.get(position);

        holder.playerName.setText(teamList.getPlayerName());
        holder.role.setText(teamList.getPlayerRole());
        Picasso.with(context).load(teamList.getUrl()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return listOfteam.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView playerName,role;
        ImageView img;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            img=itemView.findViewById(R.id.player_pic);
            playerName=itemView.findViewById(R.id.tv_playerName);
            role = itemView.findViewById(R.id.tv_role);
        }
    }
}
