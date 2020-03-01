package com.example.cpl;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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


public class ViewFeedbackFragment extends Fragment {

      List<FeedbackList> feedbackLists = new ArrayList<>();
      RecyclerView rvViewFeedbackList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_feedback, container, false);
        rvViewFeedbackList = view.findViewById(R.id.rvViewFeedback);
        new MyTask().execute();

        return view;
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {
        String return_msg;

        @Override
        protected Void doInBackground(Void... voids) {
            URL url = null;
            try {

                url = new URL("http://" + Constants.localHost+"/" + Constants.projectPath + "leagueManager/viewFeedback");

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

                JSONArray feedbackArray = obj.getJSONArray("Feedbacks");
                JSONObject ViewList;
                for(int i=0;i<feedbackArray.length();i++){
                    ViewList=feedbackArray.getJSONObject(i);
                    int feedbackId = ViewList.getInt("feedbackId");
                    String email = ViewList.getString("email");
                    String title = ViewList.getString("title");
                    String description = ViewList.getString("description");
                    System.out.println(feedbackId);
                    System.out.println(email);
                    System.out.println(title);
                    System.out.println(description);

                    feedbackLists.add(new FeedbackList(feedbackId,email,title,description));
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

            rvViewFeedbackList.setLayoutManager(new LinearLayoutManager(getActivity()));
            ViewFeedbackAdapter FAdapter = new ViewFeedbackAdapter(feedbackLists,getActivity());
            rvViewFeedbackList.setAdapter(FAdapter);

            super.onPostExecute(result);
        }
    }

}
