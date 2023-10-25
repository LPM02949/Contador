package com.example.contador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Carrito extends AppCompatActivity {
    int num;
    int inc = 0;
    int incAuto = 0;
    int tiempoAutoClick = 0;
    int costeBillete = 100;
    int costeOro = 250;
    int costePlata = 500;
    int costeTesoro = 1000;
    int contadorbilletes = 0;
    int contadororo = 0;
    int contadorplata = 0;
    int contadortesoro = 0;
    boolean autoClickEnabled = false;


    TextView textValorClick;
    TextView textValorAutoClick;
    TextView textVelocidadAutoClick;
    TextView textMonedasContador;
    TextView textContBilletes;
    TextView textContOro;
    TextView textContPlata;
    TextView textContTesoro;
    private Button botonMejora1, botonMejora2;
    private ExecutorService executor; // Para ejecutar la pulsación automática en segundo plano
    private Handler handler; // Para realizar operaciones en el hilo principal (UI Thread)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);
        textMonedasContador = (TextView) findViewById(R.id.textMonedasContador);
        executor = Executors.newSingleThreadExecutor(); // Inicializa el ExecutorService para tareas en segundo plano
        handler = new Handler(Looper.getMainLooper()); // Inicializa el Handler para operaciones en el hilo principal (UI Thread)
        botonMejora1 = findViewById(R.id.boton_billetes);
        botonMejora2 = findViewById(R.id.boton_oro);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            num = extras.getInt("MONEY_COUNT");
            inc = extras.getInt("CLICK_VALUE");
            incAuto = extras.getInt("AUTOCLICK_VALUE");
            tiempoAutoClick = extras.getInt("AUTOCLICK_TIME");
        }
        textValorClick=(TextView)findViewById(R.id.textValorClick);
        textValorAutoClick=(TextView) findViewById(R.id.textValorAutoClick);
        textVelocidadAutoClick=(TextView) findViewById(R.id.textVelocidadAutoClick);
        textMonedasContador=(TextView) findViewById(R.id.textMonedasContador);
        setContText();
    }

    public void volverAlJuego(View v) {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("MONEY_COUNT", num);
        i.putExtra("CLICK_VALUE", inc);
        i.putExtra("AUTOCLICK_VALUE", incAuto);
        i.putExtra("AUTOCLICK_TIME", tiempoAutoClick);
        startActivity(i);
    }

    public void setContText() {
        textValorClick.setText("Clicks: " + inc);
        textValorAutoClick.setText("Autoclicks: " + incAuto);
        textVelocidadAutoClick.setText("Velocidad Autoclicks: " + tiempoAutoClick + "m" + "s");

        textMonedasContador.setText(formatNumber(num));
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
        textMonedasContador.setText(formatNumber(num));
    }

    public void sumar(View v) {
        num += inc;
        updateCounter();
    }

    public void mejora1(View v) {
        if (num >= costeBillete) {
            num -= costeBillete;
            inc++;
            contadorbilletes++;
            updateCounter();
            costeBillete += 20;
            botonMejora1.setText("Comprar por " + costeBillete + " coins");
        }
    }
    public void mejora2(View v) {
        if (num >= costeOro) {
            num -= costeOro;
            inc += 5;
            updateCounter();
            costeOro += 200;
            botonMejora2.setText("Comprar por " + costeOro + " coins");
        }
    }

            public void mejora3 (View v){
                if (num >= costePlata) {
                    num -= costePlata;
                    inc++;
                    contadorplata++;
                    updateCounter();
                    costePlata += 100;
                    botonMejora2.setText("Comprar por" + costePlata + " coins");
                    if (!autoClickEnabled) {
                        autoClickEnabled = true; // Habilita la pulsación automática
                        tiempoAutoClick = 2000;
                        startAutoClick(); //
                    } else {
                        tiempoAutoClick -= 100; // Con cada mejora el click automático será 0.1 segundos mas rápido
                        if (tiempoAutoClick < 100) {
                            tiempoAutoClick = 100; // Fijo que todo lo rápido que serán los click automaticos sea 0.1 segundos
                        }
                    }
                }
            }


    private void startAutoClick() {
        // Ejecuta esta tarea en un hilo secundario (en segundo plano)
        executor.execute(() -> {
            // Mientras la pulsación automática esté habilitada
            while (autoClickEnabled) {
                try {
                    // Pausa el hilo durante un período de tiempo determinado (autoClickDelay) en milisegundos
                    Thread.sleep(tiempoAutoClick);
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
