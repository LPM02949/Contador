package com.example.contador;

import static com.example.contador.PantallaInicio.THEME_KEY;
import static com.example.contador.PantallaInicio.THEME_PREFERENCES;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class nosotros extends ListActivity {
    private static final String THEME_PREFERENCES = "theme_preferences" ;
    private ColaboradoresAdapter adapter;
    private List<Colaborador> colaboradores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getSharedPreferences(THEME_PREFERENCES, MODE_PRIVATE);
        int themeMode = prefs.getInt(THEME_KEY, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        AppCompatDelegate.setDefaultNightMode(themeMode);
        setContentView(R.layout.activity_nosotros);
        colaboradores = new ArrayList<>(Arrays.asList(
                new Colaborador("Antonio Álvarez Fueyo", "CEO", R.drawable.antonio),
                new Colaborador("Pedro Álvarez Naves", "Jefe Proyecto", R.drawable.pedro),
                new Colaborador("Raúl Fernández Pin", "Coordinador", R.drawable.raul),
                new Colaborador("Marcos Cordero Carbajal", "Ayudante Coordinador", R.drawable.marcos),
                new Colaborador("Mónica Álvarez Fueyo", "Programador", R.drawable.monica),
                new Colaborador("Vanesa Díaz Pombol", "Programador", R.drawable.vanesa),
                new Colaborador("Marta Fueyo Ferreiro", "Programador", R.drawable.marta)));
        adapter = new ColaboradoresAdapter(this, R.layout.elementos_lista, colaboradores);
        setListAdapter(adapter);
        registerForContextMenu(getListView());

        ImageView atrasImage = findViewById(R.id.atras);
        atrasImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atras();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contextual, menu);
    }

    // Manejar las acciones del menú contextual
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (item.getItemId() == R.id.opListaEditar) {
            // Editar el cargo del colaborador
            Colaborador colaborador = adapter.getItem(info.position);
            if (colaborador != null) {
                colaborador.editarCargo();
                adapter.notifyDataSetChanged();
            }
            return true;
        } else if (item.getItemId() == R.id.opListaEliminar) {
            // Eliminar el colaborador del adaptador y de la lista de colaboradores
            colaboradores.remove(info.position);
            adapter.notifyDataSetChanged();
            return true;
        }

        return super.onContextItemSelected(item);
    }


    private void atras() {
        Intent intent = new Intent(this, Info.class);
        startActivity(intent);
    }

    public class Colaborador {
        private final String nombre;
        private String cargo;
        private final int imagen;

        public Colaborador(String nombre, String cargo, int imagen) {
            this.nombre = nombre;
            this.cargo=cargo;
            this.imagen=imagen;
        }
        public String getNombre() {
            return nombre;
        }
        public String getCargo() {
            return cargo;
        }
        public int getImagen(){
            return imagen;
        }
        public void editarCargo() {
            this.cargo += " (editado)";
        }

    }
    public class ColaboradoresAdapter extends ArrayAdapter<Colaborador>{
        public ColaboradoresAdapter(@NonNull Context context, int resource, @NonNull List<Colaborador> objects) {
            super (context, resource, objects);
        }
        @NonNull
        @Override
        public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent){
            Colaborador col = getItem(position);
            if(convertView==null)
                convertView= LayoutInflater.from(this.getContext()).inflate(R.layout.elementos_lista,parent,false);
            ((ImageView) convertView.findViewById(R.id.imageView)).setImageResource(col.getImagen());
            ((TextView) convertView.findViewById(R.id.nombreTextView)).setText(col.getNombre());
            ((TextView) convertView.findViewById(R.id.cargoTextView)).setText(col.getCargo());
            return convertView;
        }
    }
}

