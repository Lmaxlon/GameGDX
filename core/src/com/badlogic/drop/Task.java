package com.badlogic.drop;

import com.badlogic.gdx.Gdx;

public class Task implements Runnable {
    String login;
    String password;
    Task(String login,String password){
        this.login=login;
        this.password=password;
    }
    @Override
    public void run() {
        HttpClient.connectToServer(login,password );
    }
}