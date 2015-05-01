package bedrock.config;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @class GlobalSettings
 * @package bedrock.config
 *
 * @brief A brief description.
 *
 * Here typically goes a more extensive explanation of what the header
 * defines. Doxygens tags are words preceeded by either a backslash @\
 * or by an at symbol @@.
 *
 * @author Tony Stone
 * @date 4/21/15
 */
class ConfigurationSettings {

    /**
     * Storage manager to use for this configuration
     */
    private String storageProvider = "bedrock.config.PropertiesStorageProvider";

    /**
     * Properties to configure the storage manager above.  These
     * are storage manager specific.
     */
    private Map<String,String> properties = new HashMap<String,String> () {
        {
            put ("source","bedrock-config.properties");
            put ("searchPaths","./;~/");
        }
    };

    /**
     * Static storage for the global default configuration
     * as either read from the resource file or defaulted to
     * the classes default values above.
     */
    private static ConfigurationSettings globalSettings = new ConfigurationSettings ();

    /**
     * Static storage for configuration settings by class as
     * read from the resources file.
     */
    private static Map<Class<?>,ConfigurationSettings> configurationClassSettings = new HashMap<> ();

    /**
     * Static initializer to init the class one time
     */
    static  {

        try {
            loadConfigurationManagerSettings ();
        }
        catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * Default constructor
     */
    private ConfigurationSettings () {}

    /**
     * Copy constructor
     * @param other
     */
    private ConfigurationSettings (ConfigurationSettings other) {
        this.storageProvider = other.storageProvider;
        this.properties = new HashMap<> (other.properties);
    }

    public String getStorageProvider () {
        return storageProvider;
    }

    public Map<String, String> getProperties () {
        return new HashMap<> (properties);
    }

    /**
     * Get the configuration for a particular class name.  If the class
     * does not have a specific override, return the global configuration.
     *
     * @param configurationClass
     * @return
     */
    public static ConfigurationSettings getConfigurationSettings (Class<?> configurationClass) {
        return configurationClassSettings.getOrDefault (configurationClass, globalSettings);
    }

    /**
     *
     * @return
     */
    private static void loadConfigurationManagerSettings () throws Exception {

        InputStream stream = ConfigurationManager.class.getClassLoader ().getResourceAsStream("bedrock-config.xml");

        if (stream != null) {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance ().newDocumentBuilder ();

            Document bedrockConfigDocument =  builder.parse (stream);

            Element bedrockConfigElement = bedrockConfigDocument.getDocumentElement ();

            NodeList childNodes = bedrockConfigElement.getChildNodes ();

            for (int i = 0; i < childNodes.getLength (); i++) {
                Node childNode = childNodes.item (i);

                if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element childElement = Element.class.cast (childNode);

                    if (childElement.getNodeName ().equals ("storage-provider")) {

                        globalSettings = getConfigurationSettings (childElement, new ConfigurationSettings ());

                    } else if (childElement.getNodeName ().equals ("configuration")) {


                        try {

                            String className = childElement.getAttribute ("class");
                            Class<?> clazz   = Class.forName (className);

                            NodeList storageProviderElements = childElement.getElementsByTagName ("storage-provider");

                            if (storageProviderElements.getLength () > 0) {
                                Element storageProviderElement = Element.class.cast (storageProviderElements.item (0));

                                configurationClassSettings.put (clazz, getConfigurationSettings (storageProviderElement, globalSettings));
                            }

                        } catch (final ClassNotFoundException e) {
                            System.out.println (e);
                        }
                    }
                }
            }
        }
    }

    private static ConfigurationSettings getConfigurationSettings (Element storageManagerElement, ConfigurationSettings defaultValues) {
        ConfigurationSettings settings = new ConfigurationSettings (defaultValues);

        if (storageManagerElement.hasAttribute ("class")) {
            settings.storageProvider = storageManagerElement.getAttribute ("class");
        }

        NodeList propertyElements = storageManagerElement.getElementsByTagName ("property");
        for (int i = 0; i < propertyElements.getLength (); i++) {

            Node childNode = propertyElements.item (i);

            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                Element propertyElement = Element.class.cast (childNode);

                String key   = propertyElement.getAttribute ("name");
                String value = propertyElement.getAttribute ("value");

                settings.properties.put (key,value);
            }
        }
        return settings;
    }
}
