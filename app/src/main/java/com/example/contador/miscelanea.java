package com.example.contador;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class miscelanea extends AppCompatActivity implements View.OnClickListener, ActivityResultCallback<ActivityResult> {
    // Variable para manejar el lanzamiento de actividades esperando un resultado
    ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miscelanea);

        // Asigna el evento onClick a los botones mediante el manejador actual
        findViewById(R.id.boton_verificar).setOnClickListener(this);
        findViewById(R.id.boton_cosas).setOnClickListener(this);

        // Registra un lanzador de actividad para obtener un resultado de la actividad lanzada
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this);
    }

    // Implementación del evento onClick para manejar los clics en los botones
    @Override
    public void onClick(View view) {
        // Identifica qué botón fue presionado y ejecuta la acción correspondiente
        if(view.getId() == R.id.boton_verificar) {
            // Prepara un intent para la verificación y lanza la actividad esperando un resultado
            Intent intent = new Intent(this, verificacion.class);
            // Obtiene el nombre del EditText y forma la pregunta
            String nombre = ((EditText) findViewById(R.id.editNombre)).getText().toString();
            String pregunta = String.format(getResources().getString(R.string.bot_pregunta), nombre);
            // Agrega la pregunta al intent y lanza la actividad
            intent.putExtra("pregunta", pregunta);
            launcher.launch(intent);
        } else if (view.getId() == R.id.boton_cosas) {
            // Inicia la actividad 'otrasCosas' cuando se presiona el botón correspondiente
            Intent intent = new Intent(this, otrasCosas.class);
            startActivity(intent);
        } else if (view.getId() == R.id.atras) {
            // Maneja el evento clic en el botón de atrás
            atras();
        }
    }

    // Método que maneja el resultado de la actividad lanzada cuando esta finaliza
    @Override
    public void onActivityResult(ActivityResult result) {
        // Comprueba si el resultado de la actividad es OK y si es así, realiza una acción
        if(result.getResultCode() == Activity.RESULT_OK){
            // Obtiene los datos devueltos por la actividad
            Intent datos = result.getData();
            // Asegura que los datos no son nulos y establece el texto de respuesta en el TextView
            assert datos != null;
            ((TextView)findViewById(R.id.textRespuesta)).setText(datos.getStringExtra("resultado"));
        }
    }

    // Método usado anteriormente
    private void atras() {
        Intent intent = new Intent(this, pantallaInicio.class);
        startActivity(intent);
    }
}
