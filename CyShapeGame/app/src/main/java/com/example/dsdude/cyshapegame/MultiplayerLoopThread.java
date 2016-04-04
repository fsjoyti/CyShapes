package com.example.dsdude.cyshapegame;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.JsonWriter;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Andrew Snyder on 4/4/2016.
 */
public class MultiplayerLoopThread extends Thread {
    static final long FPS = 10;
    private MultiplayerView view;
    private boolean running = false;
    private Socket socket;
    private int playerID;
    private long endTime;

    public MultiplayerLoopThread(MultiplayerView view) {
        this.view = view;
        MultiplayerInstanceActivity app = (MultiplayerInstanceActivity) view.getContext();
        socket = app.getSocket();
        socket.on("onconnected", onconnected);
        socket.on("update", update);
        socket.connect();
    }

    public void setRunning(boolean run) {
        running = run;
    }

    @Override
    public void run() {
        long ticksPS = 1000 / FPS;
        endTime = System.currentTimeMillis() + 60000;
        long startTime;
        long sleepTime;
        while (running) {
            sendPosition();
            Canvas c = null;
            Paint p = new Paint();
            startTime = System.currentTimeMillis();
            long checkTime = endTime - System.currentTimeMillis();
            if (checkTime <= 0) {
                break;   // Should probably show some "Game Over" screen
            }
            try {
                c = view.getHolder().lockCanvas();
                p.setColor(Color.WHITE);
                p.setTextSize(100);
                int simpleEndTime = (int) checkTime / 1000;
                synchronized (view.getHolder()) {
                    view.onDraw(c);
                    c.drawText(Integer.toString(simpleEndTime), view.getWidth() - 125, 100, p);
                }
            } finally {
                if (c != null) {
                    view.getHolder().unlockCanvasAndPost(c);
                }
            }
            sleepTime = ticksPS-(System.currentTimeMillis() - startTime);
            try {
                if (sleepTime > 0)
                    sleep(sleepTime);
                else
                    sleep(10);
            } catch (Exception e) {}
        }
    }

    private void sendPosition() {
//        String s = "x: " + view.players.get(0).getX();
//        s += "y: " + view.players.get(0).getY();
//        s += "id: " + view.players.get(0).getID();
        JSONObject data = new JSONObject();
        try {
            data.put("time", endTime);
        } catch(JSONException e) {
            return;
        }
        socket.emit("send_position", data);
    }

    private Emitter.Listener update = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            // Update the other players
//            JSONObject data = (JSONObject) args[0];
//            int x, y, id;
//            try {
//                x = data.getInt("x");
//                y = data.getInt("y");
//                id = (int) Math.floor(data.getDouble("id") * 100);
//            } catch(JSONException e) {
//                return;
//            }
//            view.players.get(id).setX(x);
//            view.players.get(id).setY(y);
//        }
            JSONObject data = (JSONObject) args[0];
            try {
                endTime = data.getLong("time");
            } catch (JSONException e) {
                return;
            }
        }
    };

    private Emitter.Listener onconnected = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            // Update the other players
            JSONObject data = (JSONObject) args[0];
            int id;
            try {
                id = (int) Math.floor(data.getDouble("id") * 100);
            } catch(JSONException e) {
                return;
            }
            Bitmap bmp = BitmapFactory.decodeResource(view.getResources(), R.drawable.star);
            playerID = id;
            view.players.add(new Player(bmp, id));
        }
    };
}