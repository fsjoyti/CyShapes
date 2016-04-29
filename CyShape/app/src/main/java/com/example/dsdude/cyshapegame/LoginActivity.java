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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends Activity {


    private ArrayList<String> userList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        final EditText email = (EditText)findViewById(R.id.email);
        Button login = (Button)findViewById(R.id.login);
        Button joinGuest = (Button)findViewById(R.id.joinGuest);

        // Set up Socket information
        String ip = this.getIntent().getStringExtra("ip");
        Log.d("SocketIP", ip);
        SocketHandler.setSocket(ip);
        SocketHandler.getSocket().on("onconnected", onconnected);

        SocketHandler.getSocket().on("get_users", get_users);

        SocketHandler.getSocket().connect();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SocketHandler.setPlayerID(email.getText().toString());
                Intent roomIntent = new Intent(getApplicationContext(), RoomActivity.class);
                startActivity(roomIntent);

            }
        });

        joinGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SocketHandler.setPlayerID("Guest");
                Intent multiplayerIntent = new Intent(getApplicationContext(), MultiplayerInstanceActivity.class);
                startActivity(multiplayerIntent);
            }
        });


        // I might need to keep this in case my stuff doesn't work
//        loginScreen = new WebView(this);
//        loginScreen.getSettings().setJavaScriptEnabled(true);
//
//        final Activity activity = this;
//

//        loginScreen.setWebViewClient(new WebViewClient() {
//            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl){
//                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
//             }
//         });
//
//        loginScreen.loadUrl("http://10.36.23.91:3000/index");
//        setContentView(loginScreen);
    }


    private Emitter.Listener get_users = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            // Update the other players
            JSONObject data = (JSONObject) args[0];
            try {
                Log.d("UserData", data.toString());
            } catch (Exception e) {
                return;
            }
        }
    };


    private Emitter.Listener onconnected = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            // Update the other players
            JSONObject data = (JSONObject) args[0];
            try {

                SocketHandler.setId(data.getDouble("id"));

            } catch (JSONException e) {
                return;
            }
        }
    };
}

