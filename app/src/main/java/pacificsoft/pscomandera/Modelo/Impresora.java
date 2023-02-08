package pacificsoft.pscomandera.Modelo;

import com.starmicronics.stario.PortInfo;

public class Impresora extends PortInfo {


    public Impresora(String portName, String macAddress, String modelName, String USBSerialNumber){
        super(portName,macAddress,modelName,USBSerialNumber);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj!=null && obj instanceof Impresora && ((Impresora)obj).getMacAddress().equals(this.getMacAddress()));
    }
}
