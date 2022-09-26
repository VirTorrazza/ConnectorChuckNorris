package org.hotovo.connector.internal.connection;


import org.hotovo.connector.internal.config.ChuckNorrisConfig;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.RefName;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Example;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.http.api.HttpConstants;
import org.mule.runtime.http.api.HttpService;
import org.mule.runtime.http.api.client.HttpClient;
import org.mule.runtime.http.api.client.HttpClientConfiguration;
import org.mule.runtime.http.api.domain.message.request.HttpRequest;
import org.mule.runtime.http.api.domain.message.request.HttpRequestBuilder;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;
import org.mulesoft.common.ext.Diff;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeoutException;

/**
 * This class represents an extension connection just as example (there is no real connection with anything here c:).
 */
public class ChuckNorrisConnection {

  private int connectionTimeout;
  private HttpClient httpClient;
  private HttpRequestBuilder httpRequestBuilder;


  @Parameter
  @Placement(order=1)
  @DisplayName("ChuckNorrisHost")
  @Alias("Host")
  @Example("api.chucknorris.io/jokes/random")
  private String host;

  @Parameter
  @Placement(order=2)
  @DisplayName("Protocol")
  @Alias("Protocol")
  private String protocol;

  @Parameter
  @Placement(order=3)
  @DisplayName("Port")
  @Alias("Port")
  private String port;

  public ChuckNorrisConnection(HttpService httpService, int cTimeout) {

    connectionTimeout = cTimeout;

    initHttpClient(httpService);
  }

  public void initHttpClient(HttpService httpService){
    HttpClientConfiguration.Builder builder = new HttpClientConfiguration.Builder();
    builder.setName("ChuckNorris");
    httpClient = httpService.getClientFactory().create(builder.build());

    httpRequestBuilder = HttpRequest.builder();

    httpClient.start();
  }


  public void invalidate() {
    httpClient.stop();
  }

  public boolean isConnected() throws Exception{

    String strUri = "https://api.chucknorris.io/jokes/random";


    HttpRequest request = httpRequestBuilder
            .method(HttpConstants.Method.GET)
            .uri(strUri)
            .build();

    HttpResponse httpResponse = httpClient.send(request,connectionTimeout,false,null);

    if (httpResponse.getStatusCode() >= 200 && httpResponse.getStatusCode() < 300)
      return true;
    else
      throw new ConnectionException("Error connecting to the server: Error Code " + httpResponse.getStatusCode()
              + "~" + httpResponse);
  }

  public InputStream callHttp(){
    HttpResponse httpResponse = null;
    String strUri = "https://api.chucknorris.io/jokes/random";


    HttpRequest request = httpRequestBuilder
            .method(HttpConstants.Method.GET)
            .uri(strUri)
            .build();

    System.out.println("Request is: " + request);

    try {

      httpResponse = httpClient.send(request,connectionTimeout,false,null);
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





