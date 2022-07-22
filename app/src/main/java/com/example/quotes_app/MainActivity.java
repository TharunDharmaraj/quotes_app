package com.example.quotes_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author tharu
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button mainButton = (Button) findViewById(R.id.main_button);
        mainButton.setOnClickListener(v -> startActivity(new Intent(
                MainActivity.this, SignUp.class)));
    }
}