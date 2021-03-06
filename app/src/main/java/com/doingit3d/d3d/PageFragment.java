package com.doingit3d.d3d;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.sdsmdg.tastytoast.TastyToast;
import java.util.ArrayList;


public class PageFragment extends Fragment{
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    private View view;
    private ListView lista;
    private BBDD_Controller controller;
    private MensajeConstructores m;
    private ArrayList<MensajeConstructores> modelo;
    private  ArrayAdapter<MensajeConstructores> adapter;
    private Intent i;
    private BoomMenuButton bmb;
    private int fotos[]={R.drawable.mail_black, R.drawable.ic_delete_black};
    private String frases[]={"Nuevo mensaje","Borrar mensaje"};
    private boolean borrar=false;


    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        borrar=false;


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        //Muestra el layout contenedor segun la seleccion hecha
        if (mPage==1){

            view = inflater.inflate(R.layout.recibidos, container, false);

        }else{

            view = inflater.inflate(R.layout.enviados, container, false);
        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        if(mPage==1){
            borrar=false;
            bmb=(BoomMenuButton)getView().findViewById(R.id.bmb);

            modelo=new ArrayList<MensajeConstructores>();

            adapter= new ListAdapter(getActivity(),modelo);
            lista=(ListView)getView().findViewById(R.id.lista);
            lista.setAdapter(adapter);

            cargar_mensajes_recibidos ();
            adapter.notifyDataSetChanged();

            registerForContextMenu(lista);


            lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override public void onItemClick(AdapterView<?> arg0, View v,int position, long id){
                    controller.actualizar_leido(1,modelo.get(position).id);

                   i= new Intent(getContext(),Ver_mensajes.class);
                   // i.putExtra("sms",0);
                    i.putExtra("id_sms",modelo.get(position).id);
                    startActivity(i);


                }
            });


            for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
                TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                        .normalImageRes(fotos[i])
                        .normalText(frases[i])
                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
                                if (index==0){
                                    startActivity(new Intent(getContext(),Enviar_Mensajes.class));
                                }else if (index==1){
                                    borrar=true;
                                    TastyToast.makeText(getContext(),"Mantenga pulsado un mensaje para borrarlo",TastyToast.LENGTH_LONG,TastyToast.DEFAULT);
                                }
                            }
                        });

                bmb.addBuilder(builder);
            }

            lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                    if (borrar==true){
                        new MaterialDialog.Builder(getContext())
                                .title("Borrar mensaje")
                                .content("¿Desea borrar el mensaje?")
                                .positiveText("si")
                                .negativeText("no")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        new MaterialDialog.Builder(getContext())
                                                .title("¿Estas seguro?")
                                                .content("No se podrá deshacer")
                                                .positiveText("si")
                                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                                    @Override
                                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                        borrar=false;
                                                        controller.eliminar_mensaje(modelo.get(pos).id);
                                                        modelo.remove(modelo.get(pos));
                                                        adapter.notifyDataSetChanged();
                                                        TastyToast.makeText(getContext(),"Mensaje eliminado",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);
                                                    }
                                                })
                                                .negativeText("no")
                                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                                    @Override
                                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                        borrar=false;
                                                        dialog.dismiss();
                                                    }
                                                })

                                                .show();
                                    }
                                })
                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        //TastyToast.makeText(getContext(),"Has pulsado no",TastyToast.LENGTH_SHORT,TastyToast.INFO);
                                        borrar=false;
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                    }else{

                    }


                    return false;
                }
            });

        }else if (mPage==2){

            bmb=(BoomMenuButton)getView().findViewById(R.id.bmb2);
            modelo=new ArrayList<MensajeConstructores>();

            adapter= new ListAdapter(getActivity(),modelo);
            lista=(ListView)getView().findViewById(R.id.lista2);
            lista.setAdapter(adapter);
            lista.setAdapter(adapter);

           cargar_mensajes_enviados();
            adapter.notifyDataSetChanged();

            registerForContextMenu(lista);

            lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override public void onItemClick(AdapterView<?> arg0, View v,int position, long id){
                    controller.actualizar_leido(1,modelo.get(position).id);

                    i= new Intent(getContext(),Ver_mensajes.class);
                   // i.putExtra("sms",1);
                    i.putExtra("id_sms",modelo.get(position).id);
                    startActivity(i);

                }
            });

            lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                    if (borrar==true){
                        new MaterialDialog.Builder(getContext())
                                .title("Borrar mensaje")
                                .content("¿Desea borrar el mensaje?")
                                .positiveText("si")
                                .negativeText("no")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        new MaterialDialog.Builder(getContext())
                                                .title("¿Estas seguro?")
                                                .content("No se podrá deshacer")
                                                .positiveText("si")
                                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                                    @Override
                                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                        borrar=false;
                                                        controller.eliminar_mensaje(modelo.get(pos).id);
                                                        modelo.remove(modelo.get(pos));
                                                        adapter.notifyDataSetChanged();
                                                        TastyToast.makeText(getContext(),"Mensaje eliminado",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);
                                                    }
                                                })
                                                .negativeText("no")
                                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                                    @Override
                                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                        borrar=false;
                                                        dialog.dismiss();
                                                    }
                                                })

                                                .show();
                                    }
                                })
                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        //TastyToast.makeText(getContext(),"Has pulsado no",TastyToast.LENGTH_SHORT,TastyToast.INFO);
                                        borrar=false;
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                    }else{

                    }


                    return false;
                }
            });

            for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
                TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                        .normalImageRes(fotos[i])
                        .normalText(frases[i])
                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
                                if (index==0){
                                    startActivity(new Intent(getContext(),Enviar_Mensajes.class));
                                }else if (index==1){

                                    borrar=true;
                                    TastyToast.makeText(getContext(),"Mantenga pulsado un mensaje para borrarlo",TastyToast.LENGTH_LONG,TastyToast.DEFAULT);
                                }
                            }
                        });

                bmb.addBuilder(builder);
            }
        }
    }


    public void cargar_mensajes_enviados(){

        controller = new BBDD_Controller(getContext());
        SQLiteDatabase db = controller.getReadableDatabase();

        try{
            if(db!=null){

                Cursor cursor = db.rawQuery("SELECT * FROM mensaje_privado WHERE from_user= "+"'"+controller.useremail_conectado()+"'", null);

                if(cursor.moveToFirst()){
                    do{

                        m = new MensajeConstructores(cursor.getString(cursor.getColumnIndex("to_user")),cursor.getString(cursor.getColumnIndex("asunto")),cursor.getInt(cursor.getColumnIndex("id")));

                        adapter.add(m);


                    }while(cursor.moveToNext());
                }
                db.close();
            }
        }catch (Exception e){

            e.printStackTrace();
        }
    }

    public void cargar_mensajes_recibidos(){

        controller = new BBDD_Controller(getContext());
        SQLiteDatabase db = controller.getReadableDatabase();

        try{
            if(db!=null){

                Cursor cursor = db.rawQuery("SELECT * FROM mensaje_privado WHERE to_user= "+"'"+controller.useremail_conectado()+"'", null);

                if(cursor.moveToFirst()){
                    do{

                        m = new MensajeConstructores(cursor.getString(cursor.getColumnIndex("from_user")),cursor.getString(cursor.getColumnIndex("asunto")),cursor.getInt(cursor.getColumnIndex("id")));

                        adapter.add(m);


                    }while(cursor.moveToNext());
                }
                db.close();
            }
        }catch (Exception e){

            e.printStackTrace();
        }
    }


}