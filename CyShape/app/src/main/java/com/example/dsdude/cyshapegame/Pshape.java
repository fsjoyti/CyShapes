package com.example.dsdude.cyshapegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

/**
 * Created by Andrew Snyder on 4/27/2016.
 */
public class Pshape extends Eshape {
    public Pshape(Context context, Bitmap res, int x, int y, int w, int h, int numFrames, int s) {
        super(context, res, x, y, w, h, numFrames, s);
        Log.d("EnemyCreated", "X:" + Integer.toString(x) + " Y:" + Integer.toString(y));
    }

    public void update(int x, int y) {
        this.setx(x);
        this.sety(y);

        Log.d("EnemyPosition", "X:" + Integer.toString(x) + " Y:" + Integer.toString(y));
        animation.update();
    }
}
