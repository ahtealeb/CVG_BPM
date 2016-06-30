/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cvg;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

/**
 *
 * @author mahmoud
 */
public class ReBuild {
      public void createLane(String file , String laneName ,String nameFlag ,String laneId, ArrayList<cvg.Node> orderedNodes , String y , int counter ){
             ParseXML parseXML = new ParseXML();
	        XPath xPath =  null;
	        Document xmlDocument = null;
	        try {
                      xmlDocument =  parseXML.getXPathDocumentElement(file);
	                  xPath =  XPathFactory.newInstance().newXPath();
                      String expression = "/definitions/process/laneSet";
                      NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
                      org.w3c.dom.Node laneSetNode = nodeList.item(0);
                      
                       String shapeExpression = "/definitions/BPMNDiagram/BPMNPlane";    
                          NodeList shapeNodeList = (NodeList) xPath.compile(shapeExpression).evaluate(xmlDocument, XPathConstants.NODESET);
                          org.w3c.dom.Node shapeSetNode = shapeNodeList.item(0);
                          
                          
                      Element laneEl = xmlDocument.createElement("lane");
                      laneEl.setAttribute("id", laneId+nameFlag);
                      laneEl.setAttribute("name", laneName+nameFlag);
                      
                          Element extensionElement_lane = xmlDocument.createElement("extensionElements");
                          laneEl.appendChild(extensionElement_lane);
                          Element  signavioMetaData_lane = xmlDocument.createElement("signavio:signavioMetaData");
                          signavioMetaData_lane.setAttribute("metaKey", "bgcolor");
                          signavioMetaData_lane.setAttribute("metaValue", "#ffffff");
                          extensionElement_lane.appendChild(signavioMetaData_lane);
                          
                          Element  signavioLabel_lane = xmlDocument.createElement("signavio:signavioLabel");
                          signavioLabel_lane.setAttribute("bold", "true");
                          signavioLabel_lane.setAttribute("fill", "");
                          signavioLabel_lane.setAttribute("fontFamily", "");
                          signavioLabel_lane.setAttribute("fontSize", "");
                          signavioLabel_lane.setAttribute("italic", "");
                          signavioLabel_lane.setAttribute("ref", "text_name");
                          extensionElement_lane.appendChild(signavioLabel_lane);
                          
                               Element shapeEl =  xmlDocument.createElement("bpmndi:BPMNShape");
                                      shapeEl.setAttribute("bpmnElement", laneId+nameFlag);
                                      shapeEl.setAttribute("id", laneId+nameFlag+"_gui");
                                      shapeEl.setAttribute("isHorizontal", "true");
                                      Element boundsEl = xmlDocument.createElement("omgdc:Bounds");
                                      boundsEl.setAttribute("height", "162");
                                      boundsEl.setAttribute("width", "736");
                                      boundsEl.setAttribute("x", "120");
                                      boundsEl.setAttribute("y", y);
                                    //  shapeEl.appendChild(boundsEl);
                                      Element labelEl = xmlDocument.createElement("bpmndi:BPMNLabel");
                                      Element labelBoundsEl = xmlDocument.createElement("omgdc:Bounds");
                                      labelBoundsEl.setAttribute("height", "92");
                                      labelBoundsEl.setAttribute("width", "12");
                                      labelBoundsEl.setAttribute("x", "125.0");
                                      Integer yLabel = Integer.valueOf(y)+36;
                                      labelBoundsEl.setAttribute("y", yLabel.toString());
                                      labelEl.appendChild(labelBoundsEl);
                                      shapeEl.appendChild(labelEl);
                                      shapeEl.appendChild(boundsEl);
                                      shapeSetNode.appendChild(shapeEl);
                          
                       for(int i = 0 ; i < orderedNodes.size() ; i++){   
                            Element flowNodeRef = xmlDocument.createElement("flowNodeRef");
                            flowNodeRef.setTextContent(orderedNodes.get(i).getNodeId()+nameFlag);
                            laneEl.appendChild(flowNodeRef);
                       }
                 
                      
                      laneSetNode.appendChild(laneEl);
                      
                          //String processExpression = "/definitions/process";    
                         // NodeList processNodeList = (NodeList) xPath.compile(processExpression).evaluate(xmlDocument, XPathConstants.NODESET);
                          //org.w3c.dom.Node procesSetNode = processNodeList.item(0);
                          
                      
                          
                       
                           createLaneItems( xPath, xmlDocument, orderedNodes,nameFlag  , Integer.valueOf(y) , counter);
                          
                          
                        /*  Element sid_seq_flow_01  = xmlDocument.createElement("sequenceFlow");
                          sid_seq_flow_01.setAttribute("id", "sid_seq_flow_01");
                          sid_seq_flow_01.setAttribute("name", "");
                          sid_seq_flow_01.setAttribute("sourceRef", "sid_16");
                          sid_seq_flow_01.setAttribute("targetRef", "sid_17");
                          procesSetNode.appendChild(sid_seq_flow_01);
                          
                           */
                         
                           
                         
                           
                           
                         
                         TransformerFactory tf = TransformerFactory.newInstance();
                         Transformer t = tf.newTransformer();
                         t.transform(new DOMSource(xmlDocument), new StreamResult(new FileOutputStream(new File(file))));
                         System.out.println("Lane Created");
                }catch(Exception e){
                    e.printStackTrace();
                }
        }
      
      public void updateLastLanePosition(String sourceFile , String destFile, Integer y){
    	  ParseXML parseXML = new ParseXML();
	        XPath xPath =  null;
	        Document xmlDocument = null;
	        try {
	              xmlDocument =  parseXML.getXPathDocumentElement(sourceFile);
		          xPath =  XPathFactory.newInstance().newXPath();
	              ArrayList<Fragment> allOldFragments = parseXML.loadAllFragments(sourceFile);
	              Collections.sort(allOldFragments);
	              
	              
	              xmlDocument =  parseXML.getXPathDocumentElement(destFile);
		          xPath =  XPathFactory.newInstance().newXPath();
	              
	              
	              String lastLaneShapeExpression = "/definitions/BPMNDiagram/BPMNPlane/BPMNShape[@bpmnElement='"+allOldFragments.get(allOldFragments.size()-1).getFragementId()+"']";
	               ArrayList<org.w3c.dom.Node> shapeNodes = parseXML.getSubNodes(lastLaneShapeExpression, xmlDocument, xPath);
	               
	                  for(org.w3c.dom.Node shapeNode : shapeNodes){
	                  	
	                       org.w3c.dom.Node boundsNode =  shapeNode.getChildNodes().item(1);
	                       NamedNodeMap boundAttr = boundsNode.getAttributes();
	                       //update y
                           boundAttr.item(3).setNodeValue(y.toString());
                   }
	                  
	                  // update for sub nodes
	                  
	                  ArrayList<Node> lastLaneNodes = allOldFragments.get(allOldFragments.size()-1).getNodes();
	                  for(Node node : lastLaneNodes){
	                	  String nodeName = node.getNodeName();
	                	  System.out.println("Node Name = "+nodeName);
	                	  lastLaneShapeExpression = "/definitions/BPMNDiagram/BPMNPlane/BPMNShape[@bpmnElement='"+node.getNodeId()+"']";
	   	                  shapeNodes = parseXML.getSubNodes(lastLaneShapeExpression, xmlDocument, xPath);
	   	               
	   	                  for(org.w3c.dom.Node shapeNode : shapeNodes){
	   	                  	
	   	                       org.w3c.dom.Node boundsNode =  shapeNode.getChildNodes().item(1);
	   	                       NamedNodeMap boundAttr = boundsNode.getAttributes();
	   	                       //update y
	   	                    Integer yy = y+50;
	   	                       if("endEvent".equals(nodeName)){
	   	                            yy = y+75;
	   	                       } 
	                              boundAttr.item(3).setNodeValue(yy.toString()); 
	                              /*
	                            ArrayList<Incoming> ins = node.getIncomings();
	                            for(Incoming in : ins){
	                            	System.out.println("in updated");
	                            	  lastLaneShapeExpression = "/definitions/BPMNDiagram/BPMNPlane/BPMNEdge[@bpmnElement='"+in.getIncomingId()+"']";
	        	   	                  shapeNodes = parseXML.getSubNodes(lastLaneShapeExpression, xmlDocument, xPath);
	        	   	               for(org.w3c.dom.Node inNode : shapeNodes){
	        	   	            	  org.w3c.dom.Node inBoundsNode =  inNode.getChildNodes().item(1);
	  	   	                          NamedNodeMap inBoundAttr = inBoundsNode.getAttributes();
	  	   	                          Integer yy_in = y+50;
	  	   	                          inBoundAttr.item(1).setNodeValue(yy_in.toString()); 
	  	   	                          
	  	   	                          inBoundsNode =  inNode.getChildNodes().item(2);
 	   	                             inBoundAttr = inBoundsNode.getAttributes();
 	   	                              inBoundAttr.item(1).setNodeValue(yy_in.toString());
	        	   	               }
	        	   	                  
	                            }
	                            
	                            */
	                            ArrayList<Outgoing> outs = node.getOutgoings();
	                            for(Outgoing out : outs){
	                            	String  lastLaneOutShapeExpression = "/definitions/BPMNDiagram/BPMNPlane/BPMNEdge[@bpmnElement='"+out.getOutgoingId()+"']";
	        	   	                  shapeNodes = parseXML.getSubNodes(lastLaneOutShapeExpression, xmlDocument, xPath);
	        	   	               for(org.w3c.dom.Node outNode : shapeNodes){
	        	   	            	   
	        	   	            	  org.w3c.dom.Node wayPoint1 =  outNode.getChildNodes().item(1);
	  	   	                          NamedNodeMap outBoundAttr = wayPoint1.getAttributes();
	  	   	                          Integer yy_in = y+80;
	  	   	                          outBoundAttr.item(1).setNodeValue(yy_in.toString()); 
	  	   	                          
	  	   	                         org.w3c.dom.Node wayPoint2 =  outNode.getChildNodes().item(3);
	  	   	                          outBoundAttr = wayPoint2.getAttributes();
	  	   	                          outBoundAttr.item(1).setNodeValue(yy_in.toString());
	        	   	               }
	        	   	                  
	                            }
	                            
	                           
	                      }
	   	                  
	   	                   
	                  }
	                  
	                  System.out.println("the last lane "+allOldFragments.get(allOldFragments.size()-1).getFragementId()+ " position y updated to "+y);
	                  TransformerFactory tf = TransformerFactory.newInstance();
                      Transformer t = tf.newTransformer();
                      t.transform(new DOMSource(xmlDocument), new StreamResult(new FileOutputStream(new File(destFile))));
    	  }catch(Exception e) {
    		  e.printStackTrace();
    	  }
      }
      
      ArrayList<Element> gateWayElements = new ArrayList<Element>();
      ArrayList<Integer> gateWayXs = new ArrayList<Integer>();
      ArrayList<Integer> gateWayYs = new ArrayList<Integer>(); 
      private void createLaneItems(XPath xPath  , Document xmlDocument , ArrayList<cvg.Node> orderedNodes ,  String nameFlag , Integer y , int counter) throws Exception{
                          String processExpression = "/definitions/process";    
                          NodeList processNodeList = (NodeList) xPath.compile(processExpression).evaluate(xmlDocument, XPathConstants.NODESET);
                          org.w3c.dom.Node procesSetNode = processNodeList.item(0);
                          
                          String shapeExpression = "/definitions/BPMNDiagram/BPMNPlane";    
                          NodeList shapeNodeList = (NodeList) xPath.compile(shapeExpression).evaluate(xmlDocument, XPathConstants.NODESET);
                          org.w3c.dom.Node shapeSetNode = shapeNodeList.item(0);
                          ArrayList<String> addedBeforeSequences = new ArrayList<String>();
                             Integer x = 285;
                             
                             ArrayList<String> allNodesIds = new ArrayList<String>();
                            for(Node node : orderedNodes){
                                allNodesIds.add(node.getNodeId());
                            }
                            
                           // Collections.sort(orderedNodes);
                            
                            
                          for(int i = 0 ; i < orderedNodes.size() ; i++){
                              Node node = orderedNodes.get(i);
                              Element el = null;
                              System.out.println("node name = "+node.getNodeName());
                                if("task".equals(node.getNodeName())){
                                 
                                        el = xmlDocument.createElement(node.getNodeName());
                                        el.setAttribute("completionQuantity", "1");
                                        el.setAttribute("id", node.getNodeId()+nameFlag);
                                        el.setAttribute("isForCompensation", "false");
                                        el.setAttribute("name", node.getNodeTitle());
                                        el.setAttribute("startQuantity", "1");
                                        procesSetNode.appendChild(el);


                                      Element extension = xmlDocument.createElement("extensionElements");
                                        el.appendChild(extension);
                                        Element  signavioMetaData = xmlDocument.createElement("signavio:signavioMetaData");
                                        signavioMetaData.setAttribute("metaKey", "bgcolor");
                                        signavioMetaData.setAttribute("metaValue", "#ffffcc");
                                        extension.appendChild(signavioMetaData);
                                        el.appendChild(extension);
                                      Element shapeEl =  xmlDocument.createElement("bpmndi:BPMNShape");
                                      shapeEl.setAttribute("bpmnElement", node.getNodeId()+nameFlag);
                                      shapeEl.setAttribute("id", node.getNodeId()+nameFlag+"_gui");
                                      Element boundsEl = xmlDocument.createElement("omgdc:Bounds");
                                      boundsEl.setAttribute("height", "80.0");
                                      boundsEl.setAttribute("width", "100.0");
                                      boundsEl.setAttribute("x", x.toString());
                                      boundsEl.setAttribute("y", String.valueOf(y+15));
                                      shapeEl.appendChild(boundsEl);
                                      Element labelEl = xmlDocument.createElement("bpmndi:BPMNLabel");
                                      Element labelBoundsEl = xmlDocument.createElement("omgdc:Bounds");
                                      labelBoundsEl.setAttribute("height", "36.0");
                                      labelBoundsEl.setAttribute("width", "55.71428680419922");
                                      labelBoundsEl.setAttribute("x", x.toString());
                                      labelBoundsEl.setAttribute("y", String.valueOf(y+15));
                                      labelEl.appendChild(labelBoundsEl);
                                      shapeEl.appendChild(labelEl);
                                      shapeSetNode.appendChild(shapeEl);
                                      x += 255;
                                       
                                }else if ("endEvent".equals(node.getNodeName())){
                                     el = xmlDocument.createElement(node.getNodeName());
                                         el.setAttribute("id", node.getNodeId()+nameFlag);
                                        el.setAttribute("name", "End");
                                        procesSetNode.appendChild(el);


                                        Element extension = xmlDocument.createElement("extensionElements");
                                        el.appendChild(extension);
                                        Element  signavioMetaData = xmlDocument.createElement("signavio:signavioMetaData");
                                        signavioMetaData.setAttribute("metaKey", "bgcolor");
                                        signavioMetaData.setAttribute("metaValue", "#ffffff");
                                        extension.appendChild(signavioMetaData); 
                                        el.appendChild(extension);
                                         Element shapeEl =  xmlDocument.createElement("bpmndi:BPMNShape");
                                      shapeEl.setAttribute("bpmnElement", node.getNodeId()+nameFlag);
                                      shapeEl.setAttribute("id", node.getNodeId()+nameFlag+"_gui");
                                      Element boundsEl = xmlDocument.createElement("omgdc:Bounds");
                                      boundsEl.setAttribute("height", "80.0");
                                      boundsEl.setAttribute("width", "100.0");
                                      boundsEl.setAttribute("x", "710");
                                      boundsEl.setAttribute("y", String.valueOf(y+15));
                                      shapeEl.appendChild(boundsEl);
                                      Element labelEl = xmlDocument.createElement("bpmndi:BPMNLabel");
                                      Element labelBoundsEl = xmlDocument.createElement("omgdc:Bounds");
                                      labelBoundsEl.setAttribute("height", "36.0");
                                      labelBoundsEl.setAttribute("width", "55.71428680419922");
                                      labelBoundsEl.setAttribute("x", "710");
                                      labelBoundsEl.setAttribute("y", String.valueOf(y+15));
                                      labelEl.appendChild(labelBoundsEl);
                                      shapeEl.appendChild(labelEl);
                                      shapeSetNode.appendChild(shapeEl);
                                      x += 255;
                                }else if("exclusiveGateway".equals(node.getNodeName())){
                                       el = xmlDocument.createElement(node.getNodeName());
                                       el.setAttribute("id", node.getNodeId()+nameFlag);
                                        el.setAttribute("name", "Approve");
                                        procesSetNode.appendChild(el);
                                        
                                       Element extension = xmlDocument.createElement("extensionElements");
                                        el.appendChild(extension);
                                        Element  signavioMetaData = xmlDocument.createElement("signavio:signavioMetaData");
                                        signavioMetaData.setAttribute("metaKey", "bgcolor");
                                        signavioMetaData.setAttribute("metaValue", "#ffffcc");
                                        extension.appendChild(signavioMetaData); 
                                        el.appendChild(extension);

                                        Element  extension_label = xmlDocument.createElement("signavio:signavioLabel");
                                        extension_label.setAttribute("align", "center");
                                        extension_label.setAttribute("ref", "text_name");
                                        extension_label.setAttribute("valign", "middle");
                                        extension_label.setAttribute("x", "-13.0");
                                        extension_label.setAttribute("y", "-22.0");
                                       
                                        extension.appendChild(extension_label); 
                                        el.appendChild(extension_label);
                                        
                                      Element shapeEl =  xmlDocument.createElement("bpmndi:BPMNShape");
                                      shapeEl.setAttribute("bpmnElement", node.getNodeId()+nameFlag);
                                      shapeEl.setAttribute("id", node.getNodeId()+nameFlag+"_gui");
                                      Element boundsEl = xmlDocument.createElement("omgdc:Bounds");
                                      boundsEl.setAttribute("height", "80.0");
                                      boundsEl.setAttribute("width", "100.0");
                                      boundsEl.setAttribute("x", "430");
                                      boundsEl.setAttribute("y", String.valueOf(y+15));
                                      shapeEl.appendChild(boundsEl);
                                      Element labelEl = xmlDocument.createElement("bpmndi:BPMNLabel");
                                      Element labelBoundsEl = xmlDocument.createElement("omgdc:Bounds");
                                      labelBoundsEl.setAttribute("height", "36.0");
                                      labelBoundsEl.setAttribute("width", "55.71428680419922");
                                      labelBoundsEl.setAttribute("x", "430");
                                      labelBoundsEl.setAttribute("y", String.valueOf(y+15));
                                      labelEl.appendChild(labelBoundsEl);
                                      shapeEl.appendChild(labelEl);
                                      shapeSetNode.appendChild(shapeEl);
                                  
                                      x += 255;
                                      gateWayElements.add(el);
                                      gateWayYs.add(y+15);
                                      gateWayXs.add(x);
                                   
                                }
                                ArrayList<Incoming> incomings = node.getIncomings();
                                if(incomings != null ){
                                    
                                    for(Incoming in : incomings){
                                        if(allNodesIds.contains(in.getSourceId()) == false){
                                               continue;
                                          }
                                    
                                            
                                          Element incomingEl = xmlDocument.createElement("incoming");
                                          incomingEl.setTextContent(in.getIncomingId()+nameFlag);
                                          el.appendChild(incomingEl);  
                                          if(! addedBeforeSequences.contains(in.getIncomingId()+nameFlag)){
                                              
                                               addedBeforeSequences.add(in.getIncomingId()+nameFlag);
                                                    Element seq_flow  = xmlDocument.createElement("sequenceFlow");
                                                    seq_flow.setAttribute("id", in.getIncomingId()+nameFlag);
                                                    seq_flow.setAttribute("name", "");
                                                    seq_flow.setAttribute("sourceRef", in.getSourceId()+nameFlag);
                                                    seq_flow.setAttribute("targetRef", in.getTargetId()+nameFlag);
                                                    procesSetNode.appendChild(seq_flow);


                                                    Element shapeEl =  xmlDocument.createElement("bpmndi:BPMNEdge");
                                                    shapeEl.setAttribute("bpmnElement", in.getIncomingId()+nameFlag);
                                                    shapeEl.setAttribute("id", in.getIncomingId()+nameFlag+"_gui");
                                                    Element boundsEl = xmlDocument.createElement("omgdi:waypoint");
                                                    boundsEl.setAttribute("x", String.valueOf(x+30));
                                                    boundsEl.setAttribute("y", String.valueOf(y+50));
                                                    shapeEl.appendChild(boundsEl);

                                                    Element boundsEl1 = xmlDocument.createElement("omgdi:waypoint");
                                                    boundsEl1.setAttribute("x", String.valueOf(x+45));
                                                    boundsEl1.setAttribute("y", String.valueOf(y+50));
                                                    shapeEl.appendChild(boundsEl1);

                                                    shapeSetNode.appendChild(shapeEl);
                                          } // added before     
                                    }
                                }
                                
                                ArrayList<Outgoing> outgoings = node.getOutgoings();
                                 if(outgoings != null){
                                        for(Outgoing out : outgoings){
                                           
	                                            if(allNodesIds.contains(out.getTargetId()) == false){
	                                                  continue;
	                                             }
                                            Element outgoingEl = xmlDocument.createElement("outgoing");
                                              outgoingEl.setTextContent(out.getOutgoingId()+nameFlag);
                                              el.appendChild(outgoingEl); 
                                               if(! addedBeforeSequences.contains(out.getOutgoingId()+nameFlag)){
                                                 addedBeforeSequences.add(out.getOutgoingId()+nameFlag);
                                                  Element seq_flow  = xmlDocument.createElement("sequenceFlow");
                                                  seq_flow.setAttribute("id", out.getOutgoingId()+nameFlag);
                                                  if("exclusiveGateway".equals(node.getNodeName())){
                                                	  seq_flow.setAttribute("name", "No");
                                                  }else {
                                                	  seq_flow.setAttribute("name", "");
                                                  }
                                                  seq_flow.setAttribute("sourceRef", out.getSourceId()+nameFlag);
                                                  seq_flow.setAttribute("targetRef", out.getTargetId()+nameFlag);
                                                  procesSetNode.appendChild(seq_flow);
                                                
                                                  Element shapeEl =  xmlDocument.createElement("bpmndi:BPMNEdge");
                                                  shapeEl.setAttribute("bpmnElement", out.getOutgoingId()+nameFlag);
                                                  shapeEl.setAttribute("id", out.getOutgoingId()+nameFlag+"_gui");
                                                  Element boundsEl = xmlDocument.createElement("omgdi:waypoint");
                                                  boundsEl.setAttribute("x",  String.valueOf(x+30));
                                                  boundsEl.setAttribute("y", String.valueOf(y+50));
                                                  shapeEl.appendChild(boundsEl);

                                                  Element boundsEl1 = xmlDocument.createElement("omgdi:waypoint");
                                                  boundsEl1.setAttribute("x", String.valueOf(x+45));
                                                  boundsEl1.setAttribute("y", String.valueOf(y+50));
                                                  shapeEl.appendChild(boundsEl1);

                                                  shapeSetNode.appendChild(shapeEl);
                                               }  // added before
                                               else {
                                            	   // update label
                                            	   if("exclusiveGateway".equals(node.getNodeName())){
                                            		    String refExpression = "/definitions/process/*[@id='"+out.getOutgoingId()+nameFlag+"']";
	                                	               ArrayList<org.w3c.dom.Node> shapeNodes = new ParseXML().getSubNodes(refExpression, xmlDocument, xPath);
	                                	               
	                                	                  for(org.w3c.dom.Node shapeNode : shapeNodes){
	                                	                  	
	                                	                       
	                                	                       NamedNodeMap boundAttr = shapeNode.getAttributes();
	                                	                       //update y
	                                                           boundAttr.item(1).setNodeValue("No");
	                                                           System.out
																	.println("label added");
	                                                   }
                                            	   }
                                	                  
                                               }
                                        }
                                }
                                
                          }
      }
      
      
      
      public void createSequenceFlow( Node gateWayNode ,  Node taskNode , String file , int fragmentCounter, Fragment fragment , boolean isFirst){
    	  System.out.println("fragmentCounter = "+fragmentCounter);
    	   
    	   ParseXML parseXML = new ParseXML();
	        XPath xPath =  null;
	        Document xmlDocument = null;
	        try {
	            xmlDocument =  parseXML.getXPathDocumentElement(file);
		        xPath =  XPathFactory.newInstance().newXPath();
		       // Element el = gateWayElements.get(fragmentCounter);
		        String processExpression = "/definitions/process";    
                NodeList processNodeList = (NodeList) xPath.compile(processExpression).evaluate(xmlDocument, XPathConstants.NODESET);
                org.w3c.dom.Node procesSetNode = processNodeList.item(0);
                
                System.out.println();
                
                 String gateWayExpression = "/definitions/process/exclusiveGateway[@id='"+gateWayNode.getNodeId()+"']";    
                NodeList gateWayNodeList = (NodeList) xPath.compile(gateWayExpression).evaluate(xmlDocument, XPathConstants.NODESET);
                org.w3c.dom.Node gateWayDomNode = gateWayNodeList.item(0);
                Element gateElement = (Element)gateWayDomNode;
                
                
                String taskExpression = "/definitions/process/task[@id='"+taskNode.getNodeId()+"']";    
                NodeList taskNodeList = (NodeList) xPath.compile(taskExpression).evaluate(xmlDocument, XPathConstants.NODESET);
                org.w3c.dom.Node taskDomNode = taskNodeList.item(0);
                Element taskElement = (Element)taskDomNode;
               // System.out.println("gateWayNode.getNodeId() = "+gateWayNode.getNodeId());
               // Element el = xmlDocument.getElementById(gateWayNode.getNodeId());
               
                
                String shapeExpression = "/definitions/BPMNDiagram/BPMNPlane";    
                NodeList shapeNodeList = (NodeList) xPath.compile(shapeExpression).evaluate(xmlDocument, XPathConstants.NODESET);
                org.w3c.dom.Node shapeSetNode = shapeNodeList.item(0);
                
		        Element outgoingEl = xmlDocument.createElement("outgoing");
                outgoingEl.setTextContent("newSequence"+fragmentCounter);
                gateElement.appendChild(outgoingEl);
                
                Element incomingEl = xmlDocument.createElement("incoming");
                incomingEl.setTextContent("newSequence"+fragmentCounter);
                taskElement.appendChild(incomingEl);  
                
                
                    Element seq_flow  = xmlDocument.createElement("sequenceFlow");
                    seq_flow.setAttribute("id", "newSequence"+fragmentCounter);
                    seq_flow.setAttribute("name", isFirst ? "Change Request": "Yes");
                    seq_flow.setAttribute("sourceRef", gateWayNode.getNodeId());
                    seq_flow.setAttribute("targetRef", taskNode.getNodeId());
                    procesSetNode.appendChild(seq_flow);
                  
                    
                    
                    
                    Element shapeEl =  xmlDocument.createElement("bpmndi:BPMNEdge");
                    shapeEl.setAttribute("bpmnElement","newSequence"+fragmentCounter);
                    shapeEl.setAttribute("id", "newSequence"+fragmentCounter+"_gui");
                    Element boundsEl = xmlDocument.createElement("omgdi:waypoint");
                    boundsEl.setAttribute("x", String.valueOf(fragment.getX()));
                    boundsEl.setAttribute("y", String.valueOf(fragment.getY()+30));
                    shapeEl.appendChild(boundsEl);

                   
                    Element boundsEl1 = xmlDocument.createElement("omgdi:waypoint");
                    boundsEl1.setAttribute("x", String.valueOf(fragment.getX()));
                    boundsEl1.setAttribute("y", String.valueOf(fragment.getY()+30));
                    shapeEl.appendChild(boundsEl1);

                    shapeSetNode.appendChild(shapeEl);
                    
                    
                    TransformerFactory tf = TransformerFactory.newInstance();
                    Transformer t = tf.newTransformer();
                    t.transform(new DOMSource(xmlDocument), new StreamResult(new FileOutputStream(new File(file))));
                    
                    System.out.println("sequence created");
                    
          }catch(Exception e){
        	  e.printStackTrace();
          }
      }
}
