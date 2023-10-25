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

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    TextView contador;
    int num = 999;
    int inc=1;
    int incAuto =0;
    int tiempoAutoClick = 0;
    TextView textValorClick;
    TextView textValorAutoClick;
    TextView textVelocidadAutoClick;


    
    ImageView coin_image;
    
    //private ExecutorService executor; // Para ejecutar la pulsación automática en segundo plano
    //private Handler handler; // Para realizar operaciones en el hilo principal (UI Thread)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contador = findViewById(R.id.textocontador);
        coin_image = findViewById(R.id.coin);
        contador.setText(formatNumber(num));
        //executor = Executors.newSingleThreadExecutor(); // Inicializa el ExecutorService para tareas en segundo plano
        //handler = new Handler(Looper.getMainLooper()); // Inicializa el Handler para operaciones en el hilo principal (UI Thread)
        ImageView atrasImage = findViewById(R.id.atras); // Obtén la referencia a la ImageView "atras"
        textValorClick=(TextView)findViewById(R.id.textValorClick);
        textValorAutoClick=(TextView) findViewById(R.id.textValorAutoClick);
        textVelocidadAutoClick=(TextView) findViewById(R.id.textVelocidadAutoClick);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            num = extras.getInt("MONEY_COUNT");
            inc = extras.getInt("CLICK_VALUE");
            incAuto = extras.getInt("AUTOCLICK_VALUE");
            tiempoAutoClick = extras.getInt("AUTOCLICK_TIME");

        // Agrega un click listener a la ImageView "atras"
        atrasImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atras();
            }


        });
        };
        setContText();
    }
    // Método para volver a la pantalla de inicio (PantallaInicio)
    public void atras() {
        Intent intent = new Intent(this, PantallaInicio.class);
        startActivity(intent);
    }
    public void setContText() {
        textValorClick.setText("Clicks: " + inc);
        textValorAutoClick.setText("Autoclicks: " + incAuto);
        textVelocidadAutoClick.setText("Velocidad Autoclicks: " + tiempoAutoClick + "m" + "s");
    }
    // Método para dar formato al numero del contador
        private String formatNumber (int value) {
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
        num += inc;
        updateCounter();
    }

    public void irACompras(View view) {
        Intent i = new Intent(this, Carrito.class);
        i.putExtra("MONEY_COUNT", num);
        i.putExtra("CLICK_VALUE", inc);
        i.putExtra("AUTOCLICK_VALUE", incAuto);
        i.putExtra("AUTOCLICK_TIME", tiempoAutoClick);
        startActivity(i);
        finish();
    }


}



