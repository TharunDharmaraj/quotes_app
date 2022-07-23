package com.example.quotes_app.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quotes_app.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * @author tharu
 */
public class SignUp extends AppCompatActivity {
    TextInputLayout phoneNumber, username, password;
    private FirebaseAuth mAuth;
    private String phone,passwod,user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        Button loginButton = findViewById(R.id.login_button);
        Button signUpButton = findViewById(R.id.signup_button);
        phoneNumber = findViewById(R.id.phone_number_field);
        password = findViewById(R.id.password_login_field);
        username = findViewById(R.id.username_signup_field);
        loginButton.setOnClickListener(v -> startActivity(
                new Intent(SignUp.this, Login.class)));
        Intent i = new Intent(SignUp.this, SignupVerification.class);

        signUpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(username.getEditText().getText())) {
                    Toast.makeText(SignUp.this,
                            "Please enter username.", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(password.getEditText().getText())) {
                    Toast.makeText(SignUp.this,
                            "Please enter password", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(phoneNumber.getEditText().getText())) {
                    Toast.makeText(SignUp.this,
                            "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
                }
                if (!TextUtils.isEmpty(phoneNumber.getEditText().getText()) && !TextUtils.isEmpty(password.getEditText().getText()) && !TextUtils.isEmpty(username.getEditText().getText())) {
                    phone = "+91" + phoneNumber.getEditText().getText().toString();
                    user = username.getEditText().getText().toString();
                    passwod = password.getEditText().getText().toString();
                    i.putExtra("username_key",user);
                    i.putExtra("password_key",passwod);
                    i.putExtra("phone_key", phone);
                    Log.d("details", user+phone+passwod);
                    startActivity(i);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            currentUser.reload();
        }
    }


}