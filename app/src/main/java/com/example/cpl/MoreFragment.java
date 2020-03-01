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

    private LinearLayout llLogin,llFeedback,llViewFeedback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View fragmentMore = inflater.inflate(R.layout.fragment_more,container,false);
        llLogin=fragmentMore.findViewById(R.id.ll_login);
        llFeedback=fragmentMore.findViewById(R.id.ll_feedback);
        llViewFeedback=fragmentMore.findViewById(R.id.ll_viewFeedback);

        llLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment loginFragment = new LoginFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft =fm.beginTransaction();
                ft.replace(R.id.frame_layout,loginFragment).commit();
               }
        });
        llFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment feedBack = new FeedbackFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft =fm.beginTransaction();
                ft.replace(R.id.frame_layout,feedBack).commit();
               // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,feedBack).commit();
            }
        });

        llViewFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment viewFeedBack = new ViewFeedbackFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft =fm.beginTransaction();
                ft.replace(R.id.frame_layout,viewFeedBack).commit();
            }
        });
        return fragmentMore;
    }
}