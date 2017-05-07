package com.doingit3d.d3d;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.widget.TextView;
import android.widget.DatePicker;
import android.app.Dialog;
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
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Aqui se recoge el dia/mes/año seleccionados
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

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

        tv_fecha=(TextView) getActivity().findViewById(R.id.tv_fecha_seleccionada);
        tv_fecha.setText(day + "/" + month + "/" + year);


    }
}