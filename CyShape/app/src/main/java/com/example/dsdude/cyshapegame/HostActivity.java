package com.example.dsdude.cyshapegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Andrew Snyder on 4/29/2016.
 */
public class HostActivity extends Activity {
    private ArrayList<String> playerIDs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.host_activity);
        SocketHandler.getSocket().on("joinPlayer", joinPlayer);

        JSONObject data = new JSONObject();
        try {
            Log.d("GameID", this.getIntent().getStringExtra("gameID"));
            data.put("gameID", this.getIntent().getStringExtra("gameID"));
        } catch (JSONException e) {

        }
        SocketHandler.getSocket().emit("sendMessage", data);

        TextView roomID = (TextView)findViewById(R.id.roomID);
        Button startGame = (Button)findViewById(R.id.button);
        ListView players = (ListView)findViewById(R.id.players);

        playerIDs.add(SocketHandler.getPlayerID());

        roomID.setText(this.getIntent().getStringExtra("gameID"));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, playerIDs);
        players.setAdapter(adapter);

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent multiplayerIntent = new Intent(getApplicationContext(), MultiplayerInstanceActivity.class);
                startActivity(multiplayerIntent);
            }
        });
    }

    private Emitter.Listener joinPlayer = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            // Update the other players
            JSONObject data = (JSONObject) args[0];
            try {
                Log.d("JoinPlayer", data.get("sockets").toString());
            } catch (JSONException e) {
                return;
            }
        }
    };
}
