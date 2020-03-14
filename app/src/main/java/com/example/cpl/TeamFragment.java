package com.example.cpl;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TeamFragment extends Fragment {

    View view;
    private RecyclerView tRecyclerView;
    List<Team> teamList = new ArrayList<>();
    FloatingActionButton fbCreateTeams;
    SharedPref pref;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_team, container, false);
        tRecyclerView =  view.findViewById(R.id.rc_teamId);
        fbCreateTeams =  view.findViewById(R.id.fbCreateTeam);
        progressBar =  view.findViewById(R.id.progressBar);
        pref=new SharedPref(this.getActivity());
        Constants.userId = pref.getId();
        Constants.userType = pref.getManagerType();

        if(Constants.userId.equals("1") && Constants.userType.equalsIgnoreCase("LeagueManager")){
            fbCreateTeams.setVisibility(View.VISIBLE);
        }

        new MyTask().execute();

        fbCreateTeams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment createTeams = new TeamCreate();
                getFragmentManager().beginTransaction().replace(R.id.frame_layout, createTeams).commit();
            }
        });
        return view;
    }
    private class MyTask extends AsyncTask<Void, Void, Void> {
        String return_msg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected Void doInBackground(Void... voids) {
            URL url = null;
            try {
                url = new URL("http://" + Constants.localHost+"/" + Constants.projectPath + "main/viewTeams");

                HttpURLConnection client = null;

                client = (HttpURLConnection) url.openConnection();

                client.setRequestMethod("GET");

                int responseCode = client.getResponseCode();

                System.out.println("\n Sending 'GET' request to URL : " + url);

                System.out.println("Response Code : " + responseCode);

                InputStreamReader myInput = new InputStreamReader(client.getInputStream());

                BufferedReader in = new BufferedReader(myInput);
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                    System.out.println("while " + response);
                }
                in.close();
                //print result
                System.out.println(response.toString());
                JSONObject mainObject = new JSONObject(response.toString());
                return_msg = mainObject.getString("Message");

                JSONArray SeasonArray = mainObject.getJSONArray("Teams");
                JSONObject singleSeason;
                for (int i = 0; i < SeasonArray.length(); i++) {
                    singleSeason = SeasonArray.getJSONObject(i);
                    int teamId=singleSeason.getInt("teamId");
                    String teamName = singleSeason.getString("teamName");
                    String color = singleSeason.getString("teamColor");
                    teamList.add(new Team(teamId,teamName,color));
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressBar.setVisibility(View.GONE);
            if (return_msg.equals("Available")) {
                progressBar.setVisibility(View.VISIBLE);
                tRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                TeamAdapter tAdapter = new TeamAdapter(teamList, getActivity());
                tRecyclerView.setAdapter(tAdapter);
            }
            else if (return_msg.equals("null"))
            {
                Toast.makeText(getActivity(),"Data not Available" , Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getActivity(),"Error" , Toast.LENGTH_SHORT).show();
            }
        }
    }
}
