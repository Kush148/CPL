package com.example.cpl;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class ChangePasswordFragment extends Fragment {

    EditText etNewPass, etConfirmPass;
    Button btnReset;
    String newPass, confirmPass;
    int userId;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View changePass = inflater.inflate(R.layout.fragment_change_password, container, false);

        etNewPass = changePass.findViewById(R.id.etNewPass);
        etConfirmPass = changePass.findViewById(R.id.etConfirmPass);
        btnReset = changePass.findViewById(R.id.btnReset);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
             userId = bundle.getInt("UserId", -1);
        }

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPass = etNewPass.getText().toString();
                confirmPass = etNewPass.getText().toString();
                if ((newPass.isEmpty())) {
                    Toast.makeText(getActivity(), "New Password Cannot be Empty", Toast.LENGTH_SHORT).show();
                } else if (confirmPass.isEmpty()) {
                    Toast.makeText(getActivity(), "Please Confirm your new password", Toast.LENGTH_SHORT).show();
                }
                if (!newPass.equals(confirmPass)) {
                    Toast.makeText(getActivity(), "Passwords Doesnt Match", Toast.LENGTH_SHORT).show();
                } else {
                    new MyTask().execute();
                }
            }
        });
        return changePass;
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {
        String return_msg;

        @Override
        protected Void doInBackground(Void... voids) {
            URL url = null;
            try {
                url = new URL("http://" + Constants.localHost + "/" + Constants.projectPath + "main/changePassword&"+newPass+"&"+userId);

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
            if (return_msg.equals("Password updated successfully")) {
                Toast.makeText(getActivity(), "Password Changed", Toast.LENGTH_SHORT).show();
                Fragment loginFragment = new LoginFragment();
               getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, loginFragment).commit();

            } else {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }
}
