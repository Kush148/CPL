package com.example.cpl;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import java.util.Calendar;

public class CreateMatchFragment extends Fragment {

    ProgressBar progressBar;
    EditText etMatchNo,etDate,etVenue,etResult,etResultDescription;
    Button btnCreateMatch;
    Spinner etTeamA,etTeamB;
    int matchNo,y,m,d;
    String teamA,teamB,date,venue,result,resultDescription;
    String  DOB;
    public ArrayList<String> TeamList = new ArrayList<>();
    private String teamAlist = null;
    private String teamBlist = null;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentLogin = inflater.inflate(R.layout.fragment_create_match, container, false);

        progressBar = fragmentLogin.findViewById(R.id.progressBar);
        etMatchNo=fragmentLogin.findViewById(R.id.etMatchNo);
        etTeamA=(Spinner)fragmentLogin.findViewById(R.id.etTeamA);
        etTeamB=(Spinner)fragmentLogin.findViewById(R.id.etTeamB);
        etDate=fragmentLogin.findViewById(R.id.etDate);
        etVenue=fragmentLogin.findViewById(R.id.etVenue);
        btnCreateMatch=fragmentLogin.findViewById(R.id.btnCreateMatch);
        new MyTask2().execute();
        final Calendar calendar=Calendar.getInstance();
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                y=calendar.get(Calendar.YEAR);
                m=calendar.get(Calendar.MONTH);
                m=calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(), R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                        String month="",day="";
                        if (monthOfYear < 10) {
                            month = "0" + (monthOfYear + 1);
                        } else {
                            month = String.valueOf(monthOfYear + 1);
                        }
                        if (dayOfMonth < 10) {
                            day = "0" + dayOfMonth;
                        } else {
                            day = String.valueOf(dayOfMonth);
                        }

                       DOB= year+"-"+(month)+"-"+day;
                       etDate.setText(DOB);
                    }
                },y,m,d);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        btnCreateMatch.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                matchNo=Integer.parseInt(etMatchNo.getText().toString());
                date = etDate.getText().toString();
                venue = etVenue.getText().toString();

                new MyTask().execute();

            }
        });

        return fragmentLogin;
    }

    private class MyTask2 extends AsyncTask<Void, Void, Void> {
        String return_msg;

        @Override
        protected Void doInBackground(Void... voids) {
            URL url = null;
            try {

                url = new URL("http://" + Constants.localHost+"/" + Constants.projectPath +"main/viewTeams");

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

                JSONArray teamManagerArray = mainObject.getJSONArray("Teams");
                JSONObject singlename;
                for (int i = 0; i < teamManagerArray.length(); i++) {
                    singlename = teamManagerArray.getJSONObject(i);
                    String teamName = singlename.getString("teamName");

                    System.out.println(teamName);
                    TeamList.add(teamName);
                    System.out.println(TeamList);
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

            ArrayAdapter SAdapter = new ArrayAdapter(getContext(),R.layout.single_spinnerdata,TeamList);
            SAdapter.setDropDownViewResource(R.layout.single_spinnerdata);
            etTeamA.setAdapter(SAdapter);
            etTeamB.setAdapter(SAdapter);

            etTeamA.setOnItemSelectedListener(new MyOnItemSelectedListener());
            etTeamB.setOnItemSelectedListener(new MyOnItemSelectedListener());

        }
    }

    private class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selectedItem = parent.getItemAtPosition(position).toString();

            //check which spinner triggered the listener
            switch (parent.getId()) {
                //country spinner
                case R.id.etTeamA:
                    //make sure the country was already selected during the onCreate
                    if (teamAlist != null) {
                        Toast.makeText(parent.getContext(), selectedItem,
                                Toast.LENGTH_LONG).show();
                    }
                    teamAlist = selectedItem;

                    break;
                case R.id.etTeamB:
                    //make sure the country was already selected during the onCreate
                    if (teamBlist != null) {
                        Toast.makeText(parent.getContext(), selectedItem,
                                Toast.LENGTH_LONG).show();
                    }
                    teamBlist = selectedItem;

                    break;
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }




    private class MyTask extends AsyncTask<Void, Void, Void> {
        String return_msg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            btnCreateMatch.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            URL url = null;
            try {

                url = new URL("http://" + Constants.localHost+"/" + Constants.projectPath + "leagueManager/createMatch"+"&"+matchNo+"&"+teamAlist+"&"+teamBlist+"&"+date+"&"+venue+"&"+null+"&"+null+"&"+1+"&"+1);

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
                System.out.println(return_msg);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;

        }

        protected void onPostExecute(Void result) {

            btnCreateMatch.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);

            if (return_msg.equals("Match Created")) {
                Toast.makeText(getActivity(), "Match Created", Toast.LENGTH_SHORT).show();
                 } else {
                Toast.makeText(getActivity(), "Something error occurred", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }


    }
