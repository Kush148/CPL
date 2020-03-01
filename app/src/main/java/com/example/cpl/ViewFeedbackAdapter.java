package com.example.cpl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewFeedbackAdapter extends RecyclerView.Adapter<ViewFeedbackAdapter.ViewHolder>{

    List<FeedbackList> listOfFeedback;
    Context context;
    FeedbackList feedbackList;
    static int feedbackId = -1;

    public interface OnEditTextChanged {
        void onTextChanged(int position, String charSeq);
    }

    public ViewFeedbackAdapter(List<FeedbackList> feedbackLists, Context context) {
        this.listOfFeedback = feedbackLists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_feedback_lists, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        feedbackList = listOfFeedback.get(position);
        holder.tvEmail.setText(feedbackList.getEmail());
        holder.tvTitle.setText(feedbackList.getTitle());
        holder.tvDescription.setText(feedbackList.getDescription());

    }

    @Override
    public int getItemCount() {
        return listOfFeedback.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvEmail, tvTitle, tvDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvEmail = itemView.findViewById(R.id.tv_email);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_destination);

        }
    }
}
