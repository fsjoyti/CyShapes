package com.example.dsdude.cyshapegame;


import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

/**
 * Created by Andrew Snyder on 4/18/2016.
 */
public class SocketHandler {
    private static Socket socket;

    public static synchronized Socket getSocket(){
        return SocketHandler.socket;
    }

    public static synchronized void setSocket(String address) {
        try {
            socket = IO.socket("http://" + address + ":3000");
        } catch (URISyntaxException e) {

        }
    }
}
