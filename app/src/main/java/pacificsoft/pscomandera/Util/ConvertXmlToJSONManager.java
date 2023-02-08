package pacificsoft.pscomandera.Util;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

/**
 * Created by desarrollo on 12/21/2017.
 */

public class ConvertXmlToJSONManager {
    public static List<String> Convertidor(String xml,String campo,String Objeto) {
        List<String> listaRes = new ArrayList<>();
        try {
            MetodosDOM dom = new MetodosDOM();


            Document doc =dom.xmlDOM( xml );
            doc.getDocumentElement().normalize();
            XPath xPath =  XPathFactory.newInstance().newXPath();
            String expression = "/DATOS/"+Objeto;
            NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(
                    doc, XPathConstants.NODESET);

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node nNode = nodeList.item( i );
                System.out.println( "\nCurrent Element :" + nNode.getNodeName() );

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    listaRes.add( eElement.getAttribute( campo ) );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaRes;
    }
}
