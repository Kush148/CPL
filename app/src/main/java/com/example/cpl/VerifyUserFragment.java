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

public class VerifyUserFragment extends Fragment {
    EditText etEmail,etDob;
    String userEmail,userDob;
    Button btnConfirm;
    DatePickerDialog.OnDateSetListener mDOBListener;
    int year,month,day;
    ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View fragmentVerify = inflater.inflate(R.layout.fragment_verify_user,container,false);
        etEmail=fragmentVerify.findViewById(R.id.userEmail);
        etDob=fragmentVerify.findViewById(R.id.userDob);
        progressBar=fragmentVerify.findViewById(R.id.progressBar);

        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);

        btnConfirm=fragmentVerify.findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEmail=etEmail.getText().toString();
                userDob=etDob.getText().toString();
                new MyTask().execute();
            }
        });

        etDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dp = new DatePickerDialog(getActivity(),mDOBListener,year, month, day);
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
                etDob.setText(date);

            }
        };
        return fragmentVerify;
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {
        String return_msg;
        int userId;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            btnConfirm.setVisibility(View.GONE);
        }
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

            progressBar.setVisibility(View.GONE);
            btnConfirm.setVisibility(View.VISIBLE);

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
