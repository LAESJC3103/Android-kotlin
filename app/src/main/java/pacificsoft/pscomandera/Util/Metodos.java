package pacificsoft.pscomandera.Util;

import android.content.res.Resources;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import pacificsoft.pscomandera.Modelo.DatosRegresados;
import pacificsoft.pscomandera.Modelo.Terminal;
import pacificsoft.pscomandera.R;

public class Metodos {
    public DatosRegresados extraerDatosTerminal(String xmlRespuestaDatosTerminal){
        DatosRegresados datosRegresados = new DatosRegresados();
        datosRegresados.setError(false);
        try {


            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builderDoc;

            try {

                try {
                    if (!xmlRespuestaDatosTerminal.equals("")) {

                        factory = DocumentBuilderFactory.newInstance();

                        builderDoc = factory.newDocumentBuilder();

                        //<Datos><Terminal Id_Proveedor="9" Serie="" Cliente="PSO176372" /><Estatus Id="0" Mensaje="" /></Datos>

                        Document document = builderDoc.parse(new InputSource(new StringReader(xmlRespuestaDatosTerminal)));
                        Element ValidaFolioEnEsperaResult = (Element) document.getElementsByTagName("Datos").item(0);
                        Element estatus = (Element) ValidaFolioEnEsperaResult.getElementsByTagName("Estatus").item(0);
                        Integer codigo = Integer.valueOf(estatus.getAttribute("Id"));
                        String respuesta = estatus.getAttribute("Mensaje");


                        if (codigo.equals(0)) {
                            Element terminalXML = (Element) ValidaFolioEnEsperaResult.getElementsByTagName("Terminal").item(0);

                            Terminal terTemp = new Terminal();

                            terTemp.setCliente(terminalXML.getAttribute("Cliente"));
                            terTemp.setSerie(terminalXML.getAttribute("Serie"));
                            terTemp.setProveedor( terminalXML.getAttribute("Id_Proveedor"));


                            datosRegresados.setDatosRegresados(terTemp);

//                                claveUsuario = terminal.getAttribute("Cliente");
//                                noSerie = terminal.getAttribute("Serie");
//                                proveedorXML = terminal.getAttribute("Id_Proveedor");
                        } else {
                            throw new Exception(respuesta);
                        }
                    } else {
                        throw new Exception(Resources.getSystem().getString(R.string.msj_error_no_hay_resultado));
                    }
                } catch (Exception e) {
                    datosRegresados.setError(true);
                    datosRegresados.setMsjError(e.getMessage());
                }
            } catch (Exception e) {
                datosRegresados.setError(true);
                datosRegresados.setMsjError(Resources.getSystem().getString(R.string.msj_error_al_consultar_terminal));

            }
        } catch (Exception e) {
            datosRegresados.setError(true);
            datosRegresados.setMsjError(e.getMessage());
        }

        return datosRegresados;
    }
}
