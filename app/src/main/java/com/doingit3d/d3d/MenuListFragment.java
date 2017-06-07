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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

/**
 * Created by david.martin on 10/05/2017.
 */

public class MenuListFragment extends Fragment {
    private Menu nav_menu;
    private BBDD_Controller controller;
    private ImageView img;
    private TextView email,nombre;
    private Context c;
    private Intent i;
    private LinearLayout fondo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        c= getActivity().getApplication().getApplicationContext();
        i=new Intent(getContext(),Project_Main.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container,false);

        NavigationView vNavigation = (NavigationView) view.findViewById(R.id.nav_view);
        nav_menu= vNavigation.getMenu();

        //se usa este view y el metodo getHeaderView para poder encontrar los elementos con findViewById, ya que no son del mismo layout que este fragmento que es el NavigationDrawer
        View vista = vNavigation.getHeaderView(0);
        img=(de.hdodenhof.circleimageview.CircleImageView) vista.findViewById(R.id.imagen_cabecera);
        nombre=(TextView) vista.findViewById(R.id.nav_username);
        email=(TextView) vista.findViewById(R.id.nav_useremail);
        fondo=(LinearLayout) vista.findViewById(R.id.fondoslider);

        controller = new BBDD_Controller(c);




        if (controller.comprobar_conectado()==true){

           // Toast.makeText(getContext(),"HAY ALGUIEN CONECTADO",Toast.LENGTH_SHORT).show();
            nav_menu.findItem(R.id.nav_perfil).setVisible(true);
            nav_menu.findItem(R.id.nav_map).setVisible(true);
            nav_menu.findItem(R.id.nav_buscar).setVisible(true);
            nav_menu.findItem(R.id.nav_publicar).setVisible(true);
            nav_menu.findItem(R.id.nav_publicados).setVisible(true);
            nav_menu.findItem(R.id.nav_mis_proyectos).setVisible(true);
            nav_menu.findItem(R.id.nav_evaluacion).setVisible(true);
            nav_menu.findItem(R.id.nav_mensajes).setVisible(true);
            nav_menu.findItem(R.id.nav_exit).setVisible(true);
            nav_menu.findItem(R.id.nav_inicio).setVisible(false);
            img.setVisibility(View.VISIBLE);

            //AQUI SE CAMBIA LA IMAGEN, EL NOMBRE Y EL EMAIL DEL MENU LATERAL SEGUN EL USUARIO QUE SE HAYA CONECTADO
            nombre.setText(controller.username_conectado());
            email.setText(controller.useremail_conectado());
            img.setImageBitmap(controller.obtener_imagen());
            fondo.setBackgroundResource(R.drawable.fondoslider2);


        }else if (controller.comprobar_conectado()==false){
            nav_menu.findItem(R.id.nav_perfil).setVisible(false);
            nav_menu.findItem(R.id.nav_map).setVisible(false);
            nav_menu.findItem(R.id.nav_buscar).setVisible(false);
            nav_menu.findItem(R.id.nav_publicar).setVisible(false);
            nav_menu.findItem(R.id.nav_publicados).setVisible(false);
            nav_menu.findItem(R.id.nav_mis_proyectos).setVisible(false);
            nav_menu.findItem(R.id.nav_evaluacion).setVisible(false);
            nav_menu.findItem(R.id.nav_mensajes).setVisible(false);
            nav_menu.findItem(R.id.nav_exit).setVisible(false);
            nav_menu.findItem(R.id.nav_inicio).setVisible(true);
            img.setVisibility(View.INVISIBLE);

        }else{
            System.out.println("AQUI ES DONDE FALLA Y NO ENTRA");
        }



        vNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                int id = item.getItemId();

                if (id== R.id.nav_como_funciona){
                    startActivity(new Intent(getActivity(),How_does_it_works.class));

                }else if (id== R.id.nav_inicio){
                    startActivity(new Intent(getActivity(), MainActivity.class));

                }else if (id== R.id.nav_map){
                    startActivity(new Intent(getActivity(), MapsActivity.class));


                }else if (id== R.id.nav_buscar){
                    startActivity(new Intent(getActivity(), Buscar_profesionales.class));

                }else if (id== R.id.nav_perfil){
                    startActivity(new Intent(getActivity(), Profile.class));

                }else if (id== R.id.nav_publicar){
                    startActivity(new Intent(getActivity(), Publish_Project.class));

                }else if (id== R.id.nav_chat){
                    startActivity(new Intent(getActivity(), ChatMain.class));

                }else if (id== R.id.nav_mis_proyectos){
                    //si la actividad se inicia desde aqui se mostraran solo los proyectos del usuario
                    i.putExtra("origen",1);
                    startActivity(i);

                }else if (id== R.id.nav_publicados){
                    //si la actividad se inicia desde aqui se mostraran todos los proyectos
                    i.putExtra("origen",0);
                    startActivity(i);

                }else if (id== R.id.nav_evaluacion){
                    startActivity(new Intent(getActivity(), Evaluation.class));

                }else if (id== R.id.nav_mensajes){
                    startActivity(new Intent(getActivity(), Messages.class));

                }else if (id== R.id.nav_exit){


                    if (controller.comprobar_conectado()==true){
                        if (AccessToken.getCurrentAccessToken() != null && com.facebook.Profile.getCurrentProfile() != null){
                            //Logged in so show the login button
                            controller.actualizar_estado_conexion(controller.obtener_id_conectado(),0);
                            LoginManager.getInstance().logOut();
                            startActivity(new Intent(c,MainActivity.class));

                        }else{
                            controller.actualizar_estado_conexion(controller.obtener_id_conectado(),0);
                            startActivity(new Intent(c,MainActivity.class));
                        }


                    }else{
                        Toast.makeText(getContext(),"inicia sesion primero",Toast.LENGTH_SHORT).show();
                    }
                }

                return true;
            }
        }) ;

        return  view ;
    }
}
