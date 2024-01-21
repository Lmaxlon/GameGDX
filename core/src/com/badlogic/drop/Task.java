package com.badlogic.drop;

import com.badlogic.gdx.Gdx;

public class Task implements Runnable {
    @Override
    public void run() {
        HttpClient.connectToServer("2.tcp.eu.ngrok.io",15754 );
    }
}
