package com.example.dsdude.cyshapegame;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class GameInstanceActivity extends Activity implements View.OnTouchListener {
    float dX;
    float dY;
    int lastAction;
    private GameView gameView;
    private ViewGroup mLinearLayout;
    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
        setContentView(gameView);
        FrameLayout rootLayout = (FrameLayout)findViewById(android.R.id.content);
        View.inflate(this, R.layout.draggableview, rootLayout);
        rootLayout.addView(new Player(this, 50, 50, 25));
        final View dragView = findViewById(R.id.draggable_view);
        dragView.setOnTouchListener(this);
        player = new Player(this, 50,50,25);
        player.setOnTouchListener(new View.OnTouchListener(){
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
                float x = event.getX();
                float y = event.getY();
                FrameLayout playerView = (FrameLayout) view;
                playerView.addView(new Player(getParent(),x,y,25));
                return true;
            }
        });
        rootLayout.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View view, MotionEvent event){
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
        });

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

