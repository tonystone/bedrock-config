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
import java.nio.file.Paths;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;

/**
 * @class PropertiesStorageProvider
 * @package bedrock.config
 *
 * @brief A brief description.
 *
 * @author Tony Stone
 * @date 4/12/15
 */
class PropertiesStorageProvider extends StorageProvider {

    /**
     * Actual properties file path which
     * was found by searching paths in searchPaths.
     */
    private Path sourcePath;

    /**
     * The backing store backingStore.
     */
    private Properties backingStore = new Properties ();

    /**
     * Constructor needed as an override to the abstract base class'.
     *
     * @param properties The properties to set up this provider.
     *
     * @throws MissingResourceException If a key in the properties is required but missing
     *
     * @throws IOException If there is an issue reading or writing to the backingStore.
     */
    PropertiesStorageProvider (Map<String, String> properties) throws MissingResourceException, IOException {
        super (properties);

        String sourceProperty = properties.get ("source");

        if (sourceProperty == null) {
            throw new MissingResourceException ("You must define a property named source to define the name of the source file.", PropertiesStorageProvider.class.getName (), "source");
        }

        String searchPathsProperty = properties.get ("searchPaths");

        if (searchPathsProperty == null) {
            throw new MissingResourceException ("You must defined a property named searchPaths to define a list of paths to search for source.", PropertiesStorageProvider.class.getName (), "searchPaths");
        }

        String[] searchPaths = searchPathsProperty.split (";");

        for (String searchPathString : searchPaths) {
            Path sourcePath = Paths.get (searchPathString.trim (),sourceProperty);

            if (Files.exists (sourcePath)) {
                // Store the first file we find as the path
                this.sourcePath = sourcePath;

                // Only load if we find the file
                this.load ();
            }
        }

        //
        // If we didn't find anything in the search path
        // create a source path from the first entry in the
        // searchPaths list if available.
        //
        if (sourcePath == null) {
            String searchPath = "./";

            if (searchPaths.length > 0) {
                searchPath = searchPaths[0];
            }
            sourcePath = Paths.get (searchPath, sourceProperty);
        }
    }

    /**
     * Load the values from disk into internal storage.
     */
    @Override
    public void load () throws IOException {
        backingStore.load (Files.newInputStream (sourcePath));
    }

    /**
     * Store the current values to disk
     */
    @Override
    public void store () throws IOException {
        backingStore.store (Files.newOutputStream (sourcePath), null);
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
        String stringValue = backingStore.getProperty (key);

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
        String stringValue = backingStore.getProperty (key, defaultValue);

        return this.convert (stringValue, targetType);
    }

    /**
     * Set the value associated with the new.
     *  @param key The key for the value that will be set.
     * @param value The value to set for key.
     */
    @Override
    public void setValue (String key, Object value) {
        backingStore.setProperty (key, value.toString ());
    }

    private Object convert(String value, Class<?> targetType) {
        if (targetType == Byte.TYPE)    return Byte.parseByte(value);
        if (targetType == Short.TYPE)   return Short.parseShort (value);
        if (targetType == Boolean.TYPE) return Boolean.parseBoolean (value);
        if (targetType == Integer.TYPE) return Integer.parseInt (value);
        if (targetType == Long.TYPE)    return Long.parseLong (value);
        if (targetType == Float.TYPE)   return Float.parseFloat(value);
        if (targetType == Double.TYPE)  return Double.parseDouble(value);
        else                            return value;
    }
}
