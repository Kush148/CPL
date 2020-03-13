package com.example.cpl;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
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

    EditText Teamname,Teamcolor;
    Button btn;
    ProgressBar progressBar;
    String teamname,teamcolor;
   Spinner spinner;
   int teamManagerid;
   private String selectedmanagername = null;
   private int val= 0;
    public HashMap<String, Integer> TeamManagerList = new HashMap<>();
    public ArrayList<String> managernames = new ArrayList<String>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentCreateTeam = inflater.inflate(R.layout.fragment_team_create,container,false);
        Teamname = fragmentCreateTeam.findViewById(R.id.teamname);
        Teamcolor = fragmentCreateTeam.findViewById(R.id.teamcolor);
        spinner=(Spinner)fragmentCreateTeam.findViewById(R.id.teammanager);
        progressBar = fragmentCreateTeam.findViewById(R.id.progressBar);
        btn =fragmentCreateTeam.findViewById(R.id.btncreateteam);

        new MyTask2().execute();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                teamname = Teamname.getText().toString();
                teamcolor = Teamcolor.getText().toString();
                teamManagerid = teamManagerid;
               // new MyTask2().execute();
                new MyTask().execute();


                Toast.makeText(getActivity(),"Successfully inserted",Toast.LENGTH_LONG).show();
            }
        });

        return fragmentCreateTeam;
    }

          //  url = new URL("http://" + Constants.localHost+"/" + Constants.projectPath +"main/viewTeamManager");
          //.............................................
          private class MyTask2 extends AsyncTask<Void, Void, Void> {
              String return_msg;

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

                      //print result
                      System.out.println(response.toString());

                      JSONObject mainObject = new JSONObject(response.toString());
                      return_msg = mainObject.getString("Status");

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

                  ArrayAdapter SAdapter = new ArrayAdapter(getContext(),R.layout.single_spinnerdata, managernames);
                  SAdapter.setDropDownViewResource(R.layout.single_spinnerdata);
                  spinner.setAdapter(SAdapter);


                  spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());


              }
          }
    public class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selectedItem = parent.getItemAtPosition(position).toString();

            //check which spinner triggered the listener
            switch (parent.getId()) {
                //country spinner
                case R.id.teammanager:
                    //make sure the country was already selected during the onCreate
//                    if (selectedmanagername != null) {
//                        Toast.makeText(parent.getContext(), " you selected is " + selectedItem,
//                                Toast.LENGTH_LONG).show();
//                    }
                    selectedmanagername = selectedItem;
                    val = (int)TeamManagerList.get(selectedmanagername);
                    break;

            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

         //..............................................

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
               // url = new URL("http://" + Constants.localHost+"/" + Constants.projectPath + "main/userLogin&" + userType + "&" + userEmail + "&" + userPassword);
                url = new URL("http://" + Constants.localHost+"/" + Constants.projectPath +"leagueManager/createTeam&"+ teamname  + "&" + teamcolor + "&" + val);
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

            btn.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);

            super.onPostExecute(result);
            System.out.println("executed");
        }

    }


}


