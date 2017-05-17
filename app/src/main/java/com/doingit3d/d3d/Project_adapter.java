package com.doingit3d.d3d;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jdann on 09/05/2017.
 */

public class Project_adapter extends ArrayAdapter<Project> {

    private BBDD_Controller controller = new BBDD_Controller(getContext());

    static class ViewHolder{
        protected TextView tv_titulo;
        protected TextView tv_autor;
        protected TextView tv_material;
        protected ImageView tv_image;
        protected LinearLayout ll;
    }

    private Activity ctx=null;
    private ArrayList<Project> modelo;

    public Project_adapter(Activity ctx, ArrayList<Project> modelo){
        super(ctx, R.layout.cada_proyecto, modelo);
        this.ctx=ctx;
        this.modelo=modelo;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            row = ctx.getLayoutInflater().inflate(R.layout.cada_proyecto, null);

            ViewHolder vh = new ViewHolder();
            vh.tv_titulo = (TextView) row.findViewById(R.id.cada_pro_titulo);
            vh.tv_autor = (TextView) row.findViewById(R.id.cada_pro_user);
            vh.tv_material = (TextView) row.findViewById(R.id.cada_pro_material);
            vh.tv_image = (ImageView) row.findViewById(R.id.cada_pro_image);
            vh.ll = (LinearLayout) row.findViewById(R.id.cada_pro_ll);
            row.setTag(vh);
        }
        ViewHolder vh = (ViewHolder) row.getTag();
        vh.tv_titulo = (TextView) row.findViewById(R.id.cada_pro_titulo);
        vh.tv_autor = (TextView) row.findViewById(R.id.cada_pro_user);
        vh.tv_material = (TextView) row.findViewById(R.id.cada_pro_material);
        vh.tv_image = (ImageView) row.findViewById(R.id.cada_pro_image);
        vh.ll = (LinearLayout) row.findViewById(R.id.cada_pro_ll);
        Project p = modelo.get(position);



        vh.tv_titulo.setText(p.titulo);
        vh.tv_autor.setText(p.autor);
        vh.tv_material.setText(p.material);

        Bitmap escala = controller.obtener_imagen().createScaledBitmap(controller.obtener_imagen(), 650, 500, true);
        vh.tv_image.setImageBitmap(escala);

        return row;


    }

}
