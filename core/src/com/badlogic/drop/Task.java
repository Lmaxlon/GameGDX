package com.badlogic.drop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.concurrent.Callable;

public class Task implements Runnable {
    String login;
    String password;
    Game game;

    Task(String login, String password, Game game) {
        this.login = login;
        this.password = password;
        this.game=game;
    }


    @Override
    public void run() {

        handleResponse(new HttpClient().connectToServer(login, password));

    }

    public void handleResponse(String response) {
        Gdx.app.debug("tag", "Task" +" "+ response + " 111");
        Object o = null;
        try {
            o = new JSONParser().parse(response);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JSONObject j = (JSONObject) o;

        if(((Long)j.get("request"))==-1){
            game.dispose();
            //game.render();
            //game.setScreen(new Map());
            game.setScreen(new ErrorScreen(game,"server",new AuthScreen(game)));

        }else {
            game.dispose();
            game.setScreen(new Map());
        }

    }
}
