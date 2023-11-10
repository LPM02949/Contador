package com.example.contador;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TimePicker;

import java.util.Calendar;

public class opciones extends AppCompatActivity {
    @RequiresApi(api= Build.VERSION_CODES.N)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);
        ImageView atrasImage = findViewById(R.id.atras);
        atrasImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atras();
            }
        });
        CharSequence[] Centros = {"Madrid", "Oviedo", "Coruña"};
        AlertDialog.Builder constructor = new AlertDialog.Builder(this);
        constructor.setMessage("Dialogo de prueba").setTitle("Titutlo del dialog")
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("PRIMER BOTON", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                    }
                })
                .setNegativeButton("SEGUNDO BOTON", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNeutralButton("TERCER BOTON", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog dialogo = constructor.create();
        dialogo.show();
        final Calendar calendario = Calendar.getInstance();
        int year = calendario.get(Calendar.YEAR);
        int month = calendario.get(Calendar.MONTH);
        int day = calendario.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog fecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {

            }
        }, year, month, day);
        fecha.show();
        final Calendar reloj = Calendar.getInstance();
        int hora = calendario.get(Calendar.HOUR);
        int minuto = calendario.get(Calendar.MINUTE);
        int segundo = calendario.get(Calendar.SECOND);
        TimePickerDialog horario = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hora, int minuto) {
                // Código a ejecutar cuando se establece la hora
            }
        }, hora, minuto, false);
        horario.show();

    }
    private void atras() {
        Intent intent = new Intent(this, PantallaInicio.class);
        startActivity(intent);
    }
}