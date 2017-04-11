package com.doingit3d.d3d;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by David M on 28/02/2017.
 */

public class CustomSwipeAdapter extends PagerAdapter{

    //NO BORREIS EL CODIGO COMENTADO
    Activity activity;
    private LayoutInflater layoutinflater;
    private int[] fotos;
    //private String []in;

    /*public CustomSwipeAdapter(Activity activity, int[]fotos, String []in){
        this.activity=activity;
        this.fotos=fotos;
        this.in=in;

    }*/

    public CustomSwipeAdapter(Activity activity, int[]fotos){
        this.activity=activity;
        this.fotos=fotos;
       // this.in=in;

    }


    @Override
    public int getCount() {
        return fotos.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //se infla el layout
        layoutinflater=(LayoutInflater)activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view =layoutinflater.inflate(R.layout.swipe_layout,container,false);

        //el imageview del swipe_layout
        ImageView im =(ImageView) item_view.findViewById(R.id.imagenes_instrucciones);
        im.setImageResource(fotos[position]);
        //TextView tv = (TextView)item_view.findViewById(R.id.textswipe);
       // tv.setText(in[position]);
        container.addView(item_view);
        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}
