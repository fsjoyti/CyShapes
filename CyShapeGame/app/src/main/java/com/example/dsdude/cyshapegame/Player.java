package com.example.dsdude.cyshapegame;


import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Player extends GameObject {

    int playerHeight;   //player's height
    int playerWidth;    //player's width
    int playerX;    //player's x position
    int playerY;    //player's y position
    Bitmap playerBitmap;    //player image
    Bitmap playerResized;   //resized player image
    Bitmap createPlayer;    //create the player
    boolean playerFingerMove;

    public Player(Bitmap bitmap){
        super.x = x;
        super.y = y;
        //width = w;
        //height = h;

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
