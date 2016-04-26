package com.example.dsdude.cyshapegame;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
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
//    public static final int WIDTH = 2000;
//    public static final int HEIGHT = 1200;
    public static int WIDTH;
    public static int HEIGHT;
    private long eshapesStartTime;
    protected MainThread thread;
    public boolean Pause_game;
    private Background bg;
    private Player player;
    private ArrayList<Eshape> eshapes;
    private Random rand = new Random();
    public GameInstanceActivity game;
    private boolean newGameCreated;
    private long startReset;
    private boolean reset;
    private boolean dissapear;
    private boolean started;
    private int eshapesCN;

    private Point point=new Point(); //touch point
    private boolean canDrag=false; //determine whether touch on the Player image
    private int offsetX=0, offsetY=0; //distance between point and left top of Player image

    public GamePanel(Context context, GameInstanceActivity game,int ScreenWidth,int Screenheigt) {
        super(context);
        getHolder().addCallback(this);
        this.game = game;
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
        WIDTH = ScreenWidth;
        HEIGHT = Screenheigt;
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
        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.spacebg), WIDTH);
        player = new Player(this.getContext(),BitmapFactory.decodeResource(getResources(), R.drawable.playerstar), 80,80,1);
        eshapes = new ArrayList<Eshape>();
        eshapesStartTime = System.nanoTime();


        //we can safely start the game loop
        thread.setRunning(true);
        thread.start();

    }



    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(!player.getPlaying()&& newGameCreated && reset)
                {
                    player.setPlaying(true);
                }
                if(player.getPlaying())
                {
                    if(!started)started = true;
                    reset = false;
                    point.x=(int)event.getX();
                    point.y=(int)event.getY();
                    if(player.getx()<=point.x&&point.x<=player.getx()+player.getwidth()&&player.gety()<=point.y&&point.y<=player.gety()+player.getheight()){
                        canDrag=true;
                        offsetX=point.x-player.getx();
                        offsetY=point.y-player.gety();
                    }
                }
                break;

            case MotionEvent.ACTION_MOVE:
//            if(canDrag){
                player.setx((int)event.getX()-offsetX);
                player.sety((int) event.getY() - offsetY);

                if (player.getx() < 0) {
                    player.setx(0);
                }

                if (player.getx()+player.getwidth() >  WIDTH) {
                    player.setx(WIDTH-player.getwidth());
                }

                if (player.gety() < 0) {
                    player.sety(0);
                }

                if (player.gety()+player.getheight() > HEIGHT) {
                    player.sety(HEIGHT - player.getheight());
                }
//            }
            break;

            case MotionEvent.ACTION_UP:
                canDrag=false;
                break;

            default:
                break;
        }
        return true;
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
                        star),WIDTH +10, WIDTH/2, 60, 60, 1,10));
                break;
            case 5:case 6:
                eshapes.add(new Eshape(this.getContext(),BitmapFactory.decodeResource(getResources(),R.drawable.
                        redstar),(int) (rand.nextDouble() * (HEIGHT)), (int) (rand.nextDouble() * (HEIGHT)), 60, 60, 1,-10));
                break;
            case 7: case 4:
                eshapes.add(new Eshape(this.getContext(),BitmapFactory.decodeResource(getResources(),R.drawable.
                        superstar),(int) (rand.nextDouble() * (HEIGHT)), (int) (rand.nextDouble() * (HEIGHT)), 60, 60, 1,20));
                break;
            case 8:
                eshapes.add(new Eshape(this.getContext(),BitmapFactory.decodeResource(getResources(),R.drawable.
                        blackstar),(int) (rand.nextDouble() * (HEIGHT)), (int) (rand.nextDouble() * (HEIGHT)), 60, 60, 1,-100));
                break;
        }
    }


    public void draw(Canvas canvas)
    {
//        final float scaleFactorX = getWidth()/(WIDTH*1.f);
//        final float scaleFactorY = getHeight()/(HEIGHT*1.f);
        if (!Pause_game) {
            if (canvas != null) {
                final int savedState = canvas.save();
//                canvas.scale(scaleFactorX, scaleFactorY);
                bg.draw(canvas);

                if (!dissapear) {
                    player.draw(canvas);
                }

                //draw eshapes
                for (Eshape m : eshapes) {
                    m.draw(canvas);
                }
                canvas.restoreToCount(savedState);
                drawText(canvas);
                canvas.restoreToCount(savedState);
            }
        }
    }

    public void newGame()
    {
        dissapear = false;

        eshapes.clear();

        player.resetDY();
        player.resetScore();
        player.setx(100);
        player.sety(HEIGHT/2);

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
        canvas.drawText("SCORE: " + player.getScore(), 0, HEIGHT/2+450, paint);

        if(!player.getPlaying()&&newGameCreated&&reset)
        {
            Paint paint1 = new Paint();
            paint1.setColor(Color.WHITE);
            paint1.setTextSize(80);
            paint1.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText("PRESS TO START", WIDTH/2-200, HEIGHT/2, paint1);
        }
    }
}
