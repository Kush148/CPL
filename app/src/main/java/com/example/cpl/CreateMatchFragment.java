package com.example.cpl;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.Calendar;

public class CreateMatchFragment extends Fragment {

    EditText etMatchNo,etTeamA,etTeamB,etDate,etVenue,etResult,etResultDescription;
    Button btnCreateMatch;
    int matchNo,y,m,d;
    String teamA,teamB,date,venue,result,resultDescription;
    String  DOB;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentLogin = inflater.inflate(R.layout.fragment_create_match, container, false);


        etMatchNo=fragmentLogin.findViewById(R.id.etMatchNo);
        etTeamA=fragmentLogin.findViewById(R.id.etTeamA);
        etTeamB=fragmentLogin.findViewById(R.id.etTeamB);
        etDate=fragmentLogin.findViewById(R.id.etDate);
        etVenue=fragmentLogin.findViewById(R.id.etVenue);
        etResult=fragmentLogin.findViewById(R.id.etResult);
        etResultDescription=fragmentLogin.findViewById(R.id.etResultDescription);
        btnCreateMatch=fragmentLogin.findViewById(R.id.btnCreateMatch);

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
                teamA = etTeamA.getText().toString();
                teamB = etTeamB.getText().toString();
                date = etDate.getText().toString();
                venue = etVenue.getText().toString();
                result = etResult.getText().toString();
                resultDescription = etResultDescription.getText().toString();

                new MyTask().execute();

            }
        });

        return fragmentLogin;
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {
        String return_msg;

        @Override
        protected Void doInBackground(Void... voids) {
            URL url = null;
            try {

                url = new URL("http://" + Constants.localHost+"/" + Constants.projectPath + "leagueManager/createMatch"+"&"+matchNo+"&"+teamA+"&"+teamB+"&"+date+"&"+venue+"&"+result+"&"+resultDescription+"&"+1+"&"+1);

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

            if (return_msg.equals("Match Created")) {
                Toast.makeText(getActivity(), "Match Created", Toast.LENGTH_SHORT).show();
                 } else {
                Toast.makeText(getActivity(), "Something error occurred", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }


    }
