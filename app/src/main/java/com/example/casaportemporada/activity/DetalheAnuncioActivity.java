package com.example.casaportemporada.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.casaportemporada.R;
import com.example.casaportemporada.model.Anuncio;
import com.squareup.picasso.Picasso;

public class DetalheAnuncioActivity extends AppCompatActivity {

    private ImageView img_anuncio;
    private TextView text_titulo_anuncio;
    private TextView text_descricao;
    private EditText edit_quarto;
    private EditText edit_banheiro;
    private EditText edit_garagem;

    private Anuncio anuncio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalhe_anuncio);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        iniciaComponetes();

        anuncio = (Anuncio) getIntent().getSerializableExtra("anuncio");
        configDados();
    }

    private void configDados(){
       if (anuncio != null){
           Picasso.get().load(anuncio.getUrlImagem()).into(img_anuncio);
           text_titulo_anuncio.setText(anuncio.getTitulo());
           text_descricao.setText(anuncio.getDescricao());
           edit_quarto.setText(anuncio.getQuarto());
           edit_banheiro.setText(anuncio.getBanheiro());
           edit_garagem.setText(anuncio.getGaragem());
       }else {
           Toast.makeText(this, "Não foi possivel recuperar as informações.", Toast.LENGTH_SHORT).show();
       }
    }

    private void iniciaComponetes(){
        TextView text_titulo_toolbar = findViewById(R.id.text_titulo);
        text_titulo_toolbar.setText("Detalhe anuncio");

        img_anuncio = findViewById(R.id.img_anuncio);
        text_titulo_anuncio = findViewById(R.id.text_titulo_anuncio);
        text_descricao = findViewById(R.id.text_descricao);
        edit_quarto = findViewById(R.id.edit_quarto);
        edit_banheiro= findViewById(R.id.edit_banheiro);
        edit_garagem = findViewById(R.id.edit_garagem);
    }

}