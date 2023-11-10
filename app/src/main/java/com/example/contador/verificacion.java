package com.example.contador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class verificacion extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificacion);
        ((Button)findViewById(R.id.boton_aceptar)).setOnClickListener(this);
        ((Button)findViewById(R.id.boton_rechazar)).setOnClickListener(this);
        ((TextView)findViewById(R.id.text_verificacion)).setText(getIntent().getStringExtra("pregunta"));
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        if (view.getId()==R.id.boton_aceptar) {
            intent.putExtra("resultado", "SI, soy jugador");
        }else if (view.getId()==R.id.boton_rechazar) {
            intent.putExtra("resultado", "NO, aún no soy jugador");
        }
        setResult(RESULT_OK, intent);
        finish();

    }
}