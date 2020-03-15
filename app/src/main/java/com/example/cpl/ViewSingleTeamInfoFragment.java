package com.example.cpl;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

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

public class ViewSingleTeamInfoFragment extends Fragment {

    ProgressBar progressBar;

    int totalPlayer, teamManagerId,teamMangagerUID;
    ImageView editImage, saveImage, removeImage, ivTeamColor;
    TextView tvTeamName, tvTeamColor, tvManagerName, tvContact, tvNoData, tvNoMatches;
    String teamName, teamColor, userName, contactNumber, newContactNumber;
    RecyclerView rvPlayerList;
    List<ViewSingleTeamInfo> teamInfoList;
    FloatingActionButton fbAddPlayers;
    SharedPref pref;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentTeamInfo = inflater.inflate(R.layout.fragment_view_single_team_info, container, false);

        progressBar = fragmentTeamInfo.findViewById(R.id.progressBar);
        tvNoMatches = fragmentTeamInfo.findViewById(R.id.tvNoData);
        tvTeamName = fragmentTeamInfo.findViewById(R.id.tv_teamName);
        ivTeamColor = (ImageView) fragmentTeamInfo.findViewById(R.id.imgTeamColor);
        tvManagerName = fragmentTeamInfo.findViewById(R.id.tv_teamManagerName);
        tvContact = fragmentTeamInfo.findViewById(R.id.et_teamManagerContact);
        editImage = fragmentTeamInfo.findViewById(R.id.editImageView);
        saveImage = fragmentTeamInfo.findViewById(R.id.rightImageView);
        tvNoData = fragmentTeamInfo.findViewById(R.id.tvNoData);
        fbAddPlayers = fragmentTeamInfo.findViewById(R.id.fbAddPlayer);
        teamInfoList = new ArrayList<>();

        pref=new SharedPref(this.getActivity());
        Constants.userId = pref.getId();
        Constants.userType = pref.getManagerType();

        tvContact.setEnabled(false);
        tvTeamName.setAllCaps(true);
        saveImage.setVisibility(View.GONE);

        if (Constants.userId.equals(String.valueOf(teamManagerId)) || Constants.userId.equals("1")) {
            fbAddPlayers.setVisibility(View.VISIBLE);
        }else {
            fbAddPlayers.setVisibility(View.GONE);

        }

        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvContact.setEnabled(true);
                editImage.setVisibility(View.GONE);
                saveImage.setVisibility(View.VISIBLE);
            }
        });

        fbAddPlayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment currentFragment = new PlayerListFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, currentFragment).commit();

            }
        });

        saveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newContactNumber = tvContact.getText().toString();

                if (validateInput()) {
                    new teamManagerInfoUpdate().execute();
                    saveImage.setVisibility(View.GONE);
                    editImage.setVisibility(View.VISIBLE);
                    tvContact.setEnabled(false);
                }
            }
        });

        rvPlayerList = fragmentTeamInfo.findViewById(R.id.rc_teamPlayer);

        new MyTask().execute();
        new teamInfo().execute();

        return fragmentTeamInfo;
    }

    private boolean validateInput() {

        contactNumber = (tvContact.getText().toString().trim());

        if (contactNumber.isEmpty()) {
            tvContact.requestFocus();
            tvContact.setError("Contact number can't be empty");
            return false;
        }
        return true;
    }


    private class teamManagerInfoUpdate extends AsyncTask<Void, Void, Void> {
        String return_msg;

        @Override
        protected Void doInBackground(Void... voids) {
            URL url = null;
            try {
                //
                url = new URL("http://" + Constants.localHost + "/" + Constants.projectPath + "leagueManager/teamManagerUpdateInfo&" + newContactNumber + "&" + teamMangagerUID);

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

            //  super.onPostExecute(result);
        }
    }

    private class teamInfo extends AsyncTask<Void, Void, Void> {
        String return_msg;

        @Override
        protected Void doInBackground(Void... voids) {
            URL url = null;
            try {
                url = new URL("http://" + Constants.localHost + "/" + Constants.projectPath + "main/viewSingleTeamInfo&" + TeamAdapter.teamId);

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

                JSONArray scheduleArray = mainObject.getJSONArray("Teams");
                JSONObject singleSchedule;
                for (int i = 0; i < scheduleArray.length(); i++) {
                    singleSchedule = scheduleArray.getJSONObject(i);

                    teamMangagerUID = singleSchedule.getInt("teamManagerUserId");
                    teamManagerId = singleSchedule.getInt("teamManagerId");
                    teamName = singleSchedule.getString("teamName");
                    userName = singleSchedule.getString("userName");
                    contactNumber = singleSchedule.getString("contactNumber");

                    teamInfoList.add(new ViewSingleTeamInfo(teamManagerId, teamName, teamColor, userName, contactNumber));
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
            if (return_msg.equals("Available")) {
                tvTeamName.setText(teamName);
                tvManagerName.setText(userName);
                tvContact.setText(contactNumber);
                Picasso.with(getActivity()).load(teamColor).into(ivTeamColor);

                if (Constants.userId.equals(String.valueOf(teamManagerId)) || Constants.userId.equals("1")) {
                    fbAddPlayers.setVisibility(View.VISIBLE);
                }
            } else {
                Toast.makeText(getActivity(), "Error! Try again later.", Toast.LENGTH_SHORT).show();
            }
        }
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
                url = new URL("http://" + Constants.localHost + "/" + Constants.projectPath + "main/viewPlayersByTeam&" + TeamAdapter.teamId);

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

                JSONArray scheduleArray = mainObject.getJSONArray("Players");
                totalPlayer = scheduleArray.length();

                JSONObject singleSchedule;
                for (int i = 0; i < scheduleArray.length(); i++) {
                    singleSchedule = scheduleArray.getJSONObject(i);

                    int playerId = singleSchedule.getInt("playerId");
                    String playerName = singleSchedule.getString("playerName");
                    String role = singleSchedule.getString("role");
                    String imageUrl = singleSchedule.getString("url");

                    teamInfoList.add(new ViewSingleTeamInfo(playerId, playerName, role, imageUrl));
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

            progressBar.setVisibility(View.INVISIBLE);
            if (totalPlayer < 15 && (Constants.userId.equals(String.valueOf(teamManagerId)) || Constants.userId.equals("1"))) {
                //only if tm or lm
                fbAddPlayers.setVisibility(View.VISIBLE);
            }
            if (return_msg.equals("Available")) {
                rvPlayerList.setLayoutManager(new LinearLayoutManager(getActivity()));
                final ViewSingleTeamAdapter SAdapter = new ViewSingleTeamAdapter(teamInfoList, getActivity());

                rvPlayerList.setAdapter(SAdapter);
            } else if (return_msg.equals("null")) {
                tvNoMatches.setText("No Players Available");
                tvNoMatches.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(getActivity(), "Error! Try again later.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}