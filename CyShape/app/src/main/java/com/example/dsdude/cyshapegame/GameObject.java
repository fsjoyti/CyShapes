package com.example.dsdude.cyshapegame;

import android.content.Context;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

public abstract class GameObject extends View {
    protected Context context;
    protected float x;
    protected float y;
    protected int dy;
    protected int dx;
    protected int width;
    protected int height;

    public GameObject(Context context) {
        super (context);
    }
    public void setx(float x)
    {
        this.x = x;
    }
    public void sety(float y)
    {
        this.y = y;
    }
    public float getx()
    {
        return x;
    }
    public float gety()
    {
        return y;
    }
    public int getheight()
    {
        return height;
    }
    public int getwidth()
    {
        return width;
    }
    public Rect getRectangle()
    {
        return new Rect((int)x, (int)y,(int) x+width,(int) y+height);
    }

    public boolean onTouch(View view, MotionEvent event){
        return false;
    };


}