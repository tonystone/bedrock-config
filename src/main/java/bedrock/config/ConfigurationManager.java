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
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.Map;

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
     * This class should never be instantiated
     * so this constructor is marked private.
     */
    private ConfigurationManager () {}

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

        ConfigurationProxy proxyImpl = new ConfigurationProxy (getStorageProvider (clazz, configuration), getKeyPrefix (clazz));

        Class<?>[] interfaces = {clazz};

        return clazz.cast (Proxy.newProxyInstance (clazz.getClassLoader (), interfaces, proxyImpl));
    }

    /**
     * Return the key prefix to use for all key lookups
     * for attributes.
     *
     * @param clazz The @Configuration annotated class
     *
     * @return  the prefix String to use or null if non
     */
    private static String getKeyPrefix (Class<?> clazz) {
        String keyPrefix = null;

        if (clazz.isAnnotationPresent (Key.class)) {
            keyPrefix = clazz.getAnnotation (Key.class).value ();
        }
        return keyPrefix;
    }

    /**
     * Returns the Storage provider to use for this class instance.
     *
     * @param clazz
     * @param configuration
     * @return
     * @throws IOException
     */
    private static StorageProvider getStorageProvider (Class<?> clazz, Configuration configuration) throws IOException {

        StorageProvider storageProvider = null;

        ConfigurationSettings configurationSettings = ConfigurationSettings.getConfigurationSettings (clazz);

        try {
            Class<?> storageProviderClass = Class.forName (configurationSettings.getStorageProvider (), false, clazz.getClassLoader ());

            if (StorageProvider.class.isAssignableFrom (storageProviderClass)) {

                Class<?> parTypes[] = new Class<?>[1];
                parTypes[0] = Map.class;

                Constructor constructor = storageProviderClass.getDeclaredConstructor (parTypes);

                storageProvider = StorageProvider.class.cast (constructor.newInstance (configurationSettings.getProperties ()));
            }

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new IOException (e);
        }
        return storageProvider;
    }

}

