package com.example.contador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class nosotros extends ListActivity implements AdapterView.OnItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nosotros);
        List<Colaborador> colaboradores = Arrays.asList(
                new Colaborador("Antonio Álvarez Fueyo", "CEO", R.drawable.antonio),
                new Colaborador("Pedro Álvarez Naves", "Desarrollador", R.drawable.pedro),
                new Colaborador("Raúl Fernández Pin", "Programador", R.drawable.raul)
        );
        ColaboradoresAdapter adapter = new ColaboradoresAdapter(this, R.layout.item, colaboradores);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
        Colaborador colaborador = (Colaborador) adapterView.getItemAtPosition(i);
        Toast.makeText(this, colaborador.getNombre(), Toast.LENGTH_LONG).show();
    }
    public class Colaborador {
        private final String nombre;
        private final String cargo;
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
                convertView= LayoutInflater.from(this.getContext()).inflate(R.layout.item,parent,false);
            ((ImageView) convertView.findViewById(R.id.imageView)).setImageResource(col.getImagen());
            ((TextView) convertView.findViewById(R.id.nombreTextView)).setText(col.getNombre());
            ((TextView) convertView.findViewById(R.id.cargoTextView)).setText(col.getCargo());
            return convertView;
        }
    }
}