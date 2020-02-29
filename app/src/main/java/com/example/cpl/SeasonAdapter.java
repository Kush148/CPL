package com.example.cpl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.ViewHolder> {
    List<Season> listOfseason;
    Context context;
    Season SeasonList;

    public SeasonAdapter(List<Season> SeasonList, Context context) {
        this.listOfseason = SeasonList;
        this.context = context;
    }

    @NonNull
    @Override
    public SeasonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_raw_league, parent, false);
        return new SeasonAdapter.ViewHolder(view);

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

        TextView SeasonNameId,StartDate,EndDate ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            SeasonNameId = itemView.findViewById(R.id.SeasonNameId);
            StartDate = itemView.findViewById(R.id.StartDate);
            EndDate = itemView.findViewById(R.id.EndDate);
        }
    }
}
