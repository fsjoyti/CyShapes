package com.example.dsdude.cyshapegame;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class LoginActivity extends Activity {

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
                loginScreen.loadUrl("http://10.36.23.91:3000/index");
                setContentView(loginScreen);
    }

}

