package com.example.dsdude.cyshapegame;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Created by Andrew Snyder on 4/29/2016.
 */
public class ProfileActivity extends Activity {
    private WebView loginScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginScreen = new WebView(this);
        loginScreen.getSettings().setJavaScriptEnabled(true);

        final Activity activity = this;

        loginScreen.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl){
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
             }
         });
        Log.d("Socket", SocketHandler.getSocketIP());
        loginScreen.loadUrl(SocketHandler.getSocketIP() + "/index");
        setContentView(loginScreen);
    }
}
