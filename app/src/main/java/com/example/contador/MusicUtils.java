package com.example.contador;

import android.content.Context;
import android.media.MediaPlayer;

public class MusicUtils {
    // Variable estática para mantener una única instancia del MediaPlayer
    private static MediaPlayer mediaPlayer = null;

    // Método estático para iniciar la música
    public static void startMusic(Context context) {
        // Comprueba si el MediaPlayer no ha sido inicializado
        if (mediaPlayer == null) {
            // Crea una instancia del MediaPlayer con una canción específica
            mediaPlayer = MediaPlayer.create(context, R.raw.music);
            // Configura el MediaPlayer para que la música se repita continuamente
            mediaPlayer.setLooping(true);
            // Inicia la reproducción de la música
            mediaPlayer.start();
            // Si el MediaPlayer ya existe pero no se está reproduciendo música, inicia la reproducción
        } else if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    // Método estático para pausar la música
    public static void pauseMusic() {
        // Comprueba si el MediaPlayer está inicializado y reproduciendo música
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            // Pausa la música
            mediaPlayer.pause();
        }
    }

}
