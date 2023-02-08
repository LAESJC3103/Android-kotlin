package pacificsoft.pscomandera.Enum;

import java.util.ArrayList;

public class Enum {
  EnumModeloImpresora [] arrayEnumModeloImpresora;
  public Enum(){
    arrayEnumModeloImpresora = EnumModeloImpresora.values();
  }

  public EnumModeloImpresora getModeloImpresoraById(int idModelo){
    for(EnumModeloImpresora v : arrayEnumModeloImpresora){
      if( v.getIdModelo() == idModelo){
        return v;
      }
    }
    return EnumModeloImpresora.NONE;
  }

  public int indexOfEnumModeloImpresora(EnumModeloImpresora enumModeloImpresora){
    int index = 0;
    for(EnumModeloImpresora v : arrayEnumModeloImpresora){
      if( v.getIdModelo() == enumModeloImpresora.getIdModelo()){
        return index;
      }
      index++;
    }
    return -1;
  }

  public ArrayList<EnumModeloImpresora> getValuesEnumModeloImpresora(){
    ArrayList<EnumModeloImpresora> lst = new ArrayList<>();
    for(EnumModeloImpresora v : arrayEnumModeloImpresora){
      lst.add(v);
    }
    return lst;
  }


}
