package org.hotovo.connector.internal.operation;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import org.hotovo.connector.internal.config.ChuckNorrisConfig;
import org.hotovo.connector.internal.connection.ChuckNorrisConnection;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.extension.api.annotation.Configurations;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.Config;

import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.http.api.client.HttpClient;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;

@Configurations(ChuckNorrisConfig.class)
public class ChuckNorrisOperations {

    @MediaType(value = ANY, strict = false)
    @DisplayName("getJoke")
    @Alias("getJoke")
    public InputStream getJoke(@Config ChuckNorrisConfig c, @Connection ChuckNorrisConnection connection){
        return connection.callHttp();
    }
}



    /*
    @Alias("getJokeCategories")
    @DisplayName("getCategories")
    @MediaType(value = ANY, strict = false)
    public String getJokeCategories(@Config ChuckNorrisConfig configuration, @Connection ChuckNorrisConnection connection) {

        response.setLength(0);
        try {
            url = new URL(configuration.getProtocol() + configuration.getHost());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(connectTimeout);
            connection.setReadTimeout(readTimeout);
            connection.addRequestProperty("User-Agent", "Chrome");
            this.status = connection.getResponseCode();
            if (status > 200) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                System.out.println(response.toString());

            }
            reader.close(); //close resource

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return response.toString();
    }


*/

