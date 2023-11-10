package com.example.contador;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class otrasCosas extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otras_cosas);

        // Configurar OnClickListener para todos los botones e ImageView
        findViewById(R.id.atras).setOnClickListener(this);
        findViewById(R.id.botonWeb).setOnClickListener(this);
        findViewById(R.id.botonLlamada).setOnClickListener(this);
        findViewById(R.id.botonCamara).setOnClickListener(this);
        findViewById(R.id.botonCorreo).setOnClickListener(this);
        findViewById(R.id.botonMapa).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        int id = v.getId();

        if (id == R.id.atras) {
            atras();
        } else if (id == R.id.botonWeb) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.educastur.es"));
            startActivity(intent);
        } else if (id == R.id.botonLlamada) {
            intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:985105500"));
            startActivity(intent);
        } else if (id == R.id.botonCamara) {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivity(intent);
        } else if (id == R.id.botonCorreo) {
            intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"micorreo@correo.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "asunto");
            intent.putExtra(Intent.EXTRA_TEXT, "texto de correo");
            startActivity(intent);
        } else if (id == R.id.botonMapa) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:43.359792,-5.85579"));
            startActivity(intent);
        }
    }

    private void atras() {
        Intent intent = new Intent(this, miscelanea.class);
        startActivity(intent);
    }
}
