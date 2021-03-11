package umn.ac.id.lyra_musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.karumi.dexter.Dexter;

public class MainActivity extends AppCompatActivity {

    Button login, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.login);
        profile = findViewById(R.id.profile);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ilogin = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(ilogin);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iprofile = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(iprofile);
            }
        });
    }

}