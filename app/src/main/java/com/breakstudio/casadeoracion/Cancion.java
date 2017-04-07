package com.breakstudio.casadeoracion;

/**
 * Created by Jaime on 7/3/2017.
 */

public class Cancion {
    private String nombre;
    private String artista;

    public Cancion(String nombre, String artista) {
        this.nombre = nombre;
        this.artista = artista;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }
}
