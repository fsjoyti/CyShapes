/**package com.example.dsdude.cyshapegame;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    public static final int WIDTH = 894;
    public static final int HEIGHT = 894;
    public static final int MOVESPEED = -5;
    private long missileStartTime;
    private MainThread thread;
    private Background bg;
    private Player thePlayer;
    private ArrayList<Missile> missiles;
    private Random rand = new Random();


    public GamePanel(Context context)
    {
        super(context);


        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        //make gamePanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        int counter = 0;
        while(retry && counter<1000)
        {
            counter++;
            try{thread.setRunning(false);
                thread.join();
                retry = false;

            }catch(InterruptedException e){e.printStackTrace();}

        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

        thePlayer = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.square), 0);
        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.spacebackground));
        missiles = new ArrayList<Missile>();
        missileStartTime = System.nanoTime();



        //we can safely start the game loop
        thread.setRunning(true);
        thread.start();

    }

    public void update()

    {
        if(true) {

            bg.update();

            //add missiles on timer
            long missileElapsed = (System.nanoTime()-missileStartTime)/1000000;
            if(missileElapsed >(2000)){

                System.out.println("making missile");
                //first missile always goes down the middle
                if(missiles.size()==0)
                {
                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(),R.drawable.
                            star),WIDTH +10, HEIGHT/2, 40, 40, 1));
                }
                else
                {

                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.star),
                            WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 40, 40, 1));
                }

                //reset timer
                missileStartTime = System.nanoTime();
            }
            //loop through every missile and check collision and remove
            for(int i = 0; i<missiles.size();i++)
            {
                //update missile
                missiles.get(i).update();
                //remove missile if it is way off the screen
                if(missiles.get(i).getX()<-100)
                {
                    missiles.remove(i);
                    break;
                }
            }
        }
    }

    //***************************************
    //*************  TOUCH  *****************
    //***************************************
    @Override
    public synchronized boolean onTouchEvent (MotionEvent ev){

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                thePlayer.x = (int) ev.getX() - thePlayer.y/2;
                thePlayer.y = (int) ev.getY() - thePlayer.height/2;

                thePlayer.playerFingerMove = true;
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                thePlayer.x = (int) ev.getX() - thePlayer.y/2;
                thePlayer.y = (int) ev.getY() - thePlayer.height/2;

                break;
            }

            case MotionEvent.ACTION_UP:
                thePlayer.playerFingerMove = false;
                break;
        }
        return true;
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);
        final float scaleFactorX = getWidth()/(WIDTH*1.f);
        final float scaleFactorY = getHeight() / (HEIGHT * 1.f);

        if(canvas!=null) {
            final int savedState = canvas.save();


            canvas.scale(scaleFactorX, scaleFactorY);
            //canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.square), 30, 30, null);
            bg.draw(canvas);
            thePlayer.draw(canvas);

            //draw missiles
            for(Missile m: missiles)
            {
                m.draw(canvas);
            }
            canvas.restoreToCount(savedState);
        }
    }

}
*/