package com.example.proyectointegrador2.Modelo;

public class Tienda {

    private String codigo;
    private String nombre;
    private String direccion;

    //se agregó
    private String imagen;


    public Tienda(){

    }

    public Tienda(String codigo, String nombre, String direccion, String imagen) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.direccion = direccion;
        this.imagen = imagen;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

}
