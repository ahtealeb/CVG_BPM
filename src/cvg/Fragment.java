package cvg;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Fragment implements Comparable<Fragment> {
	
	private String fragemenrName;
	private String fragementId;
	private ArrayList<cvg.Node> nodes;
	private Integer x;
        private Integer y;

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }
        
	
	public String getFragemenrName() {
		return fragemenrName;
	}
	public void setFragemenrName(String fragemenrName) {
		this.fragemenrName = fragemenrName;
	}
	public String getFragementId() {
		return fragementId;
	}
	public void setFragementId(String fragementId) {
		this.fragementId = fragementId;
	}
	public ArrayList<cvg.Node> getNodes() {
		return nodes;
	}
	public void setNodes(ArrayList<cvg.Node> nodes) {
		this.nodes = nodes;
	}
	
	public ArrayList<cvg.Node> getOrderedNodes(Fragment abstractFragment){
            System.out.println("xx = "+abstractFragment.getNodes().size());
          ArrayList<cvg.Node> orderedList = new ArrayList<cvg.Node>();
          cvg.Node currentNode = getFirstNode(abstractFragment);
          orderedList.add(currentNode);
          int size = abstractFragment.getNodes().size();
           System.out.println("size = "+size);
          for(int i = 0; i < size ; i++){
              System.out.println("i="+i);
              System.out.println("inside for");
        	  cvg.Node nextNode = getNextNode(abstractFragment, currentNode);
        	  if(nextNode == null) break;
        	  orderedList.add(nextNode);
        	  currentNode = nextNode;
        	  
          }
          
          return orderedList;
		
	}
	
	
	private static cvg.Node getFirstNode(Fragment fragment){
		ArrayList<cvg.Node> nodes = fragment.getNodes();
		for(cvg.Node node : nodes){
			 String id = node.getNodeId();
			 for(cvg.Node n : nodes){
				 if(! id.equals(n.getIncomings().get(0).getIncomingId())){
					 return node;
				 }
			 }
		}
		return null;	
		}
	
	private static cvg.Node getNextNode(Fragment fragment , cvg.Node currentNode){
		ArrayList<cvg.Node> nodes = fragment.getNodes();
		
		for(cvg.Node node : nodes){
			if(currentNode.getOutgoings() != null && node.getNodeId().equals(currentNode.getOutgoings().get(0).getTargetId())){
				return node;
			}
		}
		return null;
	}
	
	//[@ID='" + ID + "']
        public String deleteNodeById(cvg.Node node ,  String source , String destination){
                ParseXML parseXML = new ParseXML();
	        XPath xPath =  null;
	        Document xmlDocument = null;
	        try {
                     xmlDocument =  parseXML.getXPathDocumentElement(source);
	              xPath =  XPathFactory.newInstance().newXPath();
	              //String expression = "/definitions/process/"+node.getNodeName()+"/[@id='"+node.getNodeId()+"']";
                       String expression = "/definitions/process/"+node.getNodeName()+"[@id='"+node.getNodeId()+"']";
                       
                         NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
                            for (int i = 0; i < nodeList.getLength(); i++) {
                               Node n =  nodeList.item(i);//.getParentNode().removeChild(nodeList.item(i));
                                n.getParentNode().removeChild(n);
                            }
                      String shapEexpression = "/definitions/BPMNDiagram/BPMNPlane/BPMNShape[@id='"+node.getNodeId()+"_gui"+"']";
                      //System.out.println(" d shapEexpression = "+shapEexpression);
                      NodeList shapeNodeList = (NodeList) xPath.compile(shapEexpression).evaluate(xmlDocument, XPathConstants.NODESET);
                      Node shapeNode = shapeNodeList.item(0);
                      if(shapeNode != null){
                         shapeNode.getParentNode().removeChild(shapeNode);
                      }else {
                         shapEexpression = "/definitions/BPMNDiagram/BPMNPlane/BPMNEdge[@id='"+node.getNodeId()+"_gui"+"']";
                         //System.out.println("shapEexpression = "+shapEexpression);
                         shapeNodeList = (NodeList) xPath.compile(shapEexpression).evaluate(xmlDocument, XPathConstants.NODESET);
                         shapeNode = shapeNodeList.item(0);
                         if(shapeNode != null){
                             shapeNode.getParentNode().removeChild(shapeNode);
                         }
                      }
                      
                         TransformerFactory tf = TransformerFactory.newInstance();
                         Transformer t = tf.newTransformer();
                         t.transform(new DOMSource(xmlDocument), new StreamResult(new FileOutputStream(new File(destination))));

                    
                     }catch(Exception e){
	        	e.printStackTrace();
	        }
                return destination;
               
        }
	public String deleteAbstractFragment(String source , String destination){
	        ParseXML parseXML = new ParseXML();
	        XPath xPath =  null;
	        Document xmlDocument = null;
	        try {
	              xmlDocument =  parseXML.getXPathDocumentElement(source);
	              xPath =  XPathFactory.newInstance().newXPath();
	              String expression = "/definitions/process/laneSet/*";
	              ArrayList<Node> subNodes = parseXML.getSubNodes(expression, xmlDocument, xPath);
	              Node abstractRole = null;
	              String name = "";
                      String id = "";
	              for(Node node : subNodes){
	                   
	                  NamedNodeMap attr = node.getAttributes();
	                  
	                 
	                 for(int x = 0 ; x < attr.getLength() ; x++){
	                      if("id".equals(attr.item(x).getNodeName())){
                                 id = attr.item(x).getTextContent();
                              }
	                      if("name".equals(attr.item(x).getNodeName())){
	                         name = attr.item(x).getTextContent();
	                      }
	                      
	                      if(name.contains("Abstract")){
	                          abstractRole = node;
	                     }
	                     
	                 }

	              }
                      
	               abstractRole.getParentNode().removeChild(abstractRole);
                       String shapEexpression = "/definitions/BPMNDiagram/BPMNPlane/BPMNShape[@id='"+id+"_gui"+"']";
	               NodeList nodeList = (NodeList) xPath.compile(shapEexpression).evaluate(xmlDocument, XPathConstants.NODESET);
                       Node shapeNode = nodeList.item(0);
                       shapeNode.getParentNode().removeChild(shapeNode);
                       TransformerFactory tf = TransformerFactory.newInstance();
                       Transformer t = tf.newTransformer();
                       t.transform(new DOMSource(xmlDocument), new StreamResult(new FileOutputStream(new File(destination))));

	              
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
                return destination;
	}

    public int compareTo(Fragment t) {
        return this.y.compareTo(t.y);
    }

}
