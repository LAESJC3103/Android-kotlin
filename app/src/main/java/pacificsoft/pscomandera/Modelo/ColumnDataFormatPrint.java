package pacificsoft.pscomandera.Modelo;

public class ColumnDataFormatPrint {
    private int numberCharecters;
    private String characters;
    private int align;

    public ColumnDataFormatPrint(int numberCharecters,String characters,int align){
        this.numberCharecters = numberCharecters;
        this.characters = characters;
        this.align = align;
    }

    public int getNumberCharecters() {
        return numberCharecters;
    }

    public String getCharacters() {
        return characters;
    }

    public int getAlign() {
        return align;
    }
}
