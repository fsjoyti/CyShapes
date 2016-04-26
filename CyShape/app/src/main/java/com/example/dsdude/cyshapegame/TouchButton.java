package com.example.dsdude.cyshapegame;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class TouchButton implements OnTouchListener {
    ImageView IM;

    public TouchButton(ImageView imageButton) {
        IM = imageButton;
    }

    @Override
    public boolean onTouch(View arg0, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                IM.setImageResource(R.drawable.btn_s);
                break;
            case MotionEvent.ACTION_UP:
                IM.setImageResource(R.drawable.btn);
                break;
            case MotionEvent.ACTION_OUTSIDE:
                IM.setImageResource(R.drawable.btn);
                break;
            default:

                break;
        }
        return false;
    }

}
