package cmd;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import cvg.Fragment;
import cvg.Node;
import cvg.ParseXML;

public class RACI {
	
	public static ArrayList<String> subProcessesList = new ArrayList<String>();
	public static ArrayList<JTextField> subProcesseTextFieldPaths = new ArrayList<JTextField>();
	public static String mainFilePath = "";
	//String file="D:\\tealeb\\new work\\workspace\\CVG_BPM\\src\\cmd\\raci_2.bpmn";
     
	public static void main(String[] args) {
		new RACI();
	}
	
	static List<String> rValues = null;
	static List<String> aValues = null;
	static List<String> cValues = null;
	static List<String> iValues = null;
	static List<String> sValues = null;
	 
	public RACI() {
		final JFrame frame = new JFrame("RAM-Based Variant Generator For BPM");
		
		frame.setResizable(false);  
		frame.setSize(900, 700);
		frame.setLocation(200, 100);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		  try {
          	
          	ImageIcon imageIcon = new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("./photo/raci2.jpg")).getImage().getScaledInstance(340, 200, Image.SCALE_DEFAULT));
          	JLabel picLabel = new  JLabel();
          	picLabel.setIcon(imageIcon);
	            picLabel.setBounds(560, 0, 340, 200);
	            frame.add(picLabel);
          }catch(Exception e){
          	e.printStackTrace();
          }
		
		  
		  
		  try {
					rValues = RAMConfig.getPropValue(RAMConfig.RESPONSIBLE_CHRAR);
					aValues = RAMConfig.getPropValue(RAMConfig.ACCOUNTABLE_CHRAR);
					cValues = RAMConfig.getPropValue(RAMConfig.CONSULT_CHRAR);
					iValues = RAMConfig.getPropValue(RAMConfig.INFORM_CHRAR);
					sValues = RAMConfig.getPropValue(RAMConfig.SUPPORT_CHRAR);
					if(rValues == null || rValues.size() == 0){
						throw new Exception("R value not defined");
					}
					if(aValues == null || aValues.size() == 0){
						throw new Exception("A value not defined");
					}
					if(cValues == null || cValues.size() == 0){
						throw new Exception("C value not defined");
					}
					if(iValues == null || iValues.size() == 0){
						throw new Exception("I value not defined");
					}
					if(sValues == null || sValues.size() == 0){
						throw new Exception("S value not defined");
					}
				}catch(Exception e){
					//e.printStackTrace();
					JOptionPane.showMessageDialog(frame, e.getMessage());
					System.exit(0);
				}
				
				 
							JLabel title = new JLabel("RAM");
							title.setBounds(250, 10, 100, 30);
							frame.add(title);
							
							   
							//  final  JFileChooser filechooser = new JFileChooser("C:\\Users\\mahmood\\Documents\\new files");
							final  JFileChooser filechooser = new JFileChooser("D:\\tealeb\\RACI-BP Models files");
							 
							
					          JButton showFileDialogButton = new JButton("Open File");
					          showFileDialogButton.setBounds(20, 40, 100, 30);
					          frame.add(showFileDialogButton);
					          
					          final  JTextField text = new JTextField();
					          text.setBounds(150, 40, 250, 30);
					          frame.add(text);
					          
					          
					          showFileDialogButton.addActionListener(new ActionListener() {
					        	  public void actionPerformed(ActionEvent ae) {
					                   int returnVal = filechooser.showOpenDialog(frame);
							          if (returnVal == JFileChooser.APPROVE_OPTION) {
							             java.io.File file = filechooser.getSelectedFile();
							             text.setText(file.getAbsolutePath());
							          }
							          else{
							             text.setText("");           
							          }      
					              }
					          });
					          
					          
					          JButton loadRAMBtn = new JButton("Load File");
					          loadRAMBtn.setBounds(450, 40, 100, 30);
					          frame.add(loadRAMBtn);
					        
					         
					          loadRAMBtn.addActionListener(new ActionListener() {
					        	
								public void actionPerformed(ActionEvent e) {
									
									  getTasks( text.getText() , 0);
									  mainFilePath = text.getText();
									 if(subProcessesList.size() > 0){
										    int subProcessCount = 0;
										  for(String subProcess : subProcessesList){
											  subProcessCount++;
											  //final  JFileChooser filechooser = new JFileChooser("C:\\Users\\mahmood\\Documents\\new files");
											  final  JFileChooser filechooser = new JFileChooser("D:\\tealeb\\RACI-BP Models files");
									          JButton showFileDialogButton = new JButton(subProcess+" File");
									          showFileDialogButton.setBounds(20, 40+subProcessCount*50, 200, 30);
									          frame.add(showFileDialogButton);
									          
									          final  JTextField text = new JTextField();
									          text.setBounds(230, 40+subProcessCount*50, 250, 30);
									          frame.add(text);
									          subProcesseTextFieldPaths.add(text);
									          
									          showFileDialogButton.addActionListener(new ActionListener() {
									        	  public void actionPerformed(ActionEvent ae) {
									                   int returnVal = filechooser.showOpenDialog(frame);
											          if (returnVal == JFileChooser.APPROVE_OPTION) {
											             java.io.File file = filechooser.getSelectedFile();
											             text.setText(file.getAbsolutePath());
											          }
											          else{
											             text.setText("");           
											          }      
									              }
									          });
										  }
										  
									  } 
									 
									  JButton genrateRAMBtn = new JButton("Extract RAM");
									 // System.out.println("***** = "+subProcessesList == null ? 0  : subProcessesList.size()*100);
								         genrateRAMBtn.setBounds(120, subProcessesList == null ? 0 : (100 + subProcessesList.size()*50), 170, 30);
								         frame.add(genrateRAMBtn);
								         genrateRAMBtn.addActionListener(new ActionListener() {
											
											public void actionPerformed(ActionEvent e) {
												boolean stopped = false;
												for(int  i = 0 ; i < subProcesseTextFieldPaths.size() ; i++){
													if(subProcesseTextFieldPaths.get(i).getText().trim().equals("")){
														JOptionPane.showMessageDialog(frame, "Insert all sub files");
														stopped = true;
														break;
													}
												}
												
												if(!stopped) drawRACITable(frame,  text.getText()  , subProcesseTextFieldPaths);
												
											}
										});
								         
								            JButton exitBtn = new JButton("Back");
								            exitBtn.setBounds(320, subProcessesList == null ? 0 : (100 + subProcessesList.size()*50), 170, 30);
										    frame.add(exitBtn);
										    exitBtn.addActionListener(new  ActionListener() {
												
												public void actionPerformed(ActionEvent arg0) {
													
													//System.exit(0);
													frame.setVisible(false);
													
												}
											});
								          
										    frame.repaint();
								}
								
								
							});
					          
					          
		          
				 
          frame.setVisible(true); 
          frame.repaint();
         
	}
	
	
	
	static ArrayList<Task> allTasks = null;
	public  static void drawRACITable(final JFrame frame , final String  file , final ArrayList<JTextField> subFiles){
		  ArrayList<Role> roles = getRoles(file);
		  ArrayList<Role> duplicatedRoles = new ArrayList<Role>();
		     if(subFiles != null && subFiles.size() > 0){
			 for(int i = 0 ; i < subFiles.size() ; i++){
						ArrayList<Role> subRoles =  getRoles(subFiles.get(i).getText());
						for(int x = 0 ; x < subRoles.size() ; x++){
							if(! roles.contains(subRoles.get(x))){
								Role r = subRoles.get(x);
								roles.add(r);
							}else{
								Role r = subRoles.get(x);
								duplicatedRoles.add(r);
							}
						}
				 }
			  } 
	 	 
		  ArrayList<Task> tasks = getTasks(file , 0);
		  if(subFiles != null && subFiles.size() > 0){
			  for(int i = 0 ; i < subFiles.size() ; i++){
				  tasks.addAll(getTasks(subFiles.get(i).getText() , i+1));
			 }
		  } 
		  allTasks = tasks;
		  
		    Object columnNames[] = new Object[roles.size()+1];
		    int index = 0;
		    columnNames[index] = "Roles \\ Tasks";
		    for(Role role : roles){
		    	columnNames[index+1] = role.getRoleName();
		    	index++;
		    }
		     
		    int tasksSize = 0;
		    for(int i = 0 ; i < tasks.size() ; i++){
		    	if(tasks.get(i).getTaskFileId() == 0){
		    		tasksSize++;
		    	}
		    }
		    
		    
		    DefaultTableModel model = new DefaultTableModel(columnNames ,tasks.size());
		    
		    JTable table = new JTable(model);
		    for(int i = 0 ; i < tasks.size() ; i++){
		    	if(tasks.get(i).getTaskFileId() == 0){
		    		table.setValueAt(tasks.get(i).getTaskName() , i, 0);
		    	}
		    }
		    
		    for(int x = 0 ; x < roles.size()   ; x++){
		    	ArrayList<Task> tasksInRole = getRoleTasks(roles.get(x).getRoleFile(), roles.get(x).getRoleName());
		        if(duplicatedRoles.contains(roles.get(x))){
		        	for(int y = 0;  y < duplicatedRoles.size() ; y++){
		        		if(duplicatedRoles.get(y).getRoleName().equals(roles.get(x).getRoleName())){
		        			tasksInRole.addAll(getRoleTasks(duplicatedRoles.get(y).getRoleFile(), duplicatedRoles.get(y).getRoleName()));
		        		}
		        	}
		        }
		    	
		        
		    	 for(int y = 0 ; y < tasks.size() ; y++){
		    		 if(tasksInRole.contains(tasks.get(y))){
		    			 table.setValueAt("R" , y, x+1);
		    			 String taskName = tasks.get(y).getTaskName();
		    			//String str = "Approve Task "+taskName;
		    			  
		    			 String str0 = aValues.size() > 0 ? aValues.get(0) +  " " +taskName : null;
		    			 String str1 = aValues.size() > 1 ? aValues.get(1) +  " " +taskName : null;
		    			 String str2 = aValues.size() > 2 ? aValues.get(2) +  " " +taskName : null;
		    			 String str3 = aValues.size() > 3 ? aValues.get(3) +  " " +taskName : null;
		    			 String str4 = aValues.size() > 4 ? aValues.get(4) +  " " +taskName : null;
		    			 
		    			/* List<String> aStr = new ArrayList<String>();
		    			 for(String item : aValues){
		    				 String str = item+" "+taskName;
		    				 aStr.add(str);
		    			 }*/
		    			
		    			 for(int z = 0 ; z < tasks.size() ; z++){
					    	if(    (str0 != null && str0.equals(tasks.get(z).getTaskName()))
					    		|| (str1 != null && str1.equals(tasks.get(z).getTaskName()))
					    		|| (str2 != null && str2.equals(tasks.get(z).getTaskName()))
					    		|| (str3 != null && str3.equals(tasks.get(z).getTaskName()))
					    		|| (str4 != null && str4.equals(tasks.get(z).getTaskName()))){
			    				// if(RAMConfig.isTaskNameStarsWith( aStr , taskName )){
			    			
					    				 table.setValueAt("R" , y, x+1);
					    				 int approveTaskColoumn = 10000;
					    				  for(int c = 1 ; c <= roles.size()   ; c++){
					    				    	String val = (String)table.getValueAt(y, c);
					    				    	if(val != null && val.contains("R")){
					    				    		approveTaskColoumn = c+1;
					    				    	}
					    				  }
					    				 table.setValueAt("A" , y, approveTaskColoumn);
					    				 break;
					    	   }else{
					    				 table.setValueAt("R/A" , y, x+1);
					    	   }
		    			 }
		    			 
		    			 //if(taskName.startsWith("Provide Info")){	 
		    			 // if(taskName.startsWith("Provide Consult")){
		    			 if(RAMConfig.isTaskNameStarsWith( cValues , taskName )){
		    				 String afterForTask = taskName.substring(taskName.lastIndexOf("for")+4 , taskName.length());
		    				 int roleCol = 10000;
		    				 for(int c = 1 ; c <=  roles.size()   ; c++){
		    				    	String val = (String)table.getValueAt(y, c);
		    				    	if(val != null && val.contains("R")){
		    				    		roleCol = c;
		    				    	}
		    				  }
		    				 
		    				
		    				 for(int z = 0 ; z < tasks.size() ; z++){
		    					 if(afterForTask.equals(tasks.get(z).getTaskName())){
		    						 table.setValueAt("C", z, roleCol);
		    					 }
		    				 }
		    			 
		    			 }
		    			 
		    			 
		    			 
		    			// if(taskName.contains("Inform")){
		    			 if(RAMConfig.isTaskNameStarsWith( iValues , taskName )){
				    			
		    				 String[] patterns = taskName.split(" ");
		    				 String roleName = patterns[1];
		    				 int roleCol = 10000;
		    				 for(int c = 0 ; c <  roles.size()   ; c++){
		    				    	if(roles.get(c).getRoleName().contains(roleName)){
		    				    		roleCol = c+1;
		    				    		table.setValueAt("  I", y, roleCol);
		    				    	}
		    				  }
		    			 }
		    			 
		    			// if(taskName.contains("Provide") && taskName.contains("Support")){
		    			 if(RAMConfig.isTaskNameStarsWith( sValues , taskName)){
		    				 String[] patterns = taskName.split(" ");
		    				   String roleName = patterns[3]+" "+patterns[4];
		    				 int roleCol = 0;
		    				   for(int c = 0 ; c <  roles.size()   ; c++){
		    				    	if(roles.get(c).getRoleName().equals(roleName)){
		    				    		roleCol = c+1;
		    				    	} 
		    				    	
		    				  }
		    				 String afterForTask = taskName.substring(taskName.lastIndexOf("for")+4 , taskName.length());
		    				 for(int z = 0 ; z < tasks.size() ; z++){
		    					 if(afterForTask.equals(tasks.get(z).getTaskName())){
		    						 table.setValueAt("S", z, roleCol);
		    					 }
		    				 }
 
		    			 }
		    			 
		    		 }
		    	 }
		    	
		    }
		    
		   
		    
		   // System.out.println("***** = "+subProcessesList == null ? 0  : subProcessesList.size()*100);
		   int taskDiff = tasks.size() - tasksSize;
		    int tableRowCount = table.getRowCount();
		    for(int f = 1 ; f <= taskDiff ; f++){
		    	int r = tableRowCount - f;
		        model.removeRow( r);
		    } 
		    
		   // model.removeRow(9);
		  //  model.removeRow(8);
		    
		    JScrollPane	pane = new JScrollPane(table);
		    pane.setBounds(0, subProcessesList == null ? 0 : subProcessesList.size()*50 + 100, roles.size()*200 , tasksSize*22);
		    frame.add(pane);
		    
		    
		    JButton updateBtn = new JButton("Update RAM");
		    
		    updateBtn.setBounds(250, pane.getHeight() + 350, 150 , 30);
		    frame.add(updateBtn);
		    updateBtn.addActionListener(new  ActionListener() {
				
				public void actionPerformed(ActionEvent arg0) {
					updateRAM( file , subFiles);
					//frame.setVisible(false);
					
				}
			});
		    
		  
		    
		 
		   frame.repaint();
	}
	
	public static void updateRAM(final String file , ArrayList<JTextField> subFiles){
		
		 final ArrayList<Role> roles = getRoles(file);
		  ArrayList<Role> duplicatedRoles = new ArrayList<Role>();
		     if(subFiles != null && subFiles.size() > 0){
			 for(int i = 0 ; i < subFiles.size() ; i++){
						ArrayList<Role> subRoles =  getRoles(subFiles.get(i).getText());
						for(int x = 0 ; x < subRoles.size() ; x++){
							if(! roles.contains(subRoles.get(x))){
								Role r = subRoles.get(x);
								roles.add(r);
							}else{
								Role r = subRoles.get(x);
								duplicatedRoles.add(r);
							}
						}
				 }
			  } 
	 	 
		  ArrayList<Task> tasks = getTasks(file , 0);
		  if(subFiles != null && subFiles.size() > 0){
			  for(int i = 0 ; i < subFiles.size() ; i++){
				  tasks.addAll(getTasks(subFiles.get(i).getText() , i+1));
			 }
		  } 
		 
		  
		    Object columnNames[] = new Object[roles.size()+2];
		    int index = 0;
		    columnNames[index] = "Roles \\ Tasks";
		    for(Role role : roles){
		    	columnNames[index+1] = role.getRoleName();
		    	index++;
		    }
		     
		   columnNames[++index] = "Result";
		    final int colsIndex = index;
		    
		    int tasksSize = 0;
		    for(int i = 0 ; i < tasks.size() ; i++){
		    	if(tasks.get(i).getTaskFileId() == 0){
		    		tasksSize++;
		    	}
		    }
		    
		    
		    final JFrame frame = new JFrame("RAM-Based Variant Generator For BPM");
			frame.setSize(1000, 700);
			frame.setLocation(200, 100);
			frame.setLayout(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			  try {
	            	
	            	ImageIcon imageIcon = new ImageIcon(new ImageIcon(RACI.class.getClassLoader().getResource("./photo/RASCI.png")).getImage().getScaledInstance(400, 100, Image.SCALE_DEFAULT));
	            	JLabel picLabel = new  JLabel();
	            	picLabel.setIcon(imageIcon);
		            picLabel.setBounds(250, 50, 400, 100);
		            frame.add(picLabel);
	            }catch(Exception e){
	            	e.printStackTrace();
	            }
			  
			
			  DefaultTableModel model = new DefaultTableModel(columnNames ,tasks.size());
			   final JTable table = new JTable(model);
			    for(int i = 0 ; i < tasks.size() ; i++){
			    	if(tasks.get(i).getTaskFileId() == 0){
			    		table.setValueAt(tasks.get(i).getTaskName() , i, 0);
			    	}
			    }
			    
			    
			    int cols = table.getColumnCount();
			    
		    	for(int j = 1 ; j < cols - 1  ; j++){
		    		 setUpSportColumn(table, table.getColumnModel().getColumn(j));
		    	}
			    
			    for(int x = 0 ; x < roles.size()   ; x++){
			    	ArrayList<Task> tasksInRole = getRoleTasks(roles.get(x).getRoleFile(), roles.get(x).getRoleName());
			        if(duplicatedRoles.contains(roles.get(x))){
			        	for(int y = 0;  y < duplicatedRoles.size() ; y++){
			        		if(duplicatedRoles.get(y).getRoleName().equals(roles.get(x).getRoleName())){
			        			tasksInRole.addAll(getRoleTasks(duplicatedRoles.get(y).getRoleFile(), duplicatedRoles.get(y).getRoleName()));
			        		}
			        	}
			        }
			    	
			        
			    	 for(int y = 0 ; y < tasks.size() ; y++){
			    		 if(tasksInRole.contains(tasks.get(y))){
			    			 table.setValueAt("R" , y, x+1);
			    			 String taskName = tasks.get(y).getTaskName();
			    			 //String str = "Approve Task "+taskName;
			    			 String str0 = aValues.size() > 0 ? aValues.get(0) +  " " +taskName : null;
			    			 String str1 = aValues.size() > 1 ? aValues.get(1) +  " " +taskName : null;
			    			 String str2 = aValues.size() > 2 ? aValues.get(2) +  " " +taskName : null;
			    			 String str3 = aValues.size() > 3 ? aValues.get(3) +  " " +taskName : null;
			    			 String str4 = aValues.size() > 4 ? aValues.get(4) +  " " +taskName : null;
			    			 
			    			 for(int z = 0 ; z < tasks.size() ; z++){
					    		 
			    					if(    (str0 != null && str0.equals(tasks.get(z).getTaskName()))
								    		|| (str1 != null && str1.equals(tasks.get(z).getTaskName()))
								    		|| (str2 != null && str2.equals(tasks.get(z).getTaskName()))
								    		|| (str3 != null && str3.equals(tasks.get(z).getTaskName()))
								    		|| (str4 != null && str4.equals(tasks.get(z).getTaskName()))){
			    						
					    				 table.setValueAt("R" , y, x+1);
					    				 int approveTaskColoumn = 10000;
					    				  for(int c = 1 ; c <= roles.size()   ; c++){
					    				    	String val = (String)table.getValueAt(y, c);
					    				    	if(val != null && val.contains("R")){
					    				    		approveTaskColoumn = c+1;
					    				    	}
					    				  }
					    				 table.setValueAt("A" , y, approveTaskColoumn);
					    				 break;
					    			 }else{
					    				 table.setValueAt("R/A" , y, x+1);
					    			 }
			    			 }
			    			 
			    			 //if(taskName.startsWith("Provide Info")){	 
			    			 //if(taskName.startsWith("Provide Consult")){
			    			 if(RAMConfig.isTaskNameStarsWith( cValues , taskName )){
			    				 String afterForTask = taskName.substring(taskName.lastIndexOf("for")+4 , taskName.length());
			    				 int roleCol = 10000;
			    				 for(int c = 1 ; c <=  roles.size()   ; c++){
			    				    	String val = (String)table.getValueAt(y, c);
			    				    	if(val != null && val.contains("R")){
			    				    		roleCol = c;
			    				    		 
			    				    	}
			    				  }
			    				 
			    				
			    				 for(int z = 0 ; z < tasks.size() ; z++){
			    					 if(afterForTask.equals(tasks.get(z).getTaskName())){
			    						 table.setValueAt("C", z, roleCol);
			    					 }
			    				 }
			    			 
			    			 }
			    			 
			    			 
			    			 
			    			 //if(taskName.contains("Inform")){
			    			 if(RAMConfig.isTaskNameStarsWith( iValues , taskName )){	
			    				 String[] patterns = taskName.split(" ");
			    				 String roleName = patterns[1];
			    				 int roleCol = 10000;
			    				 for(int c = 0 ; c <  roles.size()   ; c++){
			    				    	if(roles.get(c).getRoleName().contains(roleName)){
			    				    		roleCol = c+1;
			    				    		table.setValueAt("  I", y, roleCol);
			    				    	}
			    				  }
			    			 }
			    			 
			    			// if(taskName.contains("Provide") && taskName.contains("Support")){
			    			 if(RAMConfig.isTaskNameStarsWith( sValues , taskName )){
			    				 String[] patterns = taskName.split(" ");
			    				   String roleName = patterns[3]+" "+patterns[4];
			    				 int roleCol = 0;
			    				   for(int c = 0 ; c <  roles.size()   ; c++){
			    				    	if(roles.get(c).getRoleName().equals(roleName)){
			    				    		roleCol = c+1;
			    				    	} 
			    				    	
			    				  }
			    				 String afterForTask = taskName.substring(taskName.lastIndexOf("for")+4 , taskName.length());
			    				 for(int z = 0 ; z < tasks.size() ; z++){
			    					 if(afterForTask.equals(tasks.get(z).getTaskName())){
			    						 table.setValueAt("S", z, roleCol);
			    					 }
			    				 }
	 
			    			 }
			    			 
			    		 }
			    	 }
			    	
			    }   
			    
			    
			    int taskDiff = tasks.size() - tasksSize;
			    int tableRowCount = table.getRowCount();
			    for(int f = 1 ; f <= taskDiff ; f++){
			    	int r = tableRowCount - f;
			        model.removeRow( r);
			    } 
			    
		    
		    JScrollPane	pane = new JScrollPane(table);
		    pane.setBounds(100, 200, roles.size()*200 , tasksSize*22);
		    frame.add(pane);
		    
		    
		    JButton updateBtn = new JButton("Back");
			  updateBtn.setBounds(150, pane.getHeight() + 200, 152 , 30);
			    frame.add(updateBtn);
			    updateBtn.addActionListener(new  ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						frame.setVisible(false);
					}
				});
			    
			    
			    final JButton generateVPMBtn = new JButton("Generate VPM");
			    final ArrayList<String> newRowsData = new ArrayList<String>();
			    JButton validateBtn = new JButton("Validate RAM");
			    validateBtn.setBounds(350, pane.getHeight() + 200, 150 , 30);
				frame.add(validateBtn);
				    validateBtn.addActionListener(new  ActionListener() {
						
						public void actionPerformed(ActionEvent arg0) {
							
								newRowsData.removeAll(newRowsData);
							
							 int rowCount = table.getRowCount();
							 int colCount = table.getColumnCount();
							 boolean enableVPM = true;
							 for( int i = 0 ; i <= rowCount -1  ; i++){
								 String rowValue = "";
								 for(int j = 0 ; j < colCount   ; j++){
									 
									 String value = 	(String) table.getValueAt(i, j);
									 rowValue += value+"#";
									 
								 }
								 if(rowValue.endsWith("#")){
									 rowValue = rowValue.substring(0 , rowValue.length() -1 );
								 }
								 
								 newRowsData.add(rowValue);

								 // validate
								 String[] rowValueArr = rowValue.split("#");
								 int aCount = 0;
								 int rCount = 0;
								 for(String str : rowValueArr){
									 if("A".equals(str)){
										 aCount++;
									 }
									 
									 else if("R/A".equals(str) ){
										 rCount++;
										 aCount++;
									 }
									 
									 else if("R".equals(str) ){
										 rCount++;
									 }
								 }
								 
								
								 
								if(aCount != 1 || rCount != 1){
									 table.setValueAt("Error", i, colsIndex);
									  final int currentRow = i;									  
									   MyRender render = new MyRender();
									   Component c =  render.getTableCellRendererComponent(table, "test"	, false, false, currentRow, colsIndex);
									   c.setBackground(Color.RED);
									   enableVPM = false;
								}
								 else{
									 table.setValueAt("OK", i, colsIndex);
								 }
							 }
							 
							// JOptionPane.showMessageDialog(frame, rowCount);
							 generateVPMBtn.setEnabled(enableVPM);
							
						}
					});  
		    
				   
				    final ArrayList<String> oldRowsData = new ArrayList<String>();
				    int rowCount = table.getRowCount();
					 int colCount = table.getColumnCount();
					 for( int i = 0 ; i <= rowCount -1  ; i++){
						 String rowValue = "";
						 for(int j = 0 ; j < colCount  ; j++){
							 
							 String value = 	(String) table.getValueAt(i, j);
							 rowValue += value+"#";
							 
						 }
						 if(rowValue.endsWith("#")){
							 rowValue = rowValue.substring(0 , rowValue.length() -1 );
						 }
						 
						 oldRowsData.add(rowValue);
					 }
				    
		    
				   
				    generateVPMBtn.setBounds(550, pane.getHeight() + 200, 150 , 30);
				    generateVPMBtn.setEnabled(false);
					frame.add(generateVPMBtn);
					
					
					//diffs
					generateVPMBtn.addActionListener(new ActionListener() {
						
						public void actionPerformed(ActionEvent e) {
							generateVPMBtn.setEnabled(false);
							final List<RAMDto> changedObjs = new ArrayList<RAMDto>();
							List<RAMDto> oldObjs = new ArrayList<RAMDto>();
							List<RAMDto> newObjs = new ArrayList<RAMDto>();
							for(int i = 0 ; i < oldRowsData.size() ; i++){
								String rowData = oldRowsData.get(i);
								String[] rowDataArr = rowData.split("#");
								String task = rowDataArr[0];
							    for(int j = 1 ; j < rowDataArr.length -1 ; j++){
							    	String cell  = rowDataArr[j];
									RAMDto ramDto = new RAMDto();
									ramDto.setCellValue(cell);
									ramDto.setCol(j);
									ramDto.setRow(i);
									ramDto.setTaskName(task);
									ramDto.setRoleName(roles.get(j-1).getRoleName());
									oldObjs.add(ramDto);
								}
							}
							
							for(int i = 0 ; i < newRowsData.size() ; i++){
								String rowData = newRowsData.get(i);
								String[] rowDataArr = rowData.split("#");
								String task = rowDataArr[0];
							    for(int j = 1 ; j < rowDataArr.length -1  ; j++){
							    	String cell  = rowDataArr[j];
									RAMDto ramDto = new RAMDto();
									ramDto.setCellValue(cell);
									ramDto.setCol(j);
									ramDto.setRow(i);
									ramDto.setTaskName(task);
									ramDto.setRoleName(roles.get(j-1).getRoleName());
									newObjs.add(ramDto);
								}
							}
							
							//System.out.println("oldObjs size = "+oldObjs.size());
							//System.out.println("newObjs size = "+newObjs.size());
							for(int i = 0 ; i < oldObjs.size() ; i++){
								//System.out.println("i = "+i);
								if(oldObjs.get(i).getCellValue().equals(newObjs.get(i).getCellValue())){
									continue;
								}
								RAMDto ramDto = new RAMDto();
								ramDto = newObjs.get(i);
							 	ramDto.setOldCellvalue(oldObjs.get(i).getCellValue());
								changedObjs.add(ramDto);
							}
							 
							//System.out.println(changedObjs.size());
							
							final JFrame changesFrame = new JFrame("Final Changed Records");
							changesFrame.setSize(600, 300);
							changesFrame.setLocation(200, 200);
							changesFrame.setVisible(true);
							changesFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
							changesFrame.setLayout(null);
							
							    String columnNames[] = {"Old Val","New Val" , "Task","Role" , "Operation"};
							    DefaultTableModel model = new DefaultTableModel(columnNames ,changedObjs.size());
							    JTable changesTable = new JTable(model);
							   
							    
								///changesTable.setValueAt("test ", 0, 0);
								
							 
							 	for(int i = 0 ; i < changedObjs.size() ; i++){
							   		
							   		RAMDto changesRamDto = changedObjs.get(i);
							   		
							   		String oldVal = changesRamDto.getOldCellvalue() == null || changesRamDto.getOldCellvalue().trim().equals("null") ? "" : changesRamDto.getOldCellvalue().trim();
							   		String newVal = changesRamDto.getCellValue() == null || changesRamDto.getCellValue().trim().equals("null") ? "" : changesRamDto.getCellValue().trim();
							   		
							   		
							   		
							   		changesTable.setValueAt(oldVal, i, 0);
							   		changesTable.setValueAt(newVal, i, 1);
							   		changesTable.setValueAt(changesRamDto.getTaskName(), i, 2);
							   		changesTable.setValueAt(changesRamDto.getRoleName(), i, 3);
							   		
							   		
							   		String operation = "";
							   		
							   		//System.out.println("new val = '"+newVal+"'");
							   		if(oldVal.equals("") && !(newVal.equals(""))){
							   			operation = "Insert";
							   		}
							   		
							   		
							   		else if(!oldVal.equals("") && (newVal.equals(""))){
							   			operation = "Delete";
							   		}
							   		
							   		
							   		else if(!oldVal.equals("") && !(newVal.equals(""))){
							   			operation = "Update";
							   		}
							   		
							   		 
							   		
							   		changesTable.setValueAt(operation , i, 4);
							   	}
							    
							    JScrollPane	pane = new JScrollPane(changesTable);
							   	pane.setBounds(0,20, changesFrame.getWidth() , changedObjs.size()*30+20 );
							   	changesFrame.add(pane);
							   	
							   	
							   	JButton proceesBtn = new JButton("Proceed");
							    proceesBtn.setBounds(20, pane.getHeight()+20, 100, 30);
							    changesFrame.add(proceesBtn);
							   	proceesBtn.addActionListener(new ActionListener() {
									
									public void actionPerformed(ActionEvent arg0) {
										
										ProceedBusiness business = new ProceedBusiness();
										business.proceed(changedObjs, allTasks, changesFrame, subProcesseTextFieldPaths);
										
									}
								});
							   	changesFrame.repaint();
							 
						}
					});
				    
			frame.setVisible(true);   	    
	}
	
	public static  ArrayList<Role> getRoles(String file){
		ArrayList<Role> roles = new ArrayList<Role>();
		ParseXML parser = new ParseXML();
		ArrayList<Fragment> fragments =  parser.loadAllFragments(file);
		for(Fragment fragment : fragments){
			Role role = new Role();
			role.setRoleFile(file);
			role.setRoleName(fragment.getFragemenrName());
			roles.add(role);
		}
		return roles;
	}
	

	public static  ArrayList<Task> getTasks(String file , int fileIndex){
		ArrayList<Task> tasks = new ArrayList<Task>();
		ParseXML parser = new ParseXML();
		ArrayList<Fragment> fragments =  parser.loadAllFragments(file);
		//System.out.println("**** files = "+file);
		for(Fragment fragment : fragments){
			
			ArrayList<Node> nodesList = fragment.getNodes();
			for(Node node : nodesList){
				if("serviceTask".equals(node.getNodeName()) || "userTask".equals(node.getNodeName()) || "sendTask".equals(node.getNodeName())){
					Task task = new Task();
					task.setTaskName(node.getNodeTitle());
					task.setTaskFile(file);
					task.setTaskFileId(fileIndex);
					tasks.add(task);
				}
				
				if("subProcess".equals(node.getNodeName())){
					subProcessesList.add(node.getNodeTitle());
					Task task = new Task();
					task.setTaskName(node.getNodeTitle());
					task.setTaskFile(file);
					task.setTaskFileId(fileIndex);
					tasks.add(task);
				}
			}
		}
		return tasks;
	}
	
	
	public static  ArrayList<Task> getTasksInFile(String file){
		ArrayList<Task> tasks = new ArrayList<Task>();
		ParseXML parser = new ParseXML();
		ArrayList<Fragment> fragments =  parser.loadAllFragments(file);
		//System.out.println("**** files = "+file);
		for(Fragment fragment : fragments){
			
			ArrayList<Node> nodesList = fragment.getNodes();
			for(Node node : nodesList){
				if("serviceTask".equals(node.getNodeName()) || "userTask".equals(node.getNodeName()) || "sendTask".equals(node.getNodeName()) || "exclusiveGateway".equals(node.getNodeName())){
					Task task = new Task();
					task.setTaskName(node.getNodeTitle());
					task.setTaskFile(file);
					task.setXmlElementId(node.getNodeId());
					tasks.add(task);
				}
				
			}
		}
		return tasks;
	}
	
	
	public static  ArrayList<Task> getSubProcessesInFile(String file){
		ArrayList<Task> tasks = new ArrayList<Task>();
		ParseXML parser = new ParseXML();
		ArrayList<Fragment> fragments =  parser.loadAllFragments(file);
		//System.out.println("**** files = "+file);
		for(Fragment fragment : fragments){
			
			ArrayList<Node> nodesList = fragment.getNodes();
			for(Node node : nodesList){
				if("subProcess".equals(node.getNodeName())){
					Task task = new Task();
					task.setTaskName(node.getNodeTitle());
					task.setTaskFile(file);
					task.setXmlElementId(node.getNodeId());
					task.setNodeContent(node.getNodeContent());
					tasks.add(task);
				}
				
			}
		}
		return tasks;
	}

	
	
	public static  ArrayList<Task> getRoleTasks(String file , String rolename){
		ArrayList<Task> tasks = new ArrayList<Task>();
		ParseXML parser = new ParseXML();
		ArrayList<Fragment> fragments =  parser.loadAllFragments(file);
		for(Fragment fragment : fragments){
			if(rolename.equals(fragment.getFragemenrName())){
				ArrayList<Node> nodesList = fragment.getNodes();
				for(Node node : nodesList){
					 
					if("serviceTask".equals(node.getNodeName()) || "userTask".equals(node.getNodeName()) || "sendTask".equals(node.getNodeName())){
						Task task = new Task();
						task.setTaskName(node.getNodeTitle());
						task.setTaskFile(file);
						tasks.add(task);
					}
					
					  if("subProcess".equals(node.getNodeName())){
						  Task task = new Task();
							task.setTaskName(node.getNodeTitle());
							task.setTaskFile(file);
							tasks.add(task);
					} 
					 
				}
			}
		}
		return tasks;
		
	}
	
	
	 public static  void setUpSportColumn(JTable table,
             TableColumn sportColumn) {
			//Set up the editor for the sport cells.
			JComboBox<String> comboBox = new JComboBox<String>();
			comboBox.addItem("  ");
			comboBox.addItem("R");
			comboBox.addItem("A");
			comboBox.addItem("R/A");
			comboBox.addItem("C");
			comboBox.addItem("I");
			comboBox.addItem("S");
			//comboBox.addItem("VS");
			
			sportColumn.setCellEditor(new DefaultCellEditor(comboBox));
			
			//Set up tool tips for the sport cells.
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
			sportColumn.setCellRenderer(renderer);
			}
	
	
}

 class MyRender extends DefaultTableCellRenderer    {
	 
	private static final long serialVersionUID = 1L;

	public MyRender() {
        setOpaque(true); //MUST do this for background to show up.
    }
 
    public Component getTableCellRendererComponent(
                            JTable table, Object value,
                            boolean isSelected, boolean hasFocus,
                            int row, int column) {
    	
    	Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    	return c;
    }
}
 


 

 
 