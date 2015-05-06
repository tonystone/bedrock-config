/**
 * bedrock
 *
 * @copyright Copyright (c) 2015 Mobile Grid, Inc. All rights reserved.
 *
 * @author Tony Stone
 * @date 4/12/15 7:50 AM
 */

package bedrock.config.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.Documented;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @interface   DefaultValue
 * @package     bedrock.config
 *
 * @brief       Specify a default value for an attribute.
 *
 * This method sets the default value of an attribute and
 * implicitly set the attribute to optional.
 *
 * @author      Tony Stone
 * @date        4/12/15
 */
@Retention(RUNTIME)
@Target(METHOD)
@Documented
public @interface DefaultValue {
    String value ();
}
