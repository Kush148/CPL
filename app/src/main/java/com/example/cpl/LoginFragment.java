package com.example.cpl;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginFragment extends Fragment {

    Button btnLogin;
    EditText etEmail, etPassword;
    TextView tvForgotPass;
    RadioGroup rgManagerType;
    RadioButton rbLeagueManager,rbTeamManager;
    String userType,userEmail,userPassword;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentLogin = inflater.inflate(R.layout.fragment_login, container, false);

        etEmail=fragmentLogin.findViewById(R.id.etEmail);
        etPassword=fragmentLogin.findViewById(R.id.etPassword);
        btnLogin = fragmentLogin.findViewById(R.id.btn_login);
        tvForgotPass = fragmentLogin.findViewById(R.id.reset_password);
        rgManagerType = fragmentLogin.findViewById(R.id.rgManagerType);
        rbLeagueManager = fragmentLogin.findViewById(R.id.rb_leagueManager);
        rbTeamManager = fragmentLogin.findViewById(R.id.rb_teamManager);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userEmail=etEmail.getText().toString();
                userPassword=etPassword.getText().toString();
                if(rbLeagueManager.isChecked()){
                    userType = "LeagueManager";
                    new MyTask().execute();
                }
                else if(rbTeamManager.isChecked()){
                    userType = "TeamManager";
                    new MyTask().execute();
                }
                System.out.println(userType);
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


    private class MyTask extends AsyncTask<Void, Void, Void> {
        String return_msg;

        @Override
        protected Void doInBackground(Void... voids) {
            URL url = null;
            try {
                url = new URL("http://" + Constants.localHost+"/" + Constants.projectPath + "main/userLogin&" + userType + "&" + userEmail + "&" + userPassword);

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
            System.out.println("executed");
            if (return_msg.equals("Login Successfull")) {
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


