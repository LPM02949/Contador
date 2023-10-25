package com.example.contador;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PantallaInicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicio);
    }

    public void iniciarJuego(View view) {
        // Crear un Intent para abrir la actividad MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void salir(View view) {
        finishAffinity(); // Cierra todas las actividades en el historial y finaliza la aplicación
    }

    public void mostrarInfo(View view) {
        // Crear un Intent para abrir la actividad InfoActivity
        Intent intent = new Intent(this, Info.class);
        startActivity(intent);
    }
}
