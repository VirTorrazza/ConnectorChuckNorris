package org.hotovo.connector.internal.extension;

import org.hotovo.connector.internal.config.ChuckNorrisConfig;
import org.hotovo.connector.internal.connection.provider.ChuckNorrisConnectionProvider;
import org.hotovo.connector.internal.operation.ChuckNorrisOperations;
import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.Configurations;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;


/**
 * This is the main class of an extension, is the entry point from which configurations, connection providers, operations
 * and sources are going to be declared.
 */
@Xml(prefix = "chucknorris")
@Extension(name = "Chuck Norris")
@ConnectionProviders(ChuckNorrisConnectionProvider.class)
@Operations({ChuckNorrisOperations.class})
public class ChuckNorrisConnector {

}
