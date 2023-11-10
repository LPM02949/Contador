package com.example.contador;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class opciones extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);
        ImageView atrasImage = findViewById(R.id.atras);
        atrasImage.setOnClickListener(view -> atras());
        AlertDialog.Builder constructor = new AlertDialog.Builder(this);
        constructor.setMessage("Escoge moneda en que jugar").setTitle("Estás en el juego Moneditas")
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("JUGAR EN EUROS", (dialog, i) -> {

                })
                .setNegativeButton("JUGAR EN DOLARES", (dialog, which) -> {

                })
                .setNeutralButton("JUGAR EN LIBRAS", (dialog, which) -> {

                });
        AlertDialog dialogo = constructor.create();
        dialogo.show();
        final Calendar calendario = Calendar.getInstance();
        int year = calendario.get(Calendar.YEAR);
        int month = calendario.get(Calendar.MONTH);
        int day = calendario.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog fecha = new DatePickerDialog(this, (view, year1, month1, day1) -> {

        }, year, month, day);
        fecha.show();
        int hora = calendario.get(Calendar.HOUR);
        int minuto = calendario.get(Calendar.MINUTE);
        TimePickerDialog horario = new TimePickerDialog(this, (view, hora1, minuto1) -> {
            // Código a ejecutar cuando se establece la hora
        }, hora, minuto, false);
        horario.show();

    }
    private void atras() {
        Intent intent = new Intent(this, pantallaInicio.class);
        startActivity(intent);
    }
}