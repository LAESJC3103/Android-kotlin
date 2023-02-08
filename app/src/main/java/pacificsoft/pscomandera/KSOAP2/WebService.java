package pacificsoft.pscomandera.KSOAP2;

import android.app.DownloadManager;
import android.net.LocalSocketAddress;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import pacificsoft.pscomandera.Enum.EnumTipoAlmacen;
import pacificsoft.pscomandera.Modelo.LoginComandera;
import pacificsoft.pscomandera.R;
import pacificsoft.pscomandera.serviciosweb.AMTBasicHttpBinding_IService;
import pacificsoft.pscomandera.serviciosweb.AMTExtendedSoapSerializationEnvelope;

public class WebService {
    //Es el WebService que esta publicado
    private String URL = "http://ps-servicios.cloudapp.net/PS_PagoCuenta/Pago.svc";


    private String NAMESPACE = "http://tempuri.org/";
    private String soap_action_serviciosprincipales = NAMESPACE+"IPago/";
    private String soap_action_serviciosterminal = NAMESPACE+"IPSServicioMP/";
    private String METHOD_NAME = "LoginComandera";
    private String SOAP_ACTION = soap_action_serviciosprincipales+METHOD_NAME;
    private int timeOut = 7000;




    public WebService(String URL) {
        this.URL = URL;
    }


    public WebService(String URL, String SOAP_ACTION, String NAMESPACE, String METHOD_NAME) {
        this.URL = URL;
        this.SOAP_ACTION = SOAP_ACTION;
        this.NAMESPACE = NAMESPACE;
        this.METHOD_NAME = METHOD_NAME;

    }

    public String CallServiceHttp(SoapObject Request) throws Exception{

        SoapPrimitive soapResult ;

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.dotNet = true;
        soapEnvelope.setOutputSoapObject(Request);

        HttpTransportSE transport = new HttpTransportSE(URL,timeOut);
        transport.call(SOAP_ACTION,soapEnvelope);

        soapResult = (SoapPrimitive) soapEnvelope.getResponse();

        return soapResult.toString();

    }

    public String Validar() throws Exception{

        METHOD_NAME = "Validar";
        SOAP_ACTION = soap_action_serviciosprincipales+METHOD_NAME;

        SoapObject Request = new SoapObject(NAMESPACE,METHOD_NAME);

        String result = CallServiceHttp(Request);

        return result;

    }


    public String LoginComandera(String loginComandera) throws Exception{

        METHOD_NAME = "LoginComandera";
        SOAP_ACTION = soap_action_serviciosprincipales+METHOD_NAME;

        SoapObject Request = new SoapObject(NAMESPACE,METHOD_NAME);

        Request.addProperty("jsonSolicitud",loginComandera);

        String result = CallServiceHttp(Request);

        return result;

    }

    public String AreasYMesas()throws Exception{
        METHOD_NAME = "AreasYMesas";
        SOAP_ACTION = soap_action_serviciosprincipales+METHOD_NAME;

        SoapObject Request = new SoapObject(NAMESPACE,METHOD_NAME);

        String result = CallServiceHttp(Request);

        return result;
    }

    public String ListaCartas()throws Exception{
        METHOD_NAME = "ListaCartas";
        SOAP_ACTION = soap_action_serviciosprincipales+METHOD_NAME;

        SoapObject Request = new SoapObject(NAMESPACE,METHOD_NAME);

        String result = CallServiceHttp(Request);

        return result;
    }

    public String Cajas()throws Exception{
        METHOD_NAME = "Cajas";
        SOAP_ACTION = soap_action_serviciosprincipales+METHOD_NAME;

        SoapObject Request = new SoapObject(NAMESPACE,METHOD_NAME);

        String result = CallServiceHttp(Request);

        return result;
    }

    public String Almacenes(EnumTipoAlmacen tipoAlmacen)throws Exception{
        METHOD_NAME = "Almacenes";
        SOAP_ACTION = soap_action_serviciosprincipales+METHOD_NAME;

        SoapObject Request = new SoapObject(NAMESPACE,METHOD_NAME);
        Request.addProperty("TipoAlmacen",String.valueOf(tipoAlmacen.getId()));

        String result = CallServiceHttp(Request);

        return result;
    }

    public String PrecioArt(String idArticulo)throws Exception{
        METHOD_NAME = "PrecioArt";
        SOAP_ACTION = soap_action_serviciosprincipales+METHOD_NAME;

        SoapObject Request = new SoapObject(NAMESPACE,METHOD_NAME);
        Request.addProperty("idArticulo",idArticulo);

        String result = CallServiceHttp(Request);

        return result;
    }

    public String OrdenComanda(String jsonSolicitud)throws Exception{
        METHOD_NAME = "OrdenComanda";
        SOAP_ACTION = soap_action_serviciosprincipales+METHOD_NAME;

        SoapObject Request = new SoapObject(NAMESPACE,METHOD_NAME);
        Request.addProperty("jsonSolicitud",jsonSolicitud);

        String result = CallServiceHttp(Request);

        return result;
    }

    public String CajasCerrar(String idCaja)throws Exception{
        METHOD_NAME = "CajasCerrar";
        SOAP_ACTION = soap_action_serviciosprincipales+METHOD_NAME;

        SoapObject Request = new SoapObject(NAMESPACE,METHOD_NAME);
        Request.addProperty("idCaja",idCaja);

        String result = CallServiceHttp(Request);

        return result;
    }

    public String TomarMesa(String idMesa,String Cuentas)throws Exception{
        METHOD_NAME = "TomarMesa";
        SOAP_ACTION = soap_action_serviciosprincipales+METHOD_NAME;

        SoapObject Request = new SoapObject(NAMESPACE,METHOD_NAME);
        Request.addProperty("idMesa",idMesa);
        Request.addProperty("Cuentas",Cuentas);

        String result = CallServiceHttp(Request);

        return result;
    }

    public String EstructuraModificadores(String idReceta)throws Exception{
        METHOD_NAME = "EstructuraModificadores";
        SOAP_ACTION = soap_action_serviciosprincipales+METHOD_NAME;

        SoapObject Request = new SoapObject(NAMESPACE,METHOD_NAME);
        Request.addProperty("idReceta",idReceta);

        String result = CallServiceHttp(Request);

        return result;
    }

//    public String ListaCuentas(String idMesa)throws Exception{
//        METHOD_NAME = "ListaCuentas";
//        SOAP_ACTION = soap_action_serviciosprincipales+METHOD_NAME;
//
//        SoapObject Request = new SoapObject(NAMESPACE,METHOD_NAME);
//        Request.addProperty("idMesa",idMesa);
//
//        String result = CallServiceHttp(Request);
//
//        return result;
//    }

    public String Notas(String codigo) throws Exception{
        METHOD_NAME = "Notas";
        SOAP_ACTION = soap_action_serviciosprincipales+METHOD_NAME;

        SoapObject Request = new SoapObject(NAMESPACE,METHOD_NAME);
        Request.addProperty("codigo",codigo);

        String result = CallServiceHttp(Request);

        return result;
    }


    public String ValidaWS() throws Exception{

        METHOD_NAME = "ValidaWS";
        SOAP_ACTION = soap_action_serviciosterminal+METHOD_NAME;

        SoapObject Request = new SoapObject(NAMESPACE,METHOD_NAME);

        String result = CallServiceHttp(Request);

        return result;

    }

    public String DatosTerminal(String terminal) throws Exception{

        METHOD_NAME = "DatosTerminal";
        SOAP_ACTION = soap_action_serviciosterminal+METHOD_NAME;

        SoapObject Request = new SoapObject(NAMESPACE,METHOD_NAME);

        Request.addProperty("terminal",terminal);

        String result = CallServiceHttp(Request);

        return result;

    }
}

