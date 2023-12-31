package com.example.contador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ImageView atrasImage = findViewById(R.id.atras); // Obtén la referencia a la ImageView "atras" en la actividad Info
        atrasImage.setOnClickListener(view -> atras());
    }

    // Método para volver a la actividad PantallaInicio
    private void atras() {
        Intent intent = new Intent(this, pantallaInicio.class);
        startActivity(intent);
    }

    public void irCentros(View view) {
        // Crear un Intent para abrir la actividad centros
        Intent intent = new Intent(this, centros.class);
        startActivity(intent);
    }
    public void irConocenos(View view) {
        // Crear un Intent para abrir la actividad conocer
        Intent intent = new Intent(this, conocer.class);
        startActivity(intent);
    }
    public void irConocenos2(View view) {
        // Crear un Intent para abrir la actividad mejorada de conocer que se adapta al tema
        Intent intent = new Intent(this, conocer2.class);
        startActivity(intent);
    }
}