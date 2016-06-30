package cmd;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.w3c.dom.NodeList;

import cvg.ParseXML;

public class ProceedBusiness {
	
	public void proceed(List<RAMDto> changedObjs ,  ArrayList<Task> allTasks , JFrame changesFrame ,ArrayList<JTextField> subProcesseTextFieldPaths){

		ParseXML parser = new ParseXML();
		
		for(int i = 0 ; i < changedObjs.size() ; i++){
	   		
	   		RAMDto changesRamDto = changedObjs.get(i);
	   		String currentRole = changesRamDto.getRoleName();
	   		
	   		String oldVal = changesRamDto.getOldCellvalue() == null || changesRamDto.getOldCellvalue().trim().equals("null") ? "" : changesRamDto.getOldCellvalue().trim();
	   		String newVal = changesRamDto.getCellValue() == null || changesRamDto.getCellValue().trim().equals("null") ? "" : changesRamDto.getCellValue().trim();
	   		 
	   		
	   		//44444444444444
	   		String taskName = changesRamDto.getTaskName();
	   		Task taskObj = null;
	   		try {
	   			  taskObj = fetchTaskByName(taskName , allTasks);
	   		}catch(Exception e){
	   			JOptionPane.showMessageDialog(changesFrame, "Error in getting file");
	   		}
	   		
	   		
	   		
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
	   		
	   		
	   		
	   		
	   		if(operation.equals("Delete")){
		   		if(oldVal.equals("C") || oldVal.equals("S")){
		   			
		   		}
	   		}
	   		
	   		
	   		
	   		Integer fileid = taskObj == null ? null : taskObj.getTaskFileId();
	   		String file = subProcesseTextFieldPaths.get(fileid).getText();
	   		ArrayList<Task> tasksInSubFile = RACI.getTasksInFile(file);
	   		
	   		
	   		
	   		
	   		/*
	   		 *  1. 
	   		 */
	   		 
	   		if("Update".equals(operation)){
	   			if("S".equals(oldVal) && "A".equals(newVal)){
	   				ArrayList<Task> subProcessesInTheMainFile = RACI.getSubProcessesInFile(RACI.mainFilePath);
	   				for(Task t : subProcessesInTheMainFile){
	   					String subProcessName = t.getTaskName();
	   					String updatedElementId = t.getXmlElementId();
	   					NodeList nodeList = t.getNodeContent();
	   					parser.removeElement(RACI.mainFilePath, RACI.mainFilePath, updatedElementId);
	   					parser.createElement(RACI.mainFilePath, RACI.mainFilePath, updatedElementId, subProcessName, nodeList , "userTask" , null);
	   					parser.createElement(RACI.mainFilePath, RACI.mainFilePath, updatedElementId, subProcessName+" "+RACI.aValues.get(0), null , "userTask" , currentRole);
	   					
	   				}
   				}
	   		}
	   		
	   		
	   		if("Delete".equals(operation)){
	   			if("S".equals(oldVal) && "".equals(newVal)){
	   				ArrayList<Task> subProcessesInTheMainFile = RACI.getSubProcessesInFile(RACI.mainFilePath);
	   				for(Task t : subProcessesInTheMainFile){
	   					String subProcessName = t.getTaskName();
	   					String updatedElementId = t.getXmlElementId();
	   					NodeList nodeList = t.getNodeContent();
	   					parser.removeElement(RACI.mainFilePath, RACI.mainFilePath, updatedElementId);
	   					parser.createElement(RACI.mainFilePath, RACI.mainFilePath, updatedElementId, subProcessName, nodeList , "userTask" , null);
	   					//parser.createElement(RACI.mainFilePath, RACI.mainFilePath, updatedElementId, subProcessName+" "+RACI.aValues.get(0), null , "userTask" , currentRole);
	   					
	   				}
   				}
	   		}
	   		
	   		
	   		 
	   			if("".equals(oldVal) && "A".equals(newVal)){
	   				ArrayList<Task> subProcessesInTheMainFile = RACI.getSubProcessesInFile(RACI.mainFilePath);
	   				for(Task t : subProcessesInTheMainFile){
	   					String subProcessName = t.getTaskName();
	   					String updatedElementId = t.getXmlElementId();
	   					parser.createElement(RACI.mainFilePath, RACI.mainFilePath, updatedElementId, RACI.aValues.get(0)+" "+subProcessName, null , "userTask" , currentRole);
	   				}
	   			}
	   		 
	   		
	   		 
	   		for(Task t : tasksInSubFile){
	   			if("Update".equals(operation)){
	   				if("S".equals(oldVal) && "C".equals(newVal)){
	   					//String newValue = t.getTaskName().replace("Support", "Consult");
	   					String newValue = t.getTaskName().replace(RACI.sValues.get(0), RACI.cValues.get(0));
	   					if(t.getTaskName().contains((RACI.sValues.get(0)))){
	   						String updatedElementId = t.getXmlElementId();
	   						
	   					   String srcFileWithoutExtension = file.substring(0,file.lastIndexOf(".")); 
	   		               String destinationFile = srcFileWithoutExtension+"_new.bpmn";
	   					    
	   					    String expression = "/definitions/process/userTask[@id='"+updatedElementId+"']";
	   					    parser.updateElementAttribute(file,destinationFile,expression , "name", newValue);
	   					    
	   					    String expressionGateway = "/definitions/process/exclusiveGateway[@id='"+updatedElementId+"']";
	   					    parser.updateElementAttribute(destinationFile,destinationFile,expressionGateway , "name", newValue);
                      	}
	   				}
	   				
	   			 	
	   				
	   				if("S".equals(oldVal) && "I".equals(newVal)){
	   					String newValue = t.getTaskName().replace(RACI.sValues.get(0), RACI.iValues.get(0));
	   					if(t.getTaskName().contains((RACI.sValues.get(0)))){
	   						String updatedElementId = t.getXmlElementId();
	   					    String expression = "/definitions/process/userTask[@id='"+updatedElementId+"']";
	   					    parser.updateElementAttribute(file,file,expression , "name", newValue);
	   					    
	   					    String expressionGateway = "/definitions/process/exclusiveGateway[@id='"+updatedElementId+"']";
	   					    parser.updateElementAttribute(file,file,expressionGateway , "name", newValue);
                      	}
	   				}
	   				
	   				//*************************
	   				
	   				
	   				if("C".equals(oldVal) && "S".equals(newVal)){
	   					//String newValue = t.getTaskName().replace("Consult", "Support");
	   					String newValue = t.getTaskName().replace(RACI.cValues.get(0), RACI.sValues.get(0));
	   					if(t.getTaskName().contains((RACI.cValues.get(0)))){
	   						String updatedElementId = t.getXmlElementId();
	   						
	   						String srcFileWithoutExtension = file.substring(0,file.lastIndexOf(".")); 
	   		                String destinationFile = srcFileWithoutExtension+"_new.bpmn";
	   		               
	   		               
	   					    String expression = "/definitions/process/userTask[@id='"+updatedElementId+"']";
	   					    parser.updateElementAttribute(file,destinationFile,expression , "name", newValue);
	   					    
	   					    String expressionGateway = "/definitions/process/exclusiveGateway[@id='"+updatedElementId+"']";
	   					    parser.updateElementAttribute(destinationFile,destinationFile,expressionGateway , "name", newValue);
                      	}
	   				}
	   				
	   				
	   			/*	if("C".equals(oldVal) && "A".equals(newVal)){
	   					//String newValue = t.getTaskName().replace("Consult", "Support");
	   					String newValue = t.getTaskName().replace(RACI.cValues.get(0), RACI.aValues.get(0));
	   					if(t.getTaskName().contains((RACI.cValues.get(0)))){
	   						String updatedElementId = t.getXmlElementId();
	   					    ParseXML parser = new ParseXML();
	   					    String expression = "/definitions/process/userTask[@id='"+updatedElementId+"']";
	   					    parser.updateElementAttribute(file,file,expression , "name", newValue);
	   					    
	   					    String expressionGateway = "/definitions/process/exclusiveGateway[@id='"+updatedElementId+"']";
	   					    parser.updateElementAttribute(file,file,expressionGateway , "name", newValue);
                      	}
	   				}*/
	   				
	   				if("C".equals(oldVal) && "I".equals(newVal)){
	   					//String newValue = t.getTaskName().replace("Consult", "Support");
	   					String newValue = t.getTaskName().replace(RACI.cValues.get(0), RACI.iValues.get(0));
	   					if(t.getTaskName().contains((RACI.cValues.get(0)))){
	   						String updatedElementId = t.getXmlElementId();
	   					    String expression = "/definitions/process/userTask[@id='"+updatedElementId+"']";
	   					    parser.updateElementAttribute(file,file,expression , "name", newValue);
	   					    
	   					    String expressionGateway = "/definitions/process/exclusiveGateway[@id='"+updatedElementId+"']";
	   					    parser.updateElementAttribute(file,file,expressionGateway , "name", newValue);
                      	}
	   				}
	   				
	   				//********************************
	   			}
	   		}
	   		 
	   	}
		
		
	
		
	}
	
	
	public static Task fetchTaskByName(String taskName  , ArrayList<Task> allTasks) throws Exception {
		if(allTasks == null){
			throw new Exception("Application failure");
		}
		
		
		Task wantedTask = null;
		for(Task task : allTasks){
			if(task.getTaskName().equals(taskName)){
				wantedTask = task;
			}
		}
		return wantedTask;
	}

}
