package com.example.cpl;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class PlayerListFragment extends Fragment {

    RecyclerView rvPlayerList;
    List<PlayerList> playerList = new ArrayList<>();
    ProgressBar progressBar;
    PlayerListAdapter playerListAdapter;
    Button btnAddPlayers;
    String selectedPlayers = "";
    int counter = 0;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentSchedule = inflater.inflate(R.layout.fragment_player_list, container, false);

        rvPlayerList = fragmentSchedule.findViewById(R.id.rvPlayerList);
        progressBar = fragmentSchedule.findViewById(R.id.progressBar);
        btnAddPlayers = fragmentSchedule.findViewById(R.id.btnAddPlayers);
        playerListAdapter = new PlayerListAdapter(getContext());
        new MyTask().execute();

        btnAddPlayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPlayers = "";
                counter = 0;
                for (int i = 0; i < playerList.size(); i++) {
                    if (playerList.get(i).isChecked()) {
                        counter++;
                        if (counter == 15) {
                            selectedPlayers += playerList.get(i).getPlayerId();
                        } else {
                            selectedPlayers += playerList.get(i).getPlayerId() + "&";
                        }
                    }
                }
                if (counter == 15) {
                    new AddTeamPlayers().execute();
                } else {
                    Toast.makeText(getContext(), "Please Select 15 Players", Toast.LENGTH_SHORT).show();
                }
                System.out.println(selectedPlayers);
            }
        });

        return fragmentSchedule;
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
                url = new URL("http://" + Constants.localHost + "/" + Constants.projectPath + "leagueManager/playerList");

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
                JSONObject singleSchedule;
                for (int i = 0; i < scheduleArray.length(); i++) {
                    singleSchedule = scheduleArray.getJSONObject(i);

                    int playerID = singleSchedule.getInt("playerId");
                    String playerName = singleSchedule.getString("playerName");
                    String role = singleSchedule.getString("role");

                    playerList.add(new PlayerList(playerID, playerName, role));
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
                rvPlayerList.setLayoutManager(new LinearLayoutManager(getActivity()));
                PlayerListAdapter SAdapter = new PlayerListAdapter(playerList, getActivity());
                rvPlayerList.setAdapter(SAdapter);
            }else{
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class AddTeamPlayers extends AsyncTask<Void, Void, Void> {
        String return_msg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            btnAddPlayers.setVisibility(View.INVISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            URL url = null;
            try {
                url = new URL("http://" + Constants.localHost + "/" + Constants.projectPath + "main/updatePlayerTeams&"+ TeamAdapter.teamId + "&" + selectedPlayers);
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
            super.onPostExecute(result);
            if (return_msg.equals("Players Added")) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Players Added", Toast.LENGTH_LONG).show();
                Fragment viewTeams = new TeamFragment();
                getFragmentManager().beginTransaction().replace(R.id.frame_layout, viewTeams).commit();
            }

        }
    }

}
