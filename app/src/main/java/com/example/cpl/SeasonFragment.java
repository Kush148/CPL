package com.example.cpl;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class SeasonFragment extends Fragment {
    View view;
    private RecyclerView sRecyclerview;
    private ProgressBar progressBar;
    List<Season> SeasonList = new ArrayList<>();
    FloatingActionButton fbSeason;
    SharedPref pref;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_season, container, false);
        sRecyclerview = view.findViewById(R.id.rc_SeasonId);
        progressBar = view.findViewById(R.id.progressBar);
        fbSeason = view.findViewById(R.id.fbSeason);
        new MyTask().execute();

        pref=new SharedPref(this.getActivity());
        Constants.userId = pref.getId();
        Constants.userType = pref.getManagerType();

        if(Constants.userId.equals("1") && Constants.userType.equalsIgnoreCase("Leaguemanager")){
          fbSeason.setVisibility(View.VISIBLE);
        }
        fbSeason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment createSeason = new CreateSeasonFragment();
                getFragmentManager().beginTransaction().replace(R.id.frame_layout, createSeason).commit();

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
                return_msg = mainObject.getString("Message");

                JSONArray SeasonArray = mainObject.getJSONArray("Seasons");
                JSONObject singleSeason;
                for (int i = 0; i < SeasonArray.length(); i++) {
                    singleSeason = SeasonArray.getJSONObject(i);
                    int SeasonId = singleSeason.getInt("SeasonId");
                    String SeasonTitle = singleSeason.getString("Season Title");
                    String Description = singleSeason.getString("Description");
                    String StartDate = singleSeason.getString("Start Date");
                    String EndDate = singleSeason.getString("End Date");

                    SeasonList.add(new Season(SeasonId,SeasonTitle, StartDate, EndDate));
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
            if (return_msg.equals("Available")) {
                sRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
                SeasonAdapter SAdapter = new SeasonAdapter(SeasonList, getActivity());
                sRecyclerview.setAdapter(SAdapter);
                progressBar.setVisibility(View.INVISIBLE);
            }else{
                Toast.makeText(getContext(),"Please try again",Toast.LENGTH_LONG).show();
            }
        }
    }
}
