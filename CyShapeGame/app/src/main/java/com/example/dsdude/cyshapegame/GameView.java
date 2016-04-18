package com.example.dsdude.cyshapegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DsDude on 3/27/2016.
 */
public class GameView extends SurfaceView {
    private Bitmap bmp;
    private SurfaceHolder holder;
    private GameLoopThread gameLoopThread;
    private List<Enemy> enemies = new ArrayList<Enemy>();

    public GameView(Context context) {
        super(context);
        gameLoopThread = new GameLoopThread(this);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                gameLoopThread.setRunning(false);
                while (retry) {
                    try {
                        gameLoopThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                    }
                }
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                createEnemies();
                gameLoopThread.setRunning(true);
                gameLoopThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
            }
        });
    }

    private void createEnemies(){
        enemies.add(createEnemy(R.drawable.cyshapes_transparentbg));
        enemies.add(createEnemy(R.drawable.cyshapes_transparentbg));
        enemies.add(createEnemy(R.drawable.cyshapes_transparentbg));
        enemies.add(createEnemy(R.drawable.cyshapes_transparentbg));
        enemies.add(createEnemy(R.drawable.cyshapes_transparentbg));
        enemies.add(createEnemy(R.drawable.cyshapes_transparentbg));
        enemies.add(createEnemy(R.drawable.cyshapes_transparentbg));
        enemies.add(createEnemy(R.drawable.cyshapes_transparentbg));
        enemies.add(createEnemy(R.drawable.cyshapes_transparentbg));
    }

    private Enemy createEnemy(int resource){
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
        return new Enemy(this,bmp);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        for (Enemy enemy : enemies) {
            enemy.onDraw(canvas);
        }
    }
}