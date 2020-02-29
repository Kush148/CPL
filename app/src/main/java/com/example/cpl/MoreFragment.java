package com.example.cpl;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MoreFragment extends Fragment {

    private CardView cvLogin;
    private LinearLayout llFeedback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View fragmentMore = inflater.inflate(R.layout.fragment_more,container,false);
        cvLogin=fragmentMore.findViewById(R.id.cv_login);
        llFeedback=fragmentMore.findViewById(R.id.ll_feedback);

        cvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               }
        });
        llFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment feedBack = new FeedbackFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft =fm.beginTransaction();
                ft.replace(R.id.frame_layout,feedBack).commit();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,feedBack).commit();
            }
        });
        return fragmentMore;
    }
}