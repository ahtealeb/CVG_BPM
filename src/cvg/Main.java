package cvg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;


public class Main {
	
	public static void main(String[] args) {
	
		//Main main = new Main();
                //main.doPolymorphismFlow();
               // main.doGui();
		//main.doSequentialFlow();
		//main.doParallelFlow();
	}
        
        
        public void doGui(){
           final JFrame frame = new JFrame("Frame Title");
            frame.setLayout(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setBounds(500, 100, 500, 500);
            
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
               text.setText("" );           
            }      
                }
            });
            
            
         
            frame.setVisible(true);
        }
	
	
	public void doSequentialFlow(String sourceFile , String abstractRoleName){
		try {
			
                         
                           String srcFileWithoutExtension = sourceFile.substring(0,sourceFile.lastIndexOf(".")); 
                           String destinationFile = srcFileWithoutExtension+"_Seq.bpmn";
                         
		       File fileSrc  = new File(sourceFile);
		       File fileDest = new File(destinationFile);
		       copyFile(fileSrc, fileDest);
				ParseXML parser = new ParseXML();
		                Fragment abstractFragment = parser.loadAbstractFragement(sourceFile , abstractRoleName);
		                ReBuild reBuild = new ReBuild();
		                String abstractFragmentId = abstractFragment.getFragementId();
		                 ArrayList<Node> allNodes = abstractFragment.getNodes();
		                 ArrayList<String> allNodesIds = new ArrayList<String>();
		                 for(Node node : allNodes){
		                     allNodesIds.add(node.getNodeId());
		                 }
		                 // delete lane
		                 //abstractFragment.deleteAbstractFragment(sourceFile  , destinationFile);
		                for(Node node : allNodes){
		                     //System.out.println("node name = "+node.getNodeName() + " node id = "+node.getNodeId());
		                  //  System.out.println("node name = "+node.getNodeName() + " node id = "+node.getNodeId());
		                  
		                    /*
		                    ArrayList<Incoming> incomings = node.getIncomings();
		                    if(incomings != null){
		                         
		                        for(Incoming in : incomings){
		                             if(counter == 0 && node.getNodeName().equals("task") ){
		                                 System.out.println("inside if");
		                                counter++;
		                                continue;
		                             }
		                             Node n = new Node();
		                             n.setNodeId(in.getIncomingId());
		                             n.setNodeName("sequenceFlow");
		                            // abstractFragment.deleteNodeById(n, destinationFile, destinationFile);
		                         }
		                    }
		                    */ 
		                    
		                    ArrayList<Outgoing> outgoings = node.getOutgoings();
		                   if(outgoings != null){
		                    for(Outgoing out : outgoings){
		                        if(allNodesIds .contains(out.getTargetId()) == false){
		                        //System.out.println("exist = "+allNodesIds .contains(out.getTargetId()));
		                        //System.out.println("out target = "+out.getTargetId());
		                         //   System.out.println("out id to be deleted = "+out.getOutgoingId());
		                          Node n = new Node();
		                         
		                          n.setNodeId(out.getOutgoingId());
		                          n.setNodeName("sequenceFlow");
		                          abstractFragment.deleteNodeById(n, destinationFile, destinationFile);
		                               
		                        }
		                      }
		                   }
		                   // delete tasks
		                    // abstractFragment.deleteNodeById(node, destinationFile, destinationFile);
		                }
		           
		                 
		                //  ArrayList<xpdl.Node> orderedNodes = abstractFragment.getOrderedNodes(abstractFragment);
		                 //   System.out.println("orderedNodes size ="+orderedNodes.size());
		                int levels = 3;
		                Integer y = 350;
		                for(int i = 0 ; i < levels -1    ; i++){
		                    reBuild.createLane(   destinationFile , abstractFragment.getFragemenrName(), "_new_"+i ,abstractFragment.getFragementId(), abstractFragment.getNodes() , y.toString()  , i);
		                     y = y+162;
		                }
		                  
		                
		                	reBuild.updateLastLanePosition(sourceFile, destinationFile, y);           
		                  //reBuild.createLane(destinationFile , abstractFragment.getFragemenrName(), "_new_2" ,abstractFragment.getFragementId(), abstractFragment.getNodes() , y.toString() );


				/* ArrayList<xpdl.Node> orderedNodes = abstractFragment.getOrderedNodes(abstractFragment);
		                    for(xpdl.Node node : orderedNodes){
		                      System.out.println(node.getNodeId());
		                     }
		                     * 
		                
		                ArrayList<Fragment> allLanes = parser.getAllLanes(sourceFile);
		                    Collections.sort(allLanes);
		                for(Fragment lane : allLanes){
		                    System.out.println("lane id = "+lane.getFragementId());
		                    System.out.println("lane y = "+lane.getY());
		                }
		                * 
		                * */
		                
		            	
		              //  parser.getAllLanes(sourceFile);
		            	
		                ArrayList<Fragment> allFragments = parser.loadAllFragments(destinationFile);
		                Collections.sort(allFragments);
		                int counter = 0;
		                int creationCounter = 0;
		                for(Fragment fragment : allFragments){
		                	System.out.println("fid = "+fragment.getFragementId());
		                	if(counter +1  == allFragments.size()){
		                		break;
		                	}
		                	Node currentGateWayNode = null;
		        			Node nextTaskNode = null;
		                	 
		        			System.out.println("Create first lane sequence");
		        			if(fragment.getFragementId().equals(abstractFragmentId)){
		        				Node abstractGateway = null;
		        				Node firstTask = null;
		        				ArrayList<Node> abstractFragmentNodes = abstractFragment.getNodes();
		                		for(Node node : abstractFragmentNodes){
		                    		if("exclusiveGateway".equals(node.getNodeName())){
		                    			abstractGateway = node;
		                    		}
		                    	}
		                		
		                		Fragment firstFragment = allFragments.get(0);
		                		ArrayList<Node> firstFragmentNodes = firstFragment.getNodes();
		                		for(Node node : firstFragmentNodes){
		                    		if("task".equals(node.getNodeName())){
		                    			firstTask = node;
		                    		}
		                    	}
		                		
		                		if(abstractGateway != null && firstTask != null){
		                			reBuild.createSequenceFlow(abstractGateway, firstTask, destinationFile, creationCounter , firstFragment , true);
		                			creationCounter++;
		                		}
		                		
		        			}
		        			
		        			Fragment currentFragment = fragment;
		                	ArrayList<Node> currentFragmentNodes = currentFragment.getNodes();
		            		for(Node node : currentFragmentNodes){
		                		if("exclusiveGateway".equals(node.getNodeName())){
		                			currentGateWayNode = node;
		                		}
		                	}
		                	 
		                	
		                	Fragment nextFragment = allFragments.get(counter+1);
		                	ArrayList<Node> nextFragmentNodes = nextFragment.getNodes();
		            		for(Node node : nextFragmentNodes){
		                		if("task".equals(node.getNodeName())){
		                			
		                			nextTaskNode = node;
		                			break;
		                			
		                		}
		                	}
		            		
		            	
		            		if(currentGateWayNode != null && nextTaskNode != null){
		            			reBuild.createSequenceFlow(currentGateWayNode, nextTaskNode, destinationFile, creationCounter , currentFragment , false);
		            			creationCounter++;
		            		}
		            		
		            		counter++;
		                 
		                	System.out.println("***************************************************");
		                	
		                }
		                		
		                    
		                }catch(Exception e){
		                    e.printStackTrace();
		                }
	}
	
	
	public void doParallelFlow(String sourceFile , String abstractRoleName){
		try {
		 // String sourceFile = "c:\\tealeb\\tealeb.xml";
	      // String destinationFile = "c:\\tealeb\\tealeb_par.bpmn";
                    
                    String srcFileWithoutExtension = sourceFile.substring(0,sourceFile.lastIndexOf(".")); 
                    String destinationFile = srcFileWithoutExtension+"_Par.bpmn";
                           
	       File fileSrc  = new File(sourceFile);
	       File fileDest = new File(destinationFile);
	       copyFile(fileSrc, fileDest);
			ParseXML parser = new ParseXML();
	                Fragment abstractFragment = parser.loadAbstractFragement(sourceFile,abstractRoleName);
	                ReBuildParallel reBuild = new ReBuildParallel();
	              //  String abstractFragmentId = abstractFragment.getFragementId();
	                 ArrayList<Node> allNodes = abstractFragment.getNodes();
	                 ArrayList<String> allNodesIds = new ArrayList<String>();
	                 for(Node node : allNodes){
	                     allNodesIds.add(node.getNodeId());
	                 }
	                 
	                 // delete lane
	                 abstractFragment.deleteAbstractFragment(destinationFile  , destinationFile);
	                 
	                 
	                 for(Node node : allNodes){
	                     abstractFragment.deleteNodeById(node, destinationFile, destinationFile);
                              
                             
                              ArrayList<Incoming> incomings = node.getIncomings();
		                    if(incomings != null){
		                         
		                        for(Incoming in : incomings){
		                          
		                             Node n = new Node();
		                             n.setNodeId(in.getIncomingId());
		                             n.setNodeName("sequenceFlow");
		                             abstractFragment.deleteNodeById(n, destinationFile, destinationFile);
		                         }
		                    }
                                    
	                    ArrayList<Outgoing> outgoings = node.getOutgoings();
	                   if(outgoings != null){
	                    for(Outgoing out : outgoings){
	                        if(allNodesIds .contains(out.getTargetId()) == false){
	                      
	                          Node n = new Node();
	                         
	                          n.setNodeId(out.getOutgoingId());
	                          n.setNodeName("sequenceFlow");
	                          abstractFragment.deleteNodeById(n, destinationFile, destinationFile);
	                               
	                        }
	                      }
	                   }
                           
                            
	                  
	                }
	                 
	                 
                        ArrayList<cvg.Node> parallelNodes  = new ArrayList<cvg.Node>();//abstractFragment.getNodes();
                         
	                 int levels = 3;
		                Integer y = 350;
		                for(int i = 0 ; i < levels -1    ; i++){
                                    if(i == 0){
                                        
                                        Node parallelGateway_1 = new Node();
                                        parallelGateway_1.setNodeName("parallelGateway");
                                        parallelGateway_1.setNodeId("parallelGateway_1");
                                        parallelNodes .add(parallelGateway_1);
                                        parallelNodes.addAll(abstractFragment.getNodes());
                                    }
                                    
                                    if(i == levels - 1){
                                        parallelNodes.addAll(abstractFragment.getNodes());
                                        Node parallelGateway_2 = new Node();
                                        parallelGateway_2.setNodeName("parallelGateway");
                                        parallelGateway_2.setNodeId("parallelGateway_2");
                                        parallelNodes .add(parallelGateway_2);
                                    }
		                    reBuild.createLane(   destinationFile , abstractFragment.getFragemenrName(), "_new_"+i ,abstractFragment.getFragementId(), parallelNodes , y.toString()  , i);
		                     y = y+162;
		                }
		                  
		                
		                	 reBuild.updateLastLanePosition(sourceFile, destinationFile, y); 
                                         
                                        
                                         
                                  System.out.println("start 25-04-2014");
                                 System.out.println("****************************************************");
                                 int fCounter = 0;
                                ArrayList<Fragment> allFragments = parser.loadAllFragments(destinationFile);
                                  Collections.sort(allFragments);
		                for(Fragment fragment : allFragments){
                                    fCounter++;
                                    ArrayList<Node> fragmentNodes = fragment.getNodes();
                                    for(Node node : fragmentNodes){
                                       // System.out.println("Fragment  : "+fCounter+"   Node :  "+node.getNodeName());
                                         if(fCounter == 1 && "task".equals(node.getNodeName())){
                                            Node current = node;
                                            Node next = allFragments.get(fCounter).getNodes().get(0);
                                           reBuild.createSequenceFlow(current, next, destinationFile, fCounter , fragment , false , "");
                                        }
                                        if(fCounter == 2 && "exclusiveGateway".equals(node.getNodeName())){
                                            Node current = node;
                                            Node next = allFragments.get(fCounter-2).getNodes().get(1);
                                            reBuild.createSequenceFlow(current, next, destinationFile, fCounter, fragment , false , "Change Request");
                                            Node next5 = null;
                                            Fragment nextFragment = allFragments.get(fCounter);
                                            for(Node pnode : nextFragment.getNodes()){
                                                if("parallelGateway".equals(pnode.getNodeName())){
                                                    next5 = pnode;
                                                    
                                                }
                                            }
                                            if(next5 != null){
                                                reBuild.createSequenceFlow(current, next5, destinationFile, fCounter+32, fragment , false ,"Yes");
                                            }
                                        }
                                        
                                          if(fCounter == 2 && "parallelGateway".equals(node.getNodeName())){
                                            Node current = node;
                                            Node next = allFragments.get(fCounter-1).getNodes().get(1);
                                            Node next1 = allFragments.get(fCounter).getNodes().get(1);
                                            reBuild.createSequenceFlow(current, next, destinationFile, fCounter+20, fragment , false,"");
                                            reBuild.createSequenceFlow(current, next1, destinationFile, fCounter+21, fragment , false,"");
                                        } 
                                          
                                          if(fCounter == 3 && "exclusiveGateway".equals(node.getNodeName())){
                                            Node current = node;
                                            Node next = null;
                                            Fragment nextFragment = allFragments.get(fCounter-1);
                                            for(Node pnode : nextFragment.getNodes()){
                                                if("parallelGateway".equals(pnode.getNodeName())){
                                                    next = pnode;
                                                    
                                                }
                                            }
                                           
                                            if(next != null){
                                               reBuild.createSequenceFlow(current, next, destinationFile, fCounter+233, fragment , false,"Yes");
                                            }
                                        } 
                                          
                                    if(fCounter == 3 && "parallelGateway".equals(node.getNodeName())){
                                            Node current = node;
                                            //Node next = allFragments.get(fCounter-1).getNodes().get(1);
                                            Node next1 = null;
                                            try {
                                                 next1 = allFragments.get(fCounter).getNodes().get(0);
                                                 reBuild.createSequenceFlow(current, next1, destinationFile, fCounter+21, fragment , false,"");
                                            }catch(Exception e){}
                                           // reBuild.createSequenceFlow(current, next, destinationFile, fCounter+20, fragment , false);

                                        } 
                                    }
                                }
	                 
		   }catch(Exception e){
                           e.printStackTrace();
                 }           
	}
        
        public void doPolymorphismFlow(String srcFile , String destFile , String concreteLane , String abstractLane){
            try {
              //  String concreteLane = "Graduated Student";
                //String sourceFile = "c:\\tealeb\\Register_Courses_BaseModel_V1.1.bpmn";
               // String destinationFile = "c:\\tealeb\\tealeb_poly.bpmn";
                ParseXML parser = new ParseXML();
                ArrayList<Fragment> allFragments = parser.loadAllFragments(srcFile);
                Fragment abstractFragment = null;
                Node abstractTask = null;
                for(Fragment fragment : allFragments){
                     if(abstractLane.equals(fragment.getFragemenrName())){
                         abstractFragment = fragment;
                     }
                     
                     ArrayList<Node> fNodes = fragment.getNodes();
                     if(fNodes != null){
                         for(Node node : fNodes){
                             if(node.getNodeTitle() != null && node.getNodeTitle().contains("Abstract")){
                                   System.out.println("abstractTask catched ");
                                abstractTask = node;
                             }
                         }
                     }
                }
                
                if(abstractFragment != null){
                    System.out.println("abstractFragment = "+abstractFragment);
                    System.out.println("concreteFrag = "+abstractLane);
                    System.out.println("id ?? "+abstractFragment.getFragementId());
                  String expression = "/definitions/process/laneSet/lane[@id='"+abstractFragment.getFragementId()+"']";
                    parser.updateElementAttribute(srcFile,destFile,expression , "name", concreteLane);
                }
                
                //System.out.println("abstractTask = "+abstractTask);
                if(abstractTask != null){
                    String expression = "/definitions/process/task[@id='"+abstractTask.getNodeId()+"']"; 
                    String nodeTile = abstractTask.getNodeTitle();
                    nodeTile = nodeTile.substring(nodeTile.lastIndexOf(">>")+2,nodeTile.length())+" for "+concreteLane;
                    parser.updateElementAttribute(destFile,destFile, expression, "name", nodeTile);
                }
            }catch(Exception e){
                e.printStackTrace();
            }       
        }

	public static  void copyFile(File source, File dest) throws IOException{  
		InputStream is = null;  
		OutputStream os = null; 
		try {  
			is = new FileInputStream(source);   
			os = new FileOutputStream(dest);    
			byte[] buffer = new byte[1024]; 
			int length;    
			while ((length = is.read(buffer)) > 0) {  
				os.write(buffer, 0, length);       
				}   
			} finally {  
				is.close();     
				os.close(); 
				}
		
	}
        
        
        public ArrayList<String> getFragmentsNamesList(String sourceFile){
            ArrayList<String> names = new  ArrayList<String>();
            ParseXML parser = new ParseXML();
                ArrayList<Fragment> allFragments = parser.loadAllFragments(sourceFile);
                for(Fragment fragment : allFragments){
                     names.add(fragment.getFragemenrName());
                } 
                return names;
        }
		

}
