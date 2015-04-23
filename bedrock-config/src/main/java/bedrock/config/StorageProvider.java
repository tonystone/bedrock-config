/**
 * bedrock
 *
 * @copyright Copyright (c) 2015 Mobile Grid, Inc. All rights reserved.
 *
 * @author Tony Stone
 * @date 4/12/15 8:07 AM
 */

package bedrock.config;

import java.io.IOException;

/**
 * @interface StorageManager
 * @package bedrock.config
 *
 * @brief A brief description.
 *
 * @author Tony Stone
 * @date 4/12/15
 */
interface StorageProvider {

    /**
     * Load the values from disk into internal storage.
     */
    void load () throws IOException;

    /**
     * Store the current values to disk
     */
    void store () throws IOException;

    /**
     * Get the value associated with the key.
     *
     * @param key The key for the value to get.
     * @param targetType
     * @return The object at key.
     */
    Object getValue (String key, Class<?> targetType);

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
    Object getValue (String key, Class<?> targetType, String defaultValue);

    /**
     * Set the value associated with the new.
     *
     * @param key The key for the value that will be set.
     * @param value The value to set for key.
     */
    void setValue (String key, Object value);
}
