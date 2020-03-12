package com.example.cpl;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
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
    EditText etMatchNo,etDate,etVenue;
    Button btnCreateMatch;
    Spinner etTeamA,etTeamB;
    int year,month,day;
    DatePickerDialog.OnDateSetListener mDOBListener;
    String date,venue,result,matchNumber;
    public ArrayList<String> TeamList = new ArrayList<>();
    private String teamAlist = null;
    private String teamBlist = null;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentMatch = inflater.inflate(R.layout.fragment_create_match, container, false);

        progressBar = fragmentMatch.findViewById(R.id.progressBar);
        etMatchNo=fragmentMatch.findViewById(R.id.etMatchNo);
        etTeamA=(Spinner)fragmentMatch.findViewById(R.id.etTeamA);
        etTeamB=(Spinner)fragmentMatch.findViewById(R.id.etTeamB);
        etDate=fragmentMatch.findViewById(R.id.etDate);
        etVenue=fragmentMatch.findViewById(R.id.etVenue);
        btnCreateMatch=fragmentMatch.findViewById(R.id.btnCreateMatch);
        new MyTask2().execute();
        final Calendar calendar=Calendar.getInstance();

        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dp = new DatePickerDialog(getActivity(),mDOBListener,year, month, day);
                dp.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dp.show();
            }
        });
        mDOBListener = new DatePickerDialog.OnDateSetListener() {
            String month,day;
            @Override
            public void onDateSet(DatePicker datePicker, int year, int m, int d) {
                if (m < 10) {
                    month = "0" + (m + 1);
                } else {
                    month = String.valueOf(m + 1);
                }
                if (d < 10) {
                    day = "0" + d;
                } else {
                    day = String.valueOf(d);
                }
                String date = year +"-"+ month +"-"+ day;
                etDate.setText(date);

            }
        };

        btnCreateMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateInput()) {
                    new MyTask().execute();
                }
            }
        });
        return fragmentMatch;
    }

    private boolean validateInput() {

        matchNumber = (etMatchNo.getText().toString().trim());
        date = (etDate.getText().toString().trim());
        venue = (etVenue.getText().toString().trim());

        if (matchNumber.isEmpty()) {
            etMatchNo.requestFocus();
            etMatchNo.setError("Match number can't be empty");
            return false;
        }  if (date.isEmpty()) {
            etDate.requestFocus();
            etDate.setError("Date can't be empty");
            return false;
        }   if (venue.isEmpty()) {
            etVenue.requestFocus();
            etVenue.setError("Venue can't be empty");
            return false;
        }
        else {
            etMatchNo.setError(null);
            etDate.setError(null);
            etVenue.setError(null);
            return true;
        }
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

            switch (parent.getId()) {
                case R.id.etTeamA:
                    if (teamAlist != null) {
                        Toast.makeText(parent.getContext(), selectedItem,
                                Toast.LENGTH_LONG).show();
                    }
                    teamAlist = selectedItem;
                    break;
                case R.id.etTeamB:
                    if (teamBlist != null) {
                        Toast.makeText(parent.getContext(), selectedItem,
                                Toast.LENGTH_LONG).show();
                    }
                    teamBlist = selectedItem;
                    break;
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) { }
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
                url = new URL("http://" + Constants.localHost+"/" + Constants.projectPath + "leagueManager/createMatch"+"&"+matchNumber+"&"+teamAlist+"&"+teamBlist+"&"+date+"&"+venue+"&"+null+"&"+null+"&"+1+"&"+1);

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
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }
}
