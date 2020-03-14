package com.example.cpl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.ViewHolder> {
    List<Season> listOfseason;
    Context context;
    Season SeasonList;
    int position;
    static int seasonid;

    public SeasonAdapter(List<Season> SeasonList, Context context) {
        this.listOfseason = SeasonList;
        this.context = context;
    }

    @NonNull
    @Override
    public SeasonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_item_season, parent, false);

        final SeasonAdapter.ViewHolder sholder=new SeasonAdapter.ViewHolder(v);
        sholder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position=(sholder.getAdapterPosition());
                System.out.println(position);
                seasonid=listOfseason.get(position).getSeasonId();
                Fragment matchFragment=new ViewScheduleFragment();
                FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout,matchFragment).commit();

            }
        });
        return sholder;

    }

    @Override
    public void onBindViewHolder(@NonNull SeasonAdapter.ViewHolder holder, int position) {

        SeasonList = listOfseason.get(position);

        holder.SeasonNameId.setText(SeasonList.getSeasonName());
        holder.StartDate.setText(SeasonList.getStartDate());
        holder.EndDate.setText(SeasonList.getEndDate());

    }

    @Override
    public int getItemCount() {
        return listOfseason.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout item;
        TextView SeasonNameId,StartDate,EndDate ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item=(RelativeLayout)itemView.findViewById(R.id.single_raw);
            SeasonNameId = itemView.findViewById(R.id.SeasonNameId);
            StartDate = itemView.findViewById(R.id.StartDate);
            EndDate = itemView.findViewById(R.id.EndDate);
        }
    }
}
