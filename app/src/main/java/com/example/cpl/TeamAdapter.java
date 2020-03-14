package com.example.cpl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {
    List<Team> Listofteam;
    Context context;
    Team teamlist;
    static int teamId,position;
    //check upper static variables

    public TeamAdapter(List<Team> listofteam, Context context) {
        Listofteam = listofteam;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_team, parent, false);

        final TeamAdapter.ViewHolder sholder = new TeamAdapter.ViewHolder(view);
        sholder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "pos" + String.valueOf(sholder.getAdapterPosition()), Toast.LENGTH_LONG).show();

                position = (sholder.getAdapterPosition());
                System.out.println(position);

                teamId = Listofteam.get(position).getTeamId();
                // one fragment to another fragment
                Fragment matchFragment=new ViewSingleTeamInfoFragment();
                FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout,matchFragment).commit();

            }
        });

        return sholder;


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
        RelativeLayout item;
        TextView teamName,teamColor;
        ImageView imgDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item=(RelativeLayout)itemView.findViewById(R.id.rl1);
            teamName = itemView.findViewById(R.id.teamname);
            teamColor = itemView.findViewById(R.id.tcolor);
        }
    }
}
