package com.example.dsdude.cyshapegame;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.socketio.client.IO;

import java.net.URISyntaxException;

/**
 * Created by Andrew Snyder on 4/4/2016.
 */
public class MultiplayerInstanceActivity extends Activity{

    private Socket socket;
    {
        try {
            socket = IO.socket("http://10.36.23.216:3000");
        } catch (URISyntaxException e) {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MultiplayerView(this));
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_game, menu);
//        return true;
//    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
