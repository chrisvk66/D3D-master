package com.doingit3d.d3d;

import android.media.Image;

/**
 * Created by jdann on 09/05/2017.
 */

public class Project {


    protected String titulo, autor, material;
    protected byte[] foto;


    public Project(String titulo, String autor, String material, byte[] foto){
        this.titulo=titulo;
        this.autor=autor;
        this.material = material;
        this.foto = foto;

    }


    public Project(String titulo, String autor, String material){
        this.titulo=titulo;
        this.autor=autor;
        this.material = material;

    }








    public String toString(){
        return this.autor;
    }
}

