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
import bedrock.config.Configuration;
import org.testng.annotations.*;

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
public class ConfigurationManagerTest {

    private static final String propertiesFileName = "TestConfiguration.properties";
    private static final String keyPrefix          = "test.configuration";

    private TestConfiguration testConfiguration = null;

    /**
     * This is the test configuration interface which will be created
     * and used to access the properties in the files.
     */
    @Configuration(value=propertiesFileName, prefix = keyPrefix)
    interface TestConfiguration {

        @Key("string")
        String stringValue ();

        @Key("string.optional")
        @DefaultValue ("this is a string default value")
        String stringValueOptional ();

        @Key ("byte")
        byte byteValue ();

        @Key ("byte.optional")
        @DefaultValue ("127")
        byte byteValueOptional ();

        @Key ("int")
        int intValue ();

        @Key ("int.optional")
        @DefaultValue ("2000")
        int intValueOptional ();

        @Key ("float")
        float floatValue ();

        @Key ("float.optional")
        @DefaultValue ("20.20")
        float floatValueOptional ();
    }

    private static final String workingDirectory = System.getProperty("user.dir");
    private static final Path propertiesPath = Paths.get (workingDirectory, propertiesFileName);

    /**
     * Create a configuration properties file to drive the test suite.
     *
     * @throws Exception
     */
    @BeforeSuite
    public static void suiteSetUp () throws Exception {

        Properties testProperies = new Properties();

        testProperies.setProperty (keyPrefix + ".string", "this is a string value");
        testProperies.setProperty (keyPrefix + ".byte", "-127");
        testProperies.setProperty (keyPrefix + ".int", "1000");
        testProperies.setProperty (keyPrefix + ".float", "10.10");

        testProperies.store (Files.newOutputStream (propertiesPath), null);
    }

    /**
     * Destroy the properties file after the suite runs.
     *
     * @throws Exception
     */
    @AfterSuite
    public static void suiteTearDown () throws Exception {
        Files.deleteIfExists (propertiesPath);
    }

    @BeforeTest
    public void setup () throws Exception {
        testConfiguration = ConfigurationManager.getConfiguration (TestConfiguration.class);
    }

    @AfterTest
    public void tearDown () throws Exception {
        testConfiguration = null;
    }

    /**
     * Testing main method for getting or creating Configuration objects.
     *
     * @throws Exception
     */
    @Test
    void getConfigurationTest () throws Exception {
        assertNotNull (testConfiguration);
    }

    @Test
    void getStringValue () throws Exception {
        assertNotNull (testConfiguration.stringValue ());
        assertEquals (testConfiguration.stringValue (), "this is a string value");
    }

    @Test
    void getStringValueOptional () throws Exception {
        assertNotNull (testConfiguration.stringValueOptional ());
        assertEquals (testConfiguration.stringValueOptional (), "this is a string default value");
    }

    @Test
    void getByteValue () throws Exception {
        assertTrue (testConfiguration.byteValue () == -127);
    }

    @Test
    void getByteValueOptional () throws Exception {
        assertTrue (testConfiguration.byteValueOptional () == 127);
    }

    @Test
    void getIntValue () throws Exception {
        assertTrue (testConfiguration.intValue () == 1000);
    }

    @Test
    void getIntValueOptional () throws Exception {
        assertTrue (testConfiguration.intValueOptional () == 2000);
    }

    @Test
    void getFloatValue () throws Exception {
        assertTrue (testConfiguration.floatValue () == 10.10f);
    }

    @Test
    void getFloatValueOptional () throws Exception {
        assertTrue (testConfiguration.floatValueOptional () == 20.20f);
    }
}
