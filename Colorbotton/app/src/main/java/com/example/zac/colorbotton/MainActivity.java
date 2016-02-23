package com.example.zac.colorbotton;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    LinearLayout background;
    Button green, blue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        background=(LinearLayout) findViewById(R.id.blackground);
        green=(Button) findViewById(R.id.buttongreen);
        blue=(Button) findViewById(R.id.buttonblue);

        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                background.setBackgroundColor(Color.parseColor("#00ff00"));
            }
        });

        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                background.setBackgroundColor(Color.parseColor("#006699"));
            }
        });
    }


}
