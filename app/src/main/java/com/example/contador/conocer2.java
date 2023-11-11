package com.example.contador;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class conocer2 extends AppCompatActivity {
    private ColaboradoresAdapter adapter;
    private List<Colaborador> colaboradores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conocer2);

        ListView listView = findViewById(R.id.list);
        colaboradores = new ArrayList<>(Arrays.asList(
                new Colaborador("Antonio Álvarez Fueyo", "CEO", R.drawable.antonio),
                new Colaborador("Pedro Álvarez Naves", "Jefe Proyecto", R.drawable.pedro),
                new Colaborador("Raúl Fernández Pin", "Coordinador", R.drawable.raul),
                new Colaborador("Marcos Cordero Carbajal", "Ayudante Coordinador", R.drawable.marcos),
                new Colaborador("Mónica Álvarez Fueyo", "Programador", R.drawable.monica),
                new Colaborador("Vanesa Díaz Pombol", "Programador", R.drawable.vanesa),
                new Colaborador("Marta Fueyo Ferreiro", "Programador", R.drawable.marta)
                ));
        adapter = new ColaboradoresAdapter(this, R.layout.elementos_lista, colaboradores);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);

        ImageView atrasImage = findViewById(R.id.atras);
        atrasImage.setOnClickListener(view -> atras());
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
            assert info != null;
            conocer2.Colaborador colaborador = adapter.getItem(info.position);
            if (colaborador != null) {
                colaborador.editarCargo();
                adapter.notifyDataSetChanged();
            }
            return true;
        } else if (item.getItemId() == R.id.opListaEliminar) {
            // Eliminar el colaborador del adaptador y de la lista de colaboradores
            assert info != null;
            colaboradores.remove(info.position);
            adapter.notifyDataSetChanged();
            return true;
        }

        return super.onContextItemSelected(item);
    }


    private void atras() {
        Intent intent = new Intent(this, info.class);
        startActivity(intent);
    }

    public static class Colaborador {
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
    public static class ColaboradoresAdapter extends ArrayAdapter<conocer2.Colaborador> {
        public ColaboradoresAdapter(@NonNull Context context, int resource, @NonNull List<conocer2.Colaborador> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            // Obtener el colaborador para la posición actual
            conocer2.Colaborador col = getItem(position);

            // Comprobar si la vista existente se está reutilizando, de lo contrario inflar la vista
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.elementos_lista, parent, false);
            }

            // Asegúrate de que el colaborador no sea nulo antes de configurar los datos en la vista
            if (col != null) {
                ((ImageView) convertView.findViewById(R.id.imageView)).setImageResource(col.getImagen());
                ((TextView) convertView.findViewById(R.id.nombreTextView)).setText(col.getNombre());
                ((TextView) convertView.findViewById(R.id.cargoTextView)).setText(col.getCargo());
            }

            return convertView;
        }
    }

}

