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
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {
    List<Team> Listofteam;
    Context context;
    Team teamlist;
    static int teamId,position;
    public TeamAdapter(List<Team> listofteam, Context context) {
        Listofteam = listofteam;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_team, parent, false);

        final TeamAdapter.ViewHolder sholder = new TeamAdapter.ViewHolder(view);

        return sholder;
    }

    @Override
    public void onBindViewHolder(@NonNull TeamAdapter.ViewHolder holder, int position) {
        teamlist = Listofteam.get(position);

        holder.teamName.setText(teamlist.getTeamName());
        Picasso.with(context)
                .load(teamlist.getColor())
                .fit()
                .centerCrop()
                .into(holder.teamColor);
    }

    @Override
    public int getItemCount() {
        return Listofteam.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout item;
        CardView cv;
        TextView teamName;
        ImageView teamColor;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item=(RelativeLayout)itemView.findViewById(R.id.rl1);
            teamName = itemView.findViewById(R.id.teamname);
            teamColor = itemView.findViewById(R.id.teamcolour);
            cv = itemView.findViewById(R.id.cv);
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    teamId = Listofteam.get(position).getTeamId();
                    Fragment matchFragment=new ViewSingleTeamInfoFragment();
                    FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout,matchFragment).commit();
                }
            });
        }
    }
}
