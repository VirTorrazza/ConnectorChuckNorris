package org.hotovo.connector.internal.connection.provider;

import org.hotovo.connector.internal.connection.ChuckNorrisConnection;
import org.mule.runtime.api.connection.CachedConnectionProvider;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.connection.ConnectionProvider;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.api.connection.PoolingConnectionProvider;
import org.mule.runtime.api.exception.MuleException;
import org.mule.runtime.api.lifecycle.Startable;
import org.mule.runtime.api.lifecycle.Stoppable;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.RefName;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Example;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.http.api.HttpService;
import org.mule.runtime.http.api.client.HttpClient;
import org.mule.runtime.http.api.client.HttpClientConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;


/**
 * This class (as it's name implies) provides connection instances and the funcionality to disconnect and validate those
 * connections.
 * <p>
 * All connection related parameters (values required in order to create a connection) must be
 * declared in the connection providers.
 * <p>
 * This particular example is a {@link PoolingConnectionProvider} which declares that connections resolved by this provider
 * will be pooled and reused. There are other implementations like {@link CachedConnectionProvider} which lazily creates and
 * caches connections or simply {@link ConnectionProvider} if you want a new connection each time something requires one.
 */
public class ChuckNorrisConnectionProvider implements CachedConnectionProvider<ChuckNorrisConnection>, Startable, Stoppable {

    private final Logger LOGGER = LoggerFactory.getLogger(ChuckNorrisConnectionProvider.class);

    @RefName
    private String name;

    @Parameter
    @Optional(defaultValue = "https://api.chucknorris.io/jokes/")
    @Placement(order = 1, tab = Placement.ADVANCED_TAB)
    @DisplayName("URL")
    @Example("https://api.chucknorris.io/jokes/random")
    private String url;

    @Parameter
    @Optional(defaultValue = "80")
    @Placement(order = 2, tab = Placement.ADVANCED_TAB)
    @DisplayName("Port")
    private String port;

    @Parameter
    @Placement(order = 3, tab = Placement.ADVANCED_TAB)
    @Optional(defaultValue = "5000")
    private int connectionTimeout;

    @Inject
    private HttpService httpService;
    private HttpClient httpClient;

    @Override
    public ChuckNorrisConnection connect() throws ConnectionException {
        return new ChuckNorrisConnection(httpClient, url, port, connectionTimeout);
    }

    @Override
    public void disconnect(ChuckNorrisConnection connection) {
        try {
            connection.invalidate();
        } catch (Exception e) {
            LOGGER.error("Error while disconnecting to ChuckNorris" + e.getMessage(), e);
        }
    }

    @Override
    public ConnectionValidationResult validate(ChuckNorrisConnection connection) {
        ConnectionValidationResult result;
        try {
            if (connection.isConnected()) {
                result = ConnectionValidationResult.success();
            } else {
                result = ConnectionValidationResult.failure("Connection Failed", new Exception());
            }
        } catch (Exception e) {
            result = ConnectionValidationResult.failure("Connection failed: " + e.getMessage(), new Exception());
        }
        return result;
    }

    @Override
    public void start() throws MuleException {
        httpClient = httpService.getClientFactory().create(new HttpClientConfiguration.Builder()
                .setName(name)
                .build());
        httpClient.start();
    }

    @Override
    public void stop() throws MuleException {
        java.util.Optional.ofNullable(httpClient).ifPresent(HttpClient::stop);
    }
}
