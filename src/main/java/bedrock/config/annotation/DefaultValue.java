/**
 * DefaultValue.java
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
