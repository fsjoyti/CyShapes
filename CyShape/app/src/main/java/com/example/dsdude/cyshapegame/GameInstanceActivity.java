package com.example.dsdude.cyshapegame;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
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
import java.util.Random;

public class GameInstanceActivity extends Activity implements View.OnTouchListener {
    float dX= 100;
    float dY= GamePanel.HEIGHT / 2;
    int lastAction;

    private GamePanel gameView;

    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameView = new GamePanel(this);
        setContentView(gameView);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FrameLayout rootLayout = (FrameLayout)findViewById(android.R.id.content);
        View.inflate(this, R.layout.draggableview, rootLayout);
        rootLayout.addView(new Player(this, BitmapFactory.decodeResource(getResources(), R.drawable.cyshapes_transparentbg), 32, 32, 3));

        final View dragView = findViewById(R.id.draggable_view);
        dragView.setOnTouchListener(this);


        player = new Player(this,BitmapFactory.decodeResource(getResources(), R.drawable.cyshapes_transparentbg), 32, 32, 3);
        View.OnTouchListener touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        player.setx(view.getX() - event.getRawX());
                        player.sety(view.getY() - event.getRawY());
                        player.lastAction = MotionEvent.ACTION_DOWN;
                        break;

                    case MotionEvent.ACTION_MOVE:
                        view.setY(event.getRawY() + player.gety());
                        view.setX(event.getRawX() + player.getx());
                        player.lastAction = MotionEvent.ACTION_MOVE;
                        break;

                    case MotionEvent.ACTION_UP:
                        if (player.lastAction == MotionEvent.ACTION_DOWN)
                            //Toast.makeText(draggableview.this, "Clicked!", Toast.LENGTH_SHORT).show();
                            break;

                    default:
                        return false;
                }
                return true;
            }
        };
        player.setOnTouchListener(touchListener);
//        player.setOnTouchListener(this);
//
//        player.setOnTouchListener(new View.OnTouchListener(){
//            public boolean onTouch(View view, MotionEvent event) {
//                switch (event.getActionMasked()) {
//                    case MotionEvent.ACTION_DOWN:
//                        dX = view.getX() - event.getRawX();
//                        dY = view.getY() - event.getRawY();
//                        lastAction = MotionEvent.ACTION_DOWN;
//                        break;
//
//                    case MotionEvent.ACTION_MOVE:
//                        view.setY(event.getRawY() + dY);
//                        view.setX(event.getRawX() + dX);
//                        lastAction = MotionEvent.ACTION_MOVE;
//                        break;
//
//                    case MotionEvent.ACTION_UP:
//                        if (lastAction == MotionEvent.ACTION_DOWN)
//                            //Toast.makeText(draggableview.this, "Clicked!", Toast.LENGTH_SHORT).show();
//                            break;
//
//                    default:
//                        return false;
//                }
//                float x = event.getX();
//                float y = event.getY();
//                return true;
//            }
//        });
    }


    @Override
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_game, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

