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

public class MainActivity extends AppCompatActivity {

    EditText email, senha;
    Button entrar, novo;
    CheckBox checkBox;

    SharedPreferences preferences;
    private static final String PREF_NAME = "login";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_SENHA = "senha";
    private static final String KEY_REMEMBER = "remember";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        initComponents();

        preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        // Verifica se a opção "lembrar" foi ativada anteriormente
        boolean remember = preferences.getBoolean(KEY_REMEMBER, false);
        String emailSaved = preferences.getString(KEY_EMAIL, "");
        String senhaSaved = preferences.getString(KEY_SENHA, "");

        if (remember && !emailSaved.isEmpty() && !senhaSaved.isEmpty()) {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

        entrar.setOnClickListener(v -> {
            if (validaCampos()) {
                String inputEmail = email.getText().toString();
                String inputSenha = senha.getText().toString();

                String storedEmail = preferences.getString(KEY_EMAIL, "");
                String storedSenha = preferences.getString(KEY_SENHA, "");

                // Validação das credenciais introduzidas com o que está guardado
                if (inputEmail.equals(storedEmail) && inputSenha.equals(storedSenha)) {
                    SharedPreferences.Editor editor = preferences.edit();

                    if (checkBox.isChecked()) {
                        editor.putBoolean(KEY_REMEMBER, true);
                    } else {
                        editor.putBoolean(KEY_REMEMBER, false);
                    }
                    editor.apply();

                    Toast.makeText(this, "Login realizado com sucesso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Email ou senha incorretos", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            }
        });

        novo.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private boolean validaCampos() {
        boolean camposValidados = true;

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
        email = findViewById(R.id.edt_email);
        senha = findViewById(R.id.edt_senha);
        entrar = findViewById(R.id.btn_entrar);
        novo = findViewById(R.id.btn_novo);
        checkBox = findViewById(R.id.edt_Box);
    }
}