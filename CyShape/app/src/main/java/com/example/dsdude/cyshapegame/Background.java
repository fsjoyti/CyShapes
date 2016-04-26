package com.example.dsdude.cyshapegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Background {

    Bitmap image;
    int x, y;
    int ScreenWidth;

    public Background(Bitmap res, int Screen_w)
    {
        image = res;
        this.x=0;
        this.y=0;
        this.ScreenWidth=Screen_w;
    }
    public void update()
    {
    }
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image, x, y,null);
        if(x<0)
        {
            canvas.drawBitmap(image, x, y, null);
        }
    }
//    public void setVector(int dx)
//    {
//        this.ScreenWidth = dx;
//    }
}