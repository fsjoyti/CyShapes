package com.example.dsdude.cyshapegame;

        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.view.Window;
        import android.view.WindowManager;
        import android.widget.Button;

public class HomeScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen_activity);

        //turn title off
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        //set to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button startGame = (Button)findViewById(R.id.startGame);
        Button host = (Button)findViewById(R.id.host);
        Button join = (Button)findViewById(R.id.join);
        Button loginButton = (Button)findViewById(R.id.loginButton);

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startgameIntent = new Intent(getApplicationContext(), GameInstanceActivity.class);
                startActivity(startgameIntent);
            }
        });

        host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent multiplayerIntent = new Intent(getApplicationContext(), MultiplayerInstanceActivity.class);
                startActivity(multiplayerIntent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginIntent);
            }
        });
    }

}

