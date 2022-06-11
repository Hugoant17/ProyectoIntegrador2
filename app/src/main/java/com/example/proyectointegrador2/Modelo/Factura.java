package com.example.proyectointegrador2.Modelo;

public class Factura {

    private String codigo, dni, fecha;
    private double precio;

    public Factura(String codigo, String dni, String fecha, double precio) {
        this.codigo = codigo;
        this.dni = dni;
        this.fecha = fecha;
        this.precio = precio;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
