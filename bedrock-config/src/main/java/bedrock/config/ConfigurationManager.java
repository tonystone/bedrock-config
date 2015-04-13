/*
 * bedrock
 *
 * @copyright Copyright (c) 2015 Mobile Grid, Inc. All rights reserved.
 *
 * @author Tony Stone
 * @date 4/9/15 8:47 PM
 */

package bedrock.config;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @class       ConfigurationManager
 * @package     bedrock.config
 *
 * @brief       Factory object to getConfiguration instances of {@link Configuration} annotated classes.
 *
 * @author      Tony Stone
 * @date        4/10/15
 */
public final class ConfigurationManager {

    /**
     * Create a new instance of the class T
     *
     * @param <T>
     *
     * @param clazz
     * @return
     */
    public static <T> T getConfiguration (Class<T> clazz) throws IOException {

        Configuration configuration = clazz.getAnnotation (Configuration.class);

        if (configuration == null) {
            throw new IOException ("Class does not support Configurations.");
        }

        if (!clazz.isInterface ()) {
            throw new IOException ("You can only pass an instance of an interface.");
        }

        String workingDirectory = System.getProperty("user.dir");
        Path sourcePath = Paths.get (workingDirectory, configuration.value ());

        Class<?>[] interfaces = {clazz};
        StorageManager storageManager    = new StorageManagerPropertiesImpl (sourcePath);

        ConfigurationProxyImpl proxyImpl = new ConfigurationProxyImpl(storageManager, configuration.prefix ());

        return clazz.cast (Proxy.newProxyInstance (clazz.getClassLoader (), interfaces, proxyImpl));


    }
}

