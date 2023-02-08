package pacificsoft.pscomandera.Modelo;


public class Pinpad  {

    String macAddress,modelName;

    public Pinpad(String macAddress,String modelName){
        this.macAddress = macAddress;
        this.modelName = modelName;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj!=null && obj instanceof Pinpad && ((Pinpad)obj).getMacAddress().equals(this.getMacAddress()));
    }
}
