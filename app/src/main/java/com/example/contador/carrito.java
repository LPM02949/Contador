package com.example.contador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class carrito extends AppCompatActivity {
    int num;
    int inc = 0;
    int incAuto = 0;
    int tiempoAutoClick = 1000;
    int costeBillete = 100;
    int costeOro = 250;
    int costePlata = 500;
    int costeTesoro = 1000;
    int contadorbilletes = 0;
    int contadororo = 0;
    int contadorplata = 0;
    int contadortesoro = 0;
    TextView textValorClick;
    TextView textValorAutoClick;
    TextView textVelocidadAutoClick;
    TextView textMonedasContador;
    TextView textContBilletes;
    TextView textContOro;
    TextView textContPlata;
    TextView textContTesoro;
    private Button botonMejora1, botonMejora2, botonMejora3, botonMejora4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);
        textMonedasContador = findViewById(R.id.textMonedasContador);
        botonMejora1 = findViewById(R.id.boton_billetes);
        botonMejora2 = findViewById(R.id.boton_oro);
        botonMejora3 = findViewById(R.id.boton_plata);
        botonMejora4 = findViewById(R.id.boton_tesoro);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            num = extras.getInt("MONEY_COUNT");
            inc = extras.getInt("CLICK_VALUE");
            incAuto = extras.getInt("AUTOCLICK_VALUE");
            tiempoAutoClick = extras.getInt("AUTOCLICK_TIME");
        }
        textValorClick= findViewById(R.id.textValorClick);
        textValorAutoClick= findViewById(R.id.textValorAutoClick);
        textVelocidadAutoClick= findViewById(R.id.textVelocidadAutoClick);
        textMonedasContador= findViewById(R.id.textMonedasContador);
        textContBilletes= findViewById(R.id.textContBilletes);
        textContOro= findViewById(R.id.textContOro);
        textContPlata= findViewById(R.id.textContPlata);
        textContTesoro= findViewById(R.id.textContTesoro);
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
        textValorClick.setText(getString(R.string.clicks_text, inc));
        textValorAutoClick.setText(getString(R.string.autoclicks_text, incAuto));
        textVelocidadAutoClick.setText(getString(R.string.velocidad_text, tiempoAutoClick));

        textMonedasContador.setText(formatNumber(num)); // Asumo que formatNumber es una función personalizada

        textContBilletes.setText(getString(R.string.billetes_text, contadorbilletes));
        textContOro.setText(getString(R.string.oro_text, contadororo));
        textContPlata.setText(getString(R.string.plata_text, contadorplata));
        textContTesoro.setText(getString(R.string.tesoros_text, contadortesoro));
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
            botonMejora1.setText(getString(R.string.comprar_por_text, costeBillete));
            textContBilletes.setText(getString(R.string.billetes_text, contadorbilletes));
        }
    }
    public void mejora2(View v) {
        if (num >= costeOro) {
            num -= costeOro;
            inc += 5;
            contadororo++;
            updateCounter();
            costeOro += 100;
            botonMejora2.setText(getString(R.string.comprar_por_text, costeOro));
            textContOro.setText(getString(R.string.oro_text, contadororo));
        }
    }

    public void mejora3 (View v){
        if (num >= costePlata) {
            num -= costePlata;
            inc++;
            contadorplata++;
            updateCounter();
            costePlata += 100;
            botonMejora3.setText(getString(R.string.comprar_por_text, costePlata));
            textContPlata.setText(getString(R.string.plata_text, contadorplata));
            incAuto++;
        }
    }
    public void mejora4 (View v){
        if (num >= costeTesoro) {
            num -= costeTesoro;
            inc++;
            contadortesoro++;
            updateCounter();
            costeTesoro += 200;
            botonMejora4.setText(getString(R.string.comprar_por_text, costeTesoro));
            textContTesoro.setText(getString(R.string.tesoros_text, contadortesoro));
            tiempoAutoClick-=100;
        }
    }
}

