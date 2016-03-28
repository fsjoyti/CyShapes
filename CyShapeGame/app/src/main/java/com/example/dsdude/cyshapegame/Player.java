package com.example.dsdude.cyshapegame;


import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Player extends GameObject {

    Bitmap playerBitmap;    //player image
    Bitmap createPlayer;    //create the player
    boolean playerFingerMove;

    public Player(Bitmap bitmap){

        playerBitmap = bitmap;
        //playerResized = Bitmap.createScaledBitmap(playerBitmap, 40, 40, true);
        //createPlayer = Bitmap.createBitmap(playerBitmap, 50, 50, width, height);
        createPlayer = Bitmap.createBitmap(playerBitmap, 300, 300, 60, 40);


    }

    public void Update(){
        //TODO
    }

    public void draw(Canvas canvas){

        canvas.drawBitmap(createPlayer, 400, 400, null);
    }
}
