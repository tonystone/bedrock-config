package bedrock.config;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
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
public class PropertiesStorageProviderTest extends StorageProviderTest {

    /**
     * Properties to configure the PropertiesStorageProvider with
     */
    private Map<String,String> properties = new HashMap<String,String> () {
        {
            put ("source","bedrock-config.properties");
            put ("searchPaths","./;~/");
        }
    };


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
    StorageProvider newStorageManager () throws Exception {
        return new PropertiesStorageProvider (properties);
    }
}