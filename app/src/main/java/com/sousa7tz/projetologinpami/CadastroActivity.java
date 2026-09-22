package com.sousa7tz.projetologinpami;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CadastroActivity extends AppCompatActivity {

    EditText nome, email, senha;
    Button voltar, cadastrar;
    CheckBox checkBox;

    SharedPreferences preferences;
    private static final String PREF_NAME = "login";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NAME = "nome";
    private static final String KEY_SENHA = "senha";
    private static final String KEY_REMEMBER = "remember";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);

        initComponents();

        preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        cadastrar.setOnClickListener(v -> {
            if (validaCampos()) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean(KEY_REMEMBER, checkBox.isChecked());
                editor.putString(KEY_NAME, nome.getText().toString());
                editor.putString(KEY_EMAIL, email.getText().toString());
                editor.putString(KEY_SENHA, senha.getText().toString());
                editor.apply();

                Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(CadastroActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            }
        });

        voltar.setOnClickListener(v -> {
            Intent intent = new Intent(CadastroActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private boolean validaCampos() {
        boolean camposValidados = true;

        if (TextUtils.isEmpty(nome.getText())) {
            nome.setError("Informe o nome");
            camposValidados = false;
        }
        if (TextUtils.isEmpty(email.getText())) {
            email.setError("Informe o email");
            camposValidados = false;
        }
        if (TextUtils.isEmpty(senha.getText())) {
            senha.setError("Informe a senha");
            camposValidados = false;
        }

        return camposValidados;
    }

    private void initComponents() {
        nome = findViewById(R.id.cad_nome);
        email = findViewById(R.id.cad_email);
        senha = findViewById(R.id.cad_senha);
        voltar = findViewById(R.id.btn_voltar);
        cadastrar = findViewById(R.id.btn_cadastrar);
        checkBox = findViewById(R.id.cad_Box);
    }
}