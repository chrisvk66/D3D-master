package com.doingit3d.d3d;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by david.martin on 10/05/2017.
 */

public class MenuListFragment extends Fragment {
    private Menu nav_menu;
    private BBDD_Controller controller;
    private ImageView img;
    private TextView email,nombre;
    private Context c;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        c= getActivity().getApplication().getApplicationContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container,false);

        NavigationView vNavigation = (NavigationView) view.findViewById(R.id.nav_view);
        nav_menu= vNavigation.getMenu();

        //se usa este view y el metodo getHeaderView para poder encontrar los elementos con findViewById, ya que no son del mismo layout que este fragmento que es el NavigationDrawer
        View vista = vNavigation.getHeaderView(0);
        img=(ImageView) vista.findViewById(R.id.imagen_cabecera);
        nombre=(TextView) vista.findViewById(R.id.nav_username);
        email=(TextView) vista.findViewById(R.id.nav_useremail);

        controller = new BBDD_Controller(c);




        if (controller.comprobar_conectado()==true){

           // Toast.makeText(getContext(),"HAY ALGUIEN CONECTADO",Toast.LENGTH_SHORT).show();
            nav_menu.findItem(R.id.nav_perfil).setVisible(true);
            nav_menu.findItem(R.id.nav_buscar).setVisible(true);
            nav_menu.findItem(R.id.nav_publicar).setVisible(true);
            nav_menu.findItem(R.id.nav_publicados).setVisible(true);
            nav_menu.findItem(R.id.nav_mis_proyectos).setVisible(true);
            nav_menu.findItem(R.id.nav_evaluacion).setVisible(true);
            nav_menu.findItem(R.id.nav_mensajes).setVisible(true);
            nav_menu.findItem(R.id.nav_exit).setVisible(true);

            //AQUI SE CAMBIA LA IMAGEN, EL NOMBRE Y EL EMAIL DEL MENU LATERAL SEGUN EL USUARIO QUE SE HAYA CONECTADO
            nombre.setText(controller.username_conectado());
            email.setText(controller.useremail_conectado());
            img.setImageBitmap(controller.obtener_imagen());


        }else if (controller.comprobar_conectado()==false){

            //Toast.makeText(getContext(),"NO HAY NADIE CONECTADO",Toast.LENGTH_SHORT).show();
            nav_menu.findItem(R.id.nav_perfil).setVisible(false);
            nav_menu.findItem(R.id.nav_buscar).setVisible(false);
            nav_menu.findItem(R.id.nav_publicar).setVisible(false);
            nav_menu.findItem(R.id.nav_publicados).setVisible(false);
            nav_menu.findItem(R.id.nav_mis_proyectos).setVisible(false);
            nav_menu.findItem(R.id.nav_evaluacion).setVisible(false);
            nav_menu.findItem(R.id.nav_mensajes).setVisible(false);
            nav_menu.findItem(R.id.nav_exit).setVisible(false);

        }else{
            System.out.println("AQUI ES DONDE FALLA Y NO ENTRA");
        }



        vNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                int id = item.getItemId();
                if (id== R.id.nav_como_funciona){
                    startActivity(new Intent(getActivity(),How_does_it_works.class));
                }else if (id== R.id.nav_perfil){
                    startActivity(new Intent(getActivity(), Profile.class));
                }else if (id== R.id.nav_publicar){
                    startActivity(new Intent(getActivity(), Publish_Project.class));
                }else if (id== R.id.nav_publicados){
                    startActivity(new Intent(getActivity(), Project_Main.class));
                }else if (id== R.id.nav_evaluacion){
                    startActivity(new Intent(getActivity(), Evaluation.class));
                }else if (id== R.id.nav_exit){

                    if (controller.comprobar_conectado()==true){
                        controller.actualizar_estado_conexion(controller.obtener_id_conectado(),0);
                        //b_login.setEnabled(true);
                       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            getActivity().getApplication().getApplicationContext().finishAffinity();
                        }*/
                        startActivity(new Intent(c,MainActivity.class));

                    }else{
                        Toast.makeText(getContext(),"inicia sesion primero",Toast.LENGTH_SHORT).show();
                    }

                }


                return true;
            }
        }) ;
       // setupHeader();
        return  view ;
    }
}
