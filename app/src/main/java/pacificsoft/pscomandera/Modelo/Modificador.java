package pacificsoft.pscomandera.Modelo;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class Modificador implements Serializable , Parcelable {
    private String id;
    private String descripcion;
    private String orden;
    private String tipo;
    private String nombre;
    private double costo = 0.0;
    private List<Intercambio> intercambios;
    private int listaM = 0;
    private int maximo = 0;
    private int cantidad = 0;
    private List<Ingrediente> ingredientes;

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id);
        dest.writeString(descripcion);
        dest.writeString(orden);
        dest.writeString(tipo);
        dest.writeString(nombre);
        dest.writeDouble(costo);
        dest.writeInt(cantidad);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Modificador() {
    }

    public Modificador(Parcel in){
        id = in.readString();
        descripcion = in.readString();
        orden = in.readString();
        tipo = in.readString();
        nombre = in.readString();
        costo = in.readDouble();
        cantidad = in.readInt();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public List<Intercambio> getIntercambios() {
        return intercambios;
    }

    public void setIntercambios(List<Intercambio> intercambios) {
        this.intercambios = intercambios;
    }

    public int getListaM() {
        return listaM;
    }

    public void setListaM(int listaM) {
        this.listaM = listaM;
    }

    public int getMaximo() {
        return maximo;
    }

    public void setMaximo(int maximo) {
        this.maximo = maximo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public static final Parcelable.Creator<Modificador> CREATOR = new Parcelable.Creator<Modificador>() {
        public Modificador createFromParcel(Parcel source) {
            return new Modificador(source);
        }

        @Override
        public Modificador[] newArray(int size) {
            return new Modificador[size];
        }
    };

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Modificador && obj != null && ((Modificador)obj).getId()!=null && ((Modificador)obj).getId().equals(this.id));
    }
}
