package com.doingit3d.d3d;

/**
 * Created by jdann on 09/05/2017.
 */

public class Project {


    protected String titulo, autor, material;
    protected byte[] foto;
    protected  int id_proyecto;


    public Project(String titulo, String autor, String material, byte[] foto){
        this.titulo=titulo;
        this.autor=autor;
        this.material = material;
        this.foto = foto;

    }


    public Project(String titulo, String autor, String material, int id_proyecto){
        this.titulo=titulo;
        this.autor=autor;
        this.material = material;
        this.id_proyecto=id_proyecto;

    }

    public String toString(){
        return this.autor;
    }
}

