package bedrock.config;

import org.testng.annotations.*;

import static org.testng.Assert.*;

/**
 * bedrock
 *
 * @package bedrock.config
 * @interface StorageManager
 *
 * @author Tony Stone
 * @date 4/13/15
 *
 */

/**
 * @class       StorageManager
 * @package     bedrock.config
 *
 * @brief       Abstract class to test the StorageManager implementations
 *
 * @author      Tony Stone
 * @date        4/13/15
 */
public abstract class StorageManagerTest {

    /**
     * Storage manager to use for suite of tests.
     */
    protected StorageManager storageManager = null;

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

    /**
     * BeforeSuite will call this method first to
     * setup the suite for testing.
     *
     * @throws Exception
     */
    abstract void setUp () throws Exception;

    /**
     * AfterSuite will call this method to close
     * down the suite and end testing.
     *
     * @throws Exception
     */
    abstract void tearDown () throws Exception;

    /**
     * When a new instance of a StorageMaanger is required
     * for testing, this method will be called to get it.
     *
     * @return
     * @throws Exception
     */
    abstract StorageManager newStorageManager () throws Exception;

    @BeforeSuite
    public void beforeSuite () throws Exception {

        setUp ();

        storageManager = newStorageManager ();
    }

    @AfterSuite
    public void afterSuite () throws Exception {

        tearDown ();
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
        storageManager = newStorageManager ();
    }

    @Test (dependsOnMethods={"testLoad"})
    public void testGetValue () throws Exception {

        for (TestParameters parameters : testData) {
            assertEquals (storageManager.getValue (parameters.key, parameters.type), parameters.value);
        }
    }

    @Test (dependsOnMethods={"testLoad"})
    public void testGetValueWithDefault () throws Exception {
        for (TestParameters parameters : testData) {
            assertEquals (storageManager.getValue (parameters.key, parameters.type, parameters.value.toString ()), parameters.value);
        }
    }
}