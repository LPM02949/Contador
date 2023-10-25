package com.example.contador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class centros extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centros);
        ImageView atrasImage = findViewById(R.id.atras);
        atrasImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atras();
            }
        });
        RecyclerView rw = (RecyclerView) findViewById(R.id.recyclerView2);
        rw.setHasFixedSize(true);
        rw.setLayoutManager(new LinearLayoutManager(this));
        List<Centro> c= Arrays.asList(
                new Centro("Of. Madrid", "Pso. Castellana, 2. Madrid", R.drawable.madrid),
                new Centro("Of. Asturias", "Calle Uria, 23. Oviedo", R.drawable.oviedo),
                new Centro("Of. Galicia", "Pso Riazor, 102. Coruña", R.drawable.coruna)
        );
        rw.setAdapter(new centrosAdapter(c));
    }
    private void atras() {
        Intent intent = new Intent(this, Info.class);
        startActivity(intent);
    }

    public class Centro {
        private final String nombre;
        private final String direccion;
        private final int imagen;

        public Centro(String nombre, String direccion, int imagen) {
            this.nombre = nombre;
            this.direccion=direccion;
            this.imagen=imagen;
        }
        public String getNombre() {
            return nombre;
        }
        public String getDireccion() {
            return direccion;
        }
        public int getImagen(){
            return imagen;
        }

    }
    public class centrosAdapter extends RecyclerView.Adapter<centrosAdapter.ViewHolder>{
        List<Centro> centros;
        public centrosAdapter(List<Centro> modelList){
            centros = modelList;
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.centros, parent, false);
            return new ViewHolder(v);
        }
        @Override
        public void onBindViewHolder(@NonNull centrosAdapter.ViewHolder holder, int position){
            holder.bind(centros.get(position));
        }
        @Override
        public int getItemCount(){
            return centros.size();
        }
        class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView nombre;
            private final TextView direccion;
            private final ImageView imagen;
            ViewHolder(View v){
                super(v);
                nombre = v.findViewById(R.id.nombretextView);
                direccion = v.findViewById(R.id.dirtextView);
                imagen = v.findViewById(R.id.imageView);
            }
            void bind(Centro centro){
                nombre.setText(centro.getNombre());
                direccion.setText(centro.getDireccion());
                imagen.setImageResource(centro.getImagen());
            }
        }
    }
}