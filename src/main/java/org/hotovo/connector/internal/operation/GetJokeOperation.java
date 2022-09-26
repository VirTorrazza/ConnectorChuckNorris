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
public class GetJokeOperation {

    @MediaType(value = ANY, strict = false)
    @DisplayName("getJoke")
    @Alias("getJoke")
    public InputStream getJoke(@Config ChuckNorrisConfig config, @Connection ChuckNorrisConnection connection) {
        return connection.callHttp();
    }
}
