package com.example.contador;

import android.content.SharedPreferences;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;

public abstract class MetodosJuego extends AppCompatActivity {
    /*Creo esta clase porque tenia estos 5 métodos que eran exactamente iguales en la clase MainActivity
    y en carrito, pretendia optimizar el código pero no he conseguido que funcione bien.
     */

    // Variables compartidas entre MainActivity y carrito para la lógica del juego
    protected int num, inc, incAuto, tiempoAutoClick, costeBillete, costeOro, costePlata, costeTesoro, contadorbilletes, contadororo, contadorplata, contadortesoro;
    protected TextView textValorClick, textValorAutoClick, textVelocidadAutoClick, contador;

    protected void recuperarDatos() {
        // Método para recuperar datos guardados en SharedPreferences
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        // Recupera cada valor guardado o asigna un valor por defecto si no se encuentra
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
    protected void guardarDatos() {
        // Método para guardar los datos actuales en SharedPreferences
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
    protected String formatNumber(int value) {
        // Método para formatear los números grandes con sufijos K, M, B
        if (value < 1000) return String.valueOf(value);// Devuelve el valor como cadena si es menor que 1000

        String[] units = new String[]{"K", "M", "B"};
        int exponent = (int) (Math.log(value) / Math.log(1000));
        return new DecimalFormat("0.00").format(value / Math.pow(1000, exponent)) + units[exponent - 1];
    }
    public void sumarAuto() {
        // Método que inicia un hilo para aumentar automáticamente 'num' basado en 'incAuto'
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
    protected void setContText() {
        // Método para actualizar los textos de la interfaz con los valores actuales
        textValorClick.setText(getString(R.string.clicks_text, inc));
        textValorAutoClick.setText(getString(R.string.autoclicks_text, incAuto));
        textVelocidadAutoClick.setText(getString(R.string.velocidad_text, tiempoAutoClick));
        contador.setText(formatNumber(num));
    }
    protected void inicializarJuego() {
        //recuperarDatos();
        setContText();
        //sumarAuto();
    }
}
