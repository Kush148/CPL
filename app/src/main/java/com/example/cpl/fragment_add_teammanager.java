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
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

public class fragment_add_teammanager extends Fragment {

  EditText id,name,email,contact,password,dob;
  String Password,Uname,Uemail,Ucontact,DOB;
  Button bt;

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^" + ".{6,12}" + "$");

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentaddManager = inflater.inflate(R.layout.fragment_add_teammanager, container, false);
        id = fragmentaddManager.findViewById(R.id.userId);
        name = fragmentaddManager.findViewById(R.id.userName);
        email = fragmentaddManager.findViewById(R.id.email);
        password = fragmentaddManager.findViewById(R.id.password);
        dob = fragmentaddManager.findViewById(R.id.dob);
        contact = fragmentaddManager.findViewById(R.id.contact);
        bt = fragmentaddManager.findViewById(R.id.addManger);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInput())
                {
                    Uname = name.toString();
                    Uemail = email.toString();
                    Password = password.toString();
                    DOB = dob.toString();
                    Ucontact = contact.toString();
                    new MyTask().execute();
                }

            }
        });
        return fragmentaddManager;
    }

    private boolean validateInput(){

        Uname=name.getText().toString().trim();
        Uemail = email.getText().toString().trim();
        Password = password.getText().toString().trim();
        DOB=dob.getText().toString().trim();
        Ucontact = contact.getText().toString().trim();

        if (Uname.isEmpty()) {
            name.requestFocus();
            name.setError("Username can't be empty");
            return false;
        }
        else if (Uemail.isEmpty()) {
            email.requestFocus();
            email.setError("Email can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(Uemail).matches()) {
            email.requestFocus();
            email.setError("Please enter a valid email address");
            return false;
        }
        if (Password .isEmpty()) {
            password.requestFocus();
            password.setError("Password can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(Password).matches()) {
            password.requestFocus();
            password.setError("Password Must be of 6-12 characters");
            return false;
        }
        else if (DOB.isEmpty()) {
            dob.requestFocus();
            dob.setError("Date of birth can't be empty");
            return false;
        }else if (Ucontact.isEmpty()) {
            contact.requestFocus();
            contact.setError("Contact Number can't be empty");
            return false;
        }
        else if (Ucontact.length()<10) {
            contact.requestFocus();
            contact.setError("Contact Number should contain 10 digits");
            return false;
        }
        else {
            name.setError(null);
            email.setError(null);
            password.setError(null);
            dob.setError(null);
            contact.setError(null);
            return true;
        }
    }
    public class MyTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            URL url = null;
            try {
                url = new URL("http://" + Constants.localHost+"/" + Constants.projectPath + "leagueManager/addteammanager&" + Uname  + "&" + Uemail + "&" + Password + "&"+ DOB + "&"+ Ucontact );
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
                }
                in.close();

                //print result
                System.out.println(response.toString());
                JSONObject obj =new JSONObject(response.toString());
                String return_msg = obj.getString("Message");
                System.out.println(return_msg);
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
        protected void onPostExecute(Void result){

            super.onPostExecute(result);
            System.out.println("executed");
        }
    }
}
