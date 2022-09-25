package org.hotovo.connector.internal.config;

import org.hotovo.connector.internal.connection.provider.ChuckNorrisConnectionProvider;
import org.hotovo.connector.internal.operation.ChuckNorrisOperations;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;

/**
 * This class represents an extension configuration, values set in this class are commonly used across multiple
 * operations since they represent something core from the extension.
 */
@Operations(ChuckNorrisOperations.class)
@ConnectionProviders(ChuckNorrisConnectionProvider.class)
public class ChuckNorrisConfig {

}
