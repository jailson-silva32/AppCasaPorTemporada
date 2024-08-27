package com.example.casaportemporada.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.casaportemporada.R;
import com.example.casaportemporada.activity.autenticacao.LoginActivity;
import com.example.casaportemporada.helper.FirebaseHelper;

public class MainActivity extends AppCompatActivity {

    private ImageButton ib_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        iniciaComponente();
        configCliques();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void configCliques(){
        ib_menu.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(this, ib_menu);
            popupMenu.getMenuInflater().inflate(R.menu.menu_home, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(menuItem -> {
                if (menuItem.getItemId() == R.id.menu_filtrar){
                startActivity(new Intent(this, FiltrarAnuncioActivity.class));
                } else if (menuItem.getItemId() == R.id.menu_meus_anuncios) {
                    if (FirebaseHelper.getAutenticado()){
                        startActivity(new Intent(this, MeusAnunciosActicity.class));
                    }else {
                        showDialogLogin();
                    }

                }else {
                    if (FirebaseHelper.getAutenticado()){
                        startActivity(new Intent(this, MinhaContaActivity.class));
                    }else {
                        showDialogLogin();
                    }
                }
                return true;
            });
            popupMenu.show();
        });
    }

    private void showDialogLogin(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Autenticação");
        builder.setMessage("Você não esta autenticado no app, deseja fazer isso agora?");
        builder.setCancelable(false);
        builder.setNegativeButton("Não", (dialog, which) -> dialog.dismiss());
        builder.setPositiveButton("Sim", (dialog, which) -> {
            startActivity(new Intent(this, LoginActivity.class));
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void iniciaComponente(){
        ib_menu = findViewById(R.id.ib_menu);
    }
}