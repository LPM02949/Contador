package com.example.contador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    TextView contador;
    int num = 0, inc = 1, incAuto = 0, tiempoAutoClick = 2000;
    int costeBillete = 100, costeOro = 250, costePlata = 500, costeTesoro = 1000;
    int contadorbilletes = 0, contadororo = 0, contadorplata = 0, contadortesoro = 0;
    TextView textValorClick, textValorAutoClick, textVelocidadAutoClick;
    ImageView coin_image;
    private int idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Obtiene el ID del usuario
        idUsuario = getIntent().getIntExtra("ID_USUARIO", -1);
        // Inicialización de componentes
        contador = findViewById(R.id.textocontador);
        coin_image = findViewById(R.id.coin);
        textValorClick = findViewById(R.id.textValorClick);
        textValorAutoClick = findViewById(R.id.textValorAutoClick);
        textVelocidadAutoClick = findViewById(R.id.textVelocidadAutoClick);
        ImageView atrasImage = findViewById(R.id.atras);
        if (textValorClick != null) {
            textValorClick.setText("Tu texto aquí");
        } else {
            // Manejo del caso donde textView es null
            Log.e("MainActivity", "TextView no encontrado");
        }
        // Configurar listeners y UI
        atrasImage.setOnClickListener(view -> atras());
        setContText();
        sumarAuto();
        // Intentar cargar los datos del usuario
        try {
            if (idUsuario != -1) {
                cargarDatosUsuario();
            }
        } catch (SQLiteException e) {
            Toast.makeText(this, "Error al cargar los datos del usuario.", Toast.LENGTH_SHORT).show();
            Log.e("MainActivity", "Error al cargar datos de usuario", e);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (idUsuario != -1) {
            guardarDatosUsuario();
        }

    }
    private void atras() {
        Intent intent = new Intent(this, pantallaInicio.class);
        startActivity(intent);
    }

    public void setContText() {
        if (textValorClick != null && textValorAutoClick != null && textVelocidadAutoClick != null && contador != null) {
            textValorClick.setText(getString(R.string.clicks_text, inc));
            textValorAutoClick.setText(getString(R.string.autoclicks_text, incAuto));
            textVelocidadAutoClick.setText(getString(R.string.velocidad_text, tiempoAutoClick));
            contador.setText(formatNumber(num));
        } else {
            Log.e("MainActivity", "Algun TextView es null");
        }
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

    public void reiniciarJuego() {
        // Restablece los valores a sus valores iniciales
        num = 0;
        inc = 1;
        incAuto = 0;
        tiempoAutoClick = 2000;
        costeBillete = 100;
        costeOro = 250;
        costePlata = 500;
        costeTesoro = 1000;
        contadorbilletes = 0;
        contadororo = 0;
        contadorplata = 0;
        contadortesoro = 0;

        // Guarda los valores restablecidos
        guardarDatosUsuario();

        // Actualiza la interfaz de usuario
        setContText();
    }
    public void irACompras(View view) {
        //pasa los datos que tiene a la pantalla carrito
        Intent i = new Intent(this, carrito.class);
        i.putExtra("ID_USUARIO", idUsuario);
        startActivity(i);
    }
    private void cargarDatosUsuario() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor=null;
        try{
            cursor = db.query(DatabaseHelper.TABLE_DATOS_JUEGO,
                    new String[]{"moneyCount", "clickValue", "autoClickValue", "autoClickTime", "costeBillete", "costeOro", "costePlata", "costeTesoro", "contBilletes", "contOro", "contPlata", "contTesoro"},
                    "idUsuario = ?",
                    new String[]{String.valueOf(idUsuario)},
                    null, null, null);


        if (cursor != null && cursor.moveToFirst()) {
            // Asignación de los valores a tus variables de juego
            num = cursor.getInt(cursor.getColumnIndex("moneyCount"));
            inc = cursor.getInt(cursor.getColumnIndex("clickValue"));
            incAuto = cursor.getInt(cursor.getColumnIndex("autoClickValue"));
            tiempoAutoClick = cursor.getInt(cursor.getColumnIndex("autoClickTime"));
            costeBillete = cursor.getInt(cursor.getColumnIndex("costeBillete"));
            costeOro = cursor.getInt(cursor.getColumnIndex("costeOro"));
            costePlata = cursor.getInt(cursor.getColumnIndex("costePlata"));
            costeTesoro = cursor.getInt(cursor.getColumnIndex("costeTesoro"));
            contadorbilletes = cursor.getInt(cursor.getColumnIndex("contBilletes"));
            contadororo = cursor.getInt(cursor.getColumnIndex("contOro"));
            contadorplata = cursor.getInt(cursor.getColumnIndex("contPlata"));
            contadortesoro = cursor.getInt(cursor.getColumnIndex("contTesoro"));

            // Actualizar la interfaz de usuario con estos datos
            setContText();
        }
        } catch (SQLiteException e) {
            Log.e("MainActivity", "Error al cargar datos de usuario", e);
            // Opcional: Mostrar un mensaje al usuario, etc.
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }
    private void guardarDatosUsuario() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("moneyCount", num);
        values.put("clickValue", inc);
        values.put("autoClickValue", incAuto);
        values.put("autoClickTime", tiempoAutoClick);
        values.put("costeBillete", costeBillete);
        values.put("costeOro", costeOro);
        values.put("costePlata", costePlata);
        values.put("costeTesoro", costeTesoro);
        values.put("contBilletes", contadorbilletes);
        values.put("contOro", contadororo);
        values.put("contPlata", contadorplata);
        values.put("contTesoro", contadortesoro);

        try {
            db.update(DatabaseHelper.TABLE_DATOS_JUEGO, values, "idUsuario = ?", new String[]{String.valueOf(idUsuario)});
        } catch (SQLiteException e) {
            Toast.makeText(this, "Error al guardar los datos.", Toast.LENGTH_SHORT).show();
            Log.e("DatabaseError", "Error al actualizar la base de datos", e);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (idUsuario != -1) {
            cargarDatosUsuario();
        } else {
            reiniciarJuego();
        }
        setContText();
    }
}
