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
import android.widget.ProgressBar;
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

public class CreateSeasonFragment extends Fragment {
    EditText etTitle, etDescription, etStartDate, etEndDate;
    DatePickerDialog.OnDateSetListener mSDListener,mEDListener;
    int year,month,day;
    Button btnSeason;
    String seasonTitle,seasonDescription,sStartDate,sEndDate;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View createSeason = inflater.inflate(R.layout.fragment_create_season, container, false);

        etTitle = createSeason.findViewById(R.id.etSeasonTitle);
        etDescription = createSeason.findViewById(R.id.etSeasonDescription);
        etStartDate = createSeason.findViewById(R.id.etStartDate);
        etEndDate = createSeason.findViewById(R.id.etEndDate);
        btnSeason = createSeason.findViewById(R.id.btnCreateSeason);
        progressBar = createSeason.findViewById(R.id.progressBar);
        Calendar cal = Calendar.getInstance();

        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);

        etStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dp = new DatePickerDialog(getActivity(),
                        mSDListener,
                        year, month, day);
                dp.show();
            }
        });

        etEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dp = new DatePickerDialog(getActivity(),
                        mEDListener,
                        year, month, day);
                dp.show();
            }
        });

        mSDListener = new DatePickerDialog.OnDateSetListener() {
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
                etStartDate.setText(date);
            }
        };

        mEDListener = new DatePickerDialog.OnDateSetListener() {
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
                etEndDate.setText(date);
            }
        };


        btnSeason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seasonTitle=etTitle.getText().toString();
                seasonDescription=etDescription.getText().toString();
                sStartDate=etStartDate.getText().toString();
                sEndDate=etEndDate.getText().toString();
                new MyTask().execute();
            }
        });
        //Validation
        return createSeason;
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {
        String return_msg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            btnSeason.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            URL url = null;
            try {
                url = new URL("http://" + Constants.localHost + "/" + Constants.projectPath + "leagueManager/createSeason&" + seasonTitle + "&" + sStartDate + "&" + sEndDate+ "&" + seasonDescription);

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
            btnSeason.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            if (return_msg.equals(" Records have successfully been inserted.")) {
                Toast.makeText(getActivity(), "Season Created", Toast.LENGTH_SHORT).show();
                Fragment homeFragment = new HomeFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, homeFragment).commit();
            } else {
                Toast.makeText(getActivity(), "Invalid username and password", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }
}