package com.example.dsdude.cyshapegame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by DsDude on 3/27/2016.
 */
public class GameLoopThread extends Thread {
    static final long FPS = 10;
    private GameView view;
    private boolean running = false;

    public GameLoopThread(GameView view) {
        this.view = view;
    }

    public void setRunning(boolean run) {
        running = run;
    }

    @Override
    public void run() {
        long ticksPS = 1000 / FPS;
        long endTime = System.currentTimeMillis() + 60000;
        long startTime;
        long sleepTime;
        while (running) {
            Canvas c = null;
            Paint p = new Paint();
            startTime = System.currentTimeMillis();
            long checkTime = endTime - System.currentTimeMillis();
            if (checkTime <= 0) {
                break;   // Should probably show some "Game Over" screen
            }
            try {
                c = view.getHolder().lockCanvas();
                p.setColor(Color.WHITE);
                p.setTextSize(100);
                int simpleEndTime = (int) checkTime / 1000;
                synchronized (view.getHolder()) {
                    view.onDraw(c);
                    c.drawText(Integer.toString(simpleEndTime), view.getWidth() - 125, 100, p);
                }
            } finally {
                if (c != null) {
                    view.getHolder().unlockCanvasAndPost(c);
                }
            }
            sleepTime = ticksPS-(System.currentTimeMillis() - startTime);
            try {
                if (sleepTime > 0)
                    sleep(sleepTime);
                else
                    sleep(10);
            } catch (Exception e) {}
        }
    }
}