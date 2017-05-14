package com.doingit3d.d3d;

import java.sql.*;

/**
 * Created by David M on 12/05/2017.
 */

public class BBDD_Controller {

    private Connection con;
    private PreparedStatement ps;
    private String insertar;


    //para hacer pruebas solo le paso estos parametros
    public void registrarse(byte[] img,String nombre,String email,String pass,int impresor,int disenador,int scanner){


        //Cada operacion tiene que ir en un hilo OBLIGATORIAMENTE
        Runnable runnable = new Runnable(){
            @Override
            public void run(){

                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/d3d","root","c100cpb");


                    insertar="INSERT INTO usuario (IMAGEN, NOMBRE, EMAIL, CONTRASENA, IMPRESOR, DISENADOR, SCANNER) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    ps=con.prepareStatement(insertar);

                    ps.setBytes(1,img);
                    ps.setString(2,nombre);
                    ps.setString(3,email);
                    ps.setString(4,pass);
                    ps.setInt(5,impresor);
                    ps.setInt(6,disenador);
                    ps.setInt(7,scanner);

                    ps.executeUpdate();

                    con.close();
                    System.out.println("----HA ENTRADO AQUI DENTRO----");

                    //con.close();
                }catch(Exception e){
                    e.printStackTrace();
                    System.out.println("----ERROR AL REGISTRARSE----");
                }

            }
        };

        Thread hilo = new Thread(runnable);
        hilo.start();
    }

}
