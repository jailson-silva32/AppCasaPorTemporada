package com.example.casaportemporada.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.casaportemporada.R;
import com.example.casaportemporada.helper.FirebaseHelper;
import com.example.casaportemporada.model.Anuncio;
import com.example.casaportemporada.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DetalheAnuncioActivity extends AppCompatActivity {

    private ImageView img_anuncio;
    private TextView text_titulo_anuncio;
    private TextView text_descricao;
    private EditText edit_quarto;
    private EditText edit_banheiro;
    private EditText edit_garagem;

    private Anuncio anuncio;
    private Usuario usuario;

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
        recuperaAnunciante();
        configCliques();
    }
    private void configCliques(){
        findViewById(R.id.ib_voltar).setOnClickListener(view -> finish());
    }

    public void ligar(View view){
        if (usuario != null){
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + usuario.getTelefone()));
            startActivity(intent);
        }else {
            Toast.makeText(this, "Carregando informações, aguarde...", Toast.LENGTH_SHORT).show();
        }
    }

    private void recuperaAnunciante(){
        String idUsuario = anuncio.getIdUsuario();
        Log.d("DEBUG", "ID do Usuario: " + idUsuario);

        if (idUsuario != null) {
            DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(Anuncio.getIdUsuario());
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    usuario = snapshot.getValue(Usuario.class);
                    configDados();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        } else {
            Log.e("Error", "ID do usuario e nulo!");
        }

    }

    private void configDados(){
       if (anuncio != null){
           Log.d("DEBUG", "URL da imagem: " + anuncio.getUrlImagem());

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