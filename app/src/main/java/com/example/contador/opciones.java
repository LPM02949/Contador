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
        // Establece un listener para el evento de clic para volver a la pantalla de inicio
        atrasImage.setOnClickListener(view -> atras());

        // Construye un diálogo con ejemplos que yo he puesto pero ninguna opción hace nada
        AlertDialog.Builder constructor = new AlertDialog.Builder(this);
        constructor.setMessage("Escoge moneda en que jugar") // Mensaje del diálogo
                .setTitle("Estás en el juego Moneditas") // Título del diálogo
                .setIcon(R.mipmap.ic_launcher) // Ícono del diálogo
                // Botón 1
                .setPositiveButton("JUGAR EN EUROS", (dialog, i) -> {

                })
                // Botón 2
                .setNegativeButton("JUGAR EN DOLARES", (dialog, which) -> {

                })
                // Botón 3
                .setNeutralButton("JUGAR EN LIBRAS", (dialog, which) -> {

                });
        // Crea y muestra el diálogo
        AlertDialog dialogo = constructor.create();
        dialogo.show();

        // Inicializa un calendario para obtener la fecha actual
        final Calendar calendario = Calendar.getInstance();
        int year = calendario.get(Calendar.YEAR); // Año actual
        int month = calendario.get(Calendar.MONTH); // Mes actual
        int day = calendario.get(Calendar.DAY_OF_MONTH); // Día actual
        // Crea y muestra un DatePickerDialog para seleccionar una fecha
        DatePickerDialog fecha = new DatePickerDialog(this, (view, year1, month1, day1) -> {
            // Código a ejecutar cuando se selecciona una fecha
        }, year, month, day);
        fecha.show();

        // Obtiene la hora y minuto actuales
        int hora = calendario.get(Calendar.HOUR);
        int minuto = calendario.get(Calendar.MINUTE);
        // Crea y muestra un TimePickerDialog para seleccionar una hora
        TimePickerDialog horario = new TimePickerDialog(this, (view, hora1, minuto1) -> {
            // Código a ejecutar cuando se establece la hora
        }, hora, minuto, false);
        horario.show();

    }

    // Volver atras
    private void atras() {
        Intent intent = new Intent(this, pantallaInicio.class);
        startActivity(intent);
    }
}
