package com.example.dsdude.cyshapegame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {
    GameThread thread;
    int screenW;
    int screenH;
    int shapeX; //shape x position
    int shapeY; //shape y position
    int shapeW; //shape width
    int shapeH; //shape height
    int backgroundW;
    int backgroundH;
    Bitmap shape, background;
    boolean shapeFingerMove;

    public MainGamePanel(Context context){
        super(context);
        shape = BitmapFactory.decodeResource(getResources(),R.mipmap.square); //load shape image
        background = BitmapFactory.decodeResource(getResources(),R.mipmap.skybackground); //load background
        shapeW = shape.getWidth();
        shapeH = shape.getHeight();

        getHolder().addCallback(this);
        setFocusable(true);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){

    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w, h, oldw, oldh);
        //This even-method provides
        screenW = w;
        screenH = h;

        background = Bitmap.createScaledBitmap(background, w, h, true); //Scale background to fit the screen
        backgroundW = background.getWidth();
        backgroundH = background.getHeight();
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        //Initializing background
        Rect rectSRC = new Rect(0, 0, backgroundW, backgroundH);
        Rect rectDST = new Rect (0, 0, screenW, screenH);

        canvas.drawBitmap(background, rectSRC, rectDST, null);

    }

    class GameThread extends Thread {
        private SurfaceHolder surfaceHolder;
        private MainGamePanel gameView;
        private boolean run = false;

        //Variables for frames per second
        long timeNow;
        long timePrev = 0;
        long timePrevFrame = 0;
        long timeDelta;

        public GameThread(SurfaceHolder surfaceHolder, MainGamePanel gameView) {
            this.surfaceHolder = surfaceHolder;
            this.gameView = gameView;
        }

        public void setRunning(boolean run) {
            this.run = run;
        }

        public SurfaceHolder getSurfaceHolder() {
            return surfaceHolder;
        }

        @SuppressLint("WrongCall")
        @Override
        public void run() {
            Canvas c;
            while (run) {
                c = null;

                //limit the frame rate to a cap of 60fps
                timeNow = System.currentTimeMillis();
                timeDelta = timeNow - timePrevFrame;
                if (timeDelta < 16) {
                    try {
                        Thread.sleep(16 - timeDelta);
                    } catch (InterruptedException e) {
                    }
                }
                timePrevFrame = System.currentTimeMillis();

                try{
                    c = surfaceHolder.lockCanvas(null);
                    synchronized (surfaceHolder){
                        //call the methods to draw and process next frame
                        gameView.onDraw(c);
                    }
                } finally{
                    if (c != null){
                        surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }

        }
    }
}
