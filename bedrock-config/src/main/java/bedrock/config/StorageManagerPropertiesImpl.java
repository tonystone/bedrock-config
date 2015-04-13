/**
 * bedrock
 *
 * @copyright Copyright (c) 2015 Mobile Grid, Inc. All rights reserved.
 *
 * @author Tony Stone
 * @date 4/12/15 8:09 AM
 */

package bedrock.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * @class StorageManagerPropertiesImpl
 * @package bedrock.config
 *
 * @brief A brief description.
 *
 * @author Tony Stone
 * @date 4/12/15
 */
class StorageManagerPropertiesImpl implements StorageManager {

    /**
     * Backing store for this implementation.
     */
    private Properties properties;

    private Path propertiesPath;

    private StorageManagerPropertiesImpl () {
    }

    public StorageManagerPropertiesImpl (Path propertiesPath) throws IOException {
        this.propertiesPath = propertiesPath;
        this.properties     = new Properties ();

        if (Files.exists (propertiesPath)) {
            this.load ();
        }
    }

    /**
     * Load the values from disk into internal storage.
     */
    @Override
    public void load () throws IOException {
        properties.load (Files.newInputStream (propertiesPath));
    }

    /**
     * Store the current values to disk
     */
    @Override
    public void store () throws IOException {
        properties.store (Files.newOutputStream (propertiesPath), null);
    }

    /**
     * Get the value associated with the key.
     *
     * @param key The key for the value to get.
     * @param targetType
     * @return The object at key.
     */
    @Override
    public Object getValue (String key, Class<?> targetType) {
        String stringValue = properties.getProperty (key);

        return this.convert (stringValue, targetType);
    }

    /**
     * Get the value associated with the key or the default value if
     * key is not found.
     *
     * @param key The key for the value to get.
     * @param targetType
     * @param defaultValue The defaultValue to return if no key is present.
     *
     * @return The value stored or the defaultValue if key was not found.
     */
    @Override
    public Object getValue (String key, Class<?> targetType, String defaultValue) {
        String stringValue = properties.getProperty (key, defaultValue);

        return this.convert (stringValue, targetType);
    }

    /**
     * Set the value associated with the new.
     *  @param key The key for the value that will be set.
     * @param value The value to set for key.
     */
    @Override
    public void setValue (String key, Object value) {
        properties.setProperty (key, value.toString ());
    }

    private Object convert(String value, Class<?> targetType) {
        if (targetType == Byte.TYPE) return Byte.parseByte(value);
        if (targetType == Short.TYPE) return Short.parseShort(value);
        if (targetType == Integer.TYPE) return Integer.parseInt(value);
        if (targetType == Long.TYPE) return Long.parseLong(value);
        if (targetType == Boolean.TYPE) return Boolean.parseBoolean(value);
        if (targetType == Float.TYPE) return Float.parseFloat(value);
        if (targetType == Double.TYPE) return Double.parseDouble(value);
        else return value;
    }
}
