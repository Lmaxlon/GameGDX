package com.badlogic.drop;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.net.HttpStatus;

import org.json.simple.JSONObject;

public class HttpClient {
     public static void connectToServer(String login, String password) {
          HttpRequestBuilder builder = new HttpRequestBuilder();
         HelperForJsonBody body=new HelperForJsonBody(new JSONObject());
         String jsonStr=body.FormAuth(login,password).toJSONString();
         Net.HttpRequest httpRequest = builder.newRequest().method(Net.HttpMethods.POST)                 .url("https://8e22-5-228-80-154.ngrok-free.app/api")
                 .content(jsonStr)
                 .header("Content-Type", "application/json")
                 .build();

          Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
               @Override
                public void handleHttpResponse(Net.HttpResponse httpResponse) {
                    HttpStatus status = httpResponse.getStatus();
                    if (status.getStatusCode() == HttpStatus.SC_OK) {
                         String response = httpResponse.getResultAsString();
                         Gdx.app.debug("tag","response server");
                    } else {
                         Gdx.app.debug("tag","response server1"+status.getStatusCode());
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
     }
}
