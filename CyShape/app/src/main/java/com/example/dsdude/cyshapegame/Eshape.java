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
    private Animation animation = new Animation();
    private Bitmap bmp;
    public Eshape(Context context,Bitmap res, int x, int y, int w, int h, int numFrames, int s)
    {
        super (context);
        super.x = x;
        super.y = y;
        width = w;
        height = h;
        score=s;

        //random direction and speed
        if(rand.nextInt(2)==1){xspeed=rand.nextInt(20);}
        else {xspeed=-1*rand.nextInt(20);}
        if(rand.nextInt(2)==1){yspeed=rand.nextInt(20);}
        else {yspeed=-1*rand.nextInt(20);}

        //animation
        Bitmap[] image = new Bitmap[numFrames];

        bmp = res;

        for(int i = 0; i<image.length;i++)
        {
            image[i] = Bitmap.createBitmap(bmp, 0, i*height, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(100 - xspeed);

    }
    public void update()
    {
        //bounce off
        x-=xspeed;
        y-=yspeed;
        if(score<0){
            if (x < 0 || x + width > 2000) {
                xspeed = -1 * xspeed;
//                bmp=BitmapFactory.decodeResource(getResources(), R.drawable.blackstar);
//                this.draw(canvas);
//                score=-100;
            }
            if (y < 0 || y + height > 1200) {
                yspeed = -1 * yspeed;
//                bmp=BitmapFactory.decodeResource(getResources(), R.drawable.blackstar);
//                score=-100;
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

//    // direction = 0 up, 1 left, 2 down, 3 right,
//    // animation = 3 back, 1 left, 0 front, 2 right
//    int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };
//    private static final int BMP_ROWS = 4;
//    private static final int BMP_COLUMNS = 3;
//    private int x = 0;
//    private int y = 0;
//    private int xSpeed = 5;
//    private GameView gameView;
//    private Bitmap bmp;
//    private int currentFrame = 0;
//    private int width;
//    private int height;
//    private int ySpeed;
//
//    public Enemy(GameView gameView, Bitmap bmp) {
//        this.width = bmp.getWidth() / BMP_COLUMNS;
//        this.height = bmp.getHeight() / BMP_ROWS;
//        this.gameView = gameView;
//        this.bmp = bmp;
//
//        Random rnd = new Random(System.currentTimeMillis());
//        xSpeed = rnd.nextInt(50) - 5;
//        ySpeed = rnd.nextInt(50) - 5;
//    }
//
//    private void update() {
//        if (x >= gameView.getWidth() - width - xSpeed || x + xSpeed <= 0) {
//            xSpeed = -xSpeed;
//        }
//        x = x + xSpeed;
//        if (y >= gameView.getHeight() - height - ySpeed || y + ySpeed <= 0) {
//            ySpeed = -ySpeed;
//        }
//        y = y + ySpeed;
//        currentFrame = ++currentFrame % BMP_COLUMNS;
//    }
//
//    public void onDraw(Canvas canvas) {
//        update();
//        int srcX = currentFrame * width;
//        int srcY = getAnimationRow() * height;
//        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
//        Rect dst = new Rect(x, y, x + width, y + height);
//        canvas.drawBitmap(bmp, src, dst, null);
//    }
//
//    // direction = 0 up, 1 left, 2 down, 3 right,
//    // animation = 3 back, 1 left, 0 front, 2 right
//    private int getAnimationRow() {
//        double dirDouble = (Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2);
//        int direction = (int) Math.round(dirDouble) % BMP_ROWS;
//        return DIRECTION_TO_ANIMATION_MAP[direction];
//    }
//}
