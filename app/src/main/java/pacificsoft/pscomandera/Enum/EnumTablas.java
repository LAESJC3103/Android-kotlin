package pacificsoft.pscomandera.Enum;

import android.content.res.Resources;

import pacificsoft.pscomandera.R;

public enum EnumTablas {
  TABLAS_EMPTY(""),
  TABLA_CONFIGURACION_EMPRESA("ConfiguracionEmpresa");

  String claveTabla;

  EnumTablas(String claveTabla) {
    this.claveTabla = claveTabla;
  }

  public String getClaveTabla() {
    return claveTabla;
  }
}
