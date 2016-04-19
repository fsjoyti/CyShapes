package com.example.dsdude.cyshapegame;

import android.graphics.Canvas;

/**
 * Created by DsDude on 3/27/2016.
 */

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread
{
    private int FPS = 30;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    public static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel)
    {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }
    @Override
    public void run()
    {
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount =0;
        long targetTime = 1000/FPS;

        while(running) {
            startTime = System.nanoTime();
            canvas = null;

            //try locking the canvas for pixel editing
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }
            } catch (Exception e) {
            }
            finally{
                if(canvas!=null)
                {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch(Exception e){e.printStackTrace();}
                }
            }




            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime-timeMillis;

            try{
                this.sleep(waitTime);
            }catch(Exception e){}

            totalTime += System.nanoTime()-startTime;
            frameCount++;
            if(frameCount == FPS)
            {
                averageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount =0;
                totalTime = 0;
                System.out.println(averageFPS);
            }
        }
    }
    public void setRunning(boolean b)
    {
        running=b;
    }
}
//public class MainThread extends Thread {
//    static final long FPS = 10;
//    private GameView view;
//    private boolean running = false;
//
//    public MainThread(GameView view) {
//        this.view = view;
//    }
//
//    public void setRunning(boolean run) {
//        running = run;
//    }
//
//    @Override
//    public void run() {
//        long ticksPS = 1000 / FPS;
//        long startTime;
//        long sleepTime;
//        while (running) {
//            Canvas c = null;
//            startTime = System.currentTimeMillis();
//
//            try {
//                c = view.getHolder().lockCanvas();
//                synchronized (view.getHolder()) {
//                    view.onDraw(c);
//                }
//            } finally {
//                if (c != null) {
//                    view.getHolder().unlockCanvasAndPost(c);
//                }
//            }
//            sleepTime = ticksPS-(System.currentTimeMillis() - startTime);
//            try {
//                if (sleepTime > 0)
//                    sleep(sleepTime);
//                else
//                    sleep(10);
//            } catch (Exception e) {}
//        }
//    }
//}