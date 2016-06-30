package cmd;

import java.awt.Color;
import java.awt.Component;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class RAMConfig_old {

	  Map<String, String> configMap = new HashMap<String, String>();
	 int maxHeight = 50;
	public void start(){
		 final JFrame frame = new JFrame("Confic RAM");
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 frame.setBounds(100, 100, 400, 700);
		 frame.setLayout(null);
		 
		 JLabel label = null;
		 JTextField text = null;
		
		    configMap =  getConfigList();
		 
		 for(int i = 0 ; i < 4 ; i++){
			 
			 String key = "";
			 String value = ""; 
			 if(i == 0){
				 key = "A";
				 value = getPropValue(key);
			 }else if(i == 1){
				 key = "I";
				 value = getPropValue(key);
			 }else if(i == 2){
				 key = "S";
				 value = getPropValue(key);
			 }else if(i == 3){
				 key = "C";
				 value = getPropValue(key);
			 }
			    label = new JLabel(key);
			    label.setBounds(20, 20+i*50, 100, 30);
			    frame.add(label);
			    
			    text = new JTextField(value);
			    text.setBounds(150, 20+i*50, 100, 30);
			    frame.add(text);
			    
			    maxHeight = maxHeight+50;
		 }
		  
		  
		  JButton newElementBtn = new JButton("Add..");
		  newElementBtn.setBounds(50, maxHeight, 100, 30);
		  frame.add(newElementBtn);
		  
		  final JButton saveElementBtn = new JButton("Save..");
		  saveElementBtn.setBounds(150, maxHeight + 50, 100, 30);
		  frame.add(saveElementBtn);
		  
		  
		  newElementBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			     JTextField  label = new JTextField();
			    label.setBounds(20, 50+maxHeight, 100, 30);
			    frame.add(label);
			    
			    JTextField  text = new JTextField();
			    text.setBounds(150, 50+maxHeight, 100, 30);
			    frame.add(text);
			    
			    JButton btn = new JButton("X");
			    btn.setForeground(Color.red);
			    btn.setBounds(250, 50+maxHeight, 50, 30);
			    frame.add(btn);
				
			    maxHeight = maxHeight+50;
			    
			    saveElementBtn.setBounds(150, maxHeight + 50, 100, 30);
			    
			    frame.repaint();
			}
		});
		  
		  
		final  ArrayList<JTextField> texts = new ArrayList<JTextField>();
		final  ArrayList<JLabel> labels = new ArrayList<JLabel>();
		  saveElementBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			Component[] comps = 	frame.getComponents();
			 
			for(int x = 0 ; x < comps.length ; x++){
				Component c = comps[x];
				if(c instanceof JLabel){
					labels.add((JLabel)c); 
				}else if(c instanceof JTextField){
					texts.add((JTextField)c);
				}
			}
				
			int c = 0;
			for(JLabel label : labels){
				System.out.println(label.getText());
				configMap.put(label.getText(), texts.get(c).getText());
				c++;
			}
			
			saveConfig();
			
			}		
		});
		
			  
		  frame.setVisible(true);
		  frame.repaint();
		  
	}
	
	
	public String getPropValue(String propName){
		String val = null;
		 Map<String, String> configMap =  getConfigList();
		  for (Map.Entry<String, String> entry : configMap.entrySet()) {
			  String key = entry.getKey();
			  String value =  entry.getValue();
			  if(key.equals(propName)){
				  val = value;
				  break;
			  }
			    
		  }
		  return val;
	}
	
	public void start1(){
		
		configMap.put("A", "aaaaaaaaa7777");
		configMap.put("I", "iiiiiiiii");
		configMap.put("C", "ccccccccc*dddddd*ssssss");
		configMap.put("S", "ssssssss");
		
		saveConfig();
		
		Map<String, String> configMap =  getConfigList();
		  for (Map.Entry<String, String> entry : configMap.entrySet()) {
			    String key = entry.getKey();
			    String value =  entry.getValue();
			   System.out.println(key +" = "+value);
			}
	}
	
	
	 
	
	public Map<String, String> getConfigList(){
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
	
	public void saveConfig(){
		Properties prop = new Properties();
		OutputStream output = null;
		try {

			output = new FileOutputStream("config/config.properties");

			// set the properties value
			for(int i = 0 ; i < configMap.size() ; i++){
				
			}
			
			for (Map.Entry<String, String> entry : configMap.entrySet()) {
			    String key = entry.getKey();
			    String value =  entry.getValue();
			    prop.setProperty(key, value);
			}
			 

			// save properties to project root folder
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}
	
	public static void main(String[] args) {
		new RAMConfig_old().start();
	}
}
