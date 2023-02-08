package pacificsoft.pscomandera.Util;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class MetodosDOM {
  Document dom;
  // lee el string que regresa el web service y lo transforma en xml para leerlo mas facilmente
  public Document xmlDOM(String XML) {

    DocumentBuilder db = null;
    try {
      db = DocumentBuilderFactory.newInstance().newDocumentBuilder();

      InputSource is = new InputSource();
      is.setCharacterStream(new StringReader(XML));

      dom = null;
      try {
        dom = db.parse(is);
      } catch (SAXException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }

    } catch (Exception w) {

    }
    return dom;
  }

}
