package com.example.contador;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private TextView contador;
    private int num = 999;
    private int costeBillete = 100;
    private int costeOro = 1000;
    private int valorClick = 1;
    private Button botonMejora1, botonMejora2;
    private ImageView coin_image;
    private ExecutorService executor; // Para ejecutar la pulsación automática en segundo plano
    private Handler handler; // Para realizar operaciones en el hilo principal (UI Thread)
    private boolean autoClickEnabled = false; // Indica si la pulsación automática está habilitada
    private long autoClickDelay; // Retraso inicial de la pulsación automática.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contador = findViewById(R.id.textocontador);
        botonMejora1 = findViewById(R.id.boton_billetes);
        botonMejora2 = findViewById(R.id.boton_oro);
        coin_image = findViewById(R.id.coin);
        contador.setText(formatNumber(num));
        executor = Executors.newSingleThreadExecutor(); // Inicializa el ExecutorService para tareas en segundo plano
        handler = new Handler(Looper.getMainLooper()); // Inicializa el Handler para operaciones en el hilo principal (UI Thread)
        ImageView atrasImage = findViewById(R.id.atras); // Obtén la referencia a la ImageView "atras"

        // Agrega un click listener a la ImageView "atras"
        atrasImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atras();
            }
        });
    }
    // Método para volver a la pantalla de inicio (PantallaInicio)
    public void atras() {
        Intent intent = new Intent(this, PantallaInicio.class);
        startActivity(intent);
    }

    private String formatNumber(int value) {
        if (value < 1000) {
            return String.valueOf(value);
        } else if (value < 1000000) {
            return new DecimalFormat("0.00K").format(value / 1000.0);
        } else if (value < 1000000000) {
            return new DecimalFormat("0.00M").format(value / 1000000.0);
        } else {
            return new DecimalFormat("0.00B").format(value / 1000000000.0);
        }
    }

    private void updateCounter() {
        contador.setText(formatNumber(num));
    }

    public void sumar(View v) {
        ScaleAnimation fade_in = new ScaleAnimation(0.7f, 1.2f, 0.7f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        fade_in.setDuration(100);
        coin_image.startAnimation(fade_in);
        num += valorClick;
        updateCounter();
    }

    public void mejora1(View v) {
        if (num >= costeBillete) {
            num -= costeBillete;
            valorClick++;
            updateCounter();
            costeBillete += 20;
            botonMejora1.setText("Comprar por " + costeBillete + " coins");

            if (!autoClickEnabled) {
                autoClickEnabled = true; // Habilita la pulsación automática
                autoClickDelay = 2000; // Establece que el click inicial automático sea cada 2 segundos
                startAutoClick(); //
            } else {
                autoClickDelay -= 100; // Con cada mejora el click automático será 0.1 segundos mas rápido
                if (autoClickDelay < 100) {
                    autoClickDelay = 100; // Fijo que todo lo rápido que serán los click automaticos sea 0.1 segundos
                }
            }
        }
    }

    public void mejora2(View v) {
        if (num >= costeOro) {
            num -= costeOro;
            valorClick+=5;
            updateCounter();
            costeOro += 200;
            botonMejora2.setText("Comprar por " + costeOro + " coins");

            if (!autoClickEnabled) {
                autoClickEnabled = true; // Habilita la pulsación automática
                autoClickDelay = 1000; // Establece que el click inicial automático sea cada 1 segundo
                startAutoClick(); //
            } else {
                autoClickDelay -= 50; // Con cada mejora el click automático será 0.05 segundos mas rápido
                if (autoClickDelay < 50) {
                    autoClickDelay = 50; // Fijo que todo lo rápido que serán los click automaticos sea 0.05 segundos
                }
            }
        }
    }

    // Iniciamos pulsación automática
    private void startAutoClick() {
        // Ejecuta esta tarea en un hilo secundario (en segundo plano)
        executor.execute(() -> {
            // Mientras la pulsación automática esté habilitada
            while (autoClickEnabled) {
                try {
                    // Pausa el hilo durante un período de tiempo determinado (autoClickDelay) en milisegundos
                    Thread.sleep(autoClickDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Envía tarea al hilo principal (UI Thread) para realizar una pulsación manual
                handler.post(() -> {
                    try {
                        // Realiza una pulsación manual en la moneda llamando al método 'sumar'
                        sumar(null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }
}