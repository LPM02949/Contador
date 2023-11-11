package com.example.contador;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class pantallaInicio extends AppCompatActivity {
    static final String THEME_PREFERENCES = "theme_preferences";
    static final String THEME_KEY = "theme_key";
    private static final String MUSIC_PREFERENCE = "music_preference";
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences(THEME_PREFERENCES, MODE_PRIVATE);

        int themeMode = prefs.getInt(THEME_KEY, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        AppCompatDelegate.setDefaultNightMode(themeMode);

        // Comienza a reproducir la música si la preferencia está activada o es la primera vez que se inicia la app
        boolean musicEnabled = prefs.getBoolean(MUSIC_PREFERENCE, true); // Default is true
        if (musicEnabled) {
            MusicUtils.startMusic(this);
        }

        setContentView(R.layout.activity_pantalla_inicio);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences.Editor editor = prefs.edit();
        if (item.getItemId() == R.id.mp1o1) {
            editor.putInt(THEME_KEY, AppCompatDelegate.MODE_NIGHT_NO);
        } else if (item.getItemId() == R.id.mp1o2) {
            editor.putInt(THEME_KEY, AppCompatDelegate.MODE_NIGHT_YES);
        } else if (item.getItemId() == R.id.mp1o3) {
            // Activar música
            editor.putBoolean(MUSIC_PREFERENCE, true);
            MusicUtils.startMusic(this);
        } else if (item.getItemId() == R.id.mp1o4) {
            // Desactivar música
            editor.putBoolean(MUSIC_PREFERENCE, false);
            MusicUtils.pauseMusic();
            // Llamamos a releaseMusic() aquí si queremos liberar el MediaPlayer completamente.
            // MusicUtils.releaseMusic();
        }
        editor.apply();

        // Solo recrear si se cambia el tema, no para la música.
        if (item.getItemId() == R.id.mp1o1 || item.getItemId() == R.id.mp1o2) {
            recreate();
        }
        return true;
    }


    // Lógica para iniciar el juego
    public void iniciarJuego(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // Lógica para salir de la aplicación
    public void salir(View view) {
        finishAffinity();
    }

    // Lógica para mostrar la pantalla de información
    public void mostrarInfo(View view) {
        Intent intent = new Intent(this, info.class);
        startActivity(intent);
    }
    public void irMiscelanea(View view) {
        Intent intent = new Intent(this, miscelanea.class);
        startActivity(intent);
    }
    public void irOtros(View view) {
        Intent intent = new Intent(this, opciones.class);
        startActivity(intent);
    }



    @Override
    protected void onResume() {
        super.onResume();
        // Reinicia o continua la música cuando la aplicación se reanuda, según la preferencia
        if (prefs.getBoolean(MUSIC_PREFERENCE, true)) {
            MusicUtils.startMusic(this);
        }
    }
}
