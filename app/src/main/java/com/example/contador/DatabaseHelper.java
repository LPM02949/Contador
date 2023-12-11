package com.example.contador;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TABLE_USUARIOS = "Usuarios";
    public static final String TABLE_DATOS_JUEGO = "DatosJuego" ;
    public static final String DATABASE_NAME = "JuegoDatabase";
    public static final int DATABASE_VERSION = 1;




    // Usuarios
    private static final String CREATE_TABLE_USUARIOS =
            "CREATE TABLE " + TABLE_USUARIOS + "(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombreUsuario TEXT UNIQUE NOT NULL," +
                    "contraseña TEXT NOT NULL);";

    // Datos del Juego
    private static final String CREATE_TABLE_DATOS_JUEGO =
            "CREATE TABLE " + TABLE_DATOS_JUEGO + "(" +
                    "idUsuario INTEGER PRIMARY KEY," +
                    "moneyCount INTEGER," +
                    "clickValue INTEGER," +
                    "autoClickValue INTEGER," +
                    "autoClickTime INTEGER," +
                    "costeBillete INTEGER," +
                    "costeOro INTEGER," +
                    "costePlata INTEGER," +
                    "costeTesoro INTEGER," +
                    "contBilletes INTEGER," +
                    "contOro INTEGER," +
                    "contPlata INTEGER," +
                    "contTesoro INTEGER," +
                    "FOREIGN KEY (idUsuario) REFERENCES " + TABLE_USUARIOS + "(id));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USUARIOS);
        db.execSQL(CREATE_TABLE_DATOS_JUEGO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //metodo necesario para actualizaciones de base de datos
    }
}

