/**
 * ConfigurationProxy.java
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
 * @date 4/12/15 7:40 AM
 */

package bedrock.config;

import bedrock.config.annotation.DefaultValue;
import bedrock.config.annotation.Key;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @class       ConfigurationProxyImpl
 * @package     bedrock.config
 *
 * @brief       This object is an implementation for the proxy object which handles all attribute storage and method invocations.
 *
 * @author      Tony Stone
 * @date        4/10/15
 */
class ConfigurationProxy implements InvocationHandler {

    /**
     * The storage manager responsible for storing and
     * retrieving values from the persistent storage.
     */
    private StorageProvider storageProvider;

    /**
     * The prefix to append to the beginning of
     * each key value.
     */
    private String keyPrefix = null;

    /**
     * Do not allow constructor
     */
    private ConfigurationProxy () {}


    /**
     * Construct an instance of this class with a valid
     * storage stratagy manager.
     *
     * @param storageProvider    Trhe storage manager to manage peristent storage.
     * @param keyPrefix
     */
    public ConfigurationProxy (StorageProvider storageProvider, String keyPrefix) {
        this.storageProvider = storageProvider;
        this.keyPrefix      = keyPrefix;
    }

    /**
     * Processes a method invocation on a proxy instance and returns
     * the result.  This method will be invoked on an invocation handler
     * when a method is invoked on a proxy instance that it is
     * associated with.
     *
     * @see     InvocationHandler
     */
    @Override
    public Object invoke (Object proxy, Method method, Object[] args) throws Throwable {

        Object result = null;

        String key = null;

        Key keyAnnotation = method.getAnnotation (Key.class);
        if (keyAnnotation != null) {
            key = keyAnnotation.value ();
        } else {
            key = method.getName ();
        }

        if (keyPrefix != null && keyPrefix.length () > 0) {
            key = keyPrefix + "." + key;
        }

        DefaultValue defaultValueAnnotation = method.getAnnotation (DefaultValue.class);

        if (defaultValueAnnotation != null) {
            result = storageProvider.getValue (key , method.getReturnType (), defaultValueAnnotation.value ());
        } else {
            result = storageProvider.getValue (key, method.getReturnType ());
        }
        return result;
    }

}