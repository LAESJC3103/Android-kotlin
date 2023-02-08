package pacificsoft.pscomandera.Impresion;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.util.Base64;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Metodos {

    public String codificar(String text) {


// Sending side
        byte[] data = null;
        try {
            data = text.getBytes("iso-8859-1");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String base64 = Base64.encodeToString(data, Base64.DEFAULT);
        return base64;
    }

    public String decodificar(String text) {
// Receiving side
        byte[] data1 = Base64.decode(text, Base64.DEFAULT);
        String text1 = null;
        try {
            text1 = new String(data1, "iso-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return text1;
    }

    public String leerArchivo(Context context, Uri uri) {
        String cadena = "";
        try {
            ContentResolver cr = context.getContentResolver();
            // Abrimos el archivo
            InputStream fstream = cr.openInputStream(uri);

            byte[] data = readBytes(fstream);
            // returns encrypted data
            return Base64.encodeToString(data, Base64.DEFAULT);
            /*// Creamos el objeto de entrada
            InputStreamReader entrada = new InputStreamReader(fstream, "iso-8859-image_nav_header_default_comanda");
            // Creamos el Buffer de Lectura
            BufferedReader buffer = new BufferedReader(entrada);

            String strLinea;
            // Leer el archivo linea por linea
            while ((strLinea = (buffer.readLine())) != null) {
                // Imprimimos la línea por pantalla
                cadena = cadena + strLinea;
            }
            entrada.close();*/

        } catch (Exception e) { //Catch de excepciones
            System.err.println("Ocurrio un error: " + e.getMessage());
        }

        return null;
    }

    public byte[] readBytes(InputStream inputStream) throws IOException {
        // this dynamically extends to take the bytes you read
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

        // this is storage overwritten on each iteration with bytes
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        // we need to know how may bytes were read to write them to the byteBuffer
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }

        // and then we can return your byte array.
        return byteBuffer.toByteArray();
    }

    public String formato(double x) {
        //Revisa si el valor númerico monetario es 0. De ser asi, asigna el string a 0.00, de lo contrario lo formatea adecuadamente. - Luis Acuña
        String a = "";

        if(x == 0)
        {
            a = "0.00";
        }
        else
        {
            DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
            simbolo.setDecimalSeparator('.');
            simbolo.setGroupingSeparator(',');
            DecimalFormat format = new DecimalFormat("###,###,###,###,###.00",
                    simbolo);

            a = format.format(x);
        }

        return a;
    }

    public String formato3(double x) {

        DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
        simbolo.setDecimalSeparator('.');
        simbolo.setGroupingSeparator(',');
        DecimalFormat format = new DecimalFormat("###,###,###,###,###.000",
                simbolo);
        String a = format.format(x);
        return a;
    }

    public String fechaHoy() {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        return formateador.format(ahora);
    }

    public static String horaActual() {
        Calendar calendario = new GregorianCalendar();
        int hora, minutos, segundos;
        hora = calendario.get(Calendar.HOUR_OF_DAY);
        minutos = calendario.get(Calendar.MINUTE);
        segundos = calendario.get(Calendar.SECOND);
        return hora + ":" + minutos + ":" + segundos;
    }

    public int version = 1;

    public static String xml;

    public String conseguirMAC(Context con) {
        String maca;
        WifiManager wifiManager = (WifiManager) con.getSystemService(Context.WIFI_SERVICE);

        WifiInfo info = wifiManager.getConnectionInfo();
        maca = info.getMacAddress();
        maca = maca.replace(":", "");

        return maca;
    }

    public void generarTxt(String sFileName, String sBody, Context con) {

        try {
            File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            Toast.makeText(con, "Error", Toast.LENGTH_LONG).show();
        }
    }

    public boolean ValidarFechaPasada(String fechaSeleccionada, String fechaBD) {
        boolean fechaValidada;


        String[] ff = fechaSeleccionada.split("/");
        int ffa = Integer.parseInt(ff[2]);
        int ffm = Integer.parseInt(ff[1]);
        int ffd = Integer.parseInt(ff[0]);

        String[] fec = fechaBD.split("/");
        int ffa2 = Integer.parseInt(fec[2]);
        int ffm2 = Integer.parseInt(fec[1]);
        int ffd2 = Integer.parseInt(fec[0]);

        if (ffa2 < ffa) {
            fechaValidada = true;
        } else {
            if (ffa2 == ffa) {

                if (ffm2 < ffm && ffm <= 12) {
                    fechaValidada = true;

                } else {
                    if (ffm2 == ffm) {

                        if (ffd2 <= ffd && ffd <= 31) {
                            fechaValidada = true;

                        } else {

                            fechaValidada = false;
                        }

                    } else {

                        fechaValidada = false;
                    }

                }

            } else {
                fechaValidada = false;
            }
        }

        return fechaValidada;

    }

    public boolean ValidarRangoFechas(String fechaInicial, String fechaFinal, String fechaMedia) {
        boolean fechaValidada;


        String[] ff = fechaInicial.split("/");
        int ffa = Integer.parseInt(ff[2]);
        int ffm = Integer.parseInt(ff[1]);
        int ffd = Integer.parseInt(ff[0]);

        String[] fec = fechaFinal.split("/");
        int ffa2 = Integer.parseInt(fec[2]);
        int ffm2 = Integer.parseInt(fec[1]);
        int ffd2 = Integer.parseInt(fec[0]);

        String[] fem = fechaMedia.split("/");
        int ffma = Integer.parseInt(fem[2]);
        int ffmm = Integer.parseInt(fem[1]);
        int ffmd = Integer.parseInt(fem[0]);

        if (ffa < ffma && ffma < ffa) {
            fechaValidada = true;
        } else {
            if (ffa2 == ffma || ffa == ffma) {

                if (ffm < ffmm && ffm < ffm2) {
                    fechaValidada = true;

                } else {
                    if (ffm2 == ffmm || ffm == ffmm) {

                        if (ffd <= ffmd && ffmd <= ffd2) {
                            fechaValidada = true;

                        } else {

                            fechaValidada = false;
                        }
                    } else {

                        fechaValidada = false;
                    }
                }

            } else {
                fechaValidada = false;
            }
        }

        return fechaValidada;

    }//----puede servir despues
}