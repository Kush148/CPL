package com.example.cpl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.ViewHolder> {

    List<PlayerList> listOfplayer;
    Context context;
    PlayerList playerList;
    int counter = 0;


    public PlayerListAdapter(Context context) {
        this.context = context;
    }

    public PlayerListAdapter(List<PlayerList> playerList, Context context) {
        this.listOfplayer = playerList;
        this.context = context;
    }

    @NonNull
    @Override
    public PlayerListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_player_list, parent, false);
        final PlayerListAdapter.ViewHolder holder = new PlayerListAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PlayerListAdapter.ViewHolder holder, final int position) {

        playerList = listOfplayer.get(position);
        holder.tvPlayerName.setText(playerList.getPlayerName());
        holder.tvRole.setText(playerList.getRole());

        if (listOfplayer.get(position).isChecked()) {
            holder.btnRemove.setVisibility(View.VISIBLE);
            holder.btnAdd.setVisibility(View.GONE);
        } else {
            holder.btnRemove.setVisibility(View.GONE);
            holder.btnAdd.setVisibility(View.VISIBLE);
        }
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                if (counter > 15) {
                    Toast.makeText(context, "15 players selected", Toast.LENGTH_SHORT).show();
                } else {
                    listOfplayer.get(position).setChecked(true);
                    notifyDataSetChanged();
                }
            }
        });

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listOfplayer.get(position).setChecked(false);
                counter--;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOfplayer.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPlayerName, tvRole;
        Button btnAdd, btnRemove;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            tvPlayerName = itemView.findViewById(R.id.tv_playerName);
            tvRole = itemView.findViewById(R.id.tv_role);
            btnAdd = itemView.findViewById(R.id.btnAdd);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }
}
