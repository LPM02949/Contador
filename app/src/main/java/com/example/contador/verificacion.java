package com.example.contador;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class verificacion extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Establece el layout de la actividad de verificación
        setContentView(R.layout.activity_verificacion);

        // Asigna el manejador de eventos de clicK a los botones de aceptar y rechazar
        findViewById(R.id.boton_aceptar).setOnClickListener(this);
        findViewById(R.id.boton_rechazar).setOnClickListener(this);

        // Configura el texto de la pregunta de verificación basado en la información pasada en el intent
        ((TextView)findViewById(R.id.text_verificacion)).setText(getIntent().getStringExtra("pregunta"));
    }

    @Override
    public void onClick(View view) {
        // Crea un intent para devolver datos a la actividad padre
        Intent intent = new Intent();
        // Determina qué botón se presionó y establece el resultado correspondiente en el intent
        if (view.getId() == R.id.boton_aceptar) {
            // Si el botón aceptar fue presionado, añade el resultado "SI, ..." al intent
            intent.putExtra("resultado", "SI, soy jugador");
        } else if (view.getId() == R.id.boton_rechazar) {
            // Si el botón rechazar fue presionado, añade el resultado "NO, ....." al intent
            intent.putExtra("resultado", "NO, aún no soy jugador");
        }
        // Establece el resultado de la actividad como OK y pasa el intent con los datos
        setResult(RESULT_OK, intent);
        // Finaliza la actividad y retorna a la actividad que la llamó
        finish();
    }
}
