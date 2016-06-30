/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cvg;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import org.w3c.dom.*;
import org.w3c.dom.Node;

/**
 *
 * @author mahmoud
 */
public class TestDelete {
    public static void main(String[] args) {
        try {
         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document document = dbf.newDocumentBuilder().parse(new File("c:\\tealeb\\test.xml"));

        XPathFactory xpf = XPathFactory.newInstance();
        XPath xpath = xpf.newXPath();
       // XPathExpression expression = xpath.compile("//A/B[C/E/text()=13]");
         XPathExpression expression = xpath.compile("//note/x");
        Node b13Node = (Node) expression.evaluate(document, XPathConstants.NODE);
        b13Node.getParentNode().removeChild(b13Node);

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = tf.newTransformer();
        //  t.transform(new DOMSource(document), new StreamResult(System.out));
         t.transform(new DOMSource(document), new StreamResult(new FileOutputStream(new File("c:\\tealeb\\test1.xml"))));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
