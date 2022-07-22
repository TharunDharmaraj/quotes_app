package com.example.quotes_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Homepage extends AppCompatActivity {

    private String mailId, username, password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        TextView answerText = (TextView) findViewById(R.id.extra2);
        Button signOut = (Button) findViewById(R.id.signout_button);
        Intent i = getIntent();
        mailId = i.getStringExtra("mail_key").toString();
        username = i.getStringExtra("username_key").toString();
        password = i.getStringExtra("password_key").toString();
        answerText.setText(mailId + "\n" + username + "\n" + password);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.signout_button) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(Homepage.this, Login.class));
                    finish();
                }
            }
        });
    }
}