package bedrock.config;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static org.testng.Assert.assertEquals;

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
public class PropertiesStorageManagerTest extends StorageManagerTest {

    /**
     * Define the working directory for creation of test files.
     */
    private static final String workingDirectory = System.getProperty ("user.dir");

    /**
     * Create a temporary file in the working directory for storeage of our test properties
     */
    private static final Path propertiesPath = Paths.get (workingDirectory, "test-" + UUID.randomUUID () + ".properties");


    @Override
    void setUp () throws Exception {
        ; // Nothing to do here
    }

    @Override
    void tearDown () throws Exception {

        if (Files.exists (propertiesPath)) {
            Files.delete (propertiesPath);
        }
    }

    @Override
    StorageManager newStorageManager () throws Exception {
        return new PropertiesStorageManager (propertiesPath);
    }
}