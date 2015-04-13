/*
 * bedrock
 *
 * @copyright Copyright (c) 2015 Mobile Grid, Inc. All rights reserved.
 *
 * @author Tony Stone
 * @date 4/11/15 6:31 PM
 */

package bedrock.config.test;

import bedrock.config.*;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import static org.testng.Assert.*;

/**
 * @class ConfigurationManagerTests
 * @package bedrock.config.test
 *
 * @brief Test class for ConfigurationManager
 *
 * @author Tony Stone
 * @date 4/11/15
 */
@Test (groups = "ConfigurationManager", invocationCount = 10)
public class ConfigurationManagerTest {

    private static final String propertiesFileName = "TestConfiguration.properties";
    private static final String keyPrefix          = "test.configuration";

    @Configuration(value=propertiesFileName, prefix = keyPrefix)
    interface TestConfiguration {

        @Key("string.value")
        @Required
        String stringValue ();

        @Key("string.value.optional")
        String stringValueOptional ();

        @Key("string.value.optional")
        @DefaultValue ("this is a string value")
        String stringValueOptionalDefault ();

        @Key ("int.value")
        @Required
        int intValue ();

        @Key ("int.value.optional")
        int intValueOptional ();

        @Key ("int.value.optional")
        @DefaultValue ("2000")
        int intValueOptionalDefault ();
    }

    private static final String workingDirectory = System.getProperty("user.dir");
    private static final Path propertiesPath = Paths.get (workingDirectory, propertiesFileName);

    @BeforeSuite
    public static void setUp () throws Exception {
        Properties testProperies = new Properties();

        testProperies.setProperty (keyPrefix + ".string.value", "this is a string value");
        testProperies.setProperty (keyPrefix + ".int.value", "1000");

        testProperies.store (Files.newOutputStream (propertiesPath), null);
    }

    @AfterSuite
    public static void tearDown () throws Exception {
        Files.deleteIfExists (propertiesPath);
    }

    @Test
    void getConfigurationTest () throws Exception {
        assertNotNull (ConfigurationManager.getConfiguration (TestConfiguration.class));
    }

    @Test
    void getStringValue () throws Exception {
        TestConfiguration testConfiguration = ConfigurationManager.getConfiguration (TestConfiguration.class);

        assertNotNull (testConfiguration.stringValue ());
        assertEquals (testConfiguration.stringValue (), "this is a string value");
    }

    @Test
    void getStringValueOptional () throws Exception {
        TestConfiguration testConfiguration = ConfigurationManager.getConfiguration (TestConfiguration.class);

        assertNull (testConfiguration.stringValueOptional ());
    }

    @Test
    void getStringValueOptionalDefault () throws Exception {
        TestConfiguration testConfiguration = ConfigurationManager.getConfiguration (TestConfiguration.class);

        assertNotNull (testConfiguration.stringValueOptionalDefault ());
        assertEquals (testConfiguration.stringValueOptionalDefault (), "this is a string value");

    }

    @Test
    void getIntValue () throws Exception {
        TestConfiguration testConfiguration = ConfigurationManager.getConfiguration (TestConfiguration.class);

        assertEquals (testConfiguration.intValue (), 1000);
    }
}
