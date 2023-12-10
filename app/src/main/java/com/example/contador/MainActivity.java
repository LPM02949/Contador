package com.example.contador;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    TextView contador;
    int num = 999, inc = 1, incAuto = 0, tiempoAutoClick = 1000;
    int costeBillete = 100, costeOro = 250, costePlata = 500, costeTesoro = 1000;
    int contadorbilletes = 0, contadororo = 0, contadorplata = 0, contadortesoro = 0;
    TextView textValorClick, textValorAutoClick, textVelocidadAutoClick;
    ImageView coin_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialización de componentes
        contador = findViewById(R.id.textocontador);
        coin_image = findViewById(R.id.coin);
        textValorClick = findViewById(R.id.textValorClick);
        textValorAutoClick = findViewById(R.id.textValorAutoClick);
        textVelocidadAutoClick = findViewById(R.id.textVelocidadAutoClick);
        ImageView atrasImage = findViewById(R.id.atras);

        // Recuperar datos guardados
        recuperarDatos();

        // Listeners
        atrasImage.setOnClickListener(view -> atras());
        setContText();
        sumarAuto();
    }

    @Override
    protected void onPause() {
        super.onPause();
        guardarDatos();
    }

    private void guardarDatos() {
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt("MONEY_COUNT", num);
        editor.putInt("CLICK_VALUE", inc);
        editor.putInt("AUTOCLICK_VALUE", incAuto);
        editor.putInt("AUTOCLICK_TIME", tiempoAutoClick);
        editor.putInt("COSTE_BILLETE", costeBillete);
        editor.putInt("COSTE_ORO", costeOro);
        editor.putInt("COSTE_PLATA", costePlata);
        editor.putInt("COSTE_TESORO", costeTesoro);
        editor.putInt("CONT_BILLETES", contadorbilletes);
        editor.putInt("CONT_ORO", contadororo);
        editor.putInt("CONT_PLATA", contadorplata);
        editor.putInt("CONT_TESORO", contadortesoro);

        editor.apply();
    }


    private void recuperarDatos() {
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);

        num = prefs.getInt("MONEY_COUNT", 999999999);
        inc = prefs.getInt("CLICK_VALUE", 1);
        incAuto = prefs.getInt("AUTOCLICK_VALUE", 0);
        tiempoAutoClick = prefs.getInt("AUTOCLICK_TIME", 1000);
        costeBillete = prefs.getInt("COSTE_BILLETE", 100);
        costeOro = prefs.getInt("COSTE_ORO", 250);
        costePlata = prefs.getInt("COSTE_PLATA", 500);
        costeTesoro = prefs.getInt("COSTE_TESORO", 1000);
        contadorbilletes = prefs.getInt("CONT_BILLETES", 0);
        contadororo = prefs.getInt("CONT_ORO", 0);
        contadorplata = prefs.getInt("CONT_PLATA", 0);
        contadortesoro = prefs.getInt("CONT_TESORO", 0);
    }


    private void atras() {
        Intent intent = new Intent(this, pantallaInicio.class);
        startActivity(intent);
    }

    public void setContText() {
        textValorClick.setText(getString(R.string.clicks_text, inc));
        textValorAutoClick.setText(getString(R.string.autoclicks_text, incAuto));
        textVelocidadAutoClick.setText(getString(R.string.velocidad_text, tiempoAutoClick));
        contador.setText(formatNumber(num));
    }

    private String formatNumber(int value) {
        //método inicial que ideé para manejar los grandes numeros. Mejorado en carrito
        if (value < 1000) {
            return String.valueOf(value);
        } else if (value < 1000000) {
            return new DecimalFormat("0.00K").format(value / 1000.0);
        } else if (value < 1000000000) {
            return new DecimalFormat("0.00M").format(value / 1000000.0);
        } else if (value < 1000000000000L) {
            return new DecimalFormat("0.00B").format(value / 1000000000.0);
        } else {
            return new DecimalFormat("0.00T").format(value / 1000000000000.0);
        }
    }




    public void sumar(View v) {
        ScaleAnimation fade_in = new ScaleAnimation(0.7f, 1.2f, 0.7f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        fade_in.setDuration(100);
        coin_image.startAnimation(fade_in);
        num += inc;
        updateCounter();
    }

    public void sumarAuto() {
        //inicia el click automático.
        new Thread(() -> {
            while (true) {
                num += incAuto;
                    runOnUiThread(this::setContText);
                try {
                    Thread.sleep(tiempoAutoClick);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void updateCounter() {
        //actualiza el contador con formato
        contador.setText(formatNumber(num));
    }

    public void reiniciarJuego(View view) {
        // Restablece los valores a sus valores iniciales
        num = 999;
        inc = 1;
        incAuto = 0;
        tiempoAutoClick = 1000;
        costeBillete = 100;
        costeOro = 250;
        costePlata = 500;
        costeTesoro = 1000;
        contadorbilletes = 0;
        contadororo = 0;
        contadorplata = 0;
        contadortesoro = 0;

        // Guarda los valores restablecidos
        guardarDatos();

        // Actualiza la interfaz de usuario
        setContText();
    }
    public void irACompras(View view) {
        //pasa los datos que tiene a la pantalla carrito
        Intent i = new Intent(this, carrito.class);
        i.putExtra("MONEY_COUNT", num);
        i.putExtra("CLICK_VALUE", inc);
        i.putExtra("AUTOCLICK_VALUE", incAuto);
        i.putExtra("AUTOCLICK_TIME", tiempoAutoClick);
        i.putExtra("COSTE_BILLETE", costeBillete);
        i.putExtra("COSTE_ORO", costeOro);
        i.putExtra("COSTE_PLATA", costePlata);
        i.putExtra("COSTE_TESORO", costeTesoro);
        i.putExtra("CONT_BILLETES", contadorbilletes);
        i.putExtra("CONT_ORO", contadororo);
        i.putExtra("CONT_PLATA", contadorplata);
        i.putExtra("CONT_TESORO", contadortesoro);
        startActivity(i);
    }
}
