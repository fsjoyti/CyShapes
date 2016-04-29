package com.example.dsdude.cyshapegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
}
