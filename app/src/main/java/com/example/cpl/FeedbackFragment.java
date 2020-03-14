package com.example.cpl;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View feedbackInsert = inflater.inflate(R.layout.fragment_insert_feedback,container,false);

        ETemail=feedbackInsert.findViewById(R.id.et_email);
        ETtitle=feedbackInsert.findViewById(R.id.et_title);
        ETdescription=feedbackInsert.findViewById(R.id.et_description);
        btnSubmit=feedbackInsert.findViewById(R.id.btnSubmit);
        progressBar=feedbackInsert.findViewById(R.id.progressBar);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validateInput()) {
                    new MyTask().execute();
                }
            }
        });
        return feedbackInsert;
    }

    private boolean validateInput() {

        email = (ETemail.getText().toString().trim());
        title = (ETtitle.getText().toString().trim());
        description = (ETdescription.getText().toString().trim());

        if (email.isEmpty()) {
            ETemail.requestFocus();
            ETemail.setError("Email can't be empty");
            return false;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            ETemail.requestFocus();
            ETemail.setError("Please enter a valid email address");
            return false;
        }else if (title.isEmpty()) {
            ETtitle.requestFocus();
            ETtitle.setError("Title can't be empty");
            return false;
        }else if (description.isEmpty()) {
            description="None";
            return true;
        }
        else {
            ETemail.setError(null);
            ETtitle.setError(null);
            return true;
        }
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {
        String return_msg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            btnSubmit.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }

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
            btnSubmit.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            if(return_msg.equalsIgnoreCase("Feedback Sent")){
                Toast.makeText(getActivity(),"Feedback Sent Successfully",Toast.LENGTH_SHORT).show();
                Fragment currentFragment = new MoreFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, currentFragment).commit();

            }else{
                Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
            }

            super.onPostExecute(result);
        }
    }


}
