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

public class ViewScheduleFragment extends Fragment {

    RecyclerView rvScheduleList;
    List<ViewSchedule> scheduleList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentSchedule = inflater.inflate(R.layout.fragment_view_schedule, container, false);

        rvScheduleList = fragmentSchedule.findViewById(R.id.rvScheduleList);
        new MyTask().execute();

        return fragmentSchedule;
    }

        private class MyTask extends AsyncTask<Void, Void, Void> {
            String return_msg;

            @Override
            protected Void doInBackground(Void... voids) {
                URL url = null;
                try {
                    url = new URL("http://" + Constants.localHost+"/" + Constants.projectPath + "main/viewMatch&"+ SeasonAdapter.seasonid);

                    HttpURLConnection client = null;

                    client = (HttpURLConnection) url.openConnection();

                    client.setRequestMethod("GET");

                    int responseCode = client.getResponseCode();

                    System.out.println("\n Sending 'GET' request to URL : " + url);

                    System.out.println("Response Code : " + responseCode);

                    InputStreamReader myInput= new InputStreamReader(client.getInputStream());

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
                    return_msg=mainObject.getString("Message");

                    JSONArray scheduleArray = mainObject.getJSONArray("String");
                    JSONObject singleSchedule;
                    for(int i=0;i<scheduleArray.length();i++){
                        singleSchedule=scheduleArray.getJSONObject(i);

                        int matchNo = singleSchedule.getInt("Match Number");
                        String teamA = singleSchedule.getString("TeamA");
                        String teamB = singleSchedule.getString("TeamB");
                        String date = singleSchedule.getString("Date");
                        String venue = singleSchedule.getString("Venue");
                        String result = singleSchedule.getString("Result");
                        String resultDescription = singleSchedule.getString("Result Description");

                        scheduleList.add(new ViewSchedule(matchNo,teamA,teamB,date,venue,result,resultDescription));
                    }
                }
                catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {

                super.onPostExecute(result);
                rvScheduleList.setLayoutManager(new LinearLayoutManager(getActivity()));
                ScheduleAdapter SAdapter = new ScheduleAdapter(scheduleList,getActivity());
                rvScheduleList.setAdapter(SAdapter);
            }
        }
    }






