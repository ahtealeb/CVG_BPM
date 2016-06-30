package cvg;

import java.util.ArrayList;

import org.w3c.dom.NodeList;

public class Node  implements Comparable<Node>{
	
    
	private String nodeId;
	private String nodeName;
	private String nodeType;
    private String nodeTitle;
	private ArrayList<Incoming> incomings;
	private ArrayList<Outgoing> outgoings;
	private NodeList nodeContent;
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

    public ArrayList<Incoming> getIncomings() {
        return incomings;
    }

    public void setIncomings(ArrayList<Incoming> incomings) {
        this.incomings = incomings;
    }

    public ArrayList<Outgoing> getOutgoings() {
        return outgoings;
    }

    public void setOutgoings(ArrayList<Outgoing> outgoings) {
        this.outgoings = outgoings;
    }
	 
   public String getNodeTitle() {
        return nodeTitle;
    }

    public void setNodeTitle(String nodeTitle) {
        this.nodeTitle = nodeTitle;
    }
    
 
    public NodeList getNodeContent() {
		return nodeContent;
	}
	public void setNodeContent(NodeList nodeContent) {
		this.nodeContent = nodeContent;
	}
	public int compareTo(Node n) {
        return this.nodeName.compareTo(n.nodeName);
    }

}
