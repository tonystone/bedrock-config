/**
 * StorageProvider.java
 *
 * @Copyright 2018 Tony Stone
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 * @author Tony Stone
 * @date 4/12/15 8:07 AM
 */

package bedrock.config;

import java.io.IOException;
import java.util.Map;

/**
 * @interface StorageProvider
 * @package bedrock.config
 *
 * @brief A brief description.
 *
 * @author Tony Stone
 * @date 4/12/15
 */
abstract class StorageProvider {

    /**
     * Default constructor required for all subclasses
     * of this class.
     *
     * If you require settings for your subclass, override
     * this constructor and the settings will be passed to
     * you in the properties.
     *
     * @param properties
     * @throws IOException
     */
    StorageProvider (Map<String,String> properties) throws IOException {}

    /**
     * Load the values from disk into internal storage.
     */
    abstract void load () throws IOException;

    /**
     * Store the current values to disk
     */
    abstract void store () throws IOException;

    /**
     * Get the value associated with the key.
     *
     * @param key The key for the value to get.
     * @param targetType
     * @return The object at key.
     */
    abstract Object getValue (String key, Class<?> targetType);

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
    abstract Object getValue (String key, Class<?> targetType, String defaultValue);

    /**
     * Set the value associated with the new.
     *
     * @param key The key for the value that will be set.
     * @param value The value to set for key.
     */
    abstract void setValue (String key, Object value);
}
