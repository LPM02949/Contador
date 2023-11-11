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

    private List<Centro> listaCentros;
    private centrosAdapter adapter;
    private int itemPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centros);

        ImageView atrasImage = findViewById(R.id.atras);
        atrasImage.setOnClickListener(view -> atras());

        RecyclerView rw = findViewById(R.id.recyclerView2);
        rw.setHasFixedSize(true);
        rw.setLayoutManager(new LinearLayoutManager(this));

        listaCentros = new ArrayList<>(Arrays.asList(
                new Centro("Of. Madrid", "Pso. Castellana, 2. Madrid", R.drawable.madrid),
                new Centro("Of. Asturias", "Calle Uria, 23. Oviedo", R.drawable.oviedo),
                new Centro("Of. Galicia", "Pso Riazor, 102. Coruña", R.drawable.coruna)
        ));

        adapter = new centrosAdapter(listaCentros);
        rw.setAdapter(adapter);
    }

    private void atras() {
        Intent intent = new Intent(this, info.class);
        startActivity(intent);
    }


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (itemPosition == -1) {
            return super.onContextItemSelected(item);
        }

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


    public static class Centro {
        private final String nombre;
        private String direccion;
        private final int imagen;

        public Centro(String nombre, String direccion, int imagen) {
            this.nombre = nombre;
            this.direccion = direccion;
            this.imagen = imagen;
        }

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
        private final List<Centro> centros;

        public centrosAdapter(List<Centro> centros) {
            this.centros = centros;
        }

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
            TextView nombre;
            TextView direccion;
            ImageView imagen;

            ViewHolder(View itemView) {
                super(itemView);
                nombre = itemView.findViewById(R.id.nombretextView);
                direccion = itemView.findViewById(R.id.dirtextView);
                imagen = itemView.findViewById(R.id.imageView);

                itemView.setOnCreateContextMenuListener(this);
            }

            void bind(Centro centro) {
                nombre.setText(centro.getNombre());
                direccion.setText(centro.getDireccion());
                imagen.setImageResource(centro.getImagen());
            }

            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                itemPosition = getAdapterPosition();
                MenuInflater inflater = new MenuInflater(v.getContext());
                inflater.inflate(R.menu.menu_contextual, menu);
            }
        }
    }
    private void mostrarDialogoEditarDireccion(int position) {
        Centro centro = listaCentros.get(position);

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
