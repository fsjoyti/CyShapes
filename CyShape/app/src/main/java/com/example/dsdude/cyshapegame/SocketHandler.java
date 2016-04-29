package com.example.dsdude.cyshapegame;


import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

/**
 * Created by Andrew Snyder on 4/18/2016.
 */
public class SocketHandler {
    private static Socket socket;
    private static Double id;
    private static String playerID;

    public static synchronized Socket getSocket(){
        return SocketHandler.socket;
    }

    public static synchronized void setSocket(String address){
        try {
            socket = IO.socket("http://" + address + ":3000");
        } catch (URISyntaxException e) {

        }
    }

    public static synchronized void setId(Double id){
        SocketHandler.id = id;
    }

    public static synchronized Double getId(){
        return SocketHandler.id;
    }

    public static synchronized void setPlayerID(String id){
        SocketHandler.playerID = id;
    }

    public static synchronized String getPlayerID(){
        return SocketHandler.playerID;
    }
}
