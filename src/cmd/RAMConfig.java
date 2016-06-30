package cmd;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class RAMConfig {
	
	static final String RESPONSIBLE_CHRAR = "R";
	static final String ACCOUNTABLE_CHRAR = "A";
	static final String CONSULT_CHRAR = "C";
	static final String INFORM_CHRAR = "I";
	static final String SUPPORT_CHRAR = "S";
	static final String DELIMETER_CHRA = ",";
	
	public static  List<String> getPropValue(String propName){
		 List<String> values = null;
		 Map<String, String> configMap =  getConfigList();
		  for (Map.Entry<String, String> entry : configMap.entrySet()) {
			  String key = entry.getKey();
			  String value =  entry.getValue();
			  if(key.equals(propName)){
				 String[] valuesArr = value.split(RAMConfig.DELIMETER_CHRA);
				 values = Arrays.asList(valuesArr);
				  break;
			  }
		  }
		  return values;
	}
	
	
	public static boolean isTaskNameStarsWith(List<String> list , String taskName){
		boolean startsWith = false;
		for(String item : list){
			if(taskName.startsWith(item)){
				startsWith = true;
			}
		}
		return startsWith;
		
	}
	public static  Map<String, String> getConfigList(){
		Map<String, String> configMap = new HashMap<String, String>();
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream("config/config.properties");

			// load a properties file
			prop.load(input);

			configMap = new HashMap<String, String>();
			
			 Enumeration<Object> keys = prop.keys();
			 while(keys.hasMoreElements()){
				 String key = (String)keys.nextElement();
				 String value = (String) prop.getProperty(key);
				 configMap.put(key, value);
			 }
			 

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return configMap;

	}

}
