package com.example.cpl;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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


/**
 * A simple {@link Fragment} subclass.
 */
public class PointTable extends Fragment {

    List<PointsTableList> pointsTableLists = new ArrayList<>();
    RecyclerView rvPointsTableList;
    private ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_point_table, container, false);

        rvPointsTableList = view.findViewById(R.id.rvPointsTable);
        progressBar = view.findViewById(R.id.progressBar);
        new MyTask().execute();

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

                url = new URL("http://192.168.2.32:8080/WebServicesCPL/cpl/main/viewPointsTable&1");

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

                //print result
                System.out.println(response.toString());

                JSONObject obj = new JSONObject(response.toString());
                return_msg = obj.getString("Message");

                JSONArray pointsTableArray = obj.getJSONArray("PointsTable");
                JSONObject ViewList;
                for(int i=0;i<pointsTableArray.length();i++){
                    ViewList=pointsTableArray.getJSONObject(i);

                    String team_Name = ViewList.getString("TeamName");
                    int playMatch = ViewList.getInt("play");
                    int WinMatch = ViewList.getInt("Win");
                    int LoseMatch = ViewList.getInt("Lose");
                    int MatchPoints = ViewList.getInt("Points");
                    System.out.println(team_Name);
                    System.out.println(playMatch);
                    System.out.println(WinMatch);
                    System.out.println(LoseMatch);
                    System.out.println(MatchPoints);

                    pointsTableLists.add(new PointsTableList(team_Name,playMatch,WinMatch,LoseMatch,MatchPoints));
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

             rvPointsTableList.setLayoutManager(new LinearLayoutManager(getActivity()));
            PointsTableAdapter showTableAdapter = new PointsTableAdapter(pointsTableLists,getActivity());
            rvPointsTableList.setAdapter(showTableAdapter);
            progressBar.setVisibility(View.INVISIBLE);

            super.onPostExecute(result);
        }
    }

}
