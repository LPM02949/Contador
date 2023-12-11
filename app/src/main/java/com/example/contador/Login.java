package com.example.contador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private EditText etNombreUsuario, etContrasena;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ImageView atrasImage = findViewById(R.id.atras);
        atrasImage.setOnClickListener(view -> atras());

        dbHelper = new DatabaseHelper(this);
        etNombreUsuario = findViewById(R.id.escribirUsuario);
        etContrasena = findViewById(R.id.escribirContraseña);

        Button btnIniciarSesion = findViewById(R.id.botonIniciarSesion);
        btnIniciarSesion.setOnClickListener(v -> iniciarSesion());
    }

    private void iniciarSesion() {
        String nombreUsuario = etNombreUsuario.getText().toString().trim();
        String contraseña = etContrasena.getText().toString().trim();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_USUARIOS,
                new String[]{"id", "nombreUsuario", "contraseña"},
                "nombreUsuario=? AND contraseña=?",
                new String[]{nombreUsuario, contraseña},
                null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex("id");
                if (idIndex != -1) {
                    int idUsuario = cursor.getInt(idIndex);
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("ID_USUARIO", idUsuario);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Error: La columna 'id' no se encuentra.", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos.", Toast.LENGTH_LONG).show();
            }
            cursor.close();
        } else {
            Toast.makeText(this, "Error al realizar la consulta.", Toast.LENGTH_LONG).show();
        }
    }

    private void atras() {
        Intent intent = new Intent(this, InicioActivity.class);
        startActivity(intent);
    }
}

