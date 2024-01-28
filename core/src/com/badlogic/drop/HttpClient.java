package com.badlogic.drop;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.net.HttpStatus;

import org.json.simple.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class HttpClient {

    public String connectToServer(String login, String password) {
        String[] response={""};
        final boolean[] responseReceived = {false};
        HttpRequestBuilder builder = new HttpRequestBuilder();
        HelperForJsonBody body=new HelperForJsonBody(new JSONObject());
        String jsonStr=body.FormAuth(login,password).toJSONString();
        Net.HttpRequest httpRequest = builder.newRequest().method(Net.HttpMethods.POST)
                .url("https://ba7c-5-228-80-154.ngrok-free.app/api")
                .content(jsonStr)
                .header("Content-Type", "application/json")
                .build();
        // httpRequest.setTimeOut(6000);

        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                HttpStatus status = httpResponse.getStatus();
                if (status.getStatusCode() == HttpStatus.SC_OK) {

                    response[0] = httpResponse.getResultAsString();
                    // game.setScreen(new ErrorScreen(game, response[0], new AuthScreen(game)));
                    //String result = new Scanner(httpResponse.getResultAsStream(), "UTF-8").useDelimiter("\\A").next();
                    //String result=httpResponse.getResultAsStream();
                    Gdx.app.debug("tag","response server123"+ response[0] +"03");
                    //System.out.println(result);
                    responseReceived[0] =true;

                } else {
                    response[0] = httpResponse.getResultAsString();
                    //game.setScreen(new ErrorScreen(game, response[0], new AuthScreen(game)));
                    Gdx.app.debug("tag","response server1"+status.getStatusCode());
                    responseReceived[0] =true;
                }
            }

            @Override
            public void failed(Throwable t) {
                // Обработка ошибки подключения
            }

            @Override
            public void cancelled() {
                // Действия при отмене запроса
            }
        });

        while (!responseReceived[0]){



        }
        httpRequest.reset();

        return response[0];
    }

}


/*package com.badlogic.drop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class HttpClient {


    public static String connectToServer(String login, String password) {
        final String[] response = {""};

        HttpRequestBuilder builder = new HttpRequestBuilder();
        HelperForJsonBody body = new HelperForJsonBody(new JSONObject());
        String jsonStr = body.FormAuth(login, password).toJSONString();
        Net.HttpRequest httpRequest = builder.newRequest().method(Net.HttpMethods.POST)
                .url("https://ba7c-5-228-80-154.ngrok-free.app/api")
                .content(jsonStr)
                .header("Content-Type", "application/json")
                .build();
        Net.HttpResponseListener listener = new Net.HttpResponseListener() {


            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                if(httpResponse.getStatus().getStatusCode()==HttpStatus.SC_OK) {
                    response[0] = httpResponse.getResultAsString();
                    Gdx.app.debug("tag", response[0] + "585");
                }
                //AuthScreen.content.setString(response);
                // AuthScreen.content.notify();
                // Gdx.app.debug("хуй", ",kz,");
//                AuthScreen.content.setString(httpResponse.getResultAsString());
                //Gdx.app.debug("хуй", "response server123" + AuthScreen.content.getString() + "93");
            }

            @Override
            public void failed(Throwable t) {

            }

            @Override
            public void cancelled() {

            }
        };
        Gdx.net.sendHttpRequest(httpRequest, listener);
        return response[0];
    }

}

 */




/*package com.badlogic.drop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class HttpClient {


    public static String connectToServer(String login, String password) {
        final String[] response = {""};

        HttpRequestBuilder builder = new HttpRequestBuilder();
        HelperForJsonBody body = new HelperForJsonBody(new JSONObject());
        String jsonStr = body.FormAuth(login, password).toJSONString();
        Net.HttpRequest httpRequest = builder.newRequest().method(Net.HttpMethods.POST)
                .url("https://ba7c-5-228-80-154.ngrok-free.app/api")
                .content(jsonStr)
                .header("Content-Type", "application/json")
                .build();
        Net.HttpResponseListener listener = new Net.HttpResponseListener() {


            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                if(httpResponse.getStatus().getStatusCode()==HttpStatus.SC_OK) {
                    response[0] = httpResponse.getResultAsString();
                    Gdx.app.debug("tag", response[0] + "585");
                }
                //AuthScreen.content.setString(response);
                // AuthScreen.content.notify();
                // Gdx.app.debug("хуй", ",kz,");
//                AuthScreen.content.setString(httpResponse.getResultAsString());
                //Gdx.app.debug("хуй", "response server123" + AuthScreen.content.getString() + "93");
            }

            @Override
            public void failed(Throwable t) {

            }

            @Override
            public void cancelled() {

            }
        };
        Gdx.net.sendHttpRequest(httpRequest, listener);
        return response[0];
    }

}

 */


