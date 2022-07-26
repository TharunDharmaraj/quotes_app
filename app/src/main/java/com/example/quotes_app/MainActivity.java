package com.example.quotes_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.quotes_app.Authentication.Login;

/**
 * @author tharu
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button mainButton = (Button) findViewById(R.id.main_button);
        SharedPreferences mPrefs = getSharedPreferences("THEME", 0);
        boolean themeBoolean = mPrefs.getBoolean("theme_boolean", true);
        if (themeBoolean) {
            // Set theme to white
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            // Set theme to black
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        }
        mainButton.setOnClickListener(v -> startActivity(new Intent(
                MainActivity.this, Login.class)));
    }

}