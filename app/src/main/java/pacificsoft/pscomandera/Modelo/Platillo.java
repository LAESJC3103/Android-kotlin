package pacificsoft.pscomandera.Modelo;

import java.io.Serializable;

public class Platillo implements Serializable {
    private String id;
    private String descripcion;
    private double precio;
    private double cantidad;
    private String imagen;
    private int modificable;

    public Platillo() {
    }

    public Platillo(String id, String descripcion, double precio, double cantidad, String imagen) {
        this.id = id;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
        this.imagen = imagen;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getModificable() {
        return modificable;
    }

    public void setModificable(int modificable) {
        this.modificable = modificable;
    }
}
