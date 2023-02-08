package pacificsoft.pscomandera.Modelo;

import java.util.List;

public class Comanda {
    private String COD_MESA;
    private  String COD_USU;
    private String COD_ALM;
    private String ALM_MP;
    private String MES_ADJUNTA = "";
    private List<ComandaArticulo> Listas;

    public Comanda() {
    }

    public Comanda(String COD_MESA, String COD_USU, String COD_ALM, String ALM_MP, String MES_ADJUNTA, List<ComandaArticulo> listas) {
        this.COD_MESA = COD_MESA;
        this.COD_USU = COD_USU;
        this.COD_ALM = COD_ALM;
        this.ALM_MP = ALM_MP;
        this.MES_ADJUNTA = MES_ADJUNTA;
        Listas = listas;
    }

    public String getCOD_MESA() {
        return COD_MESA;
    }

    public void setCOD_MESA(String COD_MESA) {
        this.COD_MESA = COD_MESA;
    }

    public String getCOD_USU() {
        return COD_USU;
    }

    public void setCOD_USU(String COD_USU) {
        this.COD_USU = COD_USU;
    }

    public String getCOD_ALM() {
        return COD_ALM;
    }

    public void setCOD_ALM(String COD_ALM) {
        this.COD_ALM = COD_ALM;
    }

    public String getALM_MP() {
        return ALM_MP;
    }

    public void setALM_MP(String ALM_MP) {
        this.ALM_MP = ALM_MP;
    }

    public String getMES_ADJUNTA() {
        return MES_ADJUNTA;
    }

    public void setMES_ADJUNTA(String MES_ADJUNTA) {
        this.MES_ADJUNTA = MES_ADJUNTA;
    }

    public List<ComandaArticulo> getListas() {
        return Listas;
    }



    public void setListas(List<ComandaArticulo> listas) {
        Listas = listas;
    }

    @Override
    public boolean equals(Object obj) {
        return obj!=null && obj instanceof Comanda && ((Comanda)obj).getCOD_MESA() !=null &&((Comanda)obj).getCOD_MESA().equals(this.COD_MESA);
    }
}
