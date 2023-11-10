package com.example.contador;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class miscelanea extends AppCompatActivity implements View.OnClickListener, ActivityResultCallback<ActivityResult> {
ActivityResultLauncher<Intent>launcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miscelanea);
        ImageView atrasImage = findViewById(R.id.atras);
        ((Button)findViewById(R.id.boton_verificar)).setOnClickListener(this);
        ((Button)findViewById(R.id.boton_cosas)).setOnClickListener(this);

        launcher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.boton_verificar) {
            Intent intent = new Intent(this, verificacion.class);
            String nombre = ((EditText) findViewById(R.id.editNombre)).getText().toString();
            String pregunta = String.format(getResources().getString(R.string.bot_pregunta), nombre);
            intent.putExtra("pregunta", pregunta);
            launcher.launch(intent);
        } else if (view.getId()==R.id.boton_cosas) {
            Intent intent=new Intent(this, otrasCosas.class);
            startActivity(intent);

        } else if (view.getId()==R.id.atras) {atras();}


    }

    @Override
    public void onActivityResult(ActivityResult result) {
        if(result.getResultCode()== Activity.RESULT_OK){
            Intent datos = result.getData();
            ((TextView)findViewById(R.id.textRespuesta)).setText(datos.getStringExtra("resultado"));
        }

    }
    private void atras() {
        Intent intent = new Intent(this, PantallaInicio.class);
        startActivity(intent);
    }
}