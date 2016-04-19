package com.example.dsdude.cyshapegame;

import android.app.Activity;
import android.os.Bundle;

public class ActivityTwo extends Activity {
    MainGamePanel game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = new MainGamePanel(this);
        setContentView(game);
    }

}

