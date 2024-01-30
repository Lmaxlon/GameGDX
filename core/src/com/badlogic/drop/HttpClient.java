package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.utils.Timer;

import org.json.simple.JSONObject;

import java.util.concurrent.CountDownLatch;

public class HttpClient {

    public String connectToServer(String login, String password) throws InterruptedException {
        String[] response = {""};
        CountDownLatch latch = new CountDownLatch(1);
        int[] attemptCounter = {0};

        HttpRequestBuilder builder = new HttpRequestBuilder();
        HelperForJsonBody body = new HelperForJsonBody(new JSONObject());
        String jsonStr = body.FormAuth(login, password).toJSONString();
        Net.HttpRequest httpRequest = builder.newRequest().method(Net.HttpMethods.POST)
                .url("https://2c16-2a00-1370-816d-a0fa-b53b-2958-b9be-47d7.ngrok-free.app/api")
                .content(jsonStr)
                .header("Content-Type", "application/json")
                .build();

        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                HttpStatus status = httpResponse.getStatus();
                if (status.getStatusCode() == HttpStatus.SC_OK) {
                    response[0] = httpResponse.getResultAsString();
                    Gdx.app.debug("tag", "response server123" + response[0] + "03");
                } else {
                    response[0] = httpResponse.getResultAsString();
                    Gdx.app.debug("tag", "response server1" + status.getStatusCode());
                    attemptCounter[0]++;
                    if (attemptCounter[0] >= 3) {
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                Gdx.app.debug("tag", "Too many attempts, exiting game");
                                Timer.schedule(new Timer.Task() {
                                    @Override
                                    public void run() {
                                        Gdx.app.exit();
                                    }
                                }, 5); // Exit the game after 5 seconds
                            }
                        });
                    }
                }
                latch.countDown(); // Release the latch to unblock the waiting thread
            }

            @Override
            public void failed(Throwable t) {
                // Handle connection failure
                latch.countDown(); // Release the latch in case of failure too
            }

            @Override
            public void cancelled() {
                // Handle cancellation
                latch.countDown(); // Release the latch in case of cancellation too
            }
        });

        latch.await(); // Wait until the latch is released
        httpRequest.reset();

        return response[0];
    }
}







/*
package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.net.HttpStatus;

import org.json.simple.JSONObject;

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
