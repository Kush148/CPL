package com.example.cpl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class BrowsePlayerAdapter extends RecyclerView.Adapter<BrowsePlayerAdapter.ViewHolder> {

    List<BrowsePlayerList> listOfPlayers;
    Context context;
    BrowsePlayerList playerList;
    int position;
    static int playerId = -1;

    public interface OnEditTextChanged {
        void onTextChanged(int position, String charSeq);
    }

    public BrowsePlayerAdapter(List<BrowsePlayerList> playerLists, Context context) {
        this.listOfPlayers = playerLists;
        this.context = context;
    }

    @NonNull
    @Override
    public BrowsePlayerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_browse_player_list, parent, false);
        final BrowsePlayerAdapter.ViewHolder sholder = new BrowsePlayerAdapter.ViewHolder(view);
        sholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, "player" + String.valueOf(sholder.getAdapterPosition()),Toast.LENGTH_LONG).show();
                position = (sholder.getAdapterPosition()) + 1;
                System.out.println(position);
                playerId = position;
            }
        });

          return sholder;

          //return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BrowsePlayerAdapter.ViewHolder holder, int position) {

        playerList = listOfPlayers.get(position);
        holder.tvPlayerName.setText(playerList.getPlayerName());
        holder.tvBirthDate.setText(playerList.getBirthDate());
        holder.tvRole.setText(playerList.getPlayerRole());
        holder.tvBirthPlace.setText(playerList.getBirthPlace());
        Picasso.with(context)
                .load(playerList.getUrl())
                .fit()
                .centerCrop()
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return listOfPlayers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPlayerName, tvBirthDate, tvRole, tvBirthPlace;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPlayerName = itemView.findViewById(R.id.tv_playerName);
            tvBirthDate = itemView.findViewById(R.id.tv_birthDate);
            tvRole = itemView.findViewById(R.id.tv_playerRole);
            tvBirthPlace = itemView.findViewById(R.id.tv_birthPlace);
            img = itemView.findViewById(R.id.ivPlayer);

        }
    }


}
