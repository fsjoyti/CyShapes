package com.example.dsdude.cyshapegame;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class Player extends View {

    Bitmap playerBitmap;    //player image
    Bitmap createPlayer;    //create the player
    boolean playerFingerMove;
    float dX;
    float dY;
    int lastAction;

//    public Player(Bitmap bitmap){
//
//        playerBitmap = bitmap;
//        //playerResized = Bitmap.createScaledBitmap(playerBitmap, 40, 40, true);
//        //createPlayer = Bitmap.createBitmap(playerBitmap, 50, 50, width, height);
//        createPlayer = Bitmap.createBitmap(playerBitmap, 300, 300, 60, 40);
//
//
//    }

    private final float x;
    private final float y;
    private final int r;
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mWhite = new Paint(Paint.ANTI_ALIAS_FLAG);
    private GameView gameView;

    public Player(GameView gameView, float x, float y, int r) {
        this.gameView = gameView;
        mPaint.setColor(0xFF000000); //Black
        mWhite.setColor(0xFFFFFFFF); //White
        this.x = x;
        this.y = y;
        this.r = r;
    }

    //@Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                dX = view.getX() - event.getRawX();
                dY = view.getY() - event.getRawY();
                lastAction = MotionEvent.ACTION_DOWN;
                break;

            case MotionEvent.ACTION_MOVE:
                view.setY(event.getRawY() + dY);
                view.setX(event.getRawX() + dX);
                lastAction = MotionEvent.ACTION_MOVE;
                break;

            case MotionEvent.ACTION_UP:
                if (lastAction == MotionEvent.ACTION_DOWN)
                    //Toast.makeText(draggableview.this, "Clicked!", Toast.LENGTH_SHORT).show();
                    break;

            default:
                return false;
        }
        return true;
    }

    public void Update(){
        //TODO
    }

//    @Override
//    public boolean dispatchTouchEvent(View view, MotionEvent event) {
//        switch (event.getActionMasked()) {
//            case MotionEvent.ACTION_DOWN:
//                dX = view.getX() - event.getRawX();
//                dY = view.getY() - event.getRawY();
//                lastAction = MotionEvent.ACTION_DOWN;
//                break;
//
//            case MotionEvent.ACTION_MOVE:
//                view.setY(event.getRawY() + dY);
//                view.setX(event.getRawX() + dX);
//                lastAction = MotionEvent.ACTION_MOVE;
//                break;
//
//            case MotionEvent.ACTION_UP:
//                if (lastAction == MotionEvent.ACTION_DOWN)
//                    //Toast.makeText(draggableview.this, "Clicked!", Toast.LENGTH_SHORT).show();
//                    break;
//
//            default:
//                return false;
//        }
//        return true;
//
//        return super.dispatchTouchEvent(event);
//    }

//    public void draw(Canvas canvas){
//
//        canvas.drawBitmap(createPlayer, 400, 400, null);
//    }
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(x, y, r + 2, mWhite); //White stroke.
        canvas.drawCircle(x, y, r, mPaint); //Black circle.
    }

}
