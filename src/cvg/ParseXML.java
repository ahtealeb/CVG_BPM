/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cvg;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author mahmoud
 */
public class ParseXML {
    
public ArrayList<String> getSubTags(String expression , Document xmlDocument , XPath xPath){
        ArrayList<String> subTags = new ArrayList<String>();
        try{
            NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
              for (int i = 0; i < nodeList.getLength(); i++) {
                 subTags.add(nodeList.item(i).getNodeName());
              }
        }catch(Exception e){
            e.printStackTrace();
        }
        return subTags;
}


    
public ArrayList<Node> getSubNodes(String expression , Document xmlDocument , XPath xPath){
        ArrayList<Node> subTags = new ArrayList<Node>();
        try{
            NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
            //System.out.println("nodelist size = "+nodeList.getLength());
              for (int i = 0; i < nodeList.getLength(); i++) {
                 subTags.add(nodeList.item(i));
              }
        }catch(Exception e){
            e.printStackTrace();
        }
        return subTags;
}



    public ArrayList<String> getSubElementsNames(String expression , Document xmlDocument , XPath xPath){
        ArrayList<String> subElements = new ArrayList<String>();
         
        try {
             
             NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
              for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                NodeList subList = node.getChildNodes();
               // NamedNodeMap nodeMap =  node.getAttributes();
                //for(int s = 0 ; s < nodeMap.getLength() ; s++){
                 //   System.err.println(nodeMap.item(s).getFirstChild().getTextContent());
                //}
                for(int x = 0 ; x < subList.getLength() ; x++){
                    if(subList.item(x).getNodeType() == Node.ELEMENT_NODE){
                       //System.out.println(subList.item(x).getNodeName()+ " = "+subList.item(x).getFirstChild().getNodeValue());
                        subElements.add(subList.item(x).getNodeName());
                    }
                }
            } 
        }catch(Exception e){
            
        }
        return subElements;
    }
    
    public Map<String , String> getElementAttributes(String expression , Document xmlDocument , XPath xPath){
        Map<String , String> attributes = new HashMap<String , String>();
         try {
               NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
               for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                //NodeList subList = node.getChildNodes();
                NamedNodeMap nodeMap =  node.getAttributes();
                
                 for(int s = 0 ; s < nodeMap.getLength() ; s++){
                   //System.err.println(nodeMap.item(s).getFirstChild().getTextContent());
                    attributes.put(nodeMap.item(s).getNodeName(), nodeMap.item(s).getFirstChild().getTextContent());
                    
                }
              }
        }catch(Exception e){
            e.printStackTrace();
            
        }
        return attributes;
    }
    
    
    
    public Map<String , String> getSubElements(String expression , Document xmlDocument , XPath xPath){
        Map<String , String> elements = new HashMap<String , String>();
        try {
               NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
               Node node = nodeList.item(0);
               nodeList = node.getChildNodes();
               for (int i = 0; i < nodeList.getLength(); i++) {
                   
                           
                    Node subNode = nodeList.item(i);
                    //NodeList subList = node.getChildNodes();
                    if(nodeList.item(i).getNodeType() == Node.ELEMENT_NODE){
                        elements.put(subNode.getNodeName(), subNode.getFirstChild().getNodeValue());
                    }
                }
        }catch(Exception e){
            e.printStackTrace();
            
        }
        return elements;
    }
    
    
 
    
   
    
    
        public static  Map<String , String> getFirstNode ( java.util.List<Map<String , String>> refrencesList ,  ArrayList<String> abstractRefIDs){
        	Map<String , String> firstNode = null;
        	 int counter = 0;
        	 for(Map<String, String> refContentMap : refrencesList){
        		
           	  Iterator it = refContentMap.entrySet().iterator();
           	    while (it.hasNext()) {
           	        Map.Entry pairs = (Map.Entry)it.next();
           	        String key = (String)pairs.getKey();
           	        String value = (String)pairs.getValue();
           	       
           	         if(value.equals("source")){
           	        	  String sourceNodeId = key;
           	        	  
           	        	 
           	        	  if(abstractRefIDs.contains(sourceNodeId) == false){
           	        		  firstNode = refrencesList.get(counter) ;//refContentMap.get("nodeid");
           	        		  return firstNode;
           	        	  }
           	         }
                 }
           	 counter++;
             }
        	return firstNode;
        }
        
        
        public static  Map<String , String> getNextNode ( java.util.List<Map<String , String>> refrencesList ,  ArrayList<String> abstractRefIDs, String target){
        	Map<String , String> nextNode = null;
        	 String nodeId = "";
        	 int counter = 0;
        	 for(Map<String, String> refContentMap : refrencesList){
           	  
           	    	//System.out.println("in while");
           	         if(target.equals(refContentMap.get("nodeid"))){
           	        		nextNode = refrencesList.get(counter) ;//refContentMap.get("nodeid");
           	        	  return nextNode;
                 }
           	 counter++;
             }
        	return nextNode;
        }
        
      
        public Document getXPathDocumentElement(String xmlFilePath) throws Exception{
            Document xmlDocument =  null;
            try {
                FileInputStream file = new FileInputStream(new File(xmlFilePath));    
                DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();    
                DocumentBuilder builder =  builderFactory.newDocumentBuilder();                     
                xmlDocument = builder.parse(file);         
                 
            }catch(Exception e){
                e.printStackTrace();
            }
            return xmlDocument;
        }
        
        
        public ArrayList<Fragment> getAllLanes(String file){
                ArrayList<Fragment> fragments = new ArrayList<Fragment>();
                ParseXML parseXML = new ParseXML();
   	        XPath xPath =  null;
   	        Document xmlDocument = null;
   	        Fragment fragment = null;
   	        try {
   	              xmlDocument =  parseXML.getXPathDocumentElement(file);
   	              xPath =  XPathFactory.newInstance().newXPath();
   	              String expression = "/definitions/process/laneSet/*";
                      ArrayList<Node> subNodes = parseXML.getSubNodes(expression, xmlDocument, xPath); 
   	              for(Node node : subNodes){
   	                  fragment = new Fragment();
   	                  NamedNodeMap attr = node.getAttributes();
   	                  
                         String id = "";
   	                 for(int x = 0 ; x < attr.getLength() ; x++){
   	                    
   	                      if("id".equals(attr.item(x).getNodeName())){
   	                         id = attr.item(x).getTextContent();
                                 fragment.setFragementId(id);
   	                      }
   	                 }
                         
                          String shapeExpression = "/definitions/BPMNDiagram/BPMNPlane/BPMNShape[@bpmnElement='"+id+"']";
                          ArrayList<Node> shapeNodes = parseXML.getSubNodes(shapeExpression, xmlDocument, xPath);
                         // System.out.println("shapeNodes = "+shapeNodes);
                             for(Node shapeNode : shapeNodes){
                                  Node boundsNode =  shapeNode.getChildNodes().item(1);
                                   NamedNodeMap boundAttr = boundsNode.getAttributes();
                                   Float f = Float.valueOf(boundAttr.item(3).getTextContent());
                                   fragment.setY(f.intValue());
                            }
                           fragments.add(fragment);
   	              }
                }catch(Exception e){
                    e.printStackTrace();
                } 
            return fragments;
        }
        
        
        
        
        public ArrayList<Fragment> loadAllFragments(String file){
        	  ParseXML parseXML = new ParseXML();
     	        XPath xPath =  null;
     	        Document xmlDocument = null;
     	       ArrayList<Fragment> allFragments = new  ArrayList<Fragment>();
     	       try {
     	    	   
     	    	  xmlDocument =  parseXML.getXPathDocumentElement(file);
   	              xPath =  XPathFactory.newInstance().newXPath();
   	              String expression = "/definitions/process/laneSet/*";
   	              ArrayList<Node> subNodes = parseXML.getSubNodes(expression, xmlDocument, xPath);
   	              for(Node node : subNodes){
   	            	 
   	            	 Fragment fragment = new Fragment();
   	            	 NamedNodeMap attr = node.getAttributes();
   	            	 NodeList laneNodes = node.getChildNodes();
   	            	 
   	            	 String id = "";
   	            	 String name = "";
	                 for(int x = 0 ; x < attr.getLength() ; x++){
	                    
	                      if("id".equals(attr.item(x).getNodeName())){
	                         id = attr.item(x).getTextContent();
	                         fragment.setFragementId(id);
	                      }
                              
                            if("name".equals(attr.item(x).getNodeName())){
	                         name = attr.item(x).getTextContent();
	                         fragment.setFragemenrName(name);
	                      }
                                            
	                 }
   	            	 
	                 
	                 String shapeExpression = "/definitions/BPMNDiagram/BPMNPlane/BPMNShape[@bpmnElement='"+id+"']";
	                // System.out.println("shapeExpression = "+shapeExpression);
                     ArrayList<Node> shapeNodes = parseXML.getSubNodes(shapeExpression, xmlDocument, xPath);
                     
                     // System.out.println("shapeNodes = "+shapeNodes);
                        for(Node shapeNode : shapeNodes){
                        	
                             Node boundsNode =  shapeNode.getChildNodes().item(1);
                            // System.out.println("boundsNode = "+boundsNode);
                              NamedNodeMap boundAttr = boundsNode.getAttributes();
                           //    System.out.println("boundAttr = "+boundAttr);
                              Float fx = Float.valueOf(boundAttr.item(2).getTextContent());
                              Float fy = Float.valueOf(boundAttr.item(3).getTextContent());
                            //  System.out.println(fx);
                            //  System.out.println(fy);
                              fragment.setX(fx.intValue());
                              fragment.setY(fy.intValue());
                       }
   	              
   	              ArrayList<String> laneRefIDs = new  ArrayList<String>();
   	                      
   	              for(int i = 0 ; i < laneNodes.getLength() ; i++){
   	                  Node refNode = laneNodes.item(i);
   	                  if("flowNodeRef".equals(refNode.getNodeName())){
   	                      String refId = refNode.getTextContent();
   	                   laneRefIDs.add(refId);
   	                  }
   	              }
   	              
   	              
   	           ArrayList<cvg.Node> nodesList = new ArrayList<cvg.Node>();
   	           java.util.List<Map<String , String>> refrencesList = new ArrayList<Map<String , String>>();
	              
	              for(String refId  : laneRefIDs){
	                 Map<String, String> refContentsMap = new HashMap<String, String>();
	                  String refExpression = "/definitions/process/*[@id='"+refId+"']";
	                   ArrayList<Node> refNodes = parseXML.getSubNodes(refExpression, xmlDocument, xPath);
                        
	                   for(int i = 0 ; i < refNodes.size() ; i++){
	                	cvg.Node cnode = new cvg.Node();
	                        Node refNode = refNodes.get(i);
	                        refContentsMap.put("nodename",refNode.getNodeName());
	                        refContentsMap.put("nodeid",refId);
	                        cnode.setNodeName(refNode.getNodeName()); 
                                // get node title
                                if("task".equals(refNode.getNodeName())){
                                    NamedNodeMap nodeAttr = refNode.getAttributes();
                                    cnode.setNodeTitle(nodeAttr.item(3).getTextContent());
                                }
                             
                                if("serviceTask".equals(refNode.getNodeName()) || "userTask".equals(refNode.getNodeName()) || "sendTask".equals(refNode.getNodeName()) ){
                                    NamedNodeMap nodeAttr = refNode.getAttributes();
                                    cnode.setNodeTitle(nodeAttr.item(4) == null ? "" : nodeAttr.item(4).getTextContent());
                                }
                                
                                if("subProcess".equals(refNode.getNodeName())){
                                	 NamedNodeMap nodeAttr = refNode.getAttributes();
                                     cnode.setNodeTitle(nodeAttr.item(3).getTextContent());
                                    // System.out.println("content "+refNode.hasChildNodes());
                                     cnode.setNodeContent(refNode.getChildNodes());
                                    
                                }
                                
                                if("exclusiveGateway".equals(refNode.getNodeName())){
                                	NamedNodeMap nodeAttr = refNode.getAttributes();
                                    cnode.setNodeTitle(nodeAttr.item(2) == null ? "" : nodeAttr.item(2).getTextContent());
                               }
                                
                                
	                        cnode.setNodeId(refId);
	                        
	                         NodeList inOutRefs = refNode.getChildNodes();
                             ArrayList<Outgoing> outgoings = new ArrayList<Outgoing>();
	                         ArrayList<Incoming> incomings = new ArrayList<Incoming>();
                        
                            
                             
                          
	                        for(int x = 0 ; x < inOutRefs.getLength() ; x++){
	                            Node inOutNode = inOutRefs.item(x);
	                           //  System.out.println("cc "+inOutNode.getNodeName());
	                            Incoming in = new Incoming();
                                
	                            if("incoming".equals(inOutNode.getNodeName())){
	                                refExpression = "/definitions/process/*[@id='"+inOutNode.getTextContent()+"']";
	                                refNodes = parseXML.getSubNodes(refExpression, xmlDocument, xPath);
	                                for(int s = 0 ; s < refNodes.size() ; s++){
                                         in = new Incoming();
	                                     refContentsMap.put(refNodes.get(s).getAttributes().item(2).getTextContent() ,"source");
	                                     in.setSourceId(refNodes.get(s).getAttributes().item(2).getTextContent());
                                          in.setTargetId(refNodes.get(s).getAttributes().item(3).getTextContent());
	                                     in.setIncomingId(inOutNode.getTextContent());
	                                    
	                                }
	                                incomings.add(in);
	                              
	                            }
                                   cnode.setIncomings(incomings);
	                            Outgoing out = new Outgoing();
                                 
	                          if("outgoing".equals(inOutNode.getNodeName())){
                                    
	                              refExpression = "/definitions/process/*[@id='"+inOutNode.getTextContent()+"']";
	                              refNodes = parseXML.getSubNodes(refExpression, xmlDocument, xPath);
	                              for(int d = 0 ; d < refNodes.size() ; d++){
                                       out = new Outgoing();
	                                   refContentsMap.put("target",refNodes.get(d).getAttributes().item(3).getTextContent());
	                                   out.setTargetId(refNodes.get(d).getAttributes().item(3).getTextContent());
                                        out.setSourceId(refNodes.get(d).getAttributes().item(2).getTextContent());
	                                   out.setOutgoingId(inOutNode.getTextContent());
                                        
	                              }
	                             
	                              outgoings.add(out);
	                           }
	                          
	                               cnode.setOutgoings(outgoings);
                                    //System.out.println("outgoings size = "+outgoings.size());
	                         
	                        }   
	                            
	                        nodesList.add(cnode); 
	                        
	                   }
	                   fragment.setNodes(nodesList);
	                   refrencesList.add(refContentsMap);
	              }
   	              
   	             allFragments.add(fragment);
   	              }
     	    	   
     	      }catch(Exception e){
     	            e.printStackTrace();
     	        }
     	       return allFragments;
        }
    	public Fragment loadAbstractFragement(String file , String abstractRoleName){
   	        ParseXML parseXML = new ParseXML();
   	        XPath xPath =  null;
   	        Document xmlDocument = null;
   	        Fragment abstractFragment = new Fragment();
   	        try {
   	              xmlDocument =  parseXML.getXPathDocumentElement(file);
   	              xPath =  XPathFactory.newInstance().newXPath();
   	              String expression = "/definitions/process/laneSet/*";
   	              ArrayList<Node> subNodes = parseXML.getSubNodes(expression, xmlDocument, xPath);
   	              Node abstractRole = null;
   	              for(Node node : subNodes){
   	                   
   	                  NamedNodeMap attr = node.getAttributes();
   	                  
   	             String name = "";
                     String id = "";
   	                 for(int x = 0 ; x < attr.getLength() ; x++){
   	                    
   	                      if("id".equals(attr.item(x).getNodeName())){
   	                         id = attr.item(x).getTextContent();
                                 abstractFragment.setFragementId(id);
   	                      }
                              
                                 if("name".equals(attr.item(x).getNodeName())){
   	                             name = attr.item(x).getTextContent();
                                 abstractFragment.setFragemenrName(name);
   	                      }
   	                       
   	                      if(name.contains("Abstract")){
   	                    	  System.out.println("True True");
   	                          abstractRole = node;
   	                     }
   	                     
   	                 }

   	              }
   	              
    
   	              
   	              NodeList abstractLaneRefNodes = abstractRole.getChildNodes();
   	              ArrayList<String> abstractRefIDs = new  ArrayList<String>();
   	                      
   	              for(int i = 0 ; i < abstractLaneRefNodes.getLength() ; i++){
   	                  Node refNode = abstractLaneRefNodes.item(i);
   	                  if("flowNodeRef".equals(refNode.getNodeName())){
   	                      String refId = refNode.getTextContent();
   	                        abstractRefIDs.add(refId);
   	                  }
   	              }
   	              
   	              
   	           
   	              
   	              
   	             
   	              ArrayList<cvg.Node> abstractNodesList = new ArrayList<cvg.Node>();
   	              java.util.List<Map<String , String>> refrencesList = new ArrayList<Map<String , String>>();
   	              
   	              for(String refId  : abstractRefIDs){
   	                 Map<String, String> refContentsMap = new HashMap<String, String>();
   	                  String refExpression = "/definitions/process/*[@id='"+refId+"']";
   	                   ArrayList<Node> refNodes = parseXML.getSubNodes(refExpression, xmlDocument, xPath);
                           
   	                   for(int i = 0 ; i < refNodes.size() ; i++){
   	                	cvg.Node node = new cvg.Node();
   	                        Node refNode = refNodes.get(i);
   	                        refContentsMap.put("nodename",refNode.getNodeName());
   	                        refContentsMap.put("nodeid",refId);
   	                        node.setNodeName(refNode.getNodeName()); 
                            
   	                        node.setNodeId(refId);
   	                        
   	                        NodeList inOutRefs = refNode.getChildNodes();
                                ArrayList<Outgoing> outgoings = new ArrayList<Outgoing>();
   	                        ArrayList<Incoming> incomings = new ArrayList<Incoming>();
                                
                               if("task".equals(refNode.getNodeName())){
                                    
                                     node.setNodeTitle(refNodes.get(i).getAttributes().item(3).getTextContent());
                                     
                                
                                }
                               
                                
                             
   	                        for(int x = 0 ; x < inOutRefs.getLength() ; x++){
   	                            Node inOutNode = inOutRefs.item(x);
   	                           //  System.out.println("cc "+inOutNode.getNodeName());
   	                            Incoming in = new Incoming();
                                   
   	                            if("incoming".equals(inOutNode.getNodeName())){
   	                                refExpression = "/definitions/process/*[@id='"+inOutNode.getTextContent()+"']";
   	                                refNodes = parseXML.getSubNodes(refExpression, xmlDocument, xPath);
   	                                for(int s = 0 ; s < refNodes.size() ; s++){
                                            in = new Incoming();
   	                                     refContentsMap.put(refNodes.get(s).getAttributes().item(2).getTextContent() ,"source");
   	                                     in.setSourceId(refNodes.get(s).getAttributes().item(2).getTextContent());
                                             in.setTargetId(refNodes.get(s).getAttributes().item(3).getTextContent());
   	                                     in.setIncomingId(inOutNode.getTextContent());
   	                                    
   	                                }
   	                                incomings.add(in);
   	                              
   	                            }
                                      node.setIncomings(incomings);
   	                            Outgoing out = new Outgoing();
                                    
   	                          if("outgoing".equals(inOutNode.getNodeName())){
                                       
   	                              refExpression = "/definitions/process/*[@id='"+inOutNode.getTextContent()+"']";
   	                              refNodes = parseXML.getSubNodes(refExpression, xmlDocument, xPath);
   	                              for(int d = 0 ; d < refNodes.size() ; d++){
                                          out = new Outgoing();
   	                                   refContentsMap.put("target",refNodes.get(d).getAttributes().item(3).getTextContent());
   	                                   out.setTargetId(refNodes.get(d).getAttributes().item(3).getTextContent());
                                           out.setSourceId(refNodes.get(d).getAttributes().item(2).getTextContent());
   	                                   out.setOutgoingId(inOutNode.getTextContent());
                                           
   	                              }
   	                             
   	                              outgoings.add(out);
   	                           }
   	                          
   	                               node.setOutgoings(outgoings);
                                       //System.out.println("outgoings size = "+outgoings.size());
   	                         
   	                        }   
   	                            
   	                        abstractNodesList.add(node); 
   	                        
   	                   }
   	                   abstractFragment.setNodes(abstractNodesList);
   	                   refrencesList.add(refContentsMap);
   	              }
   	              	              
   	        }catch(Exception e){
   	            e.printStackTrace();
   	        }
   	        
   	        return abstractFragment;
   	}
        
        public void updateElementAttribute(String srcFile , String destFile ,String expression , String attribute , String value){
             ParseXML parseXML = new ParseXML();
	        XPath xPath =  null;
	        Document xmlDocument = null;
	        try {
	        	
	        	 /*  File fileSrc  = new File(srcFile);
	   		       File fileDest = new File(destFile);
	   		       Main.copyFile(fileSrc, fileDest);
	   		        */
	   		       
                  xmlDocument =  parseXML.getXPathDocumentElement(srcFile);
	              xPath =  XPathFactory.newInstance().newXPath();
                  NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
                  for (int i = 0; i < nodeList.getLength(); i++) {
                     Node node = nodeList.item(i);
                     NamedNodeMap nodeMap =  node.getAttributes();
	                 for(int s = 0 ; s < nodeMap.getLength() ; s++){
	                     if("name".equals(nodeMap.item(s).getNodeName())){
	                         nodeMap.item(s).setNodeValue(value);
	                         
	                     }
	                }
	                 
                 }
               
                 
                 
              
             TransformerFactory tf = TransformerFactory.newInstance();
             Transformer t = tf.newTransformer();
             t.transform(new DOMSource(xmlDocument), new StreamResult(new FileOutputStream(new File(destFile) )));
             
           
              
                }catch(Exception e){
                    e.printStackTrace();;
                }
        }
        
        
        public void removeElement(String srcFile , String destFile ,String id ){
            ParseXML parseXML = new ParseXML();
	        XPath xPath =  null;
	        Document xmlDocument = null;
	        try {
	        	
	        	
	        	  xmlDocument =  parseXML.getXPathDocumentElement(srcFile);
                  xPath =  XPathFactory.newInstance().newXPath();
                  String expression = "/definitions/process/subProcess[@id='"+id+"']";
                  ArrayList<Node> subNodes = parseXML.getSubNodes(expression, xmlDocument, xPath);
                  Node subProcessNode = subNodes.get(0);
                 subProcessNode.getParentNode().removeChild(subProcessNode);
              
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            t.transform(new DOMSource(xmlDocument), new StreamResult(new FileOutputStream(new File(destFile))));
              
             
               }catch(Exception e){
                   e.printStackTrace();;
               }
       }
        
        public void createElement(String srcFile  , String destFile , String taskId , String taskName , NodeList nodeContent , String nodeType , String currentRole){
            ParseXML parseXML = new ParseXML();
	        XPath xPath =  null;
	        Document xmlDocument = null;
	        try {
	        	
	        	System.out.println("srcFile = "+srcFile);
	        	
                 xmlDocument =  parseXML.getXPathDocumentElement(srcFile);
	             xPath =  XPathFactory.newInstance().newXPath();
	             
	             String expression = "/definitions/process";
	              
	             ArrayList<Node> subNodes = parseXML.getSubNodes(expression, xmlDocument, xPath);
                 Node processNode = subNodes.get(0);
                 
                 Element el = xmlDocument.createElement(nodeType);
                 if(currentRole != null){
                	 el.setAttribute("id", "sid_"+taskName);
                 }else{
                	 el.setAttribute("id", taskId);
                 }
                 el.setAttribute("name", taskName);
                 
               
               
                if(nodeContent != null){
	                for (int i = 0; i < nodeContent.getLength(); i++) {
			               	String nodeName = nodeContent.item(i).getNodeName();
			                String content = 	nodeContent.item(i).getTextContent();
			               	if("incoming".equals(nodeName)){
			               		Element incomingEl = xmlDocument.createElement("incoming");
			               		incomingEl.setTextContent(content);
			               		el.appendChild(incomingEl);
			               	}
			               	
			               	if("outgoing".equals(nodeName)){
			               		Element outgoingEl = xmlDocument.createElement("outgoing");
			               		outgoingEl.setTextContent(content);
			               		el.appendChild(outgoingEl);
			               	}
	               	
	                }
                }
                
                processNode.appendChild(el);
                
                
                if(currentRole != null){
                	System.out.println("currentRole = '"+currentRole+"'");
                	// String expressionLane = "/definitions/process/laneSet/lane[@name='"+currentRole+"']";
                	String expressionLane = "/definitions/process/laneSet/lane[@id='sid-CFD7A966-14BD-48B2-B31D-DD18D1A58A43']";
                	//sid-CFD7A966-14BD-48B2-B31D-DD18D1A58A43
                	 ArrayList<Node> subNodesForLanes = parseXML.getSubNodes(expressionLane, xmlDocument, xPath);
                	 Node laneNode = subNodesForLanes.get(0);
                	 
                	 System.out.println("laneNode =  "+laneNode);
                	 
                	 Element flowNodeRefEl = xmlDocument.createElement("flowNodeRef");
                	 flowNodeRefEl.setTextContent("sid_"+taskName+"eeee");
                	 laneNode.appendChild(flowNodeRefEl);
                	 
                	 System.out.println("1111111111 flowNodeRefEl added");
                	 
                	/* String shapeExpression = "/definitions/BPMNDiagram/BPMNPlane";    
                     NodeList shapeNodeList = (NodeList) xPath.compile(shapeExpression).evaluate(xmlDocument, XPathConstants.NODESET);
                     org.w3c.dom.Node shapeSetNode = shapeNodeList.item(0);
                     
                     System.out.println("shapeSetNode : "+shapeSetNode);
                	 
                	  Element shapeEl =  xmlDocument.createElement("bpmndi:BPMNShape");
                      shapeEl.setAttribute("bpmnElement", "sid_"+taskName);
                      shapeEl.setAttribute("id", "fffff:sid_"+taskName+"_gui");
                      shapeEl.setAttribute("isHorizontal", "true");
                      Element boundsEl = xmlDocument.createElement("omgdc:Bounds");
                      boundsEl.setAttribute("height", "80");
                      boundsEl.setAttribute("width", "100");
                      boundsEl.setAttribute("x", "374");
                      boundsEl.setAttribute("y", "145");
                    //  shapeEl.appendChild(boundsEl);
                      Element labelEl = xmlDocument.createElement("bpmndi:BPMNLabel");
                      Element labelBoundsEl = xmlDocument.createElement("omgdc:Bounds");
                      labelBoundsEl.setAttribute("height", "12");
                      labelBoundsEl.setAttribute("width", "30");
                      labelBoundsEl.setAttribute("x", "125.0");
                      Integer yLabel = Integer.valueOf(145)+36;
                      labelBoundsEl.setAttribute("y", yLabel.toString());
                      
                      labelEl.appendChild(labelBoundsEl);
                      shapeEl.appendChild(labelEl);
                      shapeEl.appendChild(boundsEl);
                      shapeSetNode.appendChild(shapeEl);*/
                }
                
              
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            
            String srcFileWithoutExtension = destFile.substring(0,destFile.lastIndexOf(".")); 
            String destinationFile = srcFileWithoutExtension+"_new.bpmn";
            
          
            
          //  t.transform(new DOMSource(xmlDocument), new StreamResult(new FileOutputStream(new File(destinationFile))));
            t.transform(new DOMSource(xmlDocument), new StreamResult(new FileOutputStream(new File(destFile))));
             
               }catch(Exception e){
                   e.printStackTrace();;
               }
       }
      
    }
    
    
    
   



