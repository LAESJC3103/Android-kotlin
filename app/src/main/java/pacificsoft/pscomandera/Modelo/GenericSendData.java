package pacificsoft.pscomandera.Modelo;

import java.util.List;

public class GenericSendData {
    private Object objectSendData;
    private List<?> listSendData;

    public GenericSendData() {
    }

    public GenericSendData(Object objectSendData, List<?> listSendData) {
        this.objectSendData = objectSendData;
        this.listSendData = listSendData;
    }

    public Object getObjectSendData() {
        return objectSendData;
    }

    public void setObjectSendData(Object objectSendData) {
        this.objectSendData = objectSendData;
    }

    public List<?> getListSendData() {
        return listSendData;
    }

    public void setListSendData(List<?> listSendData) {
        this.listSendData = listSendData;
    }
}
