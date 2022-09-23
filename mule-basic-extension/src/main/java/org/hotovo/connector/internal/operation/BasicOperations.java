package org.hotovo.connector.internal.operation;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import org.hotovo.connector.internal.config.ChuckNorrisConfig;
import org.hotovo.connector.internal.connection.BasicConnection;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.Config;

import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class BasicOperations {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasicOperations.class);
    private static HttpURLConnection connection;
    private BufferedReader reader;
    private String line;
    private StringBuilder response = new StringBuilder();
    private int connectTimeout = 3000;
    private int readTimeout = 3000;
    private URL url;
    private int status;


    @Alias("getJoke")
    @DisplayName("getJoke")
    @MediaType(value = ANY, strict = false)
    public String getRandomJoke(@Config ChuckNorrisConfig configuration) {
        String protocol = configuration.getProtocol().equals("HTTPS") ? "https://" : "http://";
        LOGGER.info("Sending GET request ...");
        try {
            url = new URL(protocol + configuration.getHost());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(connectTimeout);
            connection.setReadTimeout(readTimeout);
            connection.addRequestProperty("User-Agent", "Chrome");
            this.status = connection.getResponseCode();

            if (status > 200) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            }
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close(); //close resource
            System.out.println(response.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return response.toString();

    }

    @Alias("getJokeCategories")
    @DisplayName("getCategories")
    @MediaType(value = ANY, strict = false)
    public String getJokeCategories(@Config ChuckNorrisConfig configuration, @Connection BasicConnection connection) {

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


}
