package com.example.dsdude.cyshapegame;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class GameInstanceActivity extends Activity  {
    RelativeLayout Rel_main_game;
    private GamePanel gamePanel;
    TextView txt;

    View pausaButton;
    View PauseMenu;
//
//    MediaPlayer MainMusic;

    OnClickListener Continue_list = new OnClickListener() {

        @Override
        public void onClick(View v) {
            PauseMenu.setVisibility(View.GONE);
            pausaButton.setVisibility(View.VISIBLE);
            gamePanel.Pause_game=false;
        }
    };

    OnClickListener To_Main_Menu_list = new OnClickListener() {

        @Override
        public void onClick(View v) {
            gamePanel.thread.setRunning(false);
            GameInstanceActivity.this.finish();

        }
    };


    OnClickListener Pausa_click =new OnClickListener() {

        @Override
        public void onClick(View v) {
            pausaButton.setVisibility(View.GONE);
            PauseMenu.setVisibility(View.VISIBLE);
            gamePanel.Pause_game=true;


        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.game);
        Rel_main_game = (RelativeLayout) findViewById(R.id.game);
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);

        final int heightS = dm.heightPixels;
        final int widthS = dm.widthPixels;
        gamePanel = new GamePanel(getApplicationContext(), this,widthS, heightS);
        Rel_main_game.addView(gamePanel);

        LayoutInflater myInflater = (LayoutInflater) getApplicationContext().getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);
        pausaButton = myInflater.inflate(R.layout.pause, null, false);
        pausaButton.setX(widthS-250);
        pausaButton.setY(0);
        Rel_main_game.addView(pausaButton);

        ImageView pauseImage = (ImageView) pausaButton.findViewById(R.id.PauseImage);
        pausaButton.setOnTouchListener(new TouchButton(pauseImage));
        pausaButton.setOnClickListener(Pausa_click);
        pausaButton.getLayoutParams().height=250;
        pausaButton.getLayoutParams().width=250;

        PauseMenu= myInflater.inflate(R.layout.pausemenu, null, false);
        Rel_main_game.addView(PauseMenu);
        PauseMenu.setVisibility(View.GONE);

        ImageView Cont = (ImageView)PauseMenu.findViewById(R.id.imCont);
        ImageView MainMenuTo = (ImageView)PauseMenu.findViewById(R.id.toMain);
        Cont.setOnTouchListener(new TouchButton(Cont));
        Cont.setOnClickListener(Continue_list);
        MainMenuTo.setOnTouchListener(new TouchButton(MainMenuTo));
        MainMenuTo.setOnClickListener(To_Main_Menu_list);

//        MainMusic = MediaPlayer.create(GameInstanceActivity.this, R.raw.music);
//        MainMusic.setVolume(0.3f, 0.3f);
//        MainMusic.start();
    }

    @Override
    public void onBackPressed() {
        pausaButton.setVisibility(View.GONE);
        PauseMenu.setVisibility(View.VISIBLE);
        gamePanel.Pause_game=true;
    }

//    @Override
//    protected void onStop() {
//        if (MainMusic.isPlaying())
//            MainMusic.stop();
//        super.onStop();
//    }

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

