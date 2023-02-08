package pacificsoft.pscomandera.Enum;


public enum EnumModeloImpresora {

    NONE(EnumModelCapability.NONE, "Modelo impresora"),
    MPOP(EnumModelCapability.MPOP, EnumModelCapability.getModelTitle(EnumModelCapability.MPOP)),
    FVP10(EnumModelCapability.FVP10, EnumModelCapability.getModelTitle(EnumModelCapability.FVP10)),
    TSP100(EnumModelCapability.TSP100, EnumModelCapability.getModelTitle(EnumModelCapability.TSP100)),
    TSP650II(EnumModelCapability.TSP650II, EnumModelCapability.getModelTitle(EnumModelCapability.TSP650II)),
    TSP700II(EnumModelCapability.TSP700II, EnumModelCapability.getModelTitle(EnumModelCapability.TSP700II)),
    TSP800II(EnumModelCapability.TSP800II, EnumModelCapability.getModelTitle(EnumModelCapability.TSP800II)),
    SP700(EnumModelCapability.SP700, EnumModelCapability.getModelTitle(EnumModelCapability.SP700)),
    SM_S210I(EnumModelCapability.SM_S210I, EnumModelCapability.getModelTitle(EnumModelCapability.SM_S210I)),
    SM_S220I(EnumModelCapability.SM_S220I, EnumModelCapability.getModelTitle(EnumModelCapability.SM_S220I)),
    SM_S230I(EnumModelCapability.SM_S230I, EnumModelCapability.getModelTitle(EnumModelCapability.SM_S230I)),
    SM_T300I_T300(EnumModelCapability.SM_T300I_T300, EnumModelCapability.getModelTitle(EnumModelCapability.SM_T300I_T300)),
    SM_T400I(EnumModelCapability.SM_T400I, EnumModelCapability.getModelTitle(EnumModelCapability.SM_T400I)),
    SM_L200(EnumModelCapability.SM_L200, EnumModelCapability.getModelTitle(EnumModelCapability.SM_L200)),
    BSC10(EnumModelCapability.BSC10, EnumModelCapability.getModelTitle(EnumModelCapability.BSC10)),
    SM_S210I_StarPRNT(EnumModelCapability.SM_S210I_StarPRNT, EnumModelCapability.getModelTitle(EnumModelCapability.SM_S210I_StarPRNT)),
    SM_S220I_StarPRNT(EnumModelCapability.SM_S220I_StarPRNT, EnumModelCapability.getModelTitle(EnumModelCapability.SM_S220I_StarPRNT)),
    SM_S230I_StarPRNT(EnumModelCapability.SM_S230I_StarPRNT, EnumModelCapability.getModelTitle(EnumModelCapability.SM_S230I_StarPRNT)),
    SM_T300I_T300_StarPRNT(EnumModelCapability.SM_T300I_T300_StarPRNT, EnumModelCapability.getModelTitle(EnumModelCapability.SM_T300I_T300_StarPRNT)),
    SM_T400I_StarPRNT(EnumModelCapability.SM_T400I_StarPRNT, EnumModelCapability.getModelTitle(EnumModelCapability.SM_T400I_StarPRNT)),
    SM_L300(EnumModelCapability.SM_L300, EnumModelCapability.getModelTitle(EnumModelCapability.SM_L300)),
    MC_PRINT2(EnumModelCapability.MC_PRINT2, EnumModelCapability.getModelTitle(EnumModelCapability.MC_PRINT2)),
    MC_PRINT3(EnumModelCapability.MC_PRINT3, EnumModelCapability.getModelTitle(EnumModelCapability.MC_PRINT3));


    int idModelo;
    String modelo;

    EnumModeloImpresora(int idModelo,String modelo){
        this.idModelo = idModelo;
        this.modelo = modelo;
    }


    public int getIdModelo() {
        return idModelo;
    }

    public String getModelo() {
        return modelo;
    }
}
