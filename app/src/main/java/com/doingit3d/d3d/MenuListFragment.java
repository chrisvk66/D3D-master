package com.doingit3d.d3d;



import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.transition.Visibility;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by david.martin on 10/05/2017.
 */

public class MenuListFragment extends Fragment {
    private Menu nav_menu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container,
                false);


        NavigationView vNavigation = (NavigationView) view.findViewById(R.id.nav_view);
        nav_menu= vNavigation.getMenu();

        //ESTAS OPCIONES NO SERAN VISIBLEA HASTA QUE SE INICIE SESION; DE MOMENTO LO COMENTO MIENTRAS HACEMOS PRUEBAS
        /*nav_menu.findItem(R.id.nav_perfil).setVisible(false);
        nav_menu.findItem(R.id.nav_buscar).setVisible(false);
        nav_menu.findItem(R.id.nav_publicar).setVisible(false);
        nav_menu.findItem(R.id.nav_publicados).setVisible(false);
        nav_menu.findItem(R.id.nav_mis_proyectos).setVisible(false);
        nav_menu.findItem(R.id.nav_evaluacion).setVisible(false);
        nav_menu.findItem(R.id.nav_mensajes).setVisible(false);
        nav_menu.findItem(R.id.nav_exit).setVisible(false);*/


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
                }
                return true;
            }
        }) ;
       // setupHeader();
        return  view ;
    }
}
