package com.example.cpl;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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


public class BrowsePlayers extends Fragment {

    List<BrowsePlayerList> playerLists = new ArrayList<>();
    RecyclerView rvBrowsePlayersList;
    EditText etbrowse;
    Button btnBrowse;
    String browsing;
    ImageView arrow;
    TextView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View browsePlayer = inflater.inflate(R.layout.fragment_browse_players, container, false);

        rvBrowsePlayersList = browsePlayer.findViewById(R.id.rvBrowsePlayer);
        etbrowse = browsePlayer.findViewById(R.id.etBrowsePlayer);
        btnBrowse = browsePlayer.findViewById(R.id.btnBrowsePlayer);
        arrow = browsePlayer.findViewById(R.id.ivArrow);
        tv=browsePlayer.findViewById(R.id.tv);

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment goback = new MoreFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frame_layout,goback);
                ft.commit();
            }
        });

        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                browsing = etbrowse.getText().toString();
                new MyTask().execute();
            }
        });

        return browsePlayer;
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {
        String return_msg;

        @Override
        protected Void doInBackground(Void... voids) {
            URL url = null;
            try {

                url = new URL("http://" + Constants.localHost+"/" + Constants.projectPath +"main/browsePlayers&"+browsing);

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
                }
                in.close();

                System.out.println(response.toString());

                JSONObject obj = new JSONObject(response.toString());
                return_msg = obj.getString("Message");

                JSONArray browsePlayerArray = obj.getJSONArray("Player Details");
                JSONObject ViewList;
                for(int i=0;i<browsePlayerArray.length();i++){
                    ViewList=browsePlayerArray.getJSONObject(i);
                    int playerId = ViewList.getInt("playerId");
                    String playerName = ViewList.getString("playerName");
                    String birthDate = ViewList.getString("dob");
                    String playerRole = ViewList.getString("role");
                    String birthPlace = ViewList.getString("birthPlace");
                    String img = ViewList.getString("url");
                    int teamId = ViewList.getInt("teamId");

                    System.out.println(playerId);
                    System.out.println(playerName);
                    System.out.println(birthDate);
                    System.out.println(playerRole);
                    System.out.println(birthPlace);
                    System.out.println(teamId);

                    playerLists.add(new BrowsePlayerList(playerId,playerName,birthDate,playerRole,birthPlace,img,teamId));
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
            if(return_msg.equals("Available")) {
                rvBrowsePlayersList.setLayoutManager(new LinearLayoutManager(getActivity()));
                BrowsePlayerAdapter BrowseAdapter = new BrowsePlayerAdapter(playerLists, getActivity());
                rvBrowsePlayersList.setAdapter(BrowseAdapter);
            }
            else if(return_msg.equals("null"))
            {
                tv.setText("No players Available");
                tv.setVisibility(View.VISIBLE);
                rvBrowsePlayersList.setVisibility(View.INVISIBLE);
            }
            else
            {
                Toast.makeText(getContext(),"",Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(result);
        }
    }

}
