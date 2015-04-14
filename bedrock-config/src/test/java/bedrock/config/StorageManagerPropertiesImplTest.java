package bedrock.config;

import org.testng.annotations.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static org.testng.Assert.*;

/**
 * bedrock
 *
 * @package bedrock.config
 * @interface StorageManagerPropertiesImplTest
 *
 * @author Tony Stone
 * @date 4/13/15
 *
 */

/**
 * @class       StorageManagerPropertiesImplTest
 * @package     bedrock.config
 *
 * @brief       Class to test the StorageManagerPropertiesImpl
 *
 * @author      Tony Stone
 * @date        4/13/15
 */
public class StorageManagerPropertiesImplTest {

    /**
     * Define the working directory for creation of test files.
     */
    private static final String workingDirectory = System.getProperty("user.dir");

    /**
     * Create a temporary file in the working directory for storeage of our test properties
     */
    private static final Path propertiesPath = Paths.get (workingDirectory, "test-" + UUID.randomUUID () + ".properties");

    /**
     * Storage manager to use for suite of tests.
     */
    private StorageManagerPropertiesImpl storageManager = null;

    /**
     * Class to store test values in
     * to test all types supported.
     */
    static class TestParameters {
        String key;
        Class<?> type;
        Object value;
    }

    /**
     * Input paramters to value setters and getters
     */
    static final TestParameters[] testData = new TestParameters[] {
                                                                      new TestParameters () {{ key = "byte.max";      type =  Byte.TYPE;   value = Byte.MAX_VALUE; }},
                                                                      new TestParameters () {{ key = "byte.min";      type =  Byte.TYPE;   value = Byte.MIN_VALUE; }},

                                                                      new TestParameters () {{ key = "short.max";     type =  Short.TYPE;   value = Short.MAX_VALUE; }},
                                                                      new TestParameters () {{ key = "short.min";     type =  Short.TYPE;   value = Short.MIN_VALUE; }},

                                                                      new TestParameters () {{ key = "boolean.true";  type =  Boolean.TYPE; value =  Boolean.TRUE; }},
                                                                      new TestParameters () {{ key = "boolean.false"; type =  Boolean.TYPE; value =  Boolean.FALSE; }},

                                                                      new TestParameters () {{ key = "integer.max";   type =  Integer.TYPE; value =  Integer.MAX_VALUE; }},
                                                                      new TestParameters () {{ key = "integer.min";   type =  Integer.TYPE; value =  Integer.MIN_VALUE; }},

                                                                      new TestParameters () {{ key = "long.max";      type =  Long.TYPE;    value =  Long.MAX_VALUE; }},
                                                                      new TestParameters () {{ key = "long.min";      type =  Long.TYPE;    value =  Long.MIN_VALUE; }},

                                                                      new TestParameters () {{ key = "float.max";     type =  Float.TYPE;   value =  Float.MAX_VALUE; }},
                                                                      new TestParameters () {{ key = "float.min";     type =  Float.TYPE;   value =  Float.MIN_VALUE; }},

                                                                      new TestParameters () {{ key = "double.max";    type =  Double.TYPE;  value =  Double.MAX_VALUE; }},
                                                                      new TestParameters () {{ key = "double.min";    type =  Double.TYPE;  value =  Double.MIN_VALUE; }},

                                                                      new TestParameters () {{ key = "string";        type =  String.class; value =  "Hello world"; }}
    };

    @BeforeSuite
    public void setUp () throws Exception {

        storageManager = new StorageManagerPropertiesImpl (propertiesPath);
    }

    @AfterSuite
    public void tearDown () throws Exception {
        storageManager = null;

        if (Files.exists (propertiesPath)) {
            Files.delete (propertiesPath);
        }
    }

    @Test
    public void testSetValue () throws Exception {
        for (TestParameters parameters : testData) {
            storageManager.setValue (parameters.key, parameters.value);
        }
    }

    @Test (dependsOnMethods={"testSetValue"})
    public void testStore () throws Exception {
        storageManager.store ();
    }

    @Test (dependsOnMethods={"testStore"})
    public void testLoad () throws Exception {
        // We create a new store here so we're sure it loads from disk
        storageManager = new StorageManagerPropertiesImpl (propertiesPath);
    }

    @Test (dependsOnMethods={"testLoad"})
    public void testGetValue () throws Exception {

        for (TestParameters parameters : testData) {
            assertEquals (storageManager.getValue (parameters.key, parameters.type), parameters.value);
        }
    }

    @Test (dependsOnMethods={"testLoad"})
    public void testGetValue1 () throws Exception {
        for (TestParameters parameters : testData) {
            assertEquals (storageManager.getValue (parameters.key, parameters.type, parameters.value.toString ()), parameters.value);
        }
    }
}