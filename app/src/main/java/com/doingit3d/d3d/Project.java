package com.doingit3d.d3d;

import android.media.Image;

/**
 * Created by jdann on 09/05/2017.
 */

public class Project {
    public enum Genero {poesia, narrativa, cuento, cienciaFiccion};

    protected String titulo, autor, material;
    protected Image image;

    public Project(String titulo, String autor, String material, Image img){
        this.titulo=titulo;
        this.autor=autor;
        this.material = material;
        this.image = img;
    }





    public String toString(){
        return this.autor;
    }
}

