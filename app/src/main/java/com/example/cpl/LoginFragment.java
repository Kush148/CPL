package com.example.cpl;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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
import java.util.regex.Pattern;

public class LoginFragment extends Fragment {

    Button btnLogin;
    ProgressBar progressBar;
    EditText etEmail, etPassword;
    TextView tvForgotPass;
    RadioGroup rgManagerType;
    RadioButton rbLeagueManager, rbTeamManager;
    String userType, userEmail, userPassword,userId;
    SharedPref pref;

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^" + ".{6,12}" + "$");


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentLogin = inflater.inflate(R.layout.fragment_login, container, false);

        etEmail = fragmentLogin.findViewById(R.id.etEmail);
        etPassword = fragmentLogin.findViewById(R.id.etPassword);
        btnLogin = fragmentLogin.findViewById(R.id.btnLogin);
        progressBar = fragmentLogin.findViewById(R.id.progressBar);
        tvForgotPass = fragmentLogin.findViewById(R.id.tvForgotPassword);
        rgManagerType = fragmentLogin.findViewById(R.id.rgManagerType);
        rbLeagueManager = fragmentLogin.findViewById(R.id.rb_leagueManager);
        rbTeamManager = fragmentLogin.findViewById(R.id.rb_TeamManager);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateEmail() && validatePassword()) {
                    if (rbLeagueManager.isChecked()) {
                        userType = "LeagueManager";
                        new MyTask().execute();
                    } else if (rbTeamManager.isChecked()) {
                        userType = "TeamManager";
                        new MyTask().execute();
                    }
                }
            }
        });

        tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment verifyUser = new VerifyUserFragment();
                getFragmentManager().beginTransaction().replace(R.id.frame_layout, verifyUser).commit();
            }
        });
        return fragmentLogin;
    }

    private boolean validateEmail() {

        userEmail = etEmail.getText().toString().trim();

        if (userEmail.isEmpty()) {
            etEmail.requestFocus();
            etEmail.setError("Email can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            etEmail.requestFocus();
            etEmail.setError("Please enter a valid email address");
            return false;
        } else {
            etEmail.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        userPassword = etPassword.getText().toString().trim();

        if (userPassword.isEmpty()) {
            etPassword.requestFocus();
            etPassword.setError("Password can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(userPassword).matches()) {
            etPassword.requestFocus();
            etPassword.setError("Password Must be of 6-12 characters");
            return false;
        } else {
            etPassword.setError(null);
            return true;
        }
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {
        String return_msg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            btnLogin.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            URL url = null;
            try {
                url = new URL("http://" + Constants.localHost + "/" + Constants.projectPath + "main/userLogin&" + userType + "&" + userEmail + "&" + userPassword);

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
                userId=String.valueOf(obj.getInt("UserId"));
                System.out.println("json "+obj.getInt("UserId"));

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
            btnLogin.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            if (return_msg.equals("Login Successfull")) {
                pref=new SharedPref(getContext());
                pref.setId(userId);
               // System.out.println(pref.getId()+"sp");
                pref.setManagerType(userType);
                Toast.makeText(getActivity(), "Login Successfull", Toast.LENGTH_SHORT).show();
                Fragment homeFragment = new HomeFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, homeFragment).commit();
            } else {
                Toast.makeText(getActivity(), "Invalid username and password", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }
}


