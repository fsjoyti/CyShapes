package com.example.dsdude.cyshapegame;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class Player extends GameObject {
    private Bitmap spritesheet;
    private int score;
	int lastAction;
    private int lastEshapeScore=0;
    private int sameShapeRecord=1;
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
            case+10:case+20:case-10:
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
            case-100:
                score=-100;
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

//    @Override
//    public boolean onTouchEvent (MotionEvent event) {
//        int x = (int) event.getX();
//        int y = (int) event.getY();
//        switch(event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                if(!rect.contains(x, y)) {
//                    return false;//没有在矩形上点击，不处理触摸消息
//                }
//                deltaX = x - rect.left;
//                deltaY = y - rect.top;
//                break;
//            case MotionEvent.ACTION_MOVE:
//            case MotionEvent.ACTION_UP:
//                Rect old = new Rect(rect);
//                //更新矩形的位置
//                rect.left = x - deltaX;
//                rect.top = y - deltaY;
//                rect.right = rect.left + WIDTH;
//                rect.bottom = rect.top + WIDTH;
//                old.union(rect);//要刷新的区域，求新矩形区域与旧矩形区域的并集
//                invalidate(old);//出于效率考虑，设定脏区域，只进行局部刷新，不是刷新整个view
//                break;
//        }
//        return true;//处理了触摸消息，消息不再传递
//    }

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

