package com.example.cpl;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class HomeFragment extends Fragment {

    ViewFlipper viewFlipper;
    TextView totalSeasontv;
    TextView totalMatchtv;
    TextView totalParticipantTv;
    int tSeason,tMatch,tParticipant;
    Animation slide_in_right, slide_out_left;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View fragmenthome = inflater.inflate(R.layout.fragment_home,container,false);
        totalSeasontv = fragmenthome.findViewById(R.id.tvSeason);
        totalMatchtv = fragmenthome.findViewById(R.id.tvMatch);
        totalParticipantTv = fragmenthome.findViewById(R.id.tvTeam);

        int  images[] = {R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5, R.drawable.image6, R.drawable.image7, R.drawable.image8, R.drawable.image9,R.drawable.image10};

        viewFlipper=fragmenthome.findViewById(R.id.vFlipper);

        for(int image:images)
        {
            imageFlipper(image);
        }
        new MyTask().execute();

        return fragmenthome;
    }

    public void imageFlipper(int images)
    {
        ImageView imageView=new ImageView(getActivity());
        imageView.setBackgroundResource(images);
        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        slide_in_right = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_right);
        slide_out_left = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_left);
        viewFlipper.setInAnimation(slide_in_right);
        viewFlipper.setOutAnimation(slide_out_left);

    }
    private class MyTask extends AsyncTask {
        String return_msg;
        @Override
        protected Object doInBackground(Object[] objects) {
            URL url = null;
            try {
                url = new URL("http://" + Constants.localHost+"/" + Constants.projectPath + "main/getCount");

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
                return_msg = mainObject.getString("Message");

                JSONArray scheduleArray = mainObject.getJSONArray("Counts");
                JSONObject singledata;
                for (int i = 0; i < scheduleArray.length(); i++) {
                    singledata = scheduleArray.getJSONObject(i);
                    tSeason = singledata.getInt("totalSeason");
                    tMatch = singledata.getInt("totalMatches");
                    tParticipant = singledata.getInt("totalTeam");
                    System.out.println(tSeason+tMatch+tParticipant);
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
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            if(return_msg.equals("Available")){
                totalSeasontv.setText(String.valueOf(tSeason));
                totalMatchtv.setText(String.valueOf(tMatch));
                totalParticipantTv.setText(String.valueOf(tParticipant));
            }
            else if(return_msg.equals(null)){
                Toast.makeText(getActivity(), "No information Available", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getActivity(), "Error! Try again later.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}