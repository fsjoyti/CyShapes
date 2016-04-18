package com.example.dsdude.cyshapegame;


import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Player extends GameObject {

    Bitmap playerBitmap;    //player image
    Bitmap createPlayer;    //create the player
    boolean playerFingerMove;
    int id;
    int score;

    public Player(Bitmap bitmap, int id){

//        playerBitmap = bitmap;
//        //playerResized = Bitmap.createScaledBitmap(playerBitmap, 40, 40, true);
//        //createPlayer = Bitmap.createBitmap(playerBitmap, 50, 50, width, height);
//        createPlayer = Bitmap.createBitmap(playerBitmap, 300, 300, 60, 40);
        this.id = id;

    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void updateScore(int score) {
        this.score = score;
    }

    public void Update(){
        //TODO
    }

    public void draw(Canvas canvas){

//        canvas.drawBitmap(createPlayer, 400, 400, null);
    }
}
