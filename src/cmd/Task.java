package cmd;

 

import org.w3c.dom.NodeList;
 

public class Task {
	private String taskName;
	private String xmlElementId;
	private String taskFile;
	private int taskFileId;
	private NodeList nodeContent;
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskFile() {
		return taskFile;
	}
	public void setTaskFile(String taskFile) {
		this.taskFile = taskFile;
	}
	
	
	public int getTaskFileId() {
		return taskFileId;
	}
	public void setTaskFileId(int taskFileId) {
		this.taskFileId = taskFileId;
	}
	
	
	public String getXmlElementId() {
		return xmlElementId;
	}
	public void setXmlElementId(String xmlElementId) {
		this.xmlElementId = xmlElementId;
	}
	
	
	
	
 
	public NodeList getNodeContent() {
		return nodeContent;
	}
	public void setNodeContent(NodeList nodeContent) {
		this.nodeContent = nodeContent;
	}
	@Override
	public boolean equals(Object obj) {
		Task t = (Task) obj;
		if(t.getTaskName().equals(taskName)){
			return true;
		}else{
			return false;
		}
	}

}
