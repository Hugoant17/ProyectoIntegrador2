package com.example.proyectointegrador2.Modelo;

public class Usuario {

    private String nombre;
    private String contraseña;
    private String dni;
    private String direccion;
    private String correo;
    private String rol;
    private String foto;


    public Usuario(){

    }

    public Usuario(String nombre, String contraseña, String dni, String direccion, String correo, String rol, String foto) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.dni = dni;
        this.direccion = direccion;
        this.correo = correo;
        this.rol = rol;
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
