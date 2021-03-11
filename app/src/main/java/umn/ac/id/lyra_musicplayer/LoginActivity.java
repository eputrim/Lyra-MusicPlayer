package umn.ac.id.lyra_musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    ImageView back;
    EditText username, password;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        back = findViewById(R.id.back);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String susername = username.getText().toString();
                final String spassword = password.getText().toString();

                if(susername.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Username is required!", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (spassword.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Password is required!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (susername.equals("uasmobile") && spassword.equals("uasmobilegenap")) {
                            Intent ilogin = new Intent(LoginActivity.this, SongActivity.class);
                            startActivity(ilogin);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Wrong Password or Username!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}