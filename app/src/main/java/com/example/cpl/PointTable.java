package com.example.cpl;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

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
import java.util.HashMap;
import java.util.List;

import static java.sql.Types.NULL;


/**
 * A simple {@link Fragment} subclass.
 */
public class PointTable extends Fragment {

    List<PointsTableList> pointsTableLists = new ArrayList<>();
    RecyclerView rvPointsTableList;
    private ProgressBar progressBar;
    public ArrayList<String> Seasonlist = new ArrayList<>();
    private String seasonList = null;
    Spinner spinner;
    int seasonId;
    private int val= 0;
    public HashMap<String, Integer> HSeasonList = new HashMap<>();
    public ArrayList<String> TeamNames = new ArrayList<String>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_point_table, container, false);

        rvPointsTableList = view.findViewById(R.id.rvPointsTable);
        progressBar = view.findViewById(R.id.progressBar);
        spinner=(Spinner)view.findViewById(R.id.spinner);
        new MyTask1().execute();


        return view;
    }
    private class MyTask1 extends AsyncTask<Void, Void, Void> {
        String return_msg;

        @Override
        protected Void doInBackground(Void... voids) {
            URL url = null;
            try {

                url = new URL("http://" + Constants.localHost + "/" + Constants.projectPath + "main/viewSeason");

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
                return_msg = mainObject.getString("Status");

                JSONArray teamManagerArray = mainObject.getJSONArray("Seasons");
                JSONObject singlename;
                for (int i = 0; i < teamManagerArray.length(); i++) {
                    singlename = teamManagerArray.getJSONObject(i);
                    String teamName = singlename.getString("Season Title");
                    int teamId = singlename.getInt("SeasonId");
                    System.out.println(teamName);
                    HSeasonList.put(teamName,teamId);
                    System.out.println("..........."+HSeasonList);
                    for ( String key : HSeasonList.keySet() ) {
                        TeamNames.add(key);
                    }

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

            ArrayAdapter SAdapter = new ArrayAdapter(getContext(), R.layout.single_spinnerdata,TeamNames);
            SAdapter.setDropDownViewResource(R.layout.single_spinnerdata);
            spinner.setAdapter(SAdapter);

            spinner.setOnItemSelectedListener(new PointTable.MyOnItemSelectedListener());


        }
    }

    private class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selectedItem = parent.getItemAtPosition(position).toString();

            seasonList = selectedItem;
            val = (int)HSeasonList.get(seasonList);
            System.out.println(val);
            if(val != NULL )
            {
                new MyTask().execute();}
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

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

                url = new URL("http://192.168.2.30:8080/WebServicesCPL/cpl/main/viewPointsTable&"+val);

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

            if(return_msg.equals("null"))
            {
                progressBar.setVisibility(View.GONE);

            }
            else
            {
                rvPointsTableList.setLayoutManager(new LinearLayoutManager(getActivity()));
                PointsTableAdapter showTableAdapter = new PointsTableAdapter(pointsTableLists,getActivity());
                rvPointsTableList.setAdapter(showTableAdapter);
                progressBar.setVisibility(View.INVISIBLE);}

            super.onPostExecute(result);
        }
    }


}
