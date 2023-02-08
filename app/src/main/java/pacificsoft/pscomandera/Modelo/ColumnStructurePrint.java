package pacificsoft.pscomandera.Modelo;

import java.util.List;

public class ColumnStructurePrint {
  private int totalCharacters;
  private int numberColumns;
  private List<ColumnDataFormatPrint> dataColumn;

  public ColumnStructurePrint(int totalCharacters,List<ColumnDataFormatPrint> dataColumn){
    this.totalCharacters = totalCharacters;
    this.dataColumn = dataColumn;
  }

  public int getTotalCharacters() {
    return totalCharacters;
  }

  public int getNumberColumns() {
    return numberColumns;
  }

  public List<ColumnDataFormatPrint> getDataColumn() {
    return dataColumn;
  }
}
