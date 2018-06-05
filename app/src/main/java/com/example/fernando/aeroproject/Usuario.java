package com.example.fernando.aeroproject;

/**
 * Created by david on 04/06/18.
 */

class Usuario {
    private static final Usuario ourInstance = new Usuario();
    public int id;
    public String nombre;
    public int rol;
    public String mail;
    public String contra;

    static Usuario getInstance() {
        return ourInstance;
    }

    private Usuario() {
        id=-1;
        nombre="Anonimo";
        rol=2;
    }
    static void reset(){
        ourInstance.id=-1;
        ourInstance.nombre="Anonimo";
        ourInstance.rol=2;
    }
}
