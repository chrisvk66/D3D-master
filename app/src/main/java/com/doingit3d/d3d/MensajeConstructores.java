package com.doingit3d.d3d;

/**
 * Created by chris on 09/02/2017.
 */

public class MensajeConstructores {
    protected String email, asunto, genero, descripcion;
    protected byte[] imagen;
    protected int njuego;

    public MensajeConstructores(int njuego, String email, String asunto, String genero, String descripcion){
        this.email = email;
        this.asunto = asunto;
        this.genero = genero;
        this.descripcion = descripcion;
        this.njuego = njuego;
    }

    public MensajeConstructores(int njuego, String email, String asunto, String genero, String descripcion, byte[] imagen){
        this.email = email;
        this.asunto = asunto;
        this.genero = genero;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.njuego = njuego;
    }
}
