package com.example.contador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class InicioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        Button btnIrARegistro = findViewById(R.id.btnIrARegistro);
        Button btnIrALogin = findViewById(R.id.btnIrALogin);

        btnIrARegistro.setOnClickListener(v -> {
            Intent intent = new Intent(InicioActivity.this, Registro.class);
            startActivity(intent);
        });

        btnIrALogin.setOnClickListener(v -> {
            Intent intent = new Intent(InicioActivity.this, Login.class);
            startActivity(intent);
        });
        ImageView atrasImage = findViewById(R.id.atras);
        // Establece un listener para el evento de clic para volver a la pantalla de inicio
        atrasImage.setOnClickListener(view -> atras());
    }
    private void atras() {
        Intent intent = new Intent(this, pantallaInicio.class);
        startActivity(intent);
    }
}
