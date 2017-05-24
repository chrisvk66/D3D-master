package com.doingit3d.d3d;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by chris on 09/02/2017.
 */

public class ListAdapter extends ArrayAdapter<MensajeConstructores> {

    private BBDD_Controller controller = new BBDD_Controller(getContext());

    static class ViewHolder{
        protected TextView email;
        protected TextView asunto;
        protected LinearLayout linearLayout;
        protected CircleImageView imagen;
    }


    private Activity context= null;
    private ArrayList<MensajeConstructores> modelo;

    public ListAdapter(Activity context, ArrayList<MensajeConstructores>modelo){
        super(context, R.layout.layout_row,modelo);
        this.context=context;
        this.modelo=modelo;
    }


    public View getView(int position, View convertView, ViewGroup parent){

        View row=convertView;
        if (row==null){

            row= context.getLayoutInflater().inflate(R.layout.layout_row,null);

            ViewHolder holder = new ViewHolder();
            holder.email=(TextView) row.findViewById(R.id.row_email);
            holder.asunto=(TextView) row.findViewById(R.id.row_asunto);
            holder.linearLayout=(LinearLayout) row.findViewById(R.id.row_layout);
            holder.imagen = (CircleImageView) row.findViewById(R.id.row_imagen);

            row.setTag(holder);

        }

        ViewHolder holder =  (ViewHolder) row.getTag();

        MensajeConstructores mensajeConstructores = modelo.get(position);

      /*  byte[] imagenbytes = mensajeConstructores.imagen;
        Bitmap imageBitmap = BitmapFactory.decodeByteArray(imagenbytes, 0, imagenbytes.length);

        if (imageBitmap!=null) {

            holder.imagen.setImageBitmap(imageBitmap);

        }else{

            holder.imagen.setImageResource(R.drawable.nofoto);

        }*/
        holder.email.setText("Email: " + mensajeConstructores.email);
        holder.asunto.setText("Asunto: " + mensajeConstructores.asunto);

        Bitmap escala = controller.obtener_imagnes_mensajes( mensajeConstructores.id).createScaledBitmap(controller.obtener_imagnes_mensajes( mensajeConstructores.id), 650, 500, true);
        holder.imagen.setImageBitmap(escala);

        return row;
    }

}
