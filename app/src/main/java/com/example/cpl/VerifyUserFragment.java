package com.example.cpl;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class VerifyUserFragment extends Fragment {
    EditText etEmail,etDob;
    String userEmail,userDob;
    Button btnConfirm;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View fragmentVerify = inflater.inflate(R.layout.fragment_verify_user,container,false);
        etEmail=fragmentVerify.findViewById(R.id.userEmail);
        etDob=fragmentVerify.findViewById(R.id.userDob);

        btnConfirm=fragmentVerify.findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEmail=etEmail.getText().toString();
                userDob=etDob.getText().toString();
                new MyTask().execute();
            }
        });
        return fragmentVerify;
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {
        String return_msg;
        int userId;

        @Override
        protected Void doInBackground(Void... voids) {
            URL url = null;
            try {
                url = new URL("http://" + Constants.localHost + "/" + Constants.projectPath + "main/verifyUser&" + userEmail + "&" + userDob +" ");
                
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

                JSONObject obj = new JSONObject(response.toString());
                System.out.println(obj.toString());
                return_msg = obj.getString("Message");
                userId = obj.getInt("UserId");

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
            System.out.println("executed");
            if (return_msg.equals("Valid User")) {

                Fragment changePassword = new ChangePasswordFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("UserId", userId);
                changePassword.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, changePassword).commit();

            } else {
                Toast.makeText(getActivity(), "Invalid User", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }
}
