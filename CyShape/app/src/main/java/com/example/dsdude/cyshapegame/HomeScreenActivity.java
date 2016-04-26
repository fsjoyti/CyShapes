package com.example.dsdude.cyshapegame;

        import android.app.Activity;
        import android.content.Intent;
        import android.media.MediaPlayer;
        import android.os.Bundle;
        import android.view.View;
        import android.view.Window;
        import android.view.WindowManager;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.RelativeLayout;
        import android.widget.Toast;

public class HomeScreenActivity extends Activity {

    MediaPlayer MainMenuMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen_activity);

        //turn title off
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        //set to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final EditText ipAddress = (EditText)findViewById(R.id.ipAddress);
        final Button loginButton = (Button)findViewById(R.id.loginButton);
        Button startGame = (Button)findViewById(R.id.startGame);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ipAddress.getText().toString().equals("Enter IP Address"))
                    Toast.makeText(v.getContext(), "Enter a Valid IP", Toast.LENGTH_LONG).show();
                else {
                    Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                    loginIntent.putExtra("ip", ipAddress.getText().toString());
                    startActivity(loginIntent);
                }
            }
        });

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startgameIntent = new Intent(getApplicationContext(), GameInstanceActivity.class);
                startActivity(startgameIntent);
            }
        });
    }

    @Override
    protected void onStart() {
        MainMenuMusic = MediaPlayer.create(HomeScreenActivity.this, R.raw.music);
        MainMenuMusic.setVolume(0.3f, 0.3f);
        MainMenuMusic.start();
        super.onStart();
    }

    @Override
    protected void onStop() {
        if (MainMenuMusic.isPlaying())
            MainMenuMusic.stop();
        super.onStop();
    }

}

