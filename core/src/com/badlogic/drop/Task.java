package com.badlogic.drop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.concurrent.Callable;

public class Task implements Runnable, ApplicationListener {
    ErrorScreen error;
    String login;
    String password;
    Game game;
    AuthScreen authScreen;
    String res;

    Task(String login, String password, Game game,AuthScreen authScreen) {
        this.login = login;
        this.password = password;
        this.game=game;
        this.authScreen=authScreen;
    }


    @Override
    public void run() {
        //new HttpClient().connectToServer(login, password);
        handleResponse(new HttpClient().connectToServer(login, password));

    }

    public void handleResponse(String response) {
        res=response;
        Gdx.app.debug("tag", "Task" +" "+ response + " 111");
        Object o = null;
        try {
            o = new JSONParser().parse(response);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JSONObject j = (JSONObject) o;

        if(((Long)j.get("request"))==-1){
            //game.dispose();
            //game.render();
            //game.setScreen(new Map());
            game.setScreen(new ErrorScreen(game,"bad Auth",authScreen));
            //game.dispose();

        }else {
            //game.dispose();
            game.setScreen(new Map());
            //game.dispose();
        }

    }

    @Override
    public void create() {
        error = new ErrorScreen(game,res,authScreen);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
