package com.example.contador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Registro extends AppCompatActivity {
    private EditText etNombreUsuario, etContrasena;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        ImageView atrasImage = findViewById(R.id.atras);
        atrasImage.setOnClickListener(view -> atras());
        dbHelper = new DatabaseHelper(this);
        etNombreUsuario = findViewById(R.id.escribirNuevoUsuario);
        etContrasena = findViewById(R.id.escribirNuevaContrasena);

        Button btnRegistrar = findViewById(R.id.botonRegistro);
        btnRegistrar.setOnClickListener(v -> registrarUsuario());
    }

    private void registrarUsuario() {
        String nombreUsuario = etNombreUsuario.getText().toString().trim();
        String contrasenya = etContrasena.getText().toString().trim();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombreUsuario", nombreUsuario);
        values.put("contraseña", contrasenya);

        long id = db.insert(DatabaseHelper.TABLE_USUARIOS, null, values);
        if (id > 0) {
            // Inserta valores predeterminados en DatosJuego
            ContentValues initialValues = new ContentValues();
            initialValues.put("idUsuario", id);
            initialValues.put("moneyCount", 0); // Valores predeterminados
            initialValues.put("clickValue", 1);
            initialValues.put("autoClickValue", 0);
            initialValues.put("autoClickTime", 1000);
            initialValues.put("costeBillete", 100);
            initialValues.put("costeOro", 250);
            initialValues.put("costePlata", 500);
            initialValues.put("costeTesoro", 1000);
            initialValues.put("contBilletes", 0);
            initialValues.put("contOro", 0);
            initialValues.put("contPlata", 0);
            initialValues.put("contTesoro", 0);
            db.insert(DatabaseHelper.TABLE_DATOS_JUEGO, null, initialValues);

            // Continúa con el inicio de MainActivity
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("ID_USUARIO", (int) id);
            startActivity(intent);
        } else {
            Toast.makeText(this, "El nombre de usuario ya existe", Toast.LENGTH_LONG).show();
        }
    }


    private void atras() {
        Intent intent = new Intent(this, InicioActivity.class);
        startActivity(intent);
    }
}

