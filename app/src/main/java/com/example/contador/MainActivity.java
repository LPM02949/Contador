package com.example.contador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    TextView contador;
    int num = 999;
    int inc=1;
    int incAuto =0;
    int tiempoAutoClick = 1000;
    TextView textValorClick;
    TextView textValorAutoClick;
    TextView textVelocidadAutoClick;
    ImageView coin_image;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contador = findViewById(R.id.textocontador);
        coin_image = findViewById(R.id.coin);
        contador.setText(formatNumber(num));
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
        }
        // Agrega un click listener a la ImageView "atras"
        atrasImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atras();
            }});


        setContText();
        sumarAuto();

    }
    // Método para volver a la pantalla de inicio (PantallaInicio)
    public void atras() {
        Intent intent = new Intent(this, pantallaInicio.class);
        startActivity(intent);
    }

    public void setContText() {
        textValorClick.setText("Clicks: " + inc);
        textValorAutoClick.setText("Autoclicks: " + incAuto);
        textVelocidadAutoClick.setText("Velocidad: " + tiempoAutoClick + "m" + "s");
        contador.setText(formatNumber(num));
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
    public void sumarAuto() {
        new Thread(() -> {
            while (true) {
                num += incAuto;
                runOnUiThread(this::setContText);
                try {
                    Thread.sleep(tiempoAutoClick);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    public void irACompras(View view) {
        Intent i = new Intent(this, carrito.class);
        i.putExtra("MONEY_COUNT", num);
        i.putExtra("CLICK_VALUE", inc);
        i.putExtra("AUTOCLICK_VALUE", incAuto);
        i.putExtra("AUTOCLICK_TIME", tiempoAutoClick);
        startActivity(i);
    }

}



