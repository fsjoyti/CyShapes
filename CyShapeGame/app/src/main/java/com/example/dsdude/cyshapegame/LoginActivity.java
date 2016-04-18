package com.example.dsdude.cyshapegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends Activity {

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        Intent intent = getIntent();
        final String ip = intent.getStringExtra("ip");
        Log.d("IP", ip);
        SocketHandler.setSocket(ip);
        Socket socket = SocketHandler.getSocket();
        socket.on("onconnected", onconnected);
        socket.connect();
        Log.d("SocketConnectionStatus", socket.connected() ? "true" : "false");
        Log.d("SocketID", Integer.toString(id));

        EditText email = (EditText)findViewById(R.id.email);
        EditText password = (EditText)findViewById(R.id.password);
        Button login = (Button)findViewById(R.id.login);
        Button multiplayer = (Button)findViewById(R.id.multiplayer);

        multiplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent multiplayerIntent = new Intent(getApplicationContext(), MultiplayerInstanceActivity.class);
                multiplayerIntent.putExtra("ip", ip);
                startActivity(multiplayerIntent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private Emitter.Listener onconnected = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            // Update the other players
            JSONObject data = (JSONObject) args[0];
            try {
                id = (int) Math.floor(data.getDouble("id") * 100);
            } catch (JSONException e) {
                return;
            }
        }
    };
}

