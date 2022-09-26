package org.hotovo.connector.internal.connection;


import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.http.api.HttpConstants;
import org.mule.runtime.http.api.client.HttpClient;
import org.mule.runtime.http.api.domain.message.request.HttpRequest;
import org.mule.runtime.http.api.domain.message.request.HttpRequestBuilder;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeoutException;

/**
 * This class represents an extension connection just as example (there is no real connection with anything here c:).
 */
public class ChuckNorrisConnection {

    private final String url;
    private final String port;
    private final int connectionTimeout;
    private final HttpClient httpClient;

    public ChuckNorrisConnection(HttpClient httpClient, String url, String port, int connectionTimeout) {
        this.httpClient = httpClient;
        this.connectionTimeout = connectionTimeout;
        this.url = url;
        this.port = port;
    }

    public void invalidate() {
        httpClient.stop();
    }

    public boolean isConnected() throws Exception {
        String strUri = String.format("%s/random", url);
        HttpResponse httpResponse = httpClient.send(HttpRequest.builder()
                .method(HttpConstants.Method.GET)
                .uri(strUri)
                .build(), connectionTimeout, false, null);
        if (httpResponse.getStatusCode() >= 200 && httpResponse.getStatusCode() < 300)
            return true;
        else
            throw new ConnectionException("Error connecting to the server: Error Code " + httpResponse.getStatusCode()
                    + "~" + httpResponse);
    }

    public InputStream callHttp() {
        HttpResponse httpResponse = null;
        String strUri = "https://api.chucknorris.io/jokes/random";


        HttpRequest request = httpRequestBuilder
                .method(HttpConstants.Method.GET)
                .uri(strUri)
                .build();

        System.out.println("Request is: " + request);

        try {

            httpResponse = httpClient.send(request, connectionTimeout, false, null);
            System.out.println(httpResponse);
            return httpResponse.getEntity().getContent();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TimeoutException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}





