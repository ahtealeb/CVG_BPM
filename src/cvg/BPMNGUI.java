/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cvg;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import cmd.RACI;

/**
 *
 * @author mahmoud
 */
public class BPMNGUI {
    
    
    public static void main(String[] args){
        BPMNGUI gui = new BPMNGUI();
        //gui.doPlymorphismGui();
        gui.start();
    }
    
    public void start(){
         final JFrame frame = new JFrame("Organizational Structure Variant Generator For BPM");
            frame.setLayout(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setBounds(500, 100, 500, 500);
            frame.setVisible(true);
            
            
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("./photo/OrgStructure.jpg")).getImage().getScaledInstance(400, 100, Image.SCALE_DEFAULT));
        	JLabel picLabel = new  JLabel();
        	picLabel.setIcon(imageIcon);
            picLabel.setBounds(50, 30, 400, 100);
            frame.add(picLabel);
            
            JButton abstractBtn = new JButton("Org. Approval Process VG");
            abstractBtn.setBounds(150, 200, 250, 30);
            frame.add(abstractBtn);
            
            JButton polymorphismBtn = new JButton("Polymorphism Process VG");
            polymorphismBtn.setBounds(150, 300, 250, 30);
            frame.add(polymorphismBtn);
            
            /*
            JButton ramBtn = new JButton("RAM Process VG");
            ramBtn.setBounds(150, 300, 250, 30);
            frame.add(ramBtn);
            */
            abstractBtn.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent ae) {
                 doAbstractGui();
             }
            });
             
            polymorphismBtn.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent ae) {
                 doPlymorphismGui();
             }
            });
            
           /* ramBtn.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent arg0) {
					openRAMGenerator();
				}
			});*/
            
            JButton closeBtn = new JButton("Close");
            closeBtn.setBounds(350, 420, 100, 30);
            frame.add(closeBtn);
            closeBtn.addActionListener(new ActionListener() {
			 
				public void actionPerformed(ActionEvent arg0) {
			     	//System.exit(0);
					frame.setVisible(false);
					
				}
			});
            
            frame.setResizable(false);  
    }
    
    
     public void doAbstractGui(){
           final JFrame frame = new JFrame("Org. Structure Process VG");
            frame.setLayout(null);
         //   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setBounds(500, 100, 500, 500);
            frame.setResizable(false);  
            
            
          final  JFileChooser filechooser = new JFileChooser();
            JButton showFileDialogButton = new JButton("Open File");
            showFileDialogButton.setBounds(20, 100, 100, 30);
            frame.add(showFileDialogButton);
            
            
             final  JTextField text = new JTextField();
            text.setBounds(150, 100, 180, 30);
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
            
            
         
            
            JButton loadFileButton = new JButton("Load File");
            loadFileButton.setBounds(150, 150, 180, 30);
            frame.add(loadFileButton);
            
            
            
             JLabel abstractLabel = new JLabel("Abstract Role");
            abstractLabel.setBounds(25, 200, 180, 30);
            frame.add(abstractLabel);
            
          
           final  JTextField abstractCombo = new JTextField();
            abstractCombo.setBounds(150, 200, 180, 30);
            frame.add(abstractCombo);
            
            loadFileButton.addActionListener(new ActionListener() {

              
                public void actionPerformed(ActionEvent ae) {
                         Main main = new Main();
                         ArrayList<String> fragmentsAL = main.getFragmentsNamesList(text.getText());
                       for(String name : fragmentsAL){
                    	     if(name.contains("Abstract")){
                               abstractCombo.setText(name);
                               abstractCombo.setEnabled(false);
                    	     }
                       }
                }
            });
             
            
           
            
            JButton parrallelBtn = new JButton("Parallel Approval");
            parrallelBtn.setBounds(150, 300, 250, 30);
            frame.add(parrallelBtn);
            
             JButton sequentialBtn = new JButton("Sequential Approval");
            sequentialBtn.setBounds(150, 250, 250, 30);
            frame.add(sequentialBtn);
            
              
            
            frame. repaint();
            
            
            frame. repaint();
            sequentialBtn.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent ae) {
                 Main m = new Main();
                 String srcFile = text.getText();
                 String abstractLane =  (String)abstractCombo.getText();
                 m.doSequentialFlow(srcFile ,abstractLane);
                 JOptionPane.showMessageDialog(frame, "Done");
             }
         });
            
            parrallelBtn.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent ae) {
                 Main m = new Main();
                 String srcFile = text.getText();
                 String abstractLane =  (String)abstractCombo.getText();
                 m.doParallelFlow(srcFile,abstractLane);
                 JOptionPane.showMessageDialog(frame, "Done");
             }
         });
            
            JButton closeBtn = new JButton("Back");
            closeBtn.setBounds(350, 420, 100, 30);
            frame.add(closeBtn);
            closeBtn.addActionListener(new ActionListener() {
				
			 
				public void actionPerformed(ActionEvent arg0) {
			     	frame.setVisible(false);	
					
				}
			});
            
          frame.setVisible(true);  
        }
     
     
      
     public void doPlymorphismGui(){
           final JFrame frame = new JFrame("Polymorphism Process VG");
            frame.setLayout(null);
            //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setBounds(500, 100, 500, 500);
            frame.setResizable(false);  
            
          final  JFileChooser filechooser = new JFileChooser();
            JButton showFileDialogButton = new JButton("Open File");
            showFileDialogButton.setBounds(20, 100, 100, 30);
            frame.add(showFileDialogButton);
            
            
            final  JTextField srcFileText = new JTextField();
            srcFileText.setBounds(150, 100, 180, 30);
            frame.add(srcFileText);
            
            showFileDialogButton.addActionListener(new ActionListener() {

             
                public void actionPerformed(ActionEvent ae) {
                     int returnVal = filechooser.showOpenDialog(frame);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                       java.io.File file = filechooser.getSelectedFile();
                       srcFileText.setText(file.getAbsolutePath());
                    }
                    else{
                       srcFileText.setText("");           
                    }      
                }
            });
            
            JButton loadFileButton = new JButton("Load File");
            loadFileButton.setBounds(150, 150, 180, 30);
            frame.add(loadFileButton);
            
            
             JLabel abstractLabel = new JLabel("Abstract Role");
            abstractLabel.setBounds(25, 200, 180, 30);
            frame.add(abstractLabel);
            
          
           final  JComboBox abstractCombo = new JComboBox();
            abstractCombo.setBounds(150, 200, 180, 30);
            frame.add(abstractCombo);
            
            
            loadFileButton.addActionListener(new ActionListener() {

          
               public void actionPerformed(ActionEvent ae) {
                        Main main = new Main();
                        ArrayList<String> fragmentsAL = main.getFragmentsNamesList(srcFileText.getText());
                      for(String name : fragmentsAL){
                          abstractCombo.addItem(name);
                      }
               }
           });
            
       
            
            
            
            JLabel noOfConcreteLabel = new JLabel("No. of Concrete Roles");
            noOfConcreteLabel.setBounds(25, 250, 180, 30);
            frame.add(noOfConcreteLabel);
            
            String[] concretes = { "Select", "1", "2", "3", "4"};
            final JComboBox concreteCombo = new JComboBox(concretes);
            concreteCombo.setBounds(150, 255, 180, 30);
            frame.add(concreteCombo);
            concreteCombo.addItemListener(new ItemListener() {

              
               public void itemStateChanged(ItemEvent ie) {
                   if(ie.getStateChange() == ItemEvent.SELECTED){
                       Component[] comps =  frame.getContentPane().getComponents();
                       for(Component c : comps){
                           if(c.getName() != null && (c.getName().contains("dynamicLabel") || c.getName().contains("dynamicText"))){
                               frame.remove(c);
                           }
                       }
                       
                     //  frame.revalidate();
                       frame.repaint();
                             
                       String noOfConcretesStr = (String)concreteCombo.getSelectedItem();
                       int y = 290;
                       JLabel label;
                       JTextField text;
                       int noOfConcretes; 
                       try {
                           noOfConcretes = Integer.valueOf(noOfConcretesStr);
                       }catch(Exception e){noOfConcretes = 0;}
                       for(int i = 1; i <=  Integer.valueOf(noOfConcretes) ; i++){
                            label = new JLabel("Concrete "+i);
                            label.setBounds(50, y, 100, 30);
                            label.setName("dynamicLabel"+i);
                            frame.add(label);

                            frame.validate();
                            frame.repaint();

                            text = new JTextField();
                            text.setName("dynamicText"+i);
                            text.setBounds(150,y,200,30);
                            frame.add(text);
                             y+= 30;
                       }
                       
                     
                   }
                       
               }
           });
            
            JButton button = new JButton("Proceesd");
            button.setBounds(150, 420, 180, 30);
            button.addActionListener(new ActionListener() {

           
               public void actionPerformed(ActionEvent ae) {
                    Main m = new Main();
                    String srcFile = srcFileText.getText();
                    String srcFileWithoutExtension = srcFile.substring(0,srcFile.lastIndexOf("."));
                     Component[] comps =  frame.getContentPane().getComponents();
                       for(Component c : comps){
                           if(c.getName() != null &&  c.getName().contains("dynamicText")){
                               JTextField tf = (JTextField)c;
                               String concreteLane = tf.getText();
                               String destFile = srcFileWithoutExtension+"_"+concreteLane+".bpmn";
                               String abstractLane =  (String)abstractCombo.getSelectedItem();
                               m.doPolymorphismFlow(srcFile, destFile, concreteLane, abstractLane);
                               JOptionPane.showMessageDialog(frame, "Done");
                           }
                       }
                  
                    
               }
           });
            frame.add(button);
            
         
            
            
            JButton closeBtn = new JButton("Back");
            closeBtn.setBounds(350, 420, 100, 30);
            frame.add(closeBtn);
            closeBtn.addActionListener(new ActionListener() {
				
		 
				public void actionPerformed(ActionEvent arg0) {
			     	frame.setVisible(false);	
					
				}
			});
            frame.setVisible(true);
        }
     
     
     public void openRAMGenerator(){
    	 new RACI();
     }
     
}
