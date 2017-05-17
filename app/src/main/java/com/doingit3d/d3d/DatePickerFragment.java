package com.doingit3d.d3d;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by David M on 07/05/2017.
 */

/**
 * A simple {@link Fragment} subclass.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    //ESTA CLASE ES EL SELECTOR DE FECHA

    private TextView tv_fecha;
    private int day,month,year;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Aqui se recoge el dia/mes/año seleccionados
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        //Create a new DatePickerDialog instance and return it
        /*
            DatePickerDialog Public Constructors - Here we uses first one
            public DatePickerDialog (Context context, DatePickerDialog.OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth)
            public DatePickerDialog (Context context, int theme, DatePickerDialog.OnDateSetListener listener, int year, int monthOfYear, int dayOfMonth)
         */
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        //La fecha seleccionada se escribirá en el textview al lado del boton
        String dia,mes , anyo;
        dia=String.valueOf(day);
        mes=String.valueOf(month);
        anyo=String.valueOf(year);


        tv_fecha=(TextView) getActivity().findViewById(R.id.tv_fecha_seleccionada);
        tv_fecha.setText(anyo + "/" + mes + "/" + dia);


    }

}