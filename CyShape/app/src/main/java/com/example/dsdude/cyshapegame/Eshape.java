package com.example.dsdude.cyshapegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

public class Eshape extends GameObject{
    private int score;
    private int xspeed;
    private int yspeed;
    private Random rand = new Random();
    protected Animation animation = new Animation();
    private Bitmap bmp;
    Bitmap[] image;
    int WIDTH;
    int HEIGHT;
    public Eshape(Context context,Bitmap res, int x, int y, int w, int h, int numFrames, int s,int WIDTH,int HEIGHT)
    {
        super (context);
        super.x = x;
        super.y = y;
        width = w;
        height = h;
        score=s;
        this.WIDTH=WIDTH;
        this.HEIGHT=HEIGHT;

        //random direction and speed
        if(rand.nextInt(2)==1){xspeed=rand.nextInt(20);}
        else {xspeed=-1*rand.nextInt(20);}
        if(rand.nextInt(2)==1){yspeed=rand.nextInt(20);}
        else {yspeed=-1*rand.nextInt(20);}

        //animation
        image = new Bitmap[numFrames];

        bmp = res;

        for(int i = 0; i<image.length;i++)
        {
            image[i] = Bitmap.createBitmap(bmp,  i*width, 0, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(1000 - xspeed);

    }
    public void update()
    {
        //bounce off
        x-=xspeed;
        y-=yspeed;
        if(score<0){
            if (x < 0 || x + width > WIDTH) {
                xspeed = -1 * xspeed;
            }
            if (y < 0 || y + height > HEIGHT) {
                yspeed = -1 * yspeed;
            }
        }
        animation.update();
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        try{
            canvas.drawBitmap(animation.getImage(),x,y,null);
        }catch(Exception e){}
    }

    public int getScore() {return score;}

}