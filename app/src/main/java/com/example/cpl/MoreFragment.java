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
    private LinearLayout llLogin,llBrowserPlayers,llFeedback,llViewFeedback,llLogout,llAbout;
    private View vLogin,vFeedback,vViewFeedback,vLogout;
    private SharedPref pref;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View fragmentMore = inflater.inflate(R.layout.fragment_more,container,false);
        llLogin=fragmentMore.findViewById(R.id.ll_login);
        llBrowserPlayers=fragmentMore.findViewById(R.id.ll_browse);
        llFeedback=fragmentMore.findViewById(R.id.ll_feedback);
        llViewFeedback=fragmentMore.findViewById(R.id.ll_viewFeedback);
        llLogout=fragmentMore.findViewById(R.id.ll_logout);
        llAbout=fragmentMore.findViewById(R.id.ll_about);
        vLogin=fragmentMore.findViewById(R.id.v_login);
        vFeedback=fragmentMore.findViewById(R.id.v_feedback);
        vViewFeedback=fragmentMore.findViewById(R.id.v_viewFeedback);
        vLogout=fragmentMore.findViewById(R.id.v_logout);

        pref=new SharedPref(this.getActivity());
        Constants.userId = pref.getId();
        Constants.userType = pref.getManagerType();

        if(Constants.userId.equals("1")&&Constants.userType.equals("LeagueManager")){
            llLogin.setVisibility(View.GONE);
            vLogin.setVisibility(View.GONE);
            llFeedback.setVisibility(View.GONE);
            vFeedback.setVisibility(View.GONE);
            llViewFeedback.setVisibility(View.VISIBLE);
            vViewFeedback.setVisibility(View.VISIBLE);
            llLogout.setVisibility(View.VISIBLE);
            vLogout.setVisibility(View.VISIBLE);
        }

        llLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment loginFragment = new LoginFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft =fm.beginTransaction();
                ft.replace(R.id.frame_layout,loginFragment).commit();
            }
        });

        llBrowserPlayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment browsePlayers = new BrowsePlayers();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft =fm.beginTransaction();
                ft.replace(R.id.frame_layout,browsePlayers).commit();
            }
        });

        llFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment feedBack = new FeedbackFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft =fm.beginTransaction();
                ft.replace(R.id.frame_layout,feedBack).commit();
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
        llLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pref.logout();
                Fragment homeFragment = new HomeFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft =fm.beginTransaction();
                ft.replace(R.id.frame_layout,homeFragment).commit();
            }
        });

        llAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment aboutFragment = new AboutFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft =fm.beginTransaction();
                ft.replace(R.id.frame_layout,aboutFragment).commit();
            }
        });
        return fragmentMore;
    }
}