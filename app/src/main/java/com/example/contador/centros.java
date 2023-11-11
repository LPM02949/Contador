package com.example.contador;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class centros extends AppCompatActivity {

    // Lista para almacenar los objetos de tipo Centro
    private List<Centro> listaCentros;
    // Adaptador personalizado para manejar la visualización de los centros en un RecyclerView
    private centrosAdapter adapter;
    // Variable para mantener la posición del ítem seleccionado en el RecyclerView
    private int itemPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Establecer la vista del layout para esta Actividad
        setContentView(R.layout.activity_centros);

        // Configuración del botón de retroceso
        ImageView atrasImage = findViewById(R.id.atras);
        // Listener para manejar el evento de clic en el botón de retroceso
        atrasImage.setOnClickListener(view -> atras());

        // Inicialización y configuración del RecyclerView
        RecyclerView rw = findViewById(R.id.recyclerView2);
        // Mejora del rendimiento si el tamaño del RecyclerView no cambia
        rw.setHasFixedSize(true);
        // Establecer un LinearLayoutManager para posicionar los ítems en una lista vertical
        rw.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar la lista de centros con centros predefinidos
        listaCentros = new ArrayList<>(Arrays.asList(
                new Centro("Of. Madrid", "Pso. Castellana, 2. Madrid", R.drawable.madrid),
                new Centro("Of. Asturias", "Calle Uria, 23. Oviedo", R.drawable.oviedo),
                new Centro("Of. Galicia", "Pso Riazor, 102. Coruña", R.drawable.coruna)
        ));

        // Crear y asignar el adaptador al RecyclerView
        adapter = new centrosAdapter(listaCentros);
        rw.setAdapter(adapter);
    }


    private void atras() {
        //método estandar en el que me apoyo en Intent para volver a otra pantalla
        Intent intent = new Intent(this, info.class);
        startActivity(intent);
    }

    //Manejamos las diferentes opciones del menu contextual
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (itemPosition == -1) { //ver una posición válida
            return super.onContextItemSelected(item);
        }
        //usamos la opción escogida por el usuario
        if (item.getItemId() == R.id.opListaEditar) {
            // Editar la dirección del centro
            mostrarDialogoEditarDireccion(itemPosition);
            return true;
        } else if (item.getItemId() == R.id.opListaEliminar) {
            // Eliminar el centro
            listaCentros.remove(itemPosition);
            adapter.notifyItemRemoved(itemPosition);
            adapter.notifyItemRangeChanged(itemPosition,listaCentros.size());
            return true;
        }

        return super.onContextItemSelected(item);
    }


    public static class Centro { //clase interna que representa cada miembro
        private final String nombre;
        private String direccion;
        private final int imagen;

        public Centro(String nombre, String direccion, int imagen) {//constructor
            this.nombre = nombre;
            this.direccion = direccion;
            this.imagen = imagen;
        }
        //getters y setters necesarios
        public String getNombre() {
            return nombre;
        }

        public String getDireccion() {
            return direccion;
        }

        public void setDireccion(String direccion) {
            this.direccion = direccion;
        }

        public int getImagen() {
            return imagen;
        }

    }

    public class centrosAdapter extends RecyclerView.Adapter<centrosAdapter.ViewHolder> {
        //Adaptador personalizado del RecyclerView para que muestre los centros
        private final List<Centro> centros;

        public centrosAdapter(List<Centro> centros) {
            this.centros = centros;
        }//constructor
        //metodos del adaptador que crean, vinculan y obtienen el tamaño de la lista
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.centro_item, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.bind(centros.get(position));
        }

        @Override
        public int getItemCount() {
            return centros.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
            //personalizo el ViewHolder para que muestre los item como yo quiero
            TextView nombre;
            TextView direccion;
            ImageView imagen;

            ViewHolder(View itemView) { //inicializa y configura
                super(itemView);
                nombre = itemView.findViewById(R.id.nombretextView);
                direccion = itemView.findViewById(R.id.dirtextView);
                imagen = itemView.findViewById(R.id.imageView);

                itemView.setOnCreateContextMenuListener(this);
            }

            void bind(Centro centro) { //vincula
                nombre.setText(centro.getNombre());
                direccion.setText(centro.getDireccion());
                imagen.setImageResource(centro.getImagen());
            }

            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                //crea el menú conceptual
                itemPosition = getAdapterPosition();//obsoleto.. dejará de funcionar en versiones futuras
                MenuInflater inflater = new MenuInflater(v.getContext());
                inflater.inflate(R.menu.menu_contextual, menu);
            }
        }
    }
    // Método para mostrar un diálogo y permitir la edición de la dirección de un centro
    private void mostrarDialogoEditarDireccion(int position) {
        Centro centro = listaCentros.get(position);//centro seleccionado basandose en la posición de la lista

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Editar Dirección");

        // Configurar el EditText
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(centro.getDireccion());
        builder.setView(input);

        // Configurar los botones
        builder.setPositiveButton("Guardar", (dialog, which) -> {
            String nuevaDireccion = input.getText().toString();
            centro.setDireccion(nuevaDireccion);
            adapter.notifyItemChanged(position);
        });
        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());

        builder.show();
    }

}
