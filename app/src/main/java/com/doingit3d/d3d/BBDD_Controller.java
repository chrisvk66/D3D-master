package com.doingit3d.d3d;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.*;

/**
 * Created by David M on 12/05/2017.
 */

public class BBDD_Controller extends SQLiteOpenHelper {

    private String tabla_proyecto="CREATE TABLE IF NOT EXISTS proyecto (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, tipo_proyecto TEXT, titulo TEXT, descripcion TEXT," +
            "fecha TEXT, pais TEXT, moneda TEXT, fecha_creacion TEXT, material TEXT, FOREIGN\n" +
            "KEY (usuario_id) REFERENCES usuario (id) ,estado INTEGER ,ruta_fichero TEXT )";

    private String tabla_oferta ="CREATE TABLE IF NOT EXISTS oferta (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, importe INTEGER, tiempo_estimado INTEGER," +
            "FOREIGN KEY(usuario_id) REFERENCES usuario (id) , FOREIGN KEY (preyecto_id) REFERENCES preyecto (id), fecha_adjudicacion TEXT, fecha_envio TEXT," +
            "transporte INTEGER)";

    private String tabla_sms="CREATE TABLE IF NOT EXISTS mensaje_privado (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, from_user INTEGER), to_user INTEGER, fecha TEXT," +
            "texto TEXT, leido INTEGER";

    private String tabla_comentario="CREATE TABLE IF NOT EXISTS comentario(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,FOREIGN KEY (preyecto_id) REFERENCES preyecto (id),FOREIGN KEY(usuario_id) REFERENCES usuario (id)," +
            "usuario_destino INTEGER, texto TEXT, fecha TEXT, leido INTEGER, respuesta TEXT )";

    static  final String NOMBREBBDD="d3d";
    static final int VERSION=1;

    public BBDD_Controller (Context c, String nombre, SQLiteDatabase.CursorFactory f, int version){

        super(c,nombre,f,version);
    }

    public BBDD_Controller (Context c){

        super(c,NOMBREBBDD,null,VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS usuario (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, imagen BLOB, nombre TEXT, email TEXT," +
                "contrasena TEXT, impresor INTEGER, disenador INTEGER, scanner INTEGER, latitud INTEGER, longitud INTEGER)");

        /*db.execSQL(tabla_proyecto);

        db.execSQL(tabla_oferta);

        db.execSQL(tabla_sms);

        db.execSQL(tabla_comentario);*/

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    //inserta en la tabla los datos del registro
    public void registrarse(int scanner, int impresora, int design, String nombre, String email, String pass, byte[]imagen, int latitud, int longitud){
        try {
            SQLiteDatabase db = this.getWritableDatabase();


            if (db!=null){
                ContentValues values = new ContentValues();

                values.put("imagen",imagen);
                values.put("nombre",nombre);
                values.put("email",email);
                values.put("contrasena",pass);
                values.put("impresor",impresora);
                values.put("disenador",design);
                values.put("scanner",scanner);
                values.put("latitud",latitud);
                values.put("longitud",longitud);

                db.insert("usuario",null,values);



            }

            db.close();

        }catch(SQLiteException e){
            e.printStackTrace();
        }


    }

    //comprueba que el email existe
    public boolean comprobarusuarios(String email){
        SQLiteDatabase db = this.getReadableDatabase();

        String email_tabla;
        boolean igual=false;

        if(db!=null){
            Cursor cursor = db.rawQuery("SELECT email FROM usuario",null);
            if(cursor.moveToFirst()){
                do{

                    email_tabla=cursor.getString(cursor.getColumnIndex("email"));
                    System.out.println("--------------NOMBRE USUARIO: "+email_tabla+"--------------");

                    if (email_tabla.equals(email)){
                        igual=true;

                        break;
                    }else{
                        igual=false;

                    }

                }while(cursor.moveToNext());
                System.out.println("--------------NOMBRE IGUALNOMBRE: "+igual+"--------------");
            }

        }else{
            System.out.println("--------------NO COMPRUEBA USUARIO--------------");
        }
        return igual;
    }

    //comprueba que la contraseña existe
    public boolean comprobarpass(String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        String pass_tabla;
        boolean igual=false;
        if(db!=null){
            Cursor cursor = db.rawQuery("SELECT contrasena FROM usuario",null);
            if(cursor.moveToFirst()){
                do{

                    pass_tabla=cursor.getString(cursor.getColumnIndex("contrasena"));
                    System.out.println("--------------NOMBRE PASS: "+pass_tabla+"--------------");

                    if (pass_tabla.equals(pass)){

                        igual=true;
                        break;

                    }else{
                        igual=false;

                    }

                }while(cursor.moveToNext());
                System.out.println("--------------NOMBRE IGUALPASS: "+igual+"--------------");
            }

        }else{
            System.out.println("--------------NO COMPRUEBA USUARIO--------------");
        }
        return igual;
    }


    //Comprueba que la contraseña coincida con el usuario
    public boolean comprobar_email_pass(String email,String pass){

        SQLiteDatabase db = this.getWritableDatabase();

        String passcomprobar;
        boolean igual=false;
        if(db!=null){
            Cursor cursor = db.rawQuery("SELECT contrasena FROM usuario WHERE email = "+"'"+email+"'",null);
            if(cursor.moveToFirst()){
                do{

                    passcomprobar=cursor.getString(cursor.getColumnIndex("contrasena"));
                    System.out.println("--------------NOMBRE PASS: "+passcomprobar+"--------------");

                    //compara las contraseñas
                    if (passcomprobar.equals(pass)){

                        igual=true;
                        break;

                    }else{
                        igual=false;

                    }

                }while(cursor.moveToNext());
                System.out.println("--------------NOMBRE IGUALPASS: "+igual+"--------------");
            }

        }else{
            System.out.println("--------------NO COMPRUEBA USUARIO--------------");
        }

        return igual;
    }

    //comprueba que el email introducido al registrarse no existe
    public boolean comprobar_email_repetido(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        String email_tabla;
        boolean igual=false;
        if(db!=null){
            Cursor cursor = db.rawQuery("SELECT email FROM usuario",null);
            if(cursor.moveToFirst()){
                do{

                    email_tabla=cursor.getString(cursor.getColumnIndex("email"));
                    System.out.println("--------------NOMBRE USUARIO: "+email_tabla+"--------------");

                    if (email_tabla.equals(email)){
                        igual=true;

                        break;
                    }else{
                        igual=false;
                    }

                }while(cursor.moveToNext());
                System.out.println("--------------NOMBRE IGUALNOMBRE: "+igual+"--------------");
            }

        }else{
            System.out.println("--------------NO COMPRUEBA USUARIO--------------");
        }
        db.close();
        return igual;
    }

}
