package com.example.cpl;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

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

public class TeamCreate extends Fragment {

    EditText Teamname;
    Button btn;
    ProgressBar progressBar;
    String teamname,teamcolor,return_msg;
    Spinner spinner,Teamcolor;
    private String selectedmanagername = null;
    private int val= 0;
    public HashMap<String, Integer> TeamManagerList = new HashMap<>();
    public ArrayList<String> managernames = new ArrayList<String>();
    String[] colour = { "BLUE","GREEN","ORANGE","PINK","PURPLE","RED","YELLOW"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentCreateTeam = inflater.inflate(R.layout.fragment_team_create,container,false);
        Teamname = fragmentCreateTeam.findViewById(R.id.teamname);
        Teamcolor =(Spinner)fragmentCreateTeam.findViewById(R.id.teamcolor);
        spinner=(Spinner)fragmentCreateTeam.findViewById(R.id.teammanager);
        progressBar = fragmentCreateTeam.findViewById(R.id.progressBar);
        Teamcolor.setOnItemSelectedListener(new MyOnItemSelectedListener());
        ArrayAdapter aa = new ArrayAdapter(getActivity(),R.layout.single_spinnerdata,colour);
        Teamcolor.setAdapter(aa);
        btn =fragmentCreateTeam.findViewById(R.id.btncreateteam);

        new MyTask2().execute();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validateInput())
                {
                    new MyTask().execute();
                }

            }
        });
        return fragmentCreateTeam;
    }
    private boolean validateInput() {

        teamname = (Teamname.getText().toString().trim());

        if (teamname.isEmpty()) {
            Teamname.requestFocus();
            Teamname.setError("Team Name can't be empty");
            return false;
        }
        else {
            Teamname.setError(null);
            return true;
        }
    }

    private class MyTask2 extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            URL url = null;
            try {

                url = new URL("http://" + Constants.localHost+"/" + Constants.projectPath +"main/viewTeamManager");

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

                System.out.println(response.toString());

                JSONObject mainObject = new JSONObject(response.toString());
                return_msg = mainObject.getString("Message");

                JSONArray teamManagerArray = mainObject.getJSONArray("TeamManagers");
                JSONObject singlename;
                for (int i = 0; i < teamManagerArray.length(); i++) {
                    singlename = teamManagerArray.getJSONObject(i);
                    String UserName = singlename.getString("UserName");
                    int teamManagerId = singlename.getInt("teamManagerId");
                    System.out.println(UserName);
                    TeamManagerList.put(UserName,teamManagerId);
                    System.out.println(TeamManagerList);
                }
                for ( String key : TeamManagerList.keySet() ) {
                    managernames.add(key);
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
            if (return_msg.equals("Available")){
                ArrayAdapter SAdapter = new ArrayAdapter(getContext(),R.layout.single_spinnerdata, managernames);
                SAdapter.setDropDownViewResource(R.layout.single_spinnerdata);
                spinner.setAdapter(SAdapter);
            }
            else
            {
                Toast.makeText(getActivity(),"Error" , Toast.LENGTH_SHORT).show();
            }
            spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
        }
    }
    public class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selectedItem = parent.getItemAtPosition(position).toString();

            teamcolor= colour[position].toLowerCase();
            switch (parent.getId()) {
                case R.id.teammanager:
                    selectedmanagername = selectedItem;
                    val = (int)TeamManagerList.get(selectedmanagername);
                    break;
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }
    private class MyTask extends AsyncTask<Void , Void , Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            btn.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected Void doInBackground(Void... voids) {
            URL url = null;
            try {

                url = new URL("http://" + Constants.localHost+"/" + Constants.projectPath +"leagueManager/createTeam&"+ teamname  + "&" + teamcolor+ "&" + val);
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
                System.out.println(response.toString());
                JSONObject obj =new JSONObject(response.toString());
                return_msg = obj.getString("Message");
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

            btn.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            if (return_msg.equals(" Record(s) have been successfully inserted.")){
                Toast.makeText(getActivity()," Record(s) have been successfully inserted.",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getActivity(),"Error" , Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
            System.out.println("executed");
        }
    }
}