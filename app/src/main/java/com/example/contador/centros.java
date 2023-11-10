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
import android.widget.Toast;

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

        RecyclerView rw = findViewById(R.id.recyclerView2);
        rw.setHasFixedSize(true);
        rw.setLayoutManager(new LinearLayoutManager(this));

        List<Centro> c = Arrays.asList(
                new Centro("Of. Madrid", "Pso. Castellana, 2. Madrid", R.drawable.madrid, "09:00 - 19:00"),
                new Centro("Of. Asturias", "Calle Uria, 23. Oviedo", R.drawable.oviedo, "10:00 - 18:00"),
                new Centro("Of. Galicia", "Pso Riazor, 102. Coruña", R.drawable.coruna, "09:30 - 20:00")
        );


        centrosAdapter adapter = new centrosAdapter(c, new OnCentroClickListener() {
            @Override
            public void onCentroClick(Centro centro) {
                // Inflar el layout personalizado
                View layout = LayoutInflater.from(centros.this).inflate(R.layout.custom_toast, null);
                TextView text = layout.findViewById(R.id.custom_toast_text);
                text.setText("Horario: " + centro.getHorario());

                // Crear y mostrar el Toast personalizado
                Toast toast = new Toast(getApplicationContext());
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }
        });



        rw.setAdapter(adapter);
    }

    private void atras() {
        Intent intent = new Intent(this, Info.class);
        startActivity(intent);
    }

    public class Centro {
        private final String nombre;
        private final String direccion;
        private final int imagen;
        private final String horario;

        public Centro(String nombre, String direccion, int imagen, String horario) {
            this.nombre = nombre;
            this.direccion = direccion;
            this.imagen = imagen;
            this.horario=horario;
        }

        public String getNombre() {
            return nombre;
        }

        public String getDireccion() {
            return direccion;
        }

        public int getImagen() {
            return imagen;
        }
        public String getHorario(){
            return horario;
        }
    }

    public class centrosAdapter extends RecyclerView.Adapter<centrosAdapter.ViewHolder> {
        private List<Centro> centros;
        private OnCentroClickListener listener;

        public centrosAdapter(List<Centro> centros, OnCentroClickListener listener) {
            this.centros = centros;
            this.listener = listener;
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

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView nombre;
            TextView direccion;
            ImageView imagen;

            ViewHolder(View itemView) {
                super(itemView);
                nombre = itemView.findViewById(R.id.nombretextView);
                direccion = itemView.findViewById(R.id.dirtextView);
                imagen = itemView.findViewById(R.id.imageView);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onCentroClick(centros.get(position));
                        }
                    }
                });
            }

            void bind(Centro centro) {
                nombre.setText(centro.getNombre());
                direccion.setText(centro.getDireccion());
                imagen.setImageResource(centro.getImagen());
            }
        }
    }
}
