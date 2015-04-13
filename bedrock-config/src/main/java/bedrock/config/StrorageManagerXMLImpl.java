/**
 * bedrock
 *
 * @copyright Copyright (c) 2015 Mobile Grid, Inc. All rights reserved.
 *
 * @author Tony Stone
 * @date 4/12/15 8:10 AM
 */

package bedrock.config;

import java.io.IOException;

/**
 * @class StrorageManagerXMLImpl
 * @package bedrock.config
 *
 * @brief A brief description.
 *
 * Here typically goes a more extensive explanation of what the header
 * defines. Doxygens tags are words preceeded by either a backslash @\
 * or by an at symbol @@.
 *
 * @author Tony Stone
 * @date 4/12/15
 */
class StrorageManagerXMLImpl implements StorageManager {

    /**
     * Load the values from disk into internal storage.
     */
    @Override
    public void load () throws IOException {

    }

    /**
     * Store the current values to disk
     */
    @Override
    public void store () throws IOException {

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
        return null;
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
        return null;
    }

    /**
     * Set the value associated with the new.
     *  @param key The key for the value that will be set.
     * @param value The value to set for key.
     */
    @Override
    public void setValue (String key, Object value) {

    }
}
