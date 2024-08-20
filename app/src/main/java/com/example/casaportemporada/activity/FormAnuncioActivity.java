package com.example.casaportemporada.activity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.casaportemporada.R;
import com.example.casaportemporada.model.Produto;

public class FormAnuncioActivity extends AppCompatActivity {

    private EditText edit_titulo;
    private EditText edit_descricao;
    private EditText edit_quarto;
    private EditText edit_banheiro;
    private EditText edit_garagem;
    private CheckBox cb_status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_anuncio);

        iniciaComponetes();
        configCliques();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    private void configCliques(){
        findViewById(R.id.ib_salvar).setOnClickListener(view -> validaDados());
    }

    private void validaDados(){
String titulo = edit_titulo.getText().toString();
String descricao = edit_descricao.getText().toString();
String quartos = edit_quarto.getText().toString();
String banheiros = edit_banheiro.getText().toString();
String garagem = edit_garagem.getText().toString();


if(!titulo.isEmpty()){
    if(!descricao.isEmpty()){
        if(!quartos.isEmpty()){
            if(!banheiros.isEmpty()){
                if(!garagem.isEmpty()){

                    Produto produto = new Produto();
                    produto.setTitulo(titulo);
                    produto.setDescricao(descricao);
                    produto.setQuarto(quartos);
                    produto.setBanheiro(banheiros);
                    produto.setGaragem(garagem);
                    produto.setStatus(cb_status.isChecked());


                }else {
                    edit_garagem.requestFocus();
                    edit_garagem.setError("Informação obrigatoria.");
                }
            }else {
                edit_banheiro.requestFocus();
                edit_banheiro.setError("Informação obrigatoria.");
            }

        }else {
            edit_quarto.requestFocus();
            edit_quarto.setError("Informação obrigatoria.");
        }
    }else {
        edit_descricao.requestFocus();
        edit_descricao.setError("Informe uma Descrição.");
    }


}else {
    edit_titulo.requestFocus();
    edit_titulo.setError("Informe seu Titulo.");


}

}

private void iniciaComponetes(){
TextView text_titulo= findViewById(R.id.text_titulo);
text_titulo.setText("Form anuncio");


        edit_titulo = findViewById(R.id.edit_titulo);
        edit_descricao = findViewById(R.id.edit_descricao);
        edit_quarto = findViewById(R.id.edit_quarto);
        edit_banheiro = findViewById(R.id.edit_banheiro);
        edit_garagem = findViewById(R.id.edit_garagem);
        cb_status = findViewById(R.id.cb_status);

}

}