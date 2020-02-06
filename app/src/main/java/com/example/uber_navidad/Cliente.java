package com.example.softwareuber;

public class Cliente {
    public String cedula;
    public String nombre;
    public String apellido;
    public String CorreoElectronico;
    public String numeroTelefono;
    public String password;

    public Cliente() {
    }

    public Cliente(String cedula, String nombre, String apellido, String correoElectronico, String numeroTelefono, String password) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        CorreoElectronico = correoElectronico;
        this.numeroTelefono = numeroTelefono;
        this.password = password;
    }


}
