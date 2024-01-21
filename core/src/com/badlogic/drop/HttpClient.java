package com.badlogic.drop;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.net.HttpStatus;

public class HttpClient {
     public static void connectToServer(String ip, int port) {
          HttpRequestBuilder builder = new HttpRequestBuilder();
          Net.HttpRequest httpRequest = builder.newRequest().method(Net.HttpMethods.GET).url("http://" + ip + ":" + port ).build();

          Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
               @Override
               public void handleHttpResponse(Net.HttpResponse httpResponse) {
                    HttpStatus status = httpResponse.getStatus();
                    if (status.getStatusCode() == HttpStatus.SC_OK) {
                         String response = httpResponse.getResultAsString();
                         System.out.println("win");
                    } else {
                         // Обработка ошибки подключения
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
