package com.example.contador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class Registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        ImageView atrasImage = findViewById(R.id.atras);
        atrasImage.setOnClickListener(view -> atras());
    }
    private void atras() {
        Intent intent = new Intent(this, pantallaInicio.class);
        startActivity(intent);
    }
}