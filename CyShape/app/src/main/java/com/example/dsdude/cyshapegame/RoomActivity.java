package com.example.dsdude.cyshapegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Andrew Snyder on 4/29/2016.
 */
public class RoomActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_activity);

        Button join = (Button)findViewById(R.id.join);
        Button host = (Button)findViewById(R.id.host);
        Button visit = (Button)findViewById(R.id.visitSite);
        final EditText game = (EditText)findViewById(R.id.gameID);

        SocketHandler.getSocket().on("newGameCreated", newGameCreated);


        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent joinIntent = new Intent(getApplicationContext(), HostActivity.class);
                joinIntent.putExtra("gameID", game.getText().toString());
                startActivity(joinIntent);
            }
        });

        host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject data = new JSONObject();
                try {
                    data.put("socketID", SocketHandler.getId());
                } catch (JSONException e) {

                }
                SocketHandler.getSocket().emit("hostCreateNewGame", data);
                Intent hostIntent = new Intent(getApplicationContext(), HostActivity.class);
                hostIntent.putExtra("gameID", Integer.toString(SocketHandler.getGameRoom()));
                startActivity(hostIntent);
            }
        });

        visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent visitIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(visitIntent);
            }
        });
    }

    private Emitter.Listener newGameCreated = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            // Update the other players
            JSONObject data = (JSONObject) args[0];
            try {
                SocketHandler.setGameRoom(data.getInt("gameId"));
                Log.d("GameIDFromServer", Integer.toString(data.getInt("gameId")));
            } catch (JSONException e) {
                return;
            }
        }
    };
}
