package cvg;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import cmd.RACI;

public class Start {

	
	  public void start(){
	         final JFrame frame = new JFrame("Context Variant Generator for BPM");
	            frame.setLayout(null);
	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            frame.setBounds(500, 100, 500, 500);
	            frame.setVisible(true);
	            
	            
	            
	            
	            try {
	            	
	            	ImageIcon imageIcon = new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("./photo/Process.jpg")).getImage().getScaledInstance(400, 100, Image.SCALE_DEFAULT));
	            	JLabel picLabel = new  JLabel();
	            	picLabel.setIcon(imageIcon);
		            picLabel.setBounds(50, 30, 400, 100);
		            frame.add(picLabel);
	            }catch(Exception e){
	            	e.printStackTrace();
	            }
	            
	            
	            
	            JButton abstractBtn = new JButton("Org. Structure Process VG");
	            abstractBtn.setBounds(150, 170, 250, 30);
	            frame.add(abstractBtn);
	            
	            
	            
	            
	            JButton ramBtn = new JButton("RAM Process VG");
	            ramBtn.setBounds(150, 250, 250, 30);
	            frame.add(ramBtn);
	            
	            abstractBtn.addActionListener(new ActionListener() {
	             public void actionPerformed(ActionEvent ae) {
	            	 BPMNGUI gui = new BPMNGUI();
	                  gui.start();
	             }
	            });
	            
	            
	            
	            ramBtn.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent arg0) {
						 openRAMGenerator();
					}
				});
	            
	            JButton closeBtn = new JButton("Close");
	            closeBtn.setBounds(230, 370, 100, 30);
	            frame.add(closeBtn);
	            closeBtn.addActionListener(new ActionListener() {
				 
					public void actionPerformed(ActionEvent arg0) {
				     	System.exit(0);	
						
					}
				});
	            
	          frame.setResizable(false);  
	    }
	  
	  
	  public void openRAMGenerator(){
	    	 new RACI();
	     }
	  
	public static void main(String[] args) {
		  
				new Start().start();
	}
	
	
	public static BufferedImage resize(BufferedImage image, int width, int height) {
	    BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
	    Graphics2D g2d = (Graphics2D) bi.createGraphics();
	    g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
	    g2d.drawImage(image, 0, 0, width, height, null);
	    g2d.dispose();
	    return bi;
	}
	  
	  
}
