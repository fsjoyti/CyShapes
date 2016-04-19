package com.example.dsdude.cyshapegame;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.graphics.Rect;
import android.view.View;

import java.util.Random;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
    public static final int WIDTH = 894;
    public static final int HEIGHT = 894;
    //    public static final int MOVESPEED = -5;
    private long eshapesStartTime;
    private MainThread thread;
    private Background bg;
    private Player player;
    private ArrayList<Eshape> eshapes;
    private Random rand = new Random();

    private boolean newGameCreated;
    private long startReset;
    private boolean reset;
    private boolean dissapear;
    private boolean started;
    private int eshapesCN;
    private GameInstanceActivity gio;

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

        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.spacebackground));
        player = new Player(this.getContext(),BitmapFactory.decodeResource(getResources(), R.drawable.bluesquare), 69, 69, 1);
        eshapes = new ArrayList<Eshape>();
        eshapesStartTime = System.nanoTime();



        //we can safely start the game loop
        thread.setRunning(true);
        thread.start();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            if(!player.getPlaying()&& newGameCreated && reset)
            {
                player.setPlaying(true);
            }
            if(player.getPlaying())
            {
                if(!started)started = true;
                reset = false;
            }
            return true;
        }

        return super.onTouchEvent(event);
    }

    public void update()

    {

        if(player.getPlaying()) {

            bg.update();
            player.update();

            //add eshapes on timer
            long missileElapsed = (System.nanoTime()-eshapesStartTime)/1000000;
            if(missileElapsed >(1000)){

                System.out.println("making missile");

                //first missile always goes down the middle
                if(eshapesCN==0)
                {
                    addEshapes(0);
                }
                else
                {
                    addEshapes(rand.nextInt(11));
                }
//
                //reset timer
                eshapesStartTime = System.nanoTime();
            }
            //loop through every missile and check collision and remove
            for(int i = 0; i< eshapes.size();i++)
            {
                //update missile
                eshapes.get(i).update();

                if(collision(eshapes.get(i),player))
                {
                    player.setCollision(true,eshapes.get(i).getScore());
                    eshapes.remove(i);
                    player.setCollision(false,0);
                    if(player.getScore()<0){player.setPlaying(false);eshapesCN=0;}
                    break;
                }
                //remove missile if it is way off the screen
                if(eshapes.get(i).getx()<-100)
                {
                    eshapes.remove(i);
                    break;
                }
            }
        }
        else{
            player.resetDY();
            if(!reset)
            {
                newGameCreated = false;
                startReset = System.nanoTime();
                reset = true;
                dissapear = true;
            }

            long resetElapsed = (System.nanoTime()-startReset)/1000000;

            if(resetElapsed > 2500 && !newGameCreated)
            {
                newGame();
            }
        }
    }

    public boolean collision(GameObject a, GameObject b)
    {
        if(Rect.intersects(a.getRectangle(), b.getRectangle()))
        {
            return true;
        }
        return false;
    }

    public void addEshapes(int n){
        eshapesCN++;
        switch (n){
            case 0:case 1:case 2:case 3:case 9:case 10:
                eshapes.add(new Eshape(this.getContext(),BitmapFactory.decodeResource(getResources(),R.drawable.
                        star),WIDTH +10, WIDTH/2, 40, 40, 1,10));
                break;
            case 5:case 6:
                eshapes.add(new Eshape(this.getContext(),BitmapFactory.decodeResource(getResources(),R.drawable.
                        redstar),(int) (rand.nextDouble() * (HEIGHT)), (int) (rand.nextDouble() * (HEIGHT)), 40, 40, 1,-10));
                break;
            case 7: case 4:
                eshapes.add(new Eshape(this.getContext(),BitmapFactory.decodeResource(getResources(),R.drawable.
                        superstar),(int) (rand.nextDouble() * (HEIGHT)), (int) (rand.nextDouble() * (HEIGHT)), 40, 40, 1,20));
                break;
            case 8:
                eshapes.add(new Eshape(this.getContext(),BitmapFactory.decodeResource(getResources(),R.drawable.
                        blackstar),(int) (rand.nextDouble() * (HEIGHT)), (int) (rand.nextDouble() * (HEIGHT)), 40, 40, 1,-100));
                break;
        }
    }


    public void draw(Canvas canvas)
    {
        final float scaleFactorX = getWidth()/(WIDTH*1.f);
        final float scaleFactorY = getHeight()/(HEIGHT*1.f);

        if(canvas!=null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            bg.draw(canvas);

            if(!dissapear) {
                player.draw(canvas);
            }

            //draw eshapes
            for(Eshape m: eshapes)
            {
                m.draw(canvas);
            }
            canvas.restoreToCount(savedState);
            drawText(canvas);
            canvas.restoreToCount(savedState);
        }
    }

    public void newGame()
    {
        dissapear = false;

        eshapes.clear();

        player.resetDY();
        player.resetScore();
        player.setY(HEIGHT/2);

//        if(player.getScore()>best)
//        {
//            best = player.getScore();
//
//        }

        newGameCreated = true;
    }

    public void drawText(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(40);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("SCORE: " + player.getScore(), 10, HEIGHT+120, paint);

        if(!player.getPlaying()&&newGameCreated&&reset)
        {
            Paint paint1 = new Paint();
            paint1.setColor(Color.WHITE);
            paint1.setTextSize(80);
            paint1.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText("PRESS TO START", WIDTH/2+300, HEIGHT/2+100, paint1);
        }
    }
}
//public class GameView extends SurfaceView {
//    private Bitmap bmp;
//    private SurfaceHolder holder;
//    private MainThread gameLoopThread;
//    private List<Enemy> enemies = new ArrayList<Enemy>();
//
//    public GameView(Context context) {
//        super(context);
//        gameLoopThread = new MainThread(this);
//        holder = getHolder();
//        holder.addCallback(new SurfaceHolder.Callback() {
//
//            @Override
//            public void surfaceDestroyed(SurfaceHolder holder) {
//                boolean retry = true;
//                gameLoopThread.setRunning(false);
//                while (retry) {
//                    try {
//                        gameLoopThread.join();
//                        retry = false;
//                    } catch (InterruptedException e) {
//                    }
//                }
//            }
//
//            @Override
//            public void surfaceCreated(SurfaceHolder holder) {
//                createEnemies();
//                gameLoopThread.setRunning(true);
//                gameLoopThread.start();
//            }
//
//            @Override
//            public void surfaceChanged(SurfaceHolder holder, int format,
//                                       int width, int height) {
//            }
//        });
//    }
//
//    private void createEnemies(){
//        enemies.add(createEnemy(R.drawable.cyshapes_transparentbg));
//        enemies.add(createEnemy(R.drawable.cyshapes_transparentbg));
//        enemies.add(createEnemy(R.drawable.cyshapes_transparentbg));
//        enemies.add(createEnemy(R.drawable.cyshapes_transparentbg));
//        enemies.add(createEnemy(R.drawable.cyshapes_transparentbg));
//        enemies.add(createEnemy(R.drawable.cyshapes_transparentbg));
//        enemies.add(createEnemy(R.drawable.cyshapes_transparentbg));
//        enemies.add(createEnemy(R.drawable.cyshapes_transparentbg));
//        enemies.add(createEnemy(R.drawable.cyshapes_transparentbg));
//    }
//
//    private Enemy createEnemy(int resource){
//        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
//        return new Enemy(this,bmp);
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        canvas.drawColor(Color.BLACK);
//        for (Enemy enemy : enemies) {
//            enemy.onDraw(canvas);
//        }
//    }
//}