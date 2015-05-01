/**
 * bedrock
 *
 * @copyright Copyright (c) 2015 Mobile Grid, Inc. All rights reserved.
 *
 * @author Tony Stone
 * @date 4/12/15 7:50 AM
 */

package bedrock.config;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.Documented;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @interface   Configuration
 * @package     bedrock.config
 *
 * @brief       A brief description.
 *
 * Configuration annotated classes can be managed by bedrock config
 * runtime system.
 *
 * @author      Tony Stone
 * @date        4/10/15
 */
@Retention (RUNTIME)
@Target (TYPE)
@Documented
public @interface Configuration {
}
