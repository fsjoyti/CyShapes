package com.example.dsdude.cyshapegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew Snyder on 4/4/2016.
 */
public class MultiplayerView extends SurfaceView {
    private Bitmap bmp;
    private SurfaceHolder holder;
    private MultiplayerLoopThread multiplayerLoopThread;
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    public ArrayList<Player> players = new ArrayList<Player>();

    public MultiplayerView(Context context) {
        super(context);
        multiplayerLoopThread = new MultiplayerLoopThread(this);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                multiplayerLoopThread.setRunning(false);
                while (retry) {
                    try {
                        multiplayerLoopThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                    }
                }
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                createEnemies();
                multiplayerLoopThread.setRunning(true);
                multiplayerLoopThread.start();
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
        for (Player player : players) {
            player.draw(canvas);
        }
    }
}