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

public class fragment_add_teammanager extends Fragment {

  EditText id,name,email,contact,password,dob;
  String Password,Uname,Uemail,Ucontact,DOB;
  Button bt;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentaddManager = inflater.inflate(R.layout.fragment_add_teammanager, container, false);
        id = fragmentaddManager.findViewById(R.id.userId);
        name = fragmentaddManager.findViewById(R.id.userName);
        email = fragmentaddManager.findViewById(R.id.email);
        password = fragmentaddManager.findViewById(R.id.password);
        dob = fragmentaddManager.findViewById(R.id.dob);
        contact = fragmentaddManager.findViewById(R.id.email);
        bt = fragmentaddManager.findViewById(R.id.addManger);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uname = name.toString();
                Uemail = email.toString();
                Password = password.toString();
                DOB = dob.toString();
                Ucontact = contact.toString();
                new MyTask().execute();
                Toast.makeText(getActivity(),"Button 0",Toast.LENGTH_LONG).show();

            }
        });

        return fragmentaddManager;

    }
    public class MyTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            URL url = null;
            try {
                url = new URL("http://192.168.2.44:8080/" + Constants.projectPath + "leagueManager/addteammanager&" + Uname  + "&" + Uemail + "&" + Password + "&"+ DOB + "&"+ Ucontact );
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
