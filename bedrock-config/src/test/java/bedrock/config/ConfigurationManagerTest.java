/*
 * bedrock
 *
 * @copyright Copyright (c) 2015 Mobile Grid, Inc. All rights reserved.
 *
 * @author Tony Stone
 * @date 4/11/15 6:31 PM
 */

package bedrock.config;

import bedrock.config.*;
import bedrock.config.Configuration;
import org.testng.annotations.*;

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
public class ConfigurationManagerTest {

    private static final String propertiesFileName = "TestConfiguration.properties";
    private static final String keyPrefix          = "test.configuration";

    private TestConfiguration testConfiguration = null;

    /**
     * This is the test configuration interface which will be created
     * and used to access the properties in the files.
     */
    @Configuration
    @Key (keyPrefix)
    public interface TestConfiguration {

        @Key("string")
        String stringValue ();

        String stringValueNoAnnotations ();

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

    /**
     * Bedrock configurations must be annotated with the
     * {@code @Configuration} annotation.  This interface
     * is to test the error handling when they are not.
     */
    public interface BadTestConfiguration {

    }

    /**
     * Bedrock configurations must be an interface and not
     * a class. This class is to test the error handling when
     * a class is passed.
     */
    @Configuration
    public class BadClassTestConfiguration {

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
        testProperies.setProperty (keyPrefix + ".stringValueNoAnnotations", "this is a string value with no annotations");
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
     * Tests
     */
    @Test
    void testConstruction () throws Exception {
//        assertNotNull (new ConfigurationManager ());
    }

    @Test
    void testGetConfiguration () throws Exception {
        assertNotNull (testConfiguration);
    }

    @Test (expectedExceptions = IOException.class)
    void testGetConfigurationNotAnnotated () throws Exception {

        ConfigurationManager.getConfiguration (BadTestConfiguration.class);
    }

    @Test (expectedExceptions = IOException.class)
    void testGetConfigurationWithClass () throws Exception {

        ConfigurationManager.getConfiguration (BadClassTestConfiguration.class);
    }

    @Test
    void testStringValue () throws Exception {
        assertNotNull (testConfiguration.stringValue ());
        assertEquals (testConfiguration.stringValue (), "this is a string value");
    }

    @Test
    void testStringValueOptional () throws Exception {
        assertNotNull (testConfiguration.stringValueOptional ());
        assertEquals (testConfiguration.stringValueOptional (), "this is a string default value");
    }

    @Test
    void testStringValueNoAnnotations () throws Exception {
        assertNotNull (testConfiguration.stringValueNoAnnotations ());
        assertEquals (testConfiguration.stringValueNoAnnotations (), "this is a string value with no annotations");
    }

    @Test
    void testByteValue () throws Exception {
        assertTrue (testConfiguration.byteValue () == -127);
    }

    @Test
    void testByteValueOptional () throws Exception {
        assertTrue (testConfiguration.byteValueOptional () == 127);
    }

    @Test
    void testIntValue () throws Exception {
        assertTrue (testConfiguration.intValue () == 1000);
    }

    @Test
    void testIntValueOptional () throws Exception {
        assertTrue (testConfiguration.intValueOptional () == 2000);
    }

    @Test
    void testFloatValue () throws Exception {
        assertTrue (testConfiguration.floatValue () == 10.10f);
    }

    @Test
    void testFloatValueOptional () throws Exception {
        assertTrue (testConfiguration.floatValueOptional () == 20.20f);
    }

}
