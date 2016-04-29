package com.example.dsdude.cyshapegame;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class Player extends GameObject {
    private Bitmap spritesheet;
    protected int score;
	int lastAction;
    private int lastEshapeScore=0;
    private int sameShapeRecord=1;
    private int rednumber=0;
    private boolean collision;
    private boolean playing;
    private Animation animation = new Animation();
    private long startTime;
    private float dX;
    private float dY;

    public Player(Context context,Bitmap res, int w, int h, int numFrames) {
        super (context);
        x = 100;
        y = GamePanel.HEIGHT / 2;
        dy=0;
        score = 0;
        height = h;
        width = w;

        Bitmap[] image = new Bitmap[numFrames];
        spritesheet = res;

        for (int i = 0; i < image.length; i++)
        {
            image[i] = Bitmap.createBitmap(spritesheet, i*width, 0, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(1000);
        startTime = System.currentTimeMillis();

    }

    //    public void setUp(boolean b){up = b;}
    public void setCollision(boolean b,int eshapeScore){
        collision = b;
        switch (eshapeScore){
            case+10:case+20:
                if(lastEshapeScore!=eshapeScore){
                    lastEshapeScore=eshapeScore;
                    sameShapeRecord=1;
                    score+=eshapeScore;
                }
                else {
                    lastEshapeScore=eshapeScore;
                    sameShapeRecord++;
                    score+=(1+(sameShapeRecord-1)*0.1)*eshapeScore;
                }
                break;
            case-10:
                rednumber++;
                if(lastEshapeScore!=eshapeScore){
                    lastEshapeScore=eshapeScore;
                    sameShapeRecord=1;
                    score+=eshapeScore*rednumber;
                }
                else{
                    lastEshapeScore=eshapeScore;
                    sameShapeRecord++;
                    score+=(1+(sameShapeRecord-1)*0.1)*eshapeScore*rednumber;
                }
                break;
            case-100:
                score=-100;
                break;
        }
    }

    public void update()
    {
        long elapsed = (System.currentTimeMillis()-startTime);
        if(elapsed>5000)
        {
            score++;
            startTime = System.currentTimeMillis();
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
    public int getScore(){return score;}
    public boolean getPlaying(){return playing;}
    public void setPlaying(boolean b){playing = b;}
    public void resetDY(){dy = 0;}
    public void resetScore(){score = 0;}
}

