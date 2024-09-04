package com.example.casaportemporada.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.casaportemporada.R;
import com.example.casaportemporada.adapter.AdapterAnuncios;
import com.example.casaportemporada.helper.FirebaseHelper;
import com.example.casaportemporada.model.Anuncio;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MeusAnunciosActicity extends AppCompatActivity implements AdapterAnuncios.OnClick {

    private List<Anuncio> anuncioList = new ArrayList<>();

    private ProgressBar progressBar;
    private TextView text_info;
    private RecyclerView rv_anuncios;
    private AdapterAnuncios adapterAnuncios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_meus_anuncios_acticity);

        iniciaComponetes();
        configRv();
        configCliques();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        recuperaAnuncios();
    }

    private void configCliques(){
        findViewById(R.id.ib_add).setOnClickListener(view ->
                startActivity(new Intent(this, FormAnuncioActivity.class)));
    }

    private void configRv(){
        rv_anuncios.setLayoutManager(new LinearLayoutManager(this));
        rv_anuncios.setHasFixedSize(true);
        adapterAnuncios = new AdapterAnuncios(anuncioList, this);

        rv_anuncios.setAdapter(adapterAnuncios);
    }

    private void recuperaAnuncios(){
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("anuncios")
                .child(FirebaseHelper.getIdFirebase());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                     anuncioList.clear();
                     for (DataSnapshot snap : snapshot.getChildren()){
                        Anuncio anuncio = snap.getValue(Anuncio.class);
                        anuncioList.add(anuncio);
                     }
                     text_info.setText("");
                }else {
                    text_info.setText("Nenhum anuncio cadastrado.");
                }

                progressBar.setVisibility(View.GONE);
                Collections.reverse(anuncioList);
                adapterAnuncios.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void iniciaComponetes(){
        TextView text_titulo = findViewById(R.id.text_titulo);
        text_titulo.setText("Meus Anúncios");

    progressBar = findViewById(R.id.progressBar);
    text_info = findViewById(R.id.text_info);
    rv_anuncios = findViewById(R.id.rv_anuncios);

    }

    @Override
    public void OnClickListener(Anuncio anuncio) {
        Intent intent = new Intent(this, FormAnuncioActivity.class);
        intent.putExtra("anuncio", anuncio);
        startActivity(intent);
    }
}