package com.example.cpl;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class FeedbackFragment extends Fragment {

    String email,title,description;
    Button btnSubmit;
    EditText ETemail, ETtitle, ETdescription;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View feedbackInsert = inflater.inflate(R.layout.fragment_insert_feedback,container,false);

        ETemail=feedbackInsert.findViewById(R.id.et_email);
        ETtitle=feedbackInsert.findViewById(R.id.et_title);
        ETdescription=feedbackInsert.findViewById(R.id.et_description);
        btnSubmit=feedbackInsert.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = ETemail.getText().toString();
                title = ETtitle.getText().toString();
                description = ETdescription.getText().toString();

                new MyTask().execute();
            }
        });


        return feedbackInsert;
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {
        String return_msg;

        @Override
        protected Void doInBackground(Void... voids) {
            URL url = null;
            try {

                url = new URL("http://" + Constants.localHost+"/" + Constants.projectPath + "main/sendFeedback&" + title + "&" + description + "&" + email);

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

            if(return_msg.equalsIgnoreCase("Feedback Sent")){
                Toast.makeText(getActivity(),"Feedback Sent Successfully",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
            }

            super.onPostExecute(result);
        }
    }


}
