package com.example.dsdude.cyshapegame;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by Andrew Snyder on 4/27/2016.
 */
public class Pshape extends Eshape {
    public Pshape(Context context, Bitmap res, int x, int y, int w, int h, int numFrames, int s,int WIDTH,int HEIGHT) {
        super(context, res, x, y, w, h, numFrames, s,WIDTH,HEIGHT);
    }

    public void update(int x, int y) {
        this.setx(x);
        this.sety(y);
        animation.update();
    }
}
