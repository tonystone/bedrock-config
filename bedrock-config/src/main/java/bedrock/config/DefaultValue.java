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

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @interface   DefaultValue
 * @package     bedrock.config
 *
 * @brief       A brief description.
 *
 * Here typically goes a more extensive explanation of what the header
 * defines. Doxygens tags are words preceeded by either a backslash @\
 * or by an at symbol @@.
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
