package com.example.skripsigre;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button button1, button2;

    SessionManager sessionManager;
    TextView etUsername, etName;
    String username, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // now
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = (Button) findViewById(R.id.btninfo);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(i);
            }
        });

        // new

        Button button2 = (Button) findViewById(R.id.btnmaps);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RuteActivity.class);
                startActivity(i);
            }
        });

        sessionManager = new SessionManager(MainActivity.this);
        if (!sessionManager.isloggedin()) {
            moveToLogin();
        }

        etUsername = findViewById(R.id.etMainUsername);
        etName = findViewById(R.id.etMainName);

        username = sessionManager.getUserDetail().get(SessionManager.USERNAME);
        name = sessionManager.getUserDetail().get(SessionManager.NAME);

        etUsername.setText(username);
        etName.setText(name);

    }

    private void moveToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);

        // move to info

        // end move

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ActionLogout:
                sessionManager.logoutSession();
                moveToLogin();
        }
        return super.onOptionsItemSelected(item);
    }
}