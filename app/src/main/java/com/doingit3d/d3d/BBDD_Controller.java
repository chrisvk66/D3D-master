package com.doingit3d.d3d;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * Created by David M on 12/05/2017.
 */

public class BBDD_Controller extends SQLiteOpenHelper {

    static  final String NOMBREBBDD="d3d";
    static final int VERSION=2;

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
    public void registrarse(int scanner, int impresora, int design, String nombre, String email, String pass, byte[]imagen, double latitud, double longitud,
                            int conectado, int tutorial,String lugar,String id_face){
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
                values.put("tutorial",tutorial);
                values.put("lugar",lugar);
                values.put("id_face",id_face);

                db.insert("usuario",null,values);

            }

            db.close();

        }catch(SQLiteException e){
            e.printStackTrace();
        }

    }


    //inserta en la tabla los datos del registro
    public void publicar_proyecto(String tipo_proyecto, String titulo, String descripcion, String fecha, String pais, String moneda, String fecha_creacion,int usuario_id, String desplazamiento,
            String formato_archivo, String privacidad, String material, String nombre_user){
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
                values.put("usuario_id",usuario_id);
                values.put("desplazamiento",desplazamiento);
                values.put("formato_archivo",formato_archivo);
                values.put("privacidad",privacidad);
                values.put("material",material);
                values.put("nombre_user",nombre_user);

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

    //comprueba que el email introducido no existe
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

    //obtiene el id de usuario conectado; es decir, del usuario que tenga un 1 en su columna "conectado"
    public int obtener_id_no_conectado(){


        SQLiteDatabase db = this.getReadableDatabase();

        int id_user=0;

        if(db!=null){
            Cursor cursor = db.rawQuery("SELECT id FROM usuario WHERE conectado = 0",null);
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
        //db.close();

        return nombre;
    }


    //devuelve el nombre del usuario a partir de un email
    public String obtener_nombre_con_email(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        String nombre=null;

        if(db!=null){
            Cursor cursor = db.rawQuery("SELECT nombre FROM usuario WHERE email = "+"'"+email+"'",null);
            if(cursor.moveToFirst()){
                do{

                    nombre=cursor.getString(cursor.getColumnIndex("nombre"));


                }while(cursor.moveToNext());

            }

        }else{
           // System.out.println("--------------NO COMPRUEBA USUARIO--------------");
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
        //db.close();

        return email;
    }

    //devuelve todos los email
    public ArrayList<String> obtener_todos_email(ArrayList<String> arrayList){
        SQLiteDatabase db = this.getReadableDatabase();
        String email;

        if(db!=null){
            Cursor cursor = db.rawQuery("SELECT email FROM usuario",null);
            if(cursor.moveToFirst()){
                do{

                    email=cursor.getString(cursor.getColumnIndex("email"));
                    arrayList.add(email);


                }while(cursor.moveToNext());

            }

        }
        db.close();

        return arrayList;
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
    public Bitmap obtener_imagen_con_email(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        byte[] img;
        Bitmap bm=null;
        ByteArrayInputStream bais;


        if(db!=null){
            Cursor cursor = db.rawQuery("SELECT imagen FROM usuario WHERE email = "+"'"+email+"'",null);
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




    //obtener la imagen de todos los usuarios y pone la que corresponde con su proyecto
    public Bitmap obtener_imagen_todos(int id_proyecto){
        SQLiteDatabase db = this.getReadableDatabase();
        byte[] img;
        Bitmap bm=null;
        ByteArrayInputStream bais;


        if(db!=null){
            Cursor cursor = db.rawQuery("SELECT imagen FROM usuario WHERE id=(SELECT usuario_id FROM proyecto WHERE id = "+id_proyecto+" )",null);
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




    //cuando se pulsa un elemento de la lista se muestran los datos del proyectos
    public void resumen_list(int id_proyecto, TextView tv1, TextView tv2, TextView tv3, TextView tv4, TextView tv5, TextView tv6,
                             TextView tv8, TextView tv9, TextView tv10,TextView tv11){
        SQLiteDatabase db = this.getReadableDatabase();

        String tipo_proyecto,titulo,descripcion,fecha,pais,moneda,
                desplazamiento,formato_archivo,privacidad,material;

        try{
            if(db!=null){

                Cursor cursor = db.rawQuery("SELECT * FROM proyecto WHERE id= "+id_proyecto, null);

                if(cursor.moveToFirst()){
                    do{

                        tipo_proyecto=cursor.getString(cursor.getColumnIndex("tipo_proyecto"));
                        titulo=cursor.getString(cursor.getColumnIndex("titulo"));
                        descripcion=cursor.getString(cursor.getColumnIndex("descripcion"));
                        fecha=cursor.getString(cursor.getColumnIndex("fecha"));
                        pais=cursor.getString(cursor.getColumnIndex("pais"));
                        moneda=cursor.getString(cursor.getColumnIndex("moneda"));
                        desplazamiento=cursor.getString(cursor.getColumnIndex("desplazamiento"));
                        formato_archivo=cursor.getString(cursor.getColumnIndex("formato_archivo"));
                        privacidad=cursor.getString(cursor.getColumnIndex("privacidad"));
                        material=cursor.getString(cursor.getColumnIndex("material"));


                    }while(cursor.moveToNext());

                    tv1.setText(tipo_proyecto);
                    tv2.setText(titulo);
                    tv3.setText(descripcion);
                    tv4.setText(fecha);
                    tv5.setText(pais);
                    tv6.setText(moneda);
                    tv8.setText(desplazamiento);
                    tv9.setText(formato_archivo);
                    tv10.setText(privacidad);
                    tv11.setText(material);


                }
                db.close();
            }
        }catch (Exception e){

            e.printStackTrace();
        }
    }


    //actualiza el perfil
    public void actualizar_perfil(String nombre,String email,int design, int scanner, int impresion, String pass,
                                  byte [] foto,double latitud,double longitud,String lugar){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("nombre", nombre);
        values.put("email", email);
        values.put("disenador", design);
        values.put("scanner", scanner);
        values.put("impresor", impresion);
        values.put("contrasena",pass);
        values.put("imagen",foto);
        values.put("latitud",latitud);
        values.put("longitud",longitud);
        values.put("lugar",lugar);
        db.update("usuario",values,"id = "+obtener_id_conectado(),null);
        db.close();
    }

    //devuelve la cantidad de proyectos publicados
    public int obtener_proyectos_presentados(){
        SQLiteDatabase db = this.getReadableDatabase();
        int num=0;

        if(db!=null){
            Cursor cursor = db.rawQuery("SELECT p_presentados FROM evaluacion WHERE id_usuario = "+obtener_id_conectado() ,null);
            if(cursor.moveToFirst()){

                num=cursor.getInt(cursor.getColumnIndex("p_presentados"));

            }

        }
        // db.close();
        return num;
    }

    //devuelve la cantidad de proyectos publicados segun email
    public int obtener_proyectos_presentados_con_email(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        int num=0;

        if(db!=null){
            Cursor cursor = db.rawQuery("SELECT p_presentados FROM evaluacion WHERE id_usuario = "+obtener_id_login(email) ,null);
            if(cursor.moveToFirst()){

                num=cursor.getInt(cursor.getColumnIndex("p_presentados"));

            }

        }
        // db.close();
        return num;
    }

    //si no hay proyectos
    public void aumentar_proyectos_presentados(){
        try {
            SQLiteDatabase db = this.getWritableDatabase();


            if (db!=null){
                ContentValues values = new ContentValues();

                values.put("id_usuario",obtener_id_conectado());
                values.put("p_presentados",obtener_proyectos_presentados()+1);

                db.insert("evaluacion",null,values);
            }

            db.close();

        }catch(SQLiteException e){
            e.printStackTrace();
        }
    }


    //si hay 1 o mas proyectos, se actualiza el numero
    public void aumentar_proyectos_presentados2(){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("id_usuario", obtener_id_conectado());
        values.put("p_presentados", obtener_proyectos_presentados()+1);

        db.update("evaluacion",values,"id_usuario= "+obtener_id_conectado(),null);
        db.close();
    }


    //cuando se ve el tutorial una vez, se cambia este valor para que no vuelva a aparecer
    public void actualizar_tutorial(int tutorial){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("tutorial", tutorial);

        db.update("usuario",values,"id= "+obtener_id_conectado(),null);
        db.close();
    }

    //comprueba si se a iniciado sesion por primera vez para que salga el tutorial o no
    public int obtener_tutorial(){
        SQLiteDatabase db = this.getReadableDatabase();
        int tutorial=0;

        if(db!=null){
            Cursor cursor = db.rawQuery("SELECT tutorial FROM usuario WHERE id = "+obtener_id_conectado() ,null);
            if(cursor.moveToFirst()){

                tutorial=cursor.getInt(cursor.getColumnIndex("tutorial"));

            }

        }
        // db.close();
        return tutorial;
    }

    //mete el mensaje en la bbdd
    public void enviar_mensaje( String from, String to,String mensaje,int leido,String asunto,String fecha){
        try {
            SQLiteDatabase db = this.getWritableDatabase();


            if (db!=null){
                ContentValues values = new ContentValues();

                values.put("from_user",from);
                values.put("to_user",to);
                values.put("fecha",fecha);
                values.put("texto",mensaje);
                values.put("leido",leido);
                values.put("asunto",asunto);

                db.insert("mensaje_privado",null,values);

            }

            db.close();

        }catch(SQLiteException e){
            e.printStackTrace();
        }
    }


    //cuando se pulsa un mensaje recibido se cambia a "leido" para que no salga la notificacion
    public void actualizar_leido(int leido,int id_mensaje){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("leido", leido);

        db.update("mensaje_privado",values,"to_user= "+"'"+useremail_conectado()+"'"+" AND id ="+id_mensaje,null);
        db.close();
    }


    //obtiene si se ha leido o no el mensaje recibido para que salga la notificacion
    public boolean obtener_leido(){
        SQLiteDatabase db = this.getReadableDatabase();

        int cero;
        boolean igual=false;

        if(db!=null){
            Cursor cursor = db.rawQuery("SELECT leido FROM mensaje_privado WHERE to_user = "+"'"+useremail_conectado()+"'",null);
            if(cursor.moveToFirst()){
                do{

                    cero=cursor.getInt(cursor.getColumnIndex("leido"));

                    if (cero==0){
                        igual=true;

                        break;
                    }else{
                        igual=false;

                    }

                }while(cursor.moveToNext());
            }

        }else{
        }
        return igual;
    }

    //para poner las imagenes correspondientes en los mensajes
    public Bitmap obtener_imagnes_mensajes(int id_mensajes){
        SQLiteDatabase db = this.getReadableDatabase();
        byte[] img;
        Bitmap bm=null;
        ByteArrayInputStream bais;


        if(db!=null){
            Cursor cursor = db.rawQuery("SELECT imagen FROM usuario WHERE email=(SELECT from_user FROM mensaje_privado WHERE id = "+id_mensajes+" )",null);
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


    //cuando se pulsa un elemento de la lista se muestran los datos del mensaje
    public void ver_mensaje(int id_mensaje,TextView tv1, TextView tv2, TextView tv3,TextView tv4,TextView tv5){
        SQLiteDatabase db = this.getReadableDatabase();

        String from,to,asunto,mensaje,fecha;

        try{
            if(db!=null){

                Cursor cursor = db.rawQuery("SELECT * FROM mensaje_privado WHERE id= "+id_mensaje, null);

                if(cursor.moveToFirst()){
                    do{

                        from=cursor.getString(cursor.getColumnIndex("from_user"));
                        to=cursor.getString(cursor.getColumnIndex("to_user"));
                        asunto=cursor.getString(cursor.getColumnIndex("asunto"));
                        mensaje=cursor.getString(cursor.getColumnIndex("texto"));
                        fecha=cursor.getString(cursor.getColumnIndex("fecha"));


                    }while(cursor.moveToNext());

                    tv1.setText(from);
                    tv2.setText(to);
                    tv3.setText(asunto);
                    tv4.setText(mensaje);
                    tv5.setText(fecha);

                }
                db.close();
            }
        }catch (Exception e){

            e.printStackTrace();
        }
    }

    public boolean comprobar_design(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        int design;
        boolean igual=false;
        if(db!=null){
            Cursor cursor = db.rawQuery("SELECT disenador FROM usuario WHERE email = "+"'"+email+"'",null);
            if(cursor.moveToFirst()){
                do{

                    design=cursor.getInt(cursor.getColumnIndex("disenador"));

                    if (design==1){

                        igual=true;
                        break;

                    }else{
                        igual=false;

                    }

                }while(cursor.moveToNext());

            }

        }else{

        }
        return igual;
    }


    public boolean comprobar_impresor(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        int impresor;
        boolean igual=false;
        if(db!=null){
            Cursor cursor = db.rawQuery("SELECT impresor FROM usuario WHERE email = "+"'"+email+"'",null);
            if(cursor.moveToFirst()){
                do{

                    impresor=cursor.getInt(cursor.getColumnIndex("impresor"));

                    if (impresor==1){

                        igual=true;
                        break;

                    }else{
                        igual=false;

                    }

                }while(cursor.moveToNext());

            }

        }else{

        }
        return igual;
    }


    public boolean comprobar_scanner(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        int impresor;
        boolean igual=false;
        if(db!=null){
            Cursor cursor = db.rawQuery("SELECT scanner FROM usuario WHERE email = "+"'"+email+"'",null);
            if(cursor.moveToFirst()){
                do{

                    impresor=cursor.getInt(cursor.getColumnIndex("scanner"));

                    if (impresor==1){

                        igual=true;
                        break;

                    }else{
                        igual=false;

                    }

                }while(cursor.moveToNext());

            }

        }else{

        }
        return igual;
    }


    public void poner_valoracion(double valoracion,String email){
        try {
            SQLiteDatabase db = this.getWritableDatabase();


            if (db!=null){
                ContentValues values = new ContentValues();

                values.put("valoracion",valoracion);

                db.update("usuario",values,"email = "+"'"+email+"'",null);
            }

            db.close();

        }catch(SQLiteException e){
            e.printStackTrace();
        }
    }

    public float obtener_valoracion(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        float num=0;

        if(db!=null){
            Cursor cursor = db.rawQuery("SELECT valoracion FROM usuario WHERE email = "+"'"+email+"'",null);
            if(cursor.moveToFirst()){
                do{

                    num=cursor.getFloat(cursor.getColumnIndex("valoracion"));

                }while(cursor.moveToNext());

            }

        }else{

        }
        return num;
    }

    public void aumentar_valoracion(double valoracion,String email){
        try {
            SQLiteDatabase db = this.getWritableDatabase();


            if (db!=null){
                ContentValues values = new ContentValues();

                values.put("valoracion",obtener_valoracion(email)+valoracion);

                db.update("usuario",values,"email = "+"'"+email+"'",null);
            }

            db.close();

        }catch(SQLiteException e){
            e.printStackTrace();
        }
    }


    public void num_veces_valorado(int veces,String email){
        try {
            SQLiteDatabase db = this.getWritableDatabase();


            if (db!=null){
                ContentValues values = new ContentValues();

                values.put("veces_valorado",veces);

                db.update("usuario",values,"email = "+"'"+email+"'",null);
            }

            db.close();

        }catch(SQLiteException e){
            e.printStackTrace();
        }
    }

    public void aumentar_num_veces_valorado(String email){
        try {
            SQLiteDatabase db = this.getWritableDatabase();


            if (db!=null){
                ContentValues values = new ContentValues();

                values.put("veces_valorado",obtener_num_veces_valorado(email)+1);

                db.update("usuario",values,"email = "+"'"+email+"'",null);
            }

            db.close();

        }catch(SQLiteException e){
            e.printStackTrace();
        }
    }

    public int obtener_num_veces_valorado(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        int num=0;

        if(db!=null){
            Cursor cursor = db.rawQuery("SELECT veces_valorado FROM usuario WHERE email = "+"'"+email+"'",null);
            if(cursor.moveToFirst()){
                do{

                    num=cursor.getInt(cursor.getColumnIndex("veces_valorado"));

                }while(cursor.moveToNext());

            }

        }else{

        }
        return num;
    }

    public void eliminar_mensaje(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("mensaje_privado","id = "+id, null);
    }

    //Obtiene l latitud
    public ArrayList<Double> obtener_todos_latitud(ArrayList<Double> arrayList){
        SQLiteDatabase db = this.getReadableDatabase();
        double latitud=0;

        if(db!=null){
            Cursor cursor = db.rawQuery("SELECT latitud FROM usuario",null);
            if(cursor.moveToFirst()){
                do{

                    latitud=cursor.getDouble(cursor.getColumnIndex("latitud"));
                    arrayList.add(latitud);


                }while(cursor.moveToNext());

            }

        }else{

        }
        db.close();

        return arrayList;
    }

    //Obtiene l longitud
    public ArrayList<Double> obtener_todos_longitud(ArrayList<Double> arrayList){
        SQLiteDatabase db = this.getReadableDatabase();
        double longitud=0;

        if(db!=null){
            Cursor cursor = db.rawQuery("SELECT longitud FROM usuario",null);
            if(cursor.moveToFirst()){
                do{

                    longitud=cursor.getDouble(cursor.getColumnIndex("longitud"));
                    arrayList.add(longitud);


                }while(cursor.moveToNext());

            }

        }else{

        }
        db.close();

        return arrayList;
    }


    public String obtener_lugar(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        String lugar=null;

        if(db!=null){
            Cursor cursor = db.rawQuery("SELECT lugar FROM usuario WHERE email = "+"'"+email+"'",null);
            if(cursor.moveToFirst()){
                do{

                    lugar=cursor.getString(cursor.getColumnIndex("lugar"));

                }while(cursor.moveToNext());

            }

        }else{

        }
        return lugar;
    }

    public ArrayList<String> obtener_todos_nombres(ArrayList<String> arrayList){
        SQLiteDatabase db = this.getReadableDatabase();
        String nombre;

        if(db!=null){
            Cursor cursor = db.rawQuery("SELECT nombre FROM usuario",null);
            if(cursor.moveToFirst()){
                do{

                    nombre=cursor.getString(cursor.getColumnIndex("nombre"));
                    arrayList.add(nombre);


                }while(cursor.moveToNext());

            }

        }
        db.close();

        return arrayList;
    }

    public ArrayList<Bitmap> obtener_imagen_todos(ArrayList<Bitmap> bit, String email){
        SQLiteDatabase db = this.getReadableDatabase();
        byte[] img;
        Bitmap bm=null;
        ByteArrayInputStream bais;


        if(db!=null){
            Cursor cursor = db.rawQuery("SELECT imagen FROM usuario  WHERE email = "+"'"+email+"'",null);
            if(cursor.moveToFirst()){

                img=cursor.getBlob(cursor.getColumnIndex("imagen"));

                do{

                    if (img==null){
                        bm=null;

                    }else{


                        bais = new ByteArrayInputStream(img);
                        bm= BitmapFactory.decodeStream(bais);
                        bit.add(bm);

                    }


                }while(cursor.moveToNext());

            }

        }
        db.close();
        return bit;
    }


    public void eliminar_usuario(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("usuario","id = "+id, null);
    }

    public void eliminar_proyecto(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("proyecto","usuario_id = "+id, null);
    }

    public void insertar_id_facebook(String idface,String email){
        try {
            SQLiteDatabase db = this.getWritableDatabase();


            if (db!=null){
                ContentValues values = new ContentValues();

                values.put("id_face",idface);

                db.update("usuario",values,"email = "+"'"+email+"'",null);

            }

            db.close();

        }catch(SQLiteException e){
            e.printStackTrace();
        }
    }

    public boolean comprobar_id_face(String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        String id;
        boolean igual=false;
        if(db!=null){
            Cursor cursor = db.rawQuery("SELECT id_face FROM usuario",null);
            if(cursor.moveToFirst()){
                do{

                    id=cursor.getString(cursor.getColumnIndex("id_face"));
                    System.out.println("--------------NOMBRE PASS: "+id+"--------------");

                    if (id.equals(pass)){

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



    public int obtener_id_face(String id){

        SQLiteDatabase db = this.getReadableDatabase();

        int id_user=0;

        if(db!=null){
            Cursor cursor = db.rawQuery("SELECT id FROM usuario WHERE id_face = "+"'"+id+"'",null);
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

    public double obtener_longitud_con_email(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        double longitud=0;

        if(db!=null){
            Cursor cursor = db.rawQuery("SELECT longitud FROM usuario WHERE email = "+"'"+email+"'",null);
            if(cursor.moveToFirst()){
                do{

                    longitud=cursor.getDouble(cursor.getColumnIndex("longitud"));


                }while(cursor.moveToNext());

            }

        }else{

        }
        db.close();

        return longitud;
    }

    public double obtener_latitud_con_email(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        double latitud=0;

        if(db!=null){
            Cursor cursor = db.rawQuery("SELECT latitud FROM usuario WHERE email = "+"'"+email+"'",null);
            if(cursor.moveToFirst()){
                do{

                    latitud=cursor.getDouble(cursor.getColumnIndex("latitud"));


                }while(cursor.moveToNext());

            }

        }else{

        }
        db.close();

        return latitud;
    }

}
