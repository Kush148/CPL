package com.example.cpl;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class SeasonFragment extends Fragment {
    View view;
    private RecyclerView sRecyclerview;
    List<Season> SeasonList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fregment_league, container, false);
        sRecyclerview = (RecyclerView) view.findViewById(R.id.rc_SeasonId);
        // get the reference of Button
        new MyTask().execute();
        return view;

    }
    private class MyTask extends AsyncTask<Void, Void, Void> {
        String return_msg;

        @Override
        protected Void doInBackground(Void... voids) {
            URL url = null;
            try {

                url = new URL("http://" + Constants.localHost+"/" + Constants.projectPath + "main/viewSeason");

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

                JSONArray SeasonArray = mainObject.getJSONArray("String");
                JSONObject singleSeason;
                for (int i = 0; i < SeasonArray.length(); i++) {
                    singleSeason = SeasonArray.getJSONObject(i);
                    int seasonId=singleSeason.getInt("SeasonId");
                    String SeasonTitle = singleSeason.getString("Season Title");
                    String Description = singleSeason.getString("Description");
                    String StartDate = singleSeason.getString("Start Date");
                    String EndDate = singleSeason.getString("End Date");

                    SeasonList.add(new Season(seasonId,SeasonTitle,StartDate,EndDate));
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
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa");
            sRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
            SeasonAdapter SAdapter = new SeasonAdapter(SeasonList,getActivity());
            sRecyclerview.setAdapter(SAdapter);


        }
    }
}
