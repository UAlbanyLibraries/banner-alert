import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

public class Config {
	protected static Properties properties = null;
	protected static Date lastUpdated = null;
	static Logger log = Logger.getLogger(Config.class.getName());
	
	/**
	 * loads system properties from file
	 */
	private static void loadProperties() {
		try {
			properties = new Properties();
			properties.load(Config.class.getResourceAsStream("config.properties"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Retrieves a property value for a given key.
	 * 
	 * If properties are not yet loaded, loadProperties() is triggered before retrieving the value.
	 * 
	 * @param key - key to retrieve from properties
	 * @return value associated with given key
	 */
	public static String getValue(String key) {
		if (properties == null) {
			updateProperties();
			loadProperties();
		}
		
		return properties.getProperty(key);
	}
	
	/**
	 * Retrieves a list of property keys.
	 */
	public static Set<Object> getKeys() {
		if (properties == null) {
			updateProperties();
			loadProperties();
		}
		
		return properties.keySet();
	}
	
	/**
	 * updates Config properties object and rewrites properties load file.
	 * properties are then reloaded to Config.properties.
	 * 
	 * @return boolean signals success or failure of update process
	 */
	public static boolean updateProperties() {
		boolean success = false;
		
		String fileLocation = System.getProperty("catalina.base") + "/libutil-generic.properties";
		File newProperties = new File(fileLocation);
		
		if (newProperties.exists() && newProperties.canRead()) {
			try {
				StringBuilder sb = new StringBuilder();
				
				sb.append("lastUpdated=" + new Date().toString());
				sb.append(System.lineSeparator());
				sb.append(System.lineSeparator());
				
				BufferedReader br = new BufferedReader(new FileReader(newProperties));
				
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line + System.lineSeparator());
				}
				
				br.close();
				
				
				PrintWriter pw = new PrintWriter(new File(Config.class.getResource("config.properties").toURI()));
				pw.print(sb.toString());
				pw.close();
				
				success = true;
			}
			catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		}
		
		return success;
	}
	
	/**
	 * 
	 * @param string
	 * @return Property value parsed as an Integer object
	 */
	public static int getIntValue(String string) {
		return Integer.parseInt(getValue(string));
	}
}
