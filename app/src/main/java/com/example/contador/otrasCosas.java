package com.example.contador;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class otrasCosas extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otras_cosas);

        /* Configura esta clase como el OnClickListener para los botones e ImageView
           Así, cuando se haga clic en estos elementos, se llamará al método onClick de esta clase*/
        findViewById(R.id.atras).setOnClickListener(this);
        findViewById(R.id.botonWeb).setOnClickListener(this);
        findViewById(R.id.botonLlamada).setOnClickListener(this);
        findViewById(R.id.botonCamara).setOnClickListener(this);
        findViewById(R.id.botonCorreo).setOnClickListener(this);
        findViewById(R.id.botonMapa).setOnClickListener(this);
    }

    // Implementación del método onClick para manejar los eventos de clic en la vista
    @Override
    public void onClick(View v) {
        // Intent genérico para iniciar actividades
        Intent intent;
        // Obtiene el ID de la vista que fue clickeada
        int id = v.getId();

        // Determina qué acción realizar basado en el ID de la vista
        if (id == R.id.atras) {
            // Método que maneja la acción de volver a la actividad anterior
            atras();
        } else if (id == R.id.botonWeb) {
            // Inicia una actividad para ver una página web
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.educastur.es"));
            startActivity(intent);
        } else if (id == R.id.botonLlamada) {
            // Inicia una actividad para marcar un número de teléfono
            intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:985105500"));
            startActivity(intent);
        } else if (id == R.id.botonCamara) {
            // Inicia una actividad para tomar una foto
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivity(intent);
        } else if (id == R.id.botonCorreo) {
            // Inicia una actividad para enviar un correo electrónico
            intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822"); // Establece el tipo MIME para el correo electrónico
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"micorreo@correo.com"}); // Destinatarios
            intent.putExtra(Intent.EXTRA_SUBJECT, "asunto"); // Asunto del correo
            intent.putExtra(Intent.EXTRA_TEXT, "texto de correo"); // Cuerpo del correo
            startActivity(intent);
        } else if (id == R.id.botonMapa) {
            // Inicia una actividad para ver una ubicación en el mapa
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:43.359792,-5.85579"));
            startActivity(intent);
        }
    }

    // Método para volver a la actividad 'miscelanea'
    private void atras() {
        Intent intent = new Intent(this, miscelanea.class);
        startActivity(intent);
    }
}
