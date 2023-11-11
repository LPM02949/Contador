package com.example.contador;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class carrito extends AppCompatActivity {
    /*  Variables para la lógica del juego y componentes de la interfaz de usuario
        agrupadas para una mejor comprensión.

        Referencio a string en todos los textos para facilitar la adaptación a
        diferentes idiomas   */
    int num, inc = 0, incAuto = 0, tiempoAutoClick = 1000;
    int costeBillete = 100, costeOro = 250, costePlata = 500, costeTesoro = 1000;
    int contadorbilletes=0,contadororo = 0, contadorplata = 0, contadortesoro = 0;
    TextView textValorClick, textValorAutoClick, textVelocidadAutoClick;
    TextView textMonedasContador, textContBilletes, textContOro, textContPlata, textContTesoro;
    private Button botonMejora1, botonMejora2, botonMejora3, botonMejora4;
    private volatile boolean isRunning = true;//volatile para que sea visible inmediatamente en todos los hilos
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        // Inicialización de los componentes de la interfaz
        textMonedasContador = findViewById(R.id.textMonedasContador);
        botonMejora1 = findViewById(R.id.boton_billetes);
        botonMejora2 = findViewById(R.id.boton_oro);
        botonMejora3 = findViewById(R.id.boton_plata);
        botonMejora4 = findViewById(R.id.boton_tesoro);
        textValorClick = findViewById(R.id.textValorClick);
        textValorAutoClick = findViewById(R.id.textValorAutoClick);
        textVelocidadAutoClick = findViewById(R.id.textVelocidadAutoClick);
        textContBilletes = findViewById(R.id.textContBilletes);
        textContOro = findViewById(R.id.textContOro);
        textContPlata = findViewById(R.id.textContPlata);
        textContTesoro = findViewById(R.id.textContTesoro);

        // Recuperación de datos guardados
        recuperarDatos();

        // Actualización de los textos en la interfaz
        actualizarTextoBotones();
        actualizarTextoContadores();
        setContText();
        sumarAuto();//lo necesito para que si ya se ha activado mejora con
        //autoclick lo tenga en cuenta al regresar a carrito despues de pasar
        //por Main
    }
    public void sumarAuto() {
        //metodo que crea un hilo para manejar el clickAuto
        new Thread(() -> {
            while (isRunning) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false; // Detiene el hilo cuando la actividad se destruye
    }

    private void recuperarDatos() {
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        //recupero los datos de la aplicación y si no encuentra ninguno guardado usa los iniciales
        num = prefs.getInt("MONEY_COUNT", 999);
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
    private void guardarDatos() {
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        //guarda los datos de lo que hemos hecho en carrito
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
    public void volverAlJuego(View v) {
        //volver al juego
        guardarDatos();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
    public void setContText() {
        //muestra los datos de la aplicación de los clicks, cuantos en auto, velocidad y el contador
        textValorClick.setText(getString(R.string.clicks_text, inc));
        textValorAutoClick.setText(getString(R.string.autoclicks_text, incAuto));
        textVelocidadAutoClick.setText(getString(R.string.velocidad_text, tiempoAutoClick));

        textMonedasContador.setText(formatNumber(num));
    }
    private String formatNumber(int value) {
        // Si el valor es menor que 1000, retorna el número tal cual.
        if (value < 1000) return String.valueOf(value);

        // Array de unidades para representar los miles, millones, billones y trillones.
        String[] units = new String[]{"K", "M", "B", "T"};

        // Base 1000
        int exponent = (int) (Math.log(value) / Math.log(1000));

        /* Ajusta el exponente para que coincida con los índices de la matriz de unidades.
           Por ejemplo, para 1000, el logaritmo en base 1000 es 1, pero necesitamos usar el índice 0 ("K")*/
        exponent--;

        /* Asegura que el exponente no sea mayor que el número de unidades disponibles menos uno.
           Esto evita un índice fuera de rango al acceder al array de unidades.*/
        exponent = Math.min(exponent, units.length - 1);

        /* Formatea el número dividiendo el valor por 1000 elevado a (exponente + 1), ya que hemos
           disminuido el exponente en uno antes.
           Esto escala el número al tamaño correcto según la unidad correspondiente.*/
        return new DecimalFormat("0.00").format(value / Math.pow(1000, exponent + 1)) + units[exponent];
    }

    //Mejoras que puede usar el usuario
    public void mejora1(View v) {
        //Aumenta en 1 el valor de los clicks del usuario en la pantalla del juego
        if (num >= costeBillete) {
            num -= costeBillete;
            inc++;
            contadorbilletes++;
            updateCounter();
            costeBillete += 20;
            botonMejora1.setText(getString(R.string.comprar_por_text, costeBillete));//actualiza el boton al jhacer click
            textContBilletes.setText(getString(R.string.billetes_text, contadorbilletes));//actualiza el contador de veces usadas esta mejora
            actualizarValoresCompra();
        }
    }
    public void mejora2(View v) {
        //igual que la anterior pero aumentando de 5 en 5
        if (num >= costeOro) {
            num -= costeOro;
            inc += 5;
            contadororo++;
            updateCounter();
            costeOro += 100;
            botonMejora2.setText(getString(R.string.comprar_por_text, costeOro));
            textContOro.setText(getString(R.string.oro_text, contadororo));
            actualizarValoresCompra();
        }
    }
    public void mejora3 (View v) {
        //inicia el clickAuto y suma de 1 en 1
        if (num >= costePlata) {
            num -= costePlata;
            contadorplata++;
            updateCounter();
            costePlata += 100;
            botonMejora3.setText(getString(R.string.comprar_por_text, costePlata));
            textContPlata.setText(getString(R.string.plata_text, contadorplata));
            //si l clickAuto no está activado lo activa
            if (incAuto == 0) {
                incAuto += 1;
                sumarAuto();
            } else { //si ya está activado solo aumenta el valor de los clicks
                incAuto += 1;
            }
            actualizarValoresCompra();
        }
    }
    public void mejora4 (View v){
        //igual que la anterior pero de 5 en 5
        if (num >= costeTesoro) {
            num -= costeTesoro;
            contadortesoro++;
            updateCounter();
            costeTesoro += 200;
            botonMejora4.setText(getString(R.string.comprar_por_text, costeTesoro));
            textContTesoro.setText(getString(R.string.tesoros_text, contadortesoro));
            if(tiempoAutoClick>100) tiempoAutoClick -= 100;
            else tiempoAutoClick = 100;//es lo mas rápido que irá el autoclick
            if (incAuto == 0) {
                incAuto += 5;
                sumarAuto();
            } else incAuto += 5;
            actualizarValoresCompra();
        }
    }
    // métodos para actualizar los textos del carrito al usar las mejoras
    private void updateCounter() {
        textMonedasContador.setText(formatNumber(num));
    }
    private void actualizarTextoBotones() {
        //guarda valor de las compras en los botones no se pierda
        botonMejora1.setText(getString(R.string.comprar_por_text, costeBillete));
        botonMejora2.setText(getString(R.string.comprar_por_text, costeOro));
        botonMejora3.setText(getString(R.string.comprar_por_text, costePlata));
        botonMejora4.setText(getString(R.string.comprar_por_text, costeTesoro));
    }
    private void actualizarTextoContadores() {
        //guarda los datos de los contadores de veces que usas cada mejora
        textContBilletes.setText(getString(R.string.billetes_text, contadorbilletes));
        textContOro.setText(getString(R.string.oro_text, contadororo));
        textContPlata.setText(getString(R.string.plata_text, contadorplata));
        textContTesoro.setText(getString(R.string.tesoros_text, contadortesoro));
    }
    private void actualizarValoresCompra() {
        //guarda los datos del valor de clicks y velocidad
        textValorClick.setText(getString(R.string.clicks_text, inc));
        textValorAutoClick.setText(getString(R.string.autoclicks_text, incAuto));
        if (tiempoAutoClick <= 100) {
            textVelocidadAutoClick.setText(getString(R.string.velocidad_max));//muestra mensaje si has alcanzado la velocidad máxima
        } else {
            textVelocidadAutoClick.setText(getString(R.string.velocidad_text,tiempoAutoClick));
        }
    }
}
