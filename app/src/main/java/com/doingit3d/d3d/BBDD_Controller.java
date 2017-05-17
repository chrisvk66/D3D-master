package com.doingit3d.d3d;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.io.ByteArrayInputStream;


/**
 * Created by David M on 12/05/2017.
 */

public class BBDD_Controller extends SQLiteOpenHelper {

    static  final String NOMBREBBDD="d3d";
    static final int VERSION=1;

    public BBDD_Controller(Context c, String nombre, SQLiteDatabase.CursorFactory f, int version){

        super(c,nombre,f,version);
    }

    public BBDD_Controller(Context c){

        super(c,NOMBREBBDD,null,VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    //inserta en la tabla los datos del registro
    public void registrarse(int scanner, int impresora, int design, String nombre, String email, String pass, byte[]imagen, int latitud, int longitud, int conectado){
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
                values.put("conectado",conectado);

                db.insert("usuario",null,values);

            }

            db.close();

        }catch(SQLiteException e){
            e.printStackTrace();
        }

    }


    //inserta en la tabla los datos del registro
    public void publicar_proyecto(String tipo_proyecto, String titulo, String descripcion, String fecha, String pais, String moneda, String fecha_creacion,int usuario_id, String desplazamiento,
            String formato_archivo, String privacidad ){
        try {
            SQLiteDatabase db = this.getWritableDatabase();


            if (db!=null){
                ContentValues values = new ContentValues();

                values.put("tipo_proyecto",tipo_proyecto);
                values.put("titulo",titulo);
                values.put("descripcion",descripcion);
                values.put("fecha",fecha);
                values.put("pais",pais);
                values.put("moneda",moneda);
                values.put("fecha_creacion",fecha_creacion);
               // values.put("material",material);
                values.put("usuario_id",usuario_id);
                values.put("desplazamiento",desplazamiento);
                values.put("formato_archivo",formato_archivo);
                values.put("privacidad",privacidad);

                db.insert("proyecto",null,values);
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

    //MEDIANTE LOS 4 SIGUIENTES METODOS DE ABAJO PODEMOS PONER A UN USUARIO EN ESTADO DE CONECTADO O DESCONECTADO.
    //DE ESTA FORMA SI UN USUARIO ESTA CONECTADO, OBTENDREMOS SUS DATOS PARA RELLENAR LAS DEMÁS ACTIVIDADES
    //POR EJEMPLO LA DE MIS PROYECTOS O SU PERFIL.
    //COMO SOLO PUEDE HABER UN USUARIO CONECTADO A LA VEZ, ES FACIL SABER DE QUIEN OBTENEMOS LOS DATOS

    //actualizar estado de conexion, sabiendo su id (del que se quiera conectar o desconectar)
    // y se pone el estado de conexion que se quiera, 1 para conectado o 0 para desconectado
    public void actualizar_estado_conexion(int id,int conectado){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("conectado",conectado);
        db.update("usuario",values,"id = "+id,null);
        db.close();

    }

    //obtiene el id de usuario conectado; es decir, del usuario que tenga un 1 en su columna "conectado"
    public int obtener_id_conectado(){


        SQLiteDatabase db = this.getReadableDatabase();

        int id_user=0;

        if(db!=null){
            Cursor cursor = db.rawQuery("SELECT id FROM usuario WHERE conectado = 1",null);
            if(cursor.moveToFirst()){
                do{

                    id_user=cursor.getInt(cursor.getColumnIndex("id"));

                }while(cursor.moveToNext());
            }

        }else{
            System.out.println("--------------NO COMPRUEBA USUARIO--------------");
        }

        return id_user;
    }

    //obtiene el id de la persona que inicia sesion para poder cambiarle su estado de conectado de 0 a 1
    public int obtener_id_login(String email){

        SQLiteDatabase db = this.getReadableDatabase();

        int id_user=0;

        if(db!=null){
            Cursor cursor = db.rawQuery("SELECT id FROM usuario WHERE email ="+"'"+email+"'",null);
            if(cursor.moveToFirst()){
                do{

                    id_user=cursor.getInt(cursor.getColumnIndex("id"));

                }while(cursor.moveToNext());
            }

        }else{
            System.out.println("--------------NO COMPRUEBA USUARIO--------------");
        }

        return id_user;
    }


    //aqui simplemente se comprueba que hay alguien conectado; da igual quien sea
    public boolean comprobar_conectado(){
        SQLiteDatabase db = this.getReadableDatabase();
        int conectado_estado;
        boolean estado=false;
        if(db!=null){
            Cursor cursor = db.rawQuery("SELECT conectado FROM usuario",null);
            if(cursor.moveToFirst()){
                do{

                    conectado_estado=cursor.getInt(cursor.getColumnIndex("conectado"));


                    if (conectado_estado==1){
                        estado=true;

                        break;
                    }else{
                        estado=false;
                    }

                }while(cursor.moveToNext());

            }

        }else{
            System.out.println("--------------NO COMPRUEBA USUARIO--------------");
        }
        db.close();
        return estado;
    }


    //devuelve el nombre del usuario conectado
    public String username_conectado(){
        SQLiteDatabase db = this.getReadableDatabase();
        String nombre=null;

        if(db!=null){
            Cursor cursor = db.rawQuery("SELECT nombre FROM usuario WHERE conectado = 1",null);
            if(cursor.moveToFirst()){
                do{

                    nombre=cursor.getString(cursor.getColumnIndex("nombre"));


                }while(cursor.moveToNext());

            }

        }else{
            System.out.println("--------------NO COMPRUEBA USUARIO--------------");
        }
        db.close();

        return nombre;
    }


    //devuelve el email del usuario conectado
    public String useremail_conectado(){
        SQLiteDatabase db = this.getReadableDatabase();
        String email=null;

        if(db!=null){
            Cursor cursor = db.rawQuery("SELECT email FROM usuario WHERE conectado = 1",null);
            if(cursor.moveToFirst()){
                do{

                    email=cursor.getString(cursor.getColumnIndex("email"));


                }while(cursor.moveToNext());

            }

        }else{
            System.out.println("--------------NO COMPRUEBA USUARIO--------------");
        }
        db.close();

        return email;
    }


    //obtener la imagen del usuario conectado
    public Bitmap obtener_imagen(){
        SQLiteDatabase db = this.getReadableDatabase();
        byte[] img;
        Bitmap bm=null;
        ByteArrayInputStream bais;


        if(db!=null){
            Cursor cursor = db.rawQuery("SELECT imagen FROM usuario WHERE conectado = 1",null);
            if(cursor.moveToFirst()){

                img=cursor.getBlob(cursor.getColumnIndex("imagen"));


                if (img==null){
                    bm=null;

                }else{


                    bais = new ByteArrayInputStream(img);
                    bm= BitmapFactory.decodeStream(bais);

                }

            }

        }
        db.close();
        return bm;
    }

    //obtener la imagen del usuario conectado
    public Bitmap obtener_imagen_bitmap(){
        SQLiteDatabase db = this.getReadableDatabase();
        byte[] img;
        Bitmap bm=null;
        ByteArrayInputStream bais;


        if(db!=null){
            Cursor cursor = db.rawQuery("SELECT imagen FROM usuario WHERE conectado = 1",null);
            if(cursor.moveToFirst()){

                img=cursor.getBlob(cursor.getColumnIndex("imagen"));


                if (img==null){
                    bm=null;

                }else{


                    bais = new ByteArrayInputStream(img);
                    bm= BitmapFactory.decodeStream(bais);

                }

            }

        }
        db.close();
        return bm;
    }



    //MAÑANA LO COMPLETO
    //el método recoge lo que hay en la base de datos para poder llenar la lista
    /*public void cargar_lista(){
        SQLiteDatabase db = this.getReadableDatabase();

        try{
            if(db!=null){

                Cursor cursor = db.rawQuery("SELECT * FROM proyecto", null);
                //cursor.moveToFirst();
                if(cursor.moveToFirst()){
                    do{

                        Project proyecto = new Project(cursor.getString(cursor.getColumnIndex("titulo")),cursor.getString(cursor.getColumnIndex("titulo")),cursor.getString(cursor.getColumnIndex("material")));
                        Project_adapter adapter= new Project_adapter();
                        adapter.add(proyecto);


                    }while(cursor.moveToNext());
                }
                db.close();
            }
        }catch (Exception e){
           // Toast.makeText(this,"Error en la base de datos", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }*/

}
