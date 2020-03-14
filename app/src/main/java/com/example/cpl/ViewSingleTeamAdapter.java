package com.example.cpl;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ViewSingleTeamAdapter extends RecyclerView.Adapter<ViewSingleTeamAdapter.ViewHolder> {

    List<ViewSingleTeamInfo> listOfteam;
    Context context;
    ViewSingleTeamInfo teamList;
    static int playerId;

    public ViewSingleTeamAdapter(List<ViewSingleTeamInfo> teamList, Context context) {
        this.listOfteam = teamList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewSingleTeamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_team_player_list, parent, false);
        return new ViewSingleTeamAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewSingleTeamAdapter.ViewHolder holder, final int position) {

        teamList = listOfteam.get(position);
        holder.playerName.setText(teamList.getPlayerName());
        holder.role.setText(teamList.getPlayerRole());
        Picasso.with(context).load(teamList.getUrl()).into(holder.img);

        holder.imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerId = listOfteam.get(position).getPlayerId();
                new removePlayer().execute();
                delete(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOfteam.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView playerName, role;
        ImageView img, imgRemove;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.player_pic);
            playerName = itemView.findViewById(R.id.tv_playerName);
            role = itemView.findViewById(R.id.tv_role);
            imgRemove = itemView.findViewById(R.id.imgRemovePlayer);

        }
    }

private class removePlayer extends AsyncTask<Void, Void, Void> {
        String return_msg;

        @Override
        protected Void doInBackground(Void... voids) {
            URL url = null;
            try {
                url = new URL("http://192.168.2.14:8080/CPL/main/cpl/removePlayer&"+playerId);

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
                    System.out.println("while " + response);
                }
                in.close();
                //print result
                System.out.println(response.toString());

                JSONObject mainObject = new JSONObject(response.toString());
                return_msg=mainObject.getString("Message");


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
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
        }
    }

    public void delete(int position) { //removes the row
        listOfteam.remove(position);
        notifyItemRemoved(position);
    }
}
