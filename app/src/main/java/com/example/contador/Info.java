package com.example.contador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class Info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ImageView atrasImage = findViewById(R.id.atras); // Obtén la referencia a la ImageView "atras" en la actividad Info

        atrasImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atras();
            }
        });
    }

    // Método para volver a la actividad PantallaInicio
    private void atras() {
        Intent intent = new Intent(this, PantallaInicio.class);
        startActivity(intent);
    }

    public void irCentros(View view) {
        // Crear un Intent para abrir la actividad MainActivity
        Intent intent = new Intent(this, centros.class);
        startActivity(intent);
    }
    public void irConocenos(View view) {
        // Crear un Intent para abrir la actividad MainActivity
        Intent intent = new Intent(this, nosotros.class);
        startActivity(intent);
    }
}