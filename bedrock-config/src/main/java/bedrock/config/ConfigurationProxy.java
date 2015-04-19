/**
 * bedrock
 *
 * @copyright Copyright (c) 2015 Mobile Grid, Inc. All rights reserved.
 *
 * @author Tony Stone
 * @date 4/12/15 7:40 AM
 */

package bedrock.config;

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
    private StorageManager storageManager;

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
     * @param storageManager    Trhe storage manager to manage peristent storage.
     * @param keyPrefix
     */
    public ConfigurationProxy (StorageManager storageManager, String keyPrefix) {
        this.storageManager = storageManager;
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
            result = storageManager.getValue (key , method.getReturnType (), defaultValueAnnotation.value ());
        } else {
            result = storageManager.getValue (key, method.getReturnType ());
        }
        return result;
    }

}