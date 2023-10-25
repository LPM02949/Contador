package com.example.contador;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigInteger;

public class Carrito extends AppCompatActivity {
    BigInteger num;
    BigInteger valorClick = new BigInteger("1");
    BigInteger incAuto = new BigInteger("0");
    int tiempoAutoClick = 2000;
    private int costeBillete = 100;
    private int costeOro = 250;
    private boolean autoClickEnabled = false;
    private long autoClickDelay;

    TextView textValorClick;
    TextView textValorAutoClick;
    TextView textVelocidadAutoClick;
    TextView textMonedasContador;
    TextView textContBilletes;
    TextView textContOro;
    TextView textContPlata;
    TextView textContTesoro;
    private Button botonMejora1, botonMejora2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);
    }
    public void volverJuego(View v) {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("MONEY_COUNT", num.toString());
        i.putExtra("CLICK_VALUE", inc.toString());
        i.putExtra("AUTOCLICK_VALUE", incAuto.toString());
        i.putExtra("AUTOCLICK_TIME", tiempoAutoClick);
        startActivity(i);
    }

    public void setContText() {
        textValorClick.setText("Click: " + inc.toString());
        textValorAutoClick.setText("Autoclick: " + incAuto.toString());
        textVelocidadAutoClick.setText("Velocidad Autoclick: " + tiempoAutoClick + "m" + "s");

        textMonedasContador.setText(num.toString());


    }

    public void mejora1(View v) {
        if (num >= costeBillete) {
            num -= costeBillete;
            valorClick++;
            updateCounter();
            costeBillete += 20;
            botonMejora1.setText("Comprar por " + costeBillete + " coins");


        }
    }

    public void sumarAuto() {
        new Thread(() -> {
            while (true) {
                num = num.add(incAuto);
                runOnUiThread(this::setContText);
                try {
                    Thread.sleep(tiempoAutoClick);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

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

    public void incrementarAuto(View v) {
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
        if (num.longValue() >= 200) {

            incAuto = incAuto.add(BigInteger.valueOf(1));
            num = num.subtract(BigInteger.valueOf(200));
            setContText();
        }
    }
}